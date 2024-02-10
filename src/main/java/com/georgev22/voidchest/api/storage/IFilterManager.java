package com.georgev22.voidchest.api.storage;

import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import com.georgev22.voidchest.api.storage.data.voidstorage.Filter;

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

}
