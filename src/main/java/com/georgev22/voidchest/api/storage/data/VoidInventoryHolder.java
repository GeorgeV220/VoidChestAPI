package com.georgev22.voidchest.api.storage.data;

import com.georgev22.voidchest.api.storage.IVoidStorageManager;
import com.georgev22.voidchest.api.storage.IVoidStorageManager.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import java.io.Serializable;

/**
 * The VoidInventoryHolder interface extends the InventoryHolder interface and provides additional methods
 * for managing a VoidChest inventory holder.
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
     */
    InventoryType inventoryType();

}
