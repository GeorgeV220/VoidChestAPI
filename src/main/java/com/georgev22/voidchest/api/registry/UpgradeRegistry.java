package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.upgrade.Upgrade;
import com.georgev22.voidchest.api.utilities.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A registry for managing and accessing upgrades in a plugin.
 */
public class UpgradeRegistry {

    private UpgradeRegistry() {
        throw new AssertionError("Utility class");
    }

    private static final Map<NamespacedKey, Upgrade<?>> upgrades = new ConcurrentHashMap<>();

    /**
     * Registers a new upgrade.
     *
     * @param upgrade the upgrade to register.
     * @throws IllegalArgumentException if an upgrade with the same key is already registered.
     */
    public static void registerUpgrade(@NotNull Upgrade<?> upgrade) {
        if (upgrades.containsKey(upgrade.getKey())) {
            throw new IllegalArgumentException("An upgrade with the key " + upgrade.getKey() + " is already registered.");
        }
        upgrades.put(upgrade.getKey(), upgrade);
    }

    /**
     * Retrieves an upgrade by its key.
     *
     * @param key the key of the upgrade.
     * @return an Optional containing the Upgrade if found.
     */
    public static @NotNull Optional<Upgrade<?>> getUpgrade(NamespacedKey key) {
        return Optional.ofNullable(upgrades.get(key));
    }

    /**
     * Checks if an upgrade is registered.
     *
     * @param key the key to check.
     * @return true if the upgrade is registered, false otherwise.
     */
    public static boolean isUpgradeRegistered(NamespacedKey key) {
        return upgrades.containsKey(key);
    }

    /**
     * Unregisters an upgrade.
     *
     * @param key the key of the upgrade to unregister.
     */
    public static void unregisterUpgrade(NamespacedKey key) {
        upgrades.remove(key);
    }

    public static List<Upgrade<?>> getUpgrades() {
        return Collections.synchronizedList(List.copyOf(upgrades.values()));
    }
}