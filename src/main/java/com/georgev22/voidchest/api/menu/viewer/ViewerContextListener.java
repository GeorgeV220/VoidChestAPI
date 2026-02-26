package com.georgev22.voidchest.api.menu.viewer;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.datastructures.maps.HashObjectMap;
import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.menu.Menu;
import com.georgev22.voidchest.api.menu.item.builder.ItemBuilder;
import com.georgev22.voidchest.api.menu.item.items.MenuItem;
import com.georgev22.voidchest.api.scheduler.SchedulerTask;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Listener for ViewerContext-related inventory events.
 * Handles menu animations, async updates, and click handling while
 * ensuring flicker-free updates and proper cleanup on close.
 */
public class ViewerContextListener implements Listener {

    /**
     * Stores all per-player state in a single object for clarity.
     */
    private final ObjectMap<Player, ViewerState> viewerStates = new HashObjectMap<>();

    /**
     * Encapsulates all per-player state for a menu viewer.
     */
    private static class ViewerState {
        final ObjectMap<MenuItem, Integer> frameIndexes = new HashObjectMap<>();
        int frameTick = 0;
        SchedulerTask animatedTask;
        CompletableFuture<Void> updateQueue = CompletableFuture.completedFuture(null);
        boolean updateRunning = false;
        ObjectMap<Integer, ItemStack> lastAppliedItems = new HashObjectMap<>();
        long updateVersion = 0L;
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof ViewerContext context)) return;

        Player player = context.getPlayerContext();
        ViewerState state = viewerStates.computeIfAbsent(player, p -> new ViewerState());

        state.animatedTask = VoidChestAPI.getInstance().minecraftScheduler().createRepeatingTask(
                () -> {
                    boolean updateFrames = state.frameTick >= 20;
                    enqueueMenuUpdate(context, state, updateFrames);
                },
                1L,
                1L
        );
    }

    /**
     * Enqueues a menu update for a player asynchronously, builds ItemStacks off-main-thread,
     * and applies changes on the main thread while preventing flickering.
     *
     * @param context      The viewer context.
     * @param state        The player's encapsulated state.
     * @param updateFrames Whether to update animated frames.
     */
    private void enqueueMenuUpdate(@NonNull ViewerContext context,
                                   @NonNull ViewerState state,
                                   boolean updateFrames) {
        if (context.isUpdating()) return;
        if (state.updateRunning) return;

        state.updateRunning = true;
        state.updateVersion++;
        long thisVersion = state.updateVersion;

        state.updateQueue = state.updateQueue.thenRunAsync(() -> {
            ObjectMap<MenuItem, ItemStack> updates = new HashObjectMap<>();
            boolean dirtyMenu = false;

            for (MenuItem menuItem : context.getCachedMenuItems()) {
                int slot = menuItem.getSlot();
                if (slot == -1) {
                    dirtyMenu = true;
                    break;
                }

                int frameIndex = state.frameIndexes.getOrDefault(menuItem, 0);
                ItemStack finalItemStack = Menu.buildMenuItem(context, menuItem, frameIndex);
                updates.put(menuItem, finalItemStack);

                if (menuItem.hasFrames() && updateFrames) {
                    int nextIndex = frameIndex + 1;
                    if (nextIndex > Objects.requireNonNull(menuItem.getFrames()).size()) nextIndex = 0;
                    state.frameIndexes.put(menuItem, nextIndex);
                }
            }

            boolean finalDirtyMenu = dirtyMenu;

            // Apply updates on main thread (LET'S HOPE IT WORKS)
            VoidChestAPI.getInstance().minecraftScheduler().runTask(() -> {
                // Skip if a newer update already started
                if (state.updateVersion != thisVersion) {
                    state.updateRunning = false;
                    return;
                }

                if (finalDirtyMenu) {
                    context.notifyContext();
                    state.updateRunning = false;
                    return;
                }

                // Apply only changed items (huh?)
                for (ObjectMap.Entry<MenuItem, ItemStack> entry : updates.entrySet()) {
                    MenuItem menuItem = entry.getKey();
                    ItemStack newItem = entry.getValue();
                    int slot = menuItem.getSlot();

                    ItemStack last = state.lastAppliedItems.get(slot);
                    if (!areItemStacksEqual(last, newItem)) {
                        context.getInventory().setItem(slot, newItem);
                        state.lastAppliedItems.put(slot, newItem);
                    }
                }

                // Remove slots that are no longer present (either it works or it doesn't... I don't know, care, or trust my code)
                List<Integer> slotsToRemove = new ArrayList<>();
                for (Integer slot : state.lastAppliedItems.keySet()) {
                    boolean stillPresent = updates.keySet().stream().anyMatch(i -> i.getSlot() == slot);
                    if (!stillPresent) {
                        context.getInventory().setItem(slot, null);
                        slotsToRemove.add(slot);
                    }
                }
                slotsToRemove.forEach(state.lastAppliedItems::remove);

                state.frameTick = updateFrames ? 0 : state.frameTick + 1;
                state.updateRunning = false;
            });
        });
    }

    private boolean areItemStacksEqual(@Nullable ItemStack a, @Nullable ItemStack b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.isSimilar(b);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        InventoryHolder inventoryHolder = inventory.getHolder();
        if (!(inventoryHolder instanceof ViewerContext viewerContext)) return;

        if (event.getClick().equals(ClickType.NUMBER_KEY)) {
            event.setCancelled(true);
            return;
        }

        if (viewerContext.isUpdating()) return;

        Menu menu = viewerContext.getMenu();
        int rawSlot = event.getSlot();

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && clickedInventory.equals(event.getView().getBottomInventory())) {
            if (menu.getBottomInventoryAction() != null) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null) clickedItem = Material.PAPER.asItemType().createItemStack();
                if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
                // this item does not belong to any Menu
                MenuItem bottomInventoryItem = new MenuItem(new ItemBuilder(clickedItem));
                menu.getBottomInventoryAction().execute(viewerContext, bottomInventoryItem, event);
                return;
            }
        }

        for (MenuItem item : viewerContext.getCachedMenuItems()) {
            if (item.getSlot() == rawSlot) {
                if (item.getAction() == null) {
                    if (menu.getDefaultAction() == null) return;
                    menu.getDefaultAction().execute(viewerContext, item, event);
                    break;
                }
                item.getAction().execute(viewerContext, item, event);
                break;
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory topInventory = event.getView().getTopInventory();
        InventoryHolder inventoryHolder = topInventory.getHolder();
        if (inventoryHolder instanceof ViewerContext) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof ViewerContext viewerContext)) return;
        Menu menu = viewerContext.getMenu();
        menu.removeViewer(viewerContext);

        Player player = viewerContext.getPlayerContext();
        ViewerState state = viewerStates.remove(player);
        if (state != null && state.animatedTask != null) state.animatedTask.cancel();
    }
}
