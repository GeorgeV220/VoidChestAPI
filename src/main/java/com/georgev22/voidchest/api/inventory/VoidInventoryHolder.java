package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.exceptions.InvalidInventoryTypeException;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * The VoidInventoryHolder interface extends the InventoryHolder interface and provides additional methods
 * for managing a VoidChest inventory holder.
 * This InventoryHolder is utilized for all VoidChest inventories except for VoidChest item inventory.
 *
 * @see BlockVoidInventoryHolder
 */
public interface VoidInventoryHolder extends InventoryHolder, Serializable {

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
     * @throws InvalidInventoryTypeException If the inventory type is {@link InventoryType#STORAGE}.
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
