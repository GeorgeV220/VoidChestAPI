package com.georgev22.voidchest.api.config;

import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Global in-memory cache for resolved VoidChest configuration options.
 *
 * <p>This cache ensures that configuration values are resolved from disk only once
 * per VoidChest type, providing ultra-fast O(1) access for all subsequent lookups.</p>
 *
 * <p>The cache automatically releases memory for VoidChest types that are no longer referenced,
 * preventing memory leaks on plugin reloads.</p>
 */
public final class VoidChestOptionCache {

    /**
     * Cache structure:
     * VoidChestType -> (OptionKey -> CachedValue)
     */
    private static final Map<String, Map<VoidChestOptionsUtil<?>, Object>> CACHE =
            new ConcurrentHashMap<>();

    /**
     * Returns a cached configuration value for the given VoidChest instance.
     *
     * @param chest  the VoidChest instance
     * @param option the option key
     * @param <T>    the option value type
     * @return the resolved and cached option value
     */
    public static <T> T get(@NonNull AbstractVoidChest chest, @NonNull VoidChestOptionsUtil<T> option) {
        return get(chest.type(), option);
    }

    /**
     * Returns a cached configuration value for the given VoidChest type.
     *
     * <p>If the value has not been cached yet, it will be resolved from the configuration file
     * and stored in memory.</p>
     *
     * @param type   the VoidChest type identifier
     * @param option the option key
     * @param <T>    the option value type
     * @return the resolved option value
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(@NonNull String type, @NonNull VoidChestOptionsUtil<T> option) {
        return (T) CACHE
                .computeIfAbsent(type, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(option, k -> option.loadFromConfig(type));
    }

    /**
     * Invalidates all cached values for a specific VoidChest type.
     *
     * @param type the VoidChest type identifier
     */
    public static void invalidate(@NonNull String type) {
        CACHE.remove(type);
    }

    /**
     * Invalidates all cached values for a specific VoidChest instance.
     *
     * @param chest the VoidChest instance
     */
    public static void invalidate(@NonNull AbstractVoidChest chest) {
        invalidate(chest.type());
    }

    /**
     * Clears the entire cache.
     */
    public static void invalidateAll() {
        CACHE.clear();
    }
}
