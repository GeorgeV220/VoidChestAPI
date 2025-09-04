/**
 * This interface defines a cache for Void Chest configuration files. It provides methods
 * to interact with and manage cached configuration files for Void Chests.
 */
package com.georgev22.voidchest.api.utilities.config.voidchests;

import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A cache for Void Chest configuration files.
 */
public interface VoidChestConfigurationFileCache {

    /**
     * Retrieves a map of cached Void Chest configuration files.
     *
     * @return A map containing the cached configuration files, where the key is the name of the storage.
     */
    @NotNull
    ObjectMap<String, VoidChestConfigurationFile> getCachedCFGs();

    /**
     * Caches Void Chests based on their configuration files.
     */
    void cacheStorages();

    /**
     * Retrieves the cached configuration file for the specified storage by its name.
     *
     * @param voidChestType The named type of the Void Chest.
     * @return The cached configuration file for the specified storage, or null if not found.
     */
    @Nullable
    VoidChestConfigurationFile getCachedStorageCFG(String voidChestType);

    /**
     * Retrieves the cached configuration file for the specified Void Chest.
     *
     * @param storage The Void Chest for which to retrieve the cached configuration file.
     * @return The cached configuration file for the specified storage, or null if not found.
     */
    @Nullable
    VoidChestConfigurationFile getCachedStorageCFG(@NotNull IVoidChest storage);

}
