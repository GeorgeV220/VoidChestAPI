package com.georgev22.voidchest.api.storage.data.voidstorage;

import com.georgev22.voidchest.api.inventory.VoidInventoryItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * The Filter interface provides methods for managing a VoidChest item filter.
 * An item filter is used to determine if an item is allowed for collection from the VoidChest.
 */
public interface Filter {

    /**
     * Gets the item that the filter applies to.
     *
     * @return The item that the filter applies to.
     */
    @NotNull VoidInventoryItemStack item();

    /**
     * Checks if the item is allowed for collection from the VoidChest.
     *
     * @return True if the item is allowed, false otherwise.
     */
    boolean allow();

    /**
     * Sets if the item is allowed for collection from the VoidChest.
     *
     * @param allow True if the item is allowed, false otherwise.
     */
    void setAllow(boolean allow);

    /**
     * Retrieves if the item metadata should be ignored when checking if the item is allowed.
     *
     * @return True if the item metadata should be ignored, false otherwise.
     */
    boolean ignoreItemMetadata();

    /**
     * Sets if the item metadata should be ignored when checking if the item is allowed.
     *
     * @param ignoreItemMetadata True if the item metadata should be ignored, false otherwise.
     */
    void setIgnoreItemMetadata(boolean ignoreItemMetadata);

    /**
     * Retrieves if the item amount should be ignored when checking if the item is allowed.
     *
     * @return True if the item amount should be ignored, false otherwise.
     */
    boolean ignoreItemAmount();

    /**
     * Sets if the item amount should be ignored when checking if the item is allowed.
     *
     * @param ignoreItemAmount True if the item amount should be ignored, false otherwise.
     */
    void setIgnoreItemAmount(boolean ignoreItemAmount);
}
