package com.georgev22.voidchest.api.storage.cache;

import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * The IVoidItemManager interface provides methods for managing VoidChest items.
 */
public interface IVoidItemManager {

    /**
     * Retrieves the map of cached VoidChest items.
     *
     * @return The map of cached VoidChest items.
     */
    ObjectMap<String, IVoidChestItem> itemCacheMap();

    /**
     * Checks if the specified ItemStack represents a VoidChest item.
     *
     * @param itemStack The ItemStack to check.
     * @return True if the ItemStack represents a VoidChest item, false otherwise.
     */
    boolean voidItem(final ItemStack itemStack);

    /**
     * Checks if the specified ItemStack represents a VoidChest item and belongs to the specified VoidChest.
     *
     * @param itemStack   The ItemStack to check.
     * @param voidChest The VoidChest to check the ownership.
     * @return True if the ItemStack represents a VoidChest item and belongs to the specified VoidChest, false otherwise.
     */
    boolean voidItem(final ItemStack itemStack, IVoidChest voidChest);

    /**
     * Caches the VoidChest items.
     */
    void cacheItems();

    /**
     * Retrieves the cached VoidChest item with the specified name.
     *
     * @param name The name of the VoidChest item to retrieve.
     * @return An Optional containing the cached VoidChest item if found, or an empty Optional if not found.
     */
    @NonNull Optional<IVoidChestItem> cachedItem(final String name);

    /**
     * Retrieves the cached VoidChest item for the specified ItemStack.
     *
     * @param itemStack The ItemStack to retrieve the cached VoidChest item for.
     * @return An Optional containing the cached VoidChest item if found, or an empty Optional if not found.
     */
    @NonNull Optional<IVoidChestItem> cachedItem(final ItemStack itemStack);

}
