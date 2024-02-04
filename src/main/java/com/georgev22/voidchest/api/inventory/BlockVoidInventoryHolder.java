package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.exceptions.InvalidInventoryTypeException;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.inventory.BlockInventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * The BlockVoidInventoryHolder interface extends the BlockInventoryHolder interface and provides additional methods
 * for managing a VoidChest inventory with a specific inventory type of {@link InventoryType#STORAGE}.
 * This InventoryHolder is specifically designed for VoidChest item inventories and is not intended for other types
 * of VoidChest inventories like menus.
 *
 * @see VoidInventoryHolder
 */
public interface BlockVoidInventoryHolder extends BlockInventoryHolder, Serializable {

    /**
     * Retrieves the name of the inventory.
     *
     * @return The name of the inventory as a String.
     */
    String inventoryName();

    /**
     * Retrieves the size of the inventory.
     *
     * @return The size of the inventory as an integer.
     */
    int inventorySize();

    /**
     * Retrieves the VoidStorage associated with the inventory.
     *
     * @return The VoidStorage associated with the inventory.
     */
    IVoidStorage voidStorage();

    /**
     * Retrieves the inventory type.
     *
     * @return The inventory type.
     * @throws InvalidInventoryTypeException If the inventory type is not {@link InventoryType#STORAGE}.
     */
    InventoryType inventoryType() throws InvalidInventoryTypeException;

    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */
    @Override
    @NotNull VoidInventory getInventory();
}
