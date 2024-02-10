package com.georgev22.voidchest.api.storage;

import com.georgev22.voidchest.api.inventory.VoidInventoryItemStack;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import com.georgev22.voidchest.api.storage.data.voidstorage.Filter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FilterManager interface provides methods for managing item filters.
 * An item filter is used to determine if an item is allowed for collection from the VoidChest.
 * For user-managed filters, see {@link IVoidStorage#itemFilters()}
 */
public interface IFilterManager {

    /**
     * Returns the list of global filters
     *
     * @return The list of global filters
     */
    List<Filter> getGlobalFilters();

    /**
     * Retrieves the item filters of the specified VoidStorage.
     *
     * @param voidChest The VoidStorage to retrieve the item filters from.
     * @return The item filters of the VoidStorage.
     */
    List<Filter> getFilters(String voidChest);

    /**
     * Retrieves the item filters of the specified VoidStorage.
     *
     * @param voidChest The VoidStorage to retrieve the item filters from.
     * @return The item filters of the VoidStorage.
     */
    List<Filter> getFilters(IVoidStorage voidChest);


    /**
     * Checks if the specified item is allowed for collection from the specified VoidChest.
     * <p>
     * Note: If voidStorage is null, this method will check on the global filters only.
     *
     * @param voidStorageName The VoidChest to check if the item is allowed.
     * @param item            The item to check if it is allowed.
     * @return True if the item is allowed, false otherwise.
     */
    boolean isItemAllowed(@Nullable String voidStorageName, VoidInventoryItemStack item);

    /**
     * Checks if the specified item is allowed for collection from the specified VoidChest.
     * <p>
     * Note: If voidStorage is null, this method will check on the global filters only.
     *
     * @param voidStorage The VoidChest to check if the item is allowed.
     * @param item        The item to check if it is allowed.
     * @return True if the item is allowed, false otherwise.
     */
    boolean isItemAllowed(@Nullable IVoidStorage voidStorage, VoidInventoryItemStack item);

}
