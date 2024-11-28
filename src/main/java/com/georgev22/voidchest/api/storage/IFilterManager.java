package com.georgev22.voidchest.api.storage;

import com.georgev22.voidchest.api.storage.data.IVoidChest;
import com.georgev22.voidchest.api.storage.data.voidchest.Filter;
import com.georgev22.voidchest.api.utilities.VoidInventoryItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * FilterManager interface provides methods for managing item filters.
 * An item filter is used to determine if an item is allowed for collection from the VoidChest.
 * For user-managed filters, see {@link IVoidChest#itemFilters()}
 */
public interface IFilterManager {

    /**
     * Returns the list of global filters
     *
     * @return The list of global filters
     */
    List<Filter> getGlobalFilters();

    /**
     * Retrieves the item filters of the specified VoidChest.
     *
     * @param voidChest The VoidChest to retrieve the item filters from.
     * @return The item filters of the VoidChest.
     */
    List<Filter> getFilters(String voidChest);

    /**
     * Retrieves the item filters of the specified VoidChest.
     *
     * @param voidChest The VoidChest to retrieve the item filters from.
     * @return The item filters of the VoidChest.
     */
    List<Filter> getFilters(IVoidChest voidChest);


    /**
     * Checks if the specified item is allowed for collection from the specified VoidChest.
     * <p>
     * Note: If voidChest is null, this method will check on the global filters only.
     *
     * @param voidChestName The VoidChest to check if the item is allowed.
     * @param item            The item to check if it is allowed.
     * @return True if the item is allowed, false otherwise.
     */
    boolean isItemAllowed(@Nullable String voidChestName, VoidInventoryItemStack item);

    /**
     * Checks if the specified item is allowed for collection from the specified VoidChest.
     * <p>
     * Note: If voidChest is null, this method will check on the global filters only.
     *
     * @param voidChest The VoidChest to check if the item is allowed.
     * @param item        The item to check if it is allowed.
     * @return True if the item is allowed, false otherwise.
     */
    boolean isItemAllowed(@Nullable IVoidChest voidChest, VoidInventoryItemStack item);


    /**
     * Loads the specified item filter from the storage.
     * <p>
     * Notes: <br>
     * This method will overwrite any loaded existing filters by the same name.<br>
     * If the filter does not exist, this method will do nothing.<br>
     * The default filter name is `global`
     *
     * @param filterName The name of the item filter to load.
     */
    void load(String filterName);

    /**
     * Saves the specified item filter to the storage.
     *
     * @param filterName The name of the item filter to save.
     */
    void save(String filterName);

    /**
     * Loads all `item filters` from the storage.
     * <p>
     * Note: This method will overwrite any loaded existing filters.
     */
    void loadAll();

    /**
     * Saves all `item filters` to the storage.
     */
    void saveAll();

}
