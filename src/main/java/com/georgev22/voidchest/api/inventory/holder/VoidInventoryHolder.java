package com.georgev22.voidchest.api.inventory.holder;

import com.georgev22.voidchest.api.exceptions.InvalidInventoryTypeException;
import com.georgev22.voidchest.api.inventory.InventoryType;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * The VoidInventoryHolder interface extends the InventoryHolder interface and provides additional methods
 * for managing a VoidChest inventory holder.
 * This InventoryHolder is not to be used directly but is instead extended by other InventoryHolders.
 *
 * @see MenuVoidInventoryHolder
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
     * Retrieves the VoidChest associated with the inventory.
     *
     * @return The VoidChest associated with the inventory.
     */
    IVoidChest voidChest();

    /**
     * Retrieves the inventory type.
     *
     * @return The inventory type.
     * @throws InvalidInventoryTypeException Subclasses should throw this exception if the inventory type is not valid.
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
