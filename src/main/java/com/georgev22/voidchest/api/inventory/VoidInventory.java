package com.georgev22.voidchest.api.inventory;

import com.georgev22.library.maps.HashObjectMap;
import com.georgev22.library.maps.ObjectMap;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
     * Adds an array of {@link ItemStack}s to the void chest, converting them to {@link SerializableItemStack}s.
     *
     * @param items The item stacks to add.
     * @return An {@link ObjectMap} containing the slot indices and corresponding item stacks.
     */
    default ObjectMap<Integer, ItemStack> addItems(ItemStack... items) {
        return this.addItems(
                        Arrays.stream(items).map(
                                itemStack -> new SerializableItemStack(itemStack, BigInteger.valueOf(itemStack.getAmount()))
                        ).toArray(SerializableItemStack[]::new))
                .entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getItemStack().clone(), (a, b) -> b, HashObjectMap::new
                ));
    }

    /**
     * Overrides the default Bukkit {@link Inventory#addItem(ItemStack...)} method to use void chest-specific behavior.
     *
     * @param items The item stacks to add.
     * @return A {@link HashMap} containing the slot indices and corresponding item stacks.
     */
    @Override
    @NotNull
    default HashMap<Integer, ItemStack> addItem(ItemStack... items) {
        return this.addItems(items).entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue, (a, b) -> b, HashObjectMap::new
        ));
    }

    /**
     * Retrieves a list of {@link VoidInventoryItemStack}s representing all items in the void chest.
     *
     * @return The list of void chest item stacks.
     */
    List<VoidInventoryItemStack> getItems();

    /**
     * Sets the items in the void chest to the specified list of {@link VoidInventoryItemStack}s.
     *
     * @param items The list of void chest item stacks to set.
     */
    default void setItems(List<VoidInventoryItemStack> items) {
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
}
