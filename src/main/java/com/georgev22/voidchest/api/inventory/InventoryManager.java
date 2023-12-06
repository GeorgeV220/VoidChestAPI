package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The {@code InventoryManager} interface provides methods for managing void chest inventories.
 * Implementations of this interface are responsible
 * for creating and accessing different types of void chest inventories.
 *
 * <p>Void chest inventories are associated with a specific {@link IVoidStorage}, and each inventory type is represented
 * by the {@link InventoryType} enum.
 */
public interface InventoryManager {

    /**
     * Creates a void chest inventory associated with the specified {@link IVoidStorage} and of the specified type.
     *
     * @param voidStorage The void storage associated with the inventory.
     * @param type        The type of inventory to create.
     * @return A non-null instance of the created void chest inventory.
     * @throws NullPointerException if {@code voidStorage} is {@code null}.
     */
    @NotNull
    VoidInventory inventory(IVoidStorage voidStorage, InventoryType type);

    /**
     * Retrieves a void chest inventory associated with the specified void storage UUID and of the specified type.
     *
     * @param voidStorageUUID The UUID of the void storage associated with the inventory.
     * @param type            The type of inventory to retrieve.
     * @return A non-null instance of the retrieved void chest inventory.
     * @throws NullPointerException if {@code voidStorageUUID} is {@code null}.
     */
    @NotNull
    VoidInventory inventory(UUID voidStorageUUID, InventoryType type);

    /**
     * An enumeration representing different types of void chest inventories.
     */
    enum InventoryType {
        /**
         * Represents the storage inventory type for storing items in a void chest.
         */
        STORAGE,

        /**
         * Represents the whitelist inventory type for managing whitelist entries in a void chest.
         */
        WHITELIST,

        /**
         * Represents the blacklist inventory type for managing blacklist entries in a void chest.
         */
        BLACKLIST,

        /**
         * Represents the menu inventory type for displaying a menu in a void chest.
         */
        MENU
    }
}
