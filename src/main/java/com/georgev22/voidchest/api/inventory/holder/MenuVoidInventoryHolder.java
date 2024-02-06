package com.georgev22.voidchest.api.inventory.holder;

import com.georgev22.voidchest.api.exceptions.InvalidInventoryTypeException;
import com.georgev22.voidchest.api.inventory.InventoryType;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * The MenuVoidInventoryHolder interface extends the VoidInventoryHolder interface and provides additional methods
 * for managing a VoidChest inventory with a specific inventory type of {@link InventoryType#MENU} or {@link InventoryType#FILTERS}.
 *
 * @see BlockVoidInventoryHolder
 * @see VoidInventoryHolder
 */
public interface MenuVoidInventoryHolder extends VoidInventoryHolder, Serializable {

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
