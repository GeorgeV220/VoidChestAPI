/**
 * This interface defines a cache for Void Storage configuration files. It provides methods
 * to interact with and manage cached configuration files for Void Chests.
 */
package com.georgev22.voidchest.api.utilities.config.voidchests;

import com.georgev22.library.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A cache for Void Storage configuration files.
 */
public interface VoidStorageConfigurationFileCache {

    /**
     * Retrieves a map of cached Void Storage configuration files.
     *
     * @return A map containing the cached configuration files, where the key is the name of the storage.
     */
    @NotNull
    ObjectMap<String, VoidStorageConfigurationFile> getCachedCFGs();

    /**
     * Caches Void Storages based on their configuration files.
     */
    void cacheStorages();

    /**
     * Retrieves the cached configuration file for the specified storage by its name.
     *
     * @param name The name of the Void Storage.
     * @return The cached configuration file for the specified storage, or null if not found.
     */
    @Nullable
    VoidStorageConfigurationFile getCachedStorageCFG(String name);

    /**
     * Retrieves the cached configuration file for the specified Void Storage.
     *
     * @param storage The Void Storage for which to retrieve the cached configuration file.
     * @return The cached configuration file for the specified storage, or null if not found.
     */
    @Nullable
    VoidStorageConfigurationFile getCachedStorageCFG(@NotNull IVoidStorage storage);

}
