package com.georgev22.voidchest.api.upgrades;

import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import com.georgev22.voidchest.api.utilities.UnmodifiableArrayList;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;
import java.util.Optional;

/**
 * A manager interface for handling upgrades for void chests.
 */
public interface UpgradeManager {

    /**
     * Registers an upgrade for a given plugin.
     *
     * @param javaPlugin the plugin that owns the upgrade
     * @param upgrade    the upgrade to register
     * @throws IllegalArgumentException if the upgrade is already registered
     */
    void registerUpgrade(@NotNull JavaPlugin javaPlugin, @NotNull Upgrade<?> upgrade) throws IllegalArgumentException;

    /**
     * Gets an upgrade by its namespaced key.
     *
     * @param key the namespaced key of the upgrade
     * @return an optional containing the upgrade if found, or empty otherwise
     */
    @NotNull Optional<Upgrade<?>> getUpgrade(@NotNull NamespacedKey key);

    /**
     * Gets a list of upgrades registered by a given plugin.
     *
     * @param javaPlugin the plugin that owns the upgrades
     * @return a list of upgrades, or an empty list if none found
     */
    @UnmodifiableView
    @NotNull List<Upgrade<?>> getUpgrades(@NotNull JavaPlugin javaPlugin);

    /**
     * Gets a list of all registered upgrades.
     *
     * @return a list of upgrades, or an empty list if none registered
     */
    @UnmodifiableView
    @NotNull List<Upgrade<?>> getUpgrades();

    /**
     * Gets a list of all registered void chest upgrades.
     *
     * @return a list of upgrades, or an empty list if none registered
     */
    default @NotNull List<Upgrade<?>> getVoidChestUpgrades() {
        return getUpgrades().stream().filter(upgrade -> upgrade.getUpgradeNamespacedKey().getNamespace().equalsIgnoreCase("voidchest")).toList();
    }

    /**
     * Gets a list of upgrades applied to a given void storage.
     *
     * @param voidStorage the void storage to check
     * @return a list of upgrades, or an empty list if none applied
     */
    @UnmodifiableView
    default @NotNull List<Upgrade<?>> getUpgrades(@NotNull IVoidStorage voidStorage) {
        return new UnmodifiableArrayList<>(voidStorage.upgrades());
    }

}