package com.georgev22.voidchest.api.inventory;

import com.georgev22.library.maps.ObjectMap;
import com.georgev22.voidchest.api.utilities.NullableFixedSizeList;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * The {@code VoidInventory} interface represents a void chest inventory,
 * extending the Bukkit {@link Inventory} interface.
 * It provides methods for adding, retrieving, and iterating over items within the void chest.
 *
 * <p>Items within the void chest are represented by {@link VoidInventoryItemStack}, which encapsulates both
 * a {@link SerializableItemStack} for data serialization and an {@link ItemStack} for the visible representation
 * of the item in the inventory.
 *
 * <p>This interface extends the Bukkit {@link Inventory} interface and adds additional functionality for handling
 * void chest-specific operations.
 */
public interface VoidInventory extends Inventory {

    /**
     * Adds an array of {@link SerializableItemStack}s to the void chest.
     *
     * @param items The serializable item stacks to add.
     * @return An {@link ObjectMap} containing the slot indices and corresponding serializable item stacks.
     */
    ObjectMap<Integer, SerializableItemStack> addItems(SerializableItemStack... items);

    /**
     * Overrides the default Bukkit {@link Inventory#addItem(ItemStack...)} method to use void chest-specific behavior.
     *
     * @param items The item stacks to add.
     * @return A {@link HashMap} containing the slot indices and corresponding item stacks.
     */
    @NotNull
    HashMap<Integer, ItemStack> addItem(ItemStack... items);

    /**
     * Retrieves a list of {@link VoidInventoryItemStack}s representing all items in the void chest.
     *
     * @return The list of void chest item stacks.
     */
    NullableFixedSizeList<VoidInventoryItemStack> getItems();

    /**
     * Retrieves the index of the first partial match of the specified item in the void chest.
     *
     * @param item The item to search for.
     * @return The index of the first partial match, or {@code -1} if no partial match is found.
     */
    int firstPartial(ItemStack item);

    /**
     * Sets the items in the void chest to the specified list of {@link VoidInventoryItemStack}s.
     *
     * @param items The list of void chest item stacks to set.
     */
    default void setItems(NullableFixedSizeList<VoidInventoryItemStack> items) {
        this.getItems().clear();
        this.getItems().addAll(items);
    }

    /**
     * Retrieves the {@link VoidInventoryItemStack} at the specified slot in the void chest.
     *
     * @param slot The slot index.
     * @return The void chest item stack at the specified slot, or {@code null} if the slot is empty.
     */
    default @Nullable VoidInventoryItemStack getVoidInventoryItem(int slot) {
        if (this.getItems().size() <= slot) {
            return null;
        }
        return getItems().get(slot);
    }

    /**
     * Iterates over the original {@link ItemStack}s of all items in the void chest.
     *
     * @param action The action to be performed on each original item stack.
     */
    @Override
    default void forEach(Consumer<? super ItemStack> action) {
        this.forEachOriginalItemStacks(action);
    }

    /**
     * Iterates over the original item stacks of all items in the void chest using a specified action.
     *
     * @param action The action to be performed on each original item stack.
     */
    default void forEachOriginalItemStacks(Consumer<? super ItemStack> action) {
        this.getItems().stream()
                .filter(Objects::nonNull)
                .map(VoidInventoryItemStack::getOriginalItemStack)
                .forEach(action);
    }

    /**
     * Iterates over the visible item stacks of all items in the void chest using a specified action.
     *
     * @param action The action to be performed on each visible item stack.
     */
    default void forEachVisibleItemStacks(Consumer<? super ItemStack> action) {
        this.getItems().stream()
                .filter(Objects::nonNull)
                .map(VoidInventoryItemStack::getVisibleItemStack)
                .forEach(action);
    }

    /**
     * Iterates over all {@link VoidInventoryItemStack}s in the void chest using a specified action.
     *
     * @param action The action to be performed on each void chest item stack.
     */
    default void forEachVoidItemStack(Consumer<? super VoidInventoryItemStack> action) {
        this.getItems().forEach(action);
    }

    /**
     * The API shouldn't call this method
     * <p>
     * Updates the item stack at the specified slot in the void chest.
     * <p>
     * Used when a player modifies a specific slot in the void chest
     *
     * @param slot                   The slot index
     * @param voidInventoryItemStack The item stack
     */
    @ApiStatus.Internal
    @ApiStatus.Experimental
    default void update(int slot, VoidInventoryItemStack voidInventoryItemStack) {
        this.getItems().set(slot, voidInventoryItemStack);
    }

    /**
     * This method is used to open the void inventory and also add the specified listeners.
     *
     * @param player The player to open the inventory for
     */
    @ApiStatus.Internal
    @ApiStatus.Experimental
    void open(Player player);
}
