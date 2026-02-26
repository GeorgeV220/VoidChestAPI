package com.georgev22.voidchest.api.menu;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.animation.engine.AnimationInstance;
import com.georgev22.voidchest.api.datastructures.maps.HashObjectMap;
import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.datastructures.maps.UnmodifiableObjectMap;
import com.georgev22.voidchest.api.menu.actions.Action;
import com.georgev22.voidchest.api.menu.filter.ItemsFilter;
import com.georgev22.voidchest.api.menu.item.items.MenuFrameItem;
import com.georgev22.voidchest.api.menu.item.items.MenuItem;
import com.georgev22.voidchest.api.menu.item.items.StatefulMenuItem;
import com.georgev22.voidchest.api.menu.viewer.ViewerContext;
import com.georgev22.voidchest.api.utilities.CustomData;
import com.georgev22.voidchest.api.utilities.MessageBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Represents a menu which can be opened by a player.
 * It contains items, reserved slots, and a title. The menu is paginated to allow for a
 * large number of items to be displayed across multiple pages.
 */
public class Menu {

    private final VoidChestAPI voidChestAPI = VoidChestAPI.getInstance();
    private final List<MenuItem> items = new ArrayList<>();
    private final List<ViewerContext> viewers = new ArrayList<>();
    private final List<Integer> reservedSlots;
    private final ObjectMap<Integer, MenuItem> reservedItems = new HashObjectMap<>();
    private final int rows;
    private final String title;
    private boolean isAnimated;
    private @Nullable Action defaultAction;
    private @Nullable Action bottomInventoryAction;
    private final CustomData customData = new CustomData();
    private ObjectMap<String, String> staticPlaceholders = new HashObjectMap<>();
    private Supplier<ObjectMap<String, String>> dynamicPlaceholderSupplier;
    private final ObjectMap<UUID, Function<ViewerContext, ObjectMap<String, String>>> perViewerPlaceholderFunctions = new HashObjectMap<>();
    private final List<MenuItem> pendingItems = new ArrayList<>();
    private final ObjectMap<UUID, ItemsFilter> perViewerFilters = new HashObjectMap<>();

    /**
     * Constructs a Menu with a specified title and number of rows.
     *
     * @param title The title of the menu.
     * @param rows  The number of rows for the menu.
     */
    public Menu(String title, int rows) {
        this(title, rows, new ArrayList<>());
    }

    /**
     * Constructs a Menu with a specified title, number of rows, and reserved slots.
     *
     * @param title         The title of the menu.
     * @param rows          The number of rows for the menu.
     * @param reservedSlots The list of reserved slots in the menu.
     */
    public Menu(String title, int rows, List<Integer> reservedSlots) {
        this.rows = rows;
        this.title = title;
        this.reservedSlots = new ArrayList<>(reservedSlots);
    }

    /**
     * Opens the menu for the specified player.
     *
     * @param player The player to open the menu for.
     */
    public void open(Player player) {
        this.open(player, 0);
    }

    /**
     * Opens the menu for the specified player on a specific page.
     *
     * @param player The player to open the menu for.
     * @param page   The page number to open the menu on.
     */
    public void open(Player player, int page) {
        this.open(player, page, null);
    }

    /**
     * Opens the menu for the specified player on a specific page with dynamic placeholders.
     *
     * @param player                     The player to open the menu for.
     * @param page                       The page number to open the menu on.
     * @param dynamicPlaceholderSupplier The supplier of dynamic placeholders to use for the menu.
     */
    public void open(Player player, int page, Supplier<ObjectMap<String, String>> dynamicPlaceholderSupplier) {
        ViewerContext context = new ViewerContext(this, player, page, dynamicPlaceholderSupplier);
        viewers.add(context);
        update(context);
        player.openInventory(context.getInventory());
    }

    /**
     * Updates the menu for all viewers, refreshing their inventories.
     */
    public void updateAll() {
        viewers.forEach(this::update);
    }

    /**
     * Updates the menu for a specific page, refreshing the inventories of all viewers.
     *
     * @param page The page number to update.
     */
    public void update(int page) {
        viewers.forEach(vc -> {
            if (vc.getPage() == page) {
                update(vc);
            }
        });
    }

    public void update(@NonNull ViewerContext viewerContext) {
        if (viewerContext.isUpdating() || viewerContext.isInvalidated()) return;
        if (Bukkit.isStopping()) return;

        viewerContext.setUpdating(true);
        Inventory inventory = viewerContext.getInventory();

        long version = viewerContext.beginUpdate();
        AtomicInteger pendingTasks = new AtomicInteger(0);

        List<MenuItem> pageItems = getPageItems(viewerContext, viewerContext.getPage());
        int itemIndex = 0;
        int slotCount = rows * 9;

        // Clear non-reserved slots (sync)
        for (int slot = 0; slot < slotCount; slot++) {
            if (!reservedSlots.contains(slot)) {
                inventory.setItem(slot, null);
            }
        }

        for (int slot = 0; slot < slotCount; slot++) {
            if (viewerContext.isInvalidated()) break;

            MenuItem item = null;

            if (reservedSlots.contains(slot)) {
                item = reservedItems.get(slot);
            } else if (itemIndex < pageItems.size()) {
                item = pageItems.get(itemIndex++);
            }

            if (item != null) {
                scheduleSlotRender(viewerContext, inventory, pendingTasks, slot, item, version);
            }
        }

        // No async work finish immediately
        // probably check for edge cases
        if (pendingTasks.get() == 0) {
            finishUpdate(viewerContext, version);
        }
    }

    private void scheduleSlotRender(
            @NonNull ViewerContext viewerContext,
            @NonNull Inventory inventory,
            @NonNull AtomicInteger pendingTasks,
            int slot,
            @NonNull MenuItem menuItem,
            long version
    ) {
        pendingTasks.incrementAndGet();

        CompletableFuture
                .supplyAsync(() -> buildMenuItem(viewerContext, menuItem, 0), this.voidChestAPI.timedTaskManager().getScheduler())
                .handle((item, error) -> {
                    if (error != null) {
                        voidChestAPI.plugin().getLogger()
                                .log(Level.WARNING, "Failed to render menu item: " + item, error);
                        return null;
                    }
                    return item;
                })
                .thenAccept(item -> this.voidChestAPI
                        .minecraftScheduler()
                        .createTaskForEntity(() -> {
                            if (!viewerContext.isUpdateValid(version)) {
                                if (pendingTasks.decrementAndGet() == 0) {
                                    finishUpdate(viewerContext, version);
                                }
                                return;
                            }

                            if (item != null) {
                                menuItem.setSlot(slot);
                                menuItem.addViewerContext(viewerContext);
                                inventory.setItem(slot, item);
                            }

                            if (pendingTasks.decrementAndGet() == 0) {
                                finishUpdate(viewerContext, version);
                            }

                        }, viewerContext::invalidate, viewerContext.getPlayerContext()));
    }

    private void finishUpdate(@NonNull ViewerContext viewerContext, long version) {
        if (!viewerContext.isUpdateValid(version)) return;

        viewerContext.notifyContext();
        viewerContext.setUpdating(false);
        processPendingItems(viewerContext);
    }

    /**
     * Gets the number of items that can fit on a single page, excluding reserved slots.
     *
     * @return The number of items that can fit on a page.
     */
    public int getItemsPerPage() {
        return (rows * 9) - reservedSlots.size();
    }

    /**
     * Gets the items that should be displayed on the specified page.
     *
     * @param viewerContext The viewer context to use for filtering items.
     * @param page          The page number to retrieve items for.
     * @return A list of MenuItems for the specified page.
     */
    @UnmodifiableView
    public List<MenuItem> getPageItems(@Nullable ViewerContext viewerContext, int page) {
        List<MenuItem> sourceItems = new ArrayList<>(items);

        if (viewerContext != null) {
            UUID id = viewerContext.getPlayerContext().getUniqueId();
            ItemsFilter filter = perViewerFilters.get(id);
            if (filter != null) {
                sourceItems = filter.apply(viewerContext, sourceItems);
            }
        }

        int start = page * getItemsPerPage();
        if (start >= sourceItems.size()) {
            return List.of();
        }
        int end = Math.min(sourceItems.size(), start + getItemsPerPage());
        return List.copyOf(sourceItems.subList(start, end));
    }

    /**
     * Gets a read-only view of the reserved items in the menu.
     *
     * @return A read-only view of the reserved items in the menu.
     */
    public UnmodifiableObjectMap<Integer, MenuItem> getReservedItems() {
        return new UnmodifiableObjectMap<>(reservedItems);
    }

    /**
     * Gets a read-only view of the items in the menu.
     *
     * @return A read-only view of the items in the menu.
     */
    @UnmodifiableView
    public Collection<MenuItem> getItems() {
        return Collections.unmodifiableCollection(items);
    }

    /**
     * Sets the items filter for a specific viewer. Replaces any
     * previously configured filter chain for that viewer.
     *
     * @param playerId The unique ID of the viewer.
     * @param filter   The filter to apply, or {@code null} to clear.
     */
    public void setViewerItemsFilter(UUID playerId, ItemsFilter filter) {
        if (filter == null) {
            perViewerFilters.remove(playerId);
        } else {
            perViewerFilters.put(playerId, filter);
        }
    }

    /**
     * Adds a filter for a specific viewer. If the viewer already has
     * a filter, the new filter is chained after the existing one.
     *
     * @param playerId The unique ID of the viewer.
     * @param filter   The filter to add.
     */
    public void addViewerItemsFilter(UUID playerId, ItemsFilter filter) {
        perViewerFilters.merge(playerId, filter, ItemsFilter::chain);
    }

    /**
     * Gets the current filter chain for a specific viewer.
     *
     * @param playerId The unique ID of the viewer.
     * @return The filter chain for that viewer, or {@code null} if none exists.
     */
    public ItemsFilter getViewerItemsFilter(UUID playerId) {
        return perViewerFilters.get(playerId);
    }

    /**
     * Gets the menu item at the specified slot and page.
     *
     * @param viewerContext The viewer context to use for filtering items.
     * @param slot          The slot number to retrieve the menu item for.
     * @param page          The page number to retrieve the menu item for.
     * @return The menu item at the specified slot and page, or null if not found.
     */
    public Optional<MenuItem> getMenuItem(@Nullable ViewerContext viewerContext, int slot, int page) {
        return getPageItems(viewerContext, page).stream().filter(item -> item.getSlot() == slot).findFirst();
    }

    /**
     * Gets the reserved menu item at the specified slot.
     *
     * @param slot The slot number to retrieve the reserved menu item for.
     * @return The reserved menu item at the specified slot, or null if not found.
     */
    public Optional<MenuItem> getReservedMenuItem(int slot) {
        return Optional.ofNullable(reservedItems.get(slot));
    }

    /**
     * Checks if there is a next page available.
     *
     * @param page The current page number.
     * @return True if there is a next page; otherwise, false.
     */
    public boolean hasNextPage(int page) {
        return items.size() > (page + 1) * getItemsPerPage();
    }

    /**
     * Checks if there is a previous page available.
     *
     * @param page The current page number.
     * @return True if there is a previous page; otherwise, false.
     */
    public boolean hasPreviousPage(int page) {
        return page > 0;
    }

    /**
     * Gets the total number of pages in the menu.
     * <p>
     * The page count is calculated based on the number of items currently in the menu
     * and how many items can fit per page (excluding reserved slots).
     *
     * @return the total number of pages, always at least {@code 1} even if there are no items
     */
    public int getPageCount() {
        int itemsPerPage = getItemsPerPage();
        if (itemsPerPage <= 0) return 1;
        return Math.max(1, (int) Math.ceil((double) items.size() / itemsPerPage));
    }

    /**
     * Gets the maximum page index in the menu.
     * <p>
     * Page indices are zero-based, so the maximum page index is equal to
     * {@code getPageCount() - 1}. If there are no items, this will return {@code 0}.
     *
     * @return the maximum valid page index, never negative
     */
    public int getMaxPage() {
        return Math.max(0, getPageCount() - 1);
    }

    /**
     * Gets the number of rows in the menu.
     *
     * @return The number of rows in the menu.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the title of the menu.
     *
     * @return The title of the menu.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Adds an item to the menu.
     *
     * @param menuItem The item to add to the menu.
     */
    public void addItem(MenuItem menuItem) {
        this.items.add(menuItem);
    }

    /**
     * Adds a menu item to be added safely after the current update.
     * <p>
     * The item will be added to the end of the menu. For each viewer, if they are not
     * currently updating, the pending items will be processed and the viewer's inventory
     * will be updated if the new items are visible on their current page.
     *
     * @param menuItem the menu item to add safely
     */
    public void addItemLater(@NonNull MenuItem menuItem) {
        //noinspection ConstantValue
        if (menuItem == null) {
            this.voidChestAPI.plugin().getLogger().log(Level.WARNING, "Tried to add null item to menu " + this.getTitle());
            return;
        }
        pendingItems.add(menuItem.clone());

        viewers.forEach(vc -> {
            if (!vc.isUpdating()) {
                processPendingItems(vc);
            }
        });
    }

    /**
     * Processes all pending items for a specific viewer by adding them to the menu and
     * updating the viewer's inventory if needed.
     * <p>
     * This method checks if there are any pending items and if the viewer is currently
     * updating. It will only process the items if the viewer is not updating, preventing
     * concurrent modification issues. Items are added to the global menu item list,
     * and the viewer is updated if any of the added items are visible on their current page.
     *
     * @param viewerContext the viewer context for which to process pending items
     */
    private void processPendingItems(@NonNull ViewerContext viewerContext) {
        if (pendingItems.isEmpty() || viewerContext.isUpdating()) return;

        viewerContext.setUpdating(true);

        List<MenuItem> addedItems = new ArrayList<>(pendingItems);
        pendingItems.clear();
        items.addAll(addedItems);

        int page = viewerContext.getPage();
        int start = page * getItemsPerPage();
        int end = Math.min(items.size(), start + getItemsPerPage());

        boolean needUpdate = addedItems.stream()
                .anyMatch(item -> items.subList(start, end).contains(item));

        if (needUpdate) {
            update(viewerContext);
        }

        viewerContext.setUpdating(false);
    }

    /**
     * Removes an item from the menu.
     *
     * @param menuItem The item to remove from the menu.
     */
    public void removeItem(MenuItem menuItem) {
        this.items.remove(menuItem);
    }

    /**
     * Sets a reserved item in a specific slot.
     *
     * @param slot     The slot number to reserve.
     * @param menuItem The item to set in the reserved slot.
     */
    public void setReservedItemSlot(int slot, MenuItem menuItem) {
        if (!reservedSlots.contains(slot)) {
            reservedSlots.add(slot);
        }
        reservedItems.put(slot, menuItem);
    }

    /**
     * Returns a list of ViewerContext objects representing the current viewers.
     * The returned list is unmodifiable to ensure encapsulation.
     *
     * @return an unmodifiable list of ViewerContext instances, never null
     */
    public List<ViewerContext> getViewers() {
        return Collections.unmodifiableList(viewers);
    }

    /**
     * Removes the specified viewer context from the collection of viewers.
     * If the viewer context is not present in the collection, no action is taken.
     *
     * @param viewerContext the viewer context to be removed
     */
    public void removeViewer(ViewerContext viewerContext) {
        viewers.remove(viewerContext);
        items.forEach(item -> item.removeViewerContext(viewerContext));
        reservedItems.forEach((slot, menuItem) -> menuItem.removeViewerContext(viewerContext));
    }

    /**
     * Removes the viewer with the specified player context from the collection of viewers.
     *
     * @param player the player context of the viewer to be removed
     */
    public void removeViewer(Player player) {
        new ArrayList<>(viewers).stream().filter(vc -> vc.getPlayerContext().getUniqueId().equals(player.getUniqueId()))
                .forEach(vc -> {
                    removeViewer(vc);
                    vc.getPlayerContext().closeInventory();
                });
    }

    /**
     * Clears the collection of viewers and closes their inventories.
     */
    public void clearViewers() {
        new ArrayList<>(viewers).forEach(vc -> {
            removeViewer(vc);
            vc.getPlayerContext().closeInventory();
        });
    }

    /**
     * Sets whether the menu is animated or not.
     *
     * @param animated true if the menu should be animated, false otherwise
     */
    public void setAnimated(boolean animated) {
        isAnimated = animated;
    }

    /**
     * Checks if the menu is animated.
     *
     * @return true if the menu is animated, false otherwise
     */
    public boolean isAnimated() {
        return isAnimated;
    }

    /**
     * Replaces an existing menu item with a new one.
     *
     * @param oldItem the existing menu item to be replaced
     * @param newItem the new menu item to replace the old one
     */
    public void replaceMenuItem(MenuItem oldItem, MenuItem newItem) {
        if (items.contains(oldItem)) {
            int index = items.indexOf(oldItem);
            items.set(index, newItem);
        }
        if (reservedItems.containsKey(oldItem.getSlot())) {
            reservedItems.replace(oldItem.getSlot(), newItem);
        }
    }

    /**
     * Checks if the menu is updating.
     *
     * @return true if the menu is updating, false otherwise
     */
    public boolean isUpdating(@NonNull ViewerContext viewerContext) {
        return viewerContext.isUpdating();
    }

    /**
     * Sets the default action for the menu.
     *
     * @param defaultAction the default action to be set
     */
    public void setDefaultAction(@Nullable Action defaultAction) {
        this.defaultAction = defaultAction;
    }

    /**
     * Gets the default action for the menu.
     *
     * @return the default action for the menu
     */
    public @Nullable Action getDefaultAction() {
        return defaultAction;
    }

    /**
     * Sets the bottom inventory action for the menu.
     *
     * @param bottomInventoryAction the bottom inventory action to be set
     */
    public void setBottomInventoryAction(@Nullable Action bottomInventoryAction) {
        this.bottomInventoryAction = bottomInventoryAction;
    }

    /**
     * Gets the bottom inventory action for the menu.
     *
     * @return the bottom inventory action for the menu
     */
    public @Nullable Action getBottomInventoryAction() {
        return bottomInventoryAction;
    }

    /**
     * Retrieves the custom data associated with the menu.
     *
     * @return the custom data, never null.
     */
    @NonNull
    public CustomData getCustomData() {
        return customData;
    }

    /**
     * Clears all items from the menu.
     */
    public void clearItems() {
        items.clear();
    }

    /**
     * Sets the static placeholders for the menu.
     *
     * @param staticPlaceholders the static placeholders to be set
     */
    public void setStaticPlaceholders(ObjectMap<String, String> staticPlaceholders) {
        this.staticPlaceholders = staticPlaceholders;
    }

    /**
     * Sets the dynamic placeholder supplier for the menu.
     *
     * @param dynamicPlaceholderSupplier the dynamic placeholder supplier to be set
     */
    public void setDynamicPlaceholderSupplier(Supplier<ObjectMap<String, String>> dynamicPlaceholderSupplier) {
        this.dynamicPlaceholderSupplier = dynamicPlaceholderSupplier;
    }

    public void setViewerPlaceholderFunction(UUID playerId, Function<ViewerContext, ObjectMap<String, String>> function) {
        perViewerPlaceholderFunctions.put(playerId, function);
    }

    /**
     * Retrieves the placeholders associated with the menu.
     *
     * @return the placeholders, never null.
     */
    public ObjectMap<String, String> getPlaceholders() {
        return this.getPlaceholders(null);
    }

    /**
     * Retrieves the placeholders associated with the menu and viewer context.
     *
     * @param viewerContext the viewer context to retrieve dynamic placeholders for
     * @return the combined placeholders, never null.
     */
    public ObjectMap<String, String> getPlaceholders(@Nullable ViewerContext viewerContext) {
        HashObjectMap<String, String> placeholders = new HashObjectMap<>();
        placeholders.putAll(staticPlaceholders);

        if (dynamicPlaceholderSupplier != null) {
            placeholders.putAll(dynamicPlaceholderSupplier.get());
        }

        if (viewerContext != null) {
            if (viewerContext.getDynamicPlaceholderSupplier() != null) {
                placeholders.putAll(viewerContext.getDynamicPlaceholderSupplier().get());
            }
            UUID id = viewerContext.getPlayerContext().getUniqueId();
            Function<ViewerContext, ObjectMap<String, String>> perViewerFn = perViewerPlaceholderFunctions.get(id);
            if (perViewerFn != null) {
                placeholders.putAll(perViewerFn.apply(viewerContext));
            }
        }

        return placeholders;
    }

    /**
     * Compares this {@code Menu} to the specified object for equality without considering items.
     * The method checks if the other object is a {@code Menu} with the same
     * {@code isAnimated} status, title, rows, default action and bottom inventory action.
     * Items associated with the menu are not included in the comparison.
     *
     * @param obj the object to compare this {@code Menu} against
     * @return {@code true} if the given object represents a {@code Menu} equivalent to this one
     * without considering items, {@code false} otherwise
     */
    public boolean equalsWithoutItems(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Menu menu = (Menu) obj;
        return Objects.equals(isAnimated, menu.isAnimated)
                && Objects.equals(title, menu.title)
                && Objects.equals(rows, menu.rows)
                && Objects.equals(defaultAction, menu.defaultAction)
                && Objects.equals(bottomInventoryAction, menu.bottomInventoryAction);
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * This method compares the current {@code Menu} instance with the specified object for equality.
     * Returns true if the other object is also a {@code Menu} and all the following conditions are met:
     * <ul>
     * <li>both menus have the same animation state ({@code isAnimated})</li>
     * <li>identical title strings ({@code title})</li>
     * <li>equivalent row configurations ({@code rows})</li>
     * <li>matching item collections ({@code items})</li>
     * <li>equivalent reserved slot definitions ({@code reservedSlots})</li>
     * <li>identical reserved item mappings ({@code reservedItems})</li>
     * <li>the default actions are equal ({@code defaultAction})</li>
     * <li>the bottom inventory actions are equal ({@code bottomInventoryAction})</li>
     * <li>the custom data is equal ({@code getCustomData()})</li>
     * </ul>
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if the specified object is equal to this menu,
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Menu menu = (Menu) obj;
        return Objects.equals(isAnimated, menu.isAnimated)
                && Objects.equals(title, menu.title)
                && Objects.equals(rows, menu.rows)
                && Objects.equals(items, menu.items)
                && Objects.equals(reservedSlots, menu.reservedSlots)
                && Objects.equals(reservedItems, menu.reservedItems)
                && Objects.equals(defaultAction, menu.defaultAction)
                && Objects.equals(bottomInventoryAction, menu.bottomInventoryAction)
                && Objects.equals(getCustomData().getCustomData(), menu.getCustomData().getCustomData());
    }

    /**
     * Helper method to build a menu item.
     * It is not recommended to use this method directly, is not a public API.
     *
     * @param context    The viewer context.
     * @param menuItem   The menu item to build.
     * @param frameIndex The frame index.
     * @return The built menu item.
     */
    @ApiStatus.Internal
    public static @NonNull ItemStack buildMenuItem(ViewerContext context, MenuItem menuItem, int frameIndex) {
        ItemStack baseItem;
        if (menuItem instanceof StatefulMenuItem statefulMenuItem) {
            statefulMenuItem.updateState(context);
            baseItem = Objects.requireNonNullElseGet(
                    statefulMenuItem.getStateProvider(statefulMenuItem.getCurrentState()),
                    statefulMenuItem::getItemProvider
            ).get();
        } else {
            baseItem = menuItem.getItemProvider().get();
        }
        ItemStack finalItemStack = baseItem.clone();

        // Apply frame if present
        if (menuItem.hasFrames() && frameIndex >= 0) {
            List<MenuFrameItem> frames = menuItem.getFrames();
            if (frames != null && !frames.isEmpty() && frameIndex < frames.size()) {
                ItemStack frameItem = frames.get(frameIndex).getFrameItemProvider().get().clone();
                ItemMeta frameMeta = frameItem.hasItemMeta()
                        ? frameItem.getItemMeta()
                        : Bukkit.getItemFactory().getItemMeta(frameItem.getType());
                ItemMeta baseMeta = baseItem.hasItemMeta() ? baseItem.getItemMeta() : null;
                if (frameMeta != null && baseMeta != null) {
                    frameMeta.displayName(baseMeta.displayName());
                    frameMeta.lore(baseMeta.lore());
                    frameItem.setItemMeta(frameMeta);
                }
                finalItemStack = frameItem;
            }
        }

        // Placeholders
        Component oldName = finalItemStack.hasItemMeta() && finalItemStack.getItemMeta().hasDisplayName()
                ? finalItemStack.getItemMeta().displayName() : Component.empty();
        List<Component> oldLore = finalItemStack.hasItemMeta() && finalItemStack.getItemMeta().hasLore()
                ? finalItemStack.getItemMeta().lore() : new ArrayList<>();
        if (oldLore == null) {
            oldLore = new ArrayList<>();
        }

        Component newName = MessageBuilder.builder()
                .context(context.getPlayerContext())
                .placeholders(context.getMenu().getPlaceholders(context))
                .append(oldName).build();
        List<Component> newLore = oldLore.stream()
                .map(lore -> MessageBuilder.builder()
                        .context(context.getPlayerContext())
                        .placeholders(context.getMenu().getPlaceholders(context))
                        .append(lore).build())
                .collect(Collectors.toList());

        ItemMeta meta = finalItemStack.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(finalItemStack.getType());
        }
        meta.displayName(newName);
        meta.lore(newLore);
        finalItemStack.setItemMeta(meta);

        // Animation
        if (menuItem.hasAnimation() && menuItem.hasColors()) {
            meta = finalItemStack.getItemMeta();
            if (meta == null) {
                meta = Bukkit.getItemFactory().getItemMeta(finalItemStack.getType());
            }
            menuItem.initializeAnimation(newName);

            AnimationInstance<Component> instance = menuItem.getAnimationInstance();
            if (instance != null) {
                meta.displayName(instance.tickAndRender());
                finalItemStack.setItemMeta(meta);
            }
        }

        return finalItemStack;
    }
}
