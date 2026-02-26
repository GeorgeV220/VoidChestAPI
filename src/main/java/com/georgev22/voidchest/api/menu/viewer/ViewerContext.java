package com.georgev22.voidchest.api.menu.viewer;

import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.menu.Menu;
import com.georgev22.voidchest.api.menu.item.items.MenuItem;
import com.georgev22.voidchest.api.utilities.CustomData;
import com.georgev22.voidchest.api.utilities.MessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * Represents a viewer's context for a menu. This class stores the menu, the player
 * viewing the menu, and the associated inventory, as well as the current page of the menu.
 */
public class ViewerContext implements InventoryHolder {

    private final Menu menu;
    private final Player playerContext;
    private final Inventory inventory;
    private int page;
    private List<MenuItem> cachedMenuItems;
    private boolean isMenuDirty = true;
    private boolean isUpdating = false;
    private boolean isInvalidated = false;
    private final AtomicLong updateVersion = new AtomicLong();
    private Supplier<ObjectMap<String, String>> dynamicPlaceholderSupplier;
    private final CustomData customData = new CustomData();

    public ViewerContext(@NonNull Menu menu, Player playerContext) {
        this(menu, playerContext, 0);
    }

    /**
     * Constructs a ViewerContext for a given player and menu.
     *
     * @param menu          The menu being viewed.
     * @param playerContext The player viewing the menu.
     * @param page          The initial page number of the menu.
     */
    public ViewerContext(@NonNull Menu menu, Player playerContext, int page) {
        this(menu, playerContext, page, null);
    }

    /**
     * Constructs a ViewerContext for a given player and menu.
     *
     * @param menu                       The menu being viewed.
     * @param playerContext              The player viewing the menu.
     * @param page                       The initial page number of the menu.
     * @param dynamicPlaceholderSupplier The supplier for dynamic placeholders
     */
    public ViewerContext(@NonNull Menu menu, Player playerContext, int page, Supplier<ObjectMap<String, String>> dynamicPlaceholderSupplier) {
        this.menu = menu;
        this.playerContext = playerContext;
        this.page = page;
        this.dynamicPlaceholderSupplier = dynamicPlaceholderSupplier;
        this.inventory = Bukkit.createInventory(this, menu.getRows() * 9,
                MessageBuilder.builder()
                        .context(playerContext)
                        .placeholders(menu.getPlaceholders(this))
                        .appendMiniMessage(menu.getTitle())
                        .build());
    }

    public static @Nullable ViewerContext getContext(@NonNull Player player) {
        if (player.getOpenInventory().getTopInventory().getHolder() instanceof ViewerContext viewerContext) {
            return viewerContext;
        }
        return null;
    }

    public static @Nullable ViewerContext getContext(@NonNull InventoryEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof ViewerContext viewerContext) {
            return viewerContext;
        }
        return null;
    }

    /**
     * Gets the menu associated with this viewer's context.
     *
     * @return The menu being viewed.
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Gets the player associated with this viewer's context.
     *
     * @return The player viewing the menu.
     */
    public Player getPlayerContext() {
        return playerContext;
    }

    /**
     * Sets the current page of the menu being viewed.
     *
     * @param page The page number to set.
     */
    public void setPage(int page) {
        this.page = page;
        this.menu.update(this);
    }

    /**
     * Gets the current page of the menu being viewed.
     *
     * @return The current page number.
     */
    public int getPage() {
        return page;
    }

    /**
     * Gets the inventory associated with this viewer's context.
     *
     * @return The inventory representing the menu for the player.
     */
    @Override
    public @NonNull Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Gets the cached menu items for this viewer's context.
     *
     * @return An unmodifiable view of menu items.
     */
    @UnmodifiableView
    public List<MenuItem> getCachedMenuItems() {
        if (isMenuDirty || cachedMenuItems == null) {
            return rebuildMenuItems();
        }
        return List.copyOf(cachedMenuItems);
    }

    /**
     * Rebuilds the cached menu items for this viewer's context.
     */
    private @NonNull List<MenuItem> rebuildMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        items.addAll(getMenu().getReservedItems().values());
        items.addAll(getMenu().getPageItems(this, getPage()));
        this.cachedMenuItems = List.copyOf(items);
        this.isMenuDirty = false;
        return items;
    }

    /**
     * Notifies the viewer's context that the menu has been updated.
     */
    public void notifyContext() {
        isMenuDirty = true;
    }

    /**
     * Sets the dynamic placeholder supplier for this viewer context.
     * <p>
     * The supplier provides a mapping of placeholder keys to their corresponding values
     * that will be used when rendering menu items for this specific viewer.
     * This allows the menu to display personalized or context-sensitive content.
     *
     * @param supplier the supplier providing dynamic placeholders, or {@code null} to clear it
     */
    public void setDynamicPlaceholderSupplier(Supplier<ObjectMap<String, String>> supplier) {
        this.dynamicPlaceholderSupplier = supplier;
    }

    /**
     * Retrieves the dynamic placeholder supplier for this viewer context.
     * <p>
     * This supplier is used to generate placeholder values dynamically whenever
     * menu items are updated or rendered for this specific viewer.
     *
     * @return the dynamic placeholder supplier, or {@code null} if none has been set
     */
    public Supplier<ObjectMap<String, String>> getDynamicPlaceholderSupplier() {
        return dynamicPlaceholderSupplier;
    }

    /**
     * Checks if this viewer context is currently updating.
     * <p>
     * This flag is used to prevent concurrent updates to the viewer's inventory,
     * ensuring that pending items or animations do not interfere with each other.
     *
     * @return {@code true} if the viewer is currently updating, {@code false} otherwise
     */
    public boolean isUpdating() {
        return isUpdating;
    }

    /**
     * Sets the updating state for this viewer context.
     * <p>
     * When set to {@code true}, the viewer is considered to be in the middle of
     * an update, and no additional updates (such as processing pending items) should
     * be performed until it is set back to {@code false}.
     *
     * @param updating {@code true} to mark the viewer as updating, {@code false} otherwise
     */
    public void setUpdating(boolean updating) {
        this.isUpdating = updating;
    }

    /**
     * Marks this viewer context as invalidated.
     * <p>
     * After calling this method, the context is considered no longer active, and
     * ongoing updates should stop immediately. The updating flag is also reset
     * to {@code false}.
     */
    public void invalidate() {
        this.isInvalidated = true;
        this.isUpdating = false;
    }

    /**
     * Checks whether this viewer context has been invalidated.
     *
     * @return {@code true} if the context is invalidated and should no longer
     * receive updates, {@code false} otherwise.
     */
    public boolean isInvalidated() {
        return isInvalidated;
    }

    /**
     * Begins a new update cycle for this viewer context.
     * <p>
     * This method increments the internal update version and returns the new
     * version number. Use this version to ensure that asynchronous updates
     * apply only to the intended update cycle and are not overridden by
     * newer updates.
     *
     * @return the new update version for this cycle
     */
    public long beginUpdate() {
        return updateVersion.incrementAndGet();
    }

    /**
     * Checks whether a given update version is still valid for this viewer context.
     * <p>
     * This method can be used by asynchronous update tasks to determine whether
     * the context has been invalidated or a newer update cycle has started,
     * in which case the task should discard its results.
     *
     * @param version the update version to validate
     * @return {@code true} if the version matches the current update cycle
     * and the context is not invalidated, {@code false} otherwise
     */
    public boolean isUpdateValid(long version) {
        return !isInvalidated && updateVersion.get() == version;
    }

    /**
     * Retrieves the custom data associated with this viewer context.
     *
     * @return the custom data, never null.
     */
    public @NonNull CustomData getCustomData() {
        return customData;
    }
}
