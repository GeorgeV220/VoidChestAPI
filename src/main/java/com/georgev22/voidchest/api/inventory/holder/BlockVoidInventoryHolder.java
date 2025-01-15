package com.georgev22.voidchest.api.inventory.holder;

import com.georgev22.voidchest.api.exceptions.InvalidInventoryTypeException;
import com.georgev22.voidchest.api.inventory.InventoryType;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * The BlockVoidInventoryHolder interface extends the BlockInventoryHolder interface and provides additional methods
 * for managing a VoidChest inventory with a specific inventory type of {@link InventoryType#STORAGE}.
 * This InventoryHolder is specifically designed for VoidChest item inventories and is not intended for other types
 * of VoidChest inventories like menus.
 *
 * @see MenuVoidInventoryHolder
 * @see VoidInventoryHolder
 */
public interface BlockVoidInventoryHolder extends VoidInventoryHolder, Serializable {

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

    /**
     * Retrieves the block associated with the inventory.
     *
     * @return The block associated with the inventory.
     */
    Block getBlock();
}
