package com.georgev22.voidchest.api.storage.voidmanager;

import com.georgev22.library.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

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
     * Checks if the specified ItemStack represents a VoidChest item and belongs to the specified VoidStorage.
     *
     * @param itemStack   The ItemStack to check.
     * @param voidStorage The VoidStorage to check the ownership.
     * @return True if the ItemStack represents a VoidChest item and belongs to the specified VoidStorage, false otherwise.
     */
    boolean voidItem(final ItemStack itemStack, IVoidStorage voidStorage);

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
    @NotNull Optional<IVoidChestItem> cachedItem(final String name);

    /**
     * Retrieves the cached VoidChest item for the specified ItemStack.
     *
     * @param itemStack The ItemStack to retrieve the cached VoidChest item for.
     * @return An Optional containing the cached VoidChest item if found, or an empty Optional if not found.
     */
    @NotNull Optional<IVoidChestItem> cachedItem(final ItemStack itemStack);

}
