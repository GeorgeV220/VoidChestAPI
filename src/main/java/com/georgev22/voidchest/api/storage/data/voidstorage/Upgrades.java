package com.georgev22.voidchest.api.storage.data.voidstorage;

import com.georgev22.library.maps.ConcurrentObjectMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;

public interface Upgrades {

    /**
     * Retrieves the current upgrade of the VoidStorage.
     *
     * @return The current upgrade of the VoidStorage.
     */
    int currentUpgradeLevel();

    /**
     * Retrieves the maximum number of upgrades that can be applied to the VoidStorage.
     *
     * @return The maximum number of upgrades that can be applied to the VoidStorage.
     */
    int maxUpgrades();

    /**
     * Retrieves the upgrades of the VoidStorage.
     * <p>
     * Modifications to this list will not be reflected in the VoidStorage.
     *
     * @return The upgrades of the VoidStorage.
     */
    @UnmodifiableView
    List<Upgrade<?>> getUpgrades();

    /**
     * Retrieves the upgrades of the VoidStorage with the specified level.
     * <p>
     * Modifications to this list will not be reflected in the VoidStorage.
     *
     * @param level the level of the upgrades to retrieve
     * @return The upgrades of the VoidStorage with the specified level.
     */
    @UnmodifiableView
    List<Upgrade<?>> getUpgrades(int level);

    /**
     * Adds an Upgrade to the VoidStorage.
     *
     * @param upgrade the Upgrade to add
     */
    void addUpgrade(@NotNull Upgrade<?> upgrade);

    /**
     * Removes an Upgrade from the VoidStorage.
     *
     * @param upgrade the Upgrade to remove
     */
    void removeUpgrade(@NotNull Upgrade<?> upgrade);

    /**
     * Removes all Upgrades from the VoidStorage with the specified name.
     *
     * @param upgradeName the name of the Upgrades to remove
     */
    void removeUpgrade(@NotNull String upgradeName);

    /**
     * Removes an Upgrade from the VoidStorage with the specified name and level.
     *
     * @param upgradeName the name of the Upgrade to remove
     * @param level       the level of the Upgrade to remove
     */
    void removeUpgrade(@NotNull String upgradeName, int level);

    /**
     * Retrieves the Upgrade with the specified name or null if it does not exist.
     *
     * @param upgradeName the name of the Upgrade
     * @return the Upgrade with the specified name or null if it does not exist.
     */
    @Nullable
    Upgrade<?> getUpgrade(String upgradeName);

    /**
     * Retrieves the Upgrade with the specified name and level or null if it does not exist.
     *
     * @param upgradeName the name of the Upgrade
     * @param level       the level of the Upgrade
     * @return the Upgrade with the specified name and level or null if it does not exist.
     */
    @Nullable
    Upgrade<?> getUpgrade(String upgradeName, int level);

    /**
     * Adds custom data to the Upgrades with the specified key and value.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated Upgrades with the added custom data
     */
    default Upgrades addCustomData(String key, Object value) {
        this.getCustomData().append(key, value);
        return this;
    }

    /**
     * Adds custom data to the Upgrades with the specified key and value if the key does not already exist.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated Upgrades with the added custom data (if the key did not already exist)
     */
    default Upgrades addCustomDataIfNotExists(String key, Object value) {
        this.getCustomData().appendIfTrue(key, value, !this.getCustomData().containsKey(key));
        return this;
    }

    /**
     * Retrieves the value of the custom data associated with the specified key.
     *
     * @param key the key of the custom data
     * @param <T> the type of the value to retrieve
     * @return the value associated with the specified key, or {@code null} if the key does not exist
     */
    default <T> T getCustomData(String key) {
        return (T) getCustomData().get(key);
    }

    /**
     * Retrieves the map of custom data associated with the Upgrades.
     *
     * @return the {@link ConcurrentObjectMap} containing the custom data of the void storage upgrades
     */
    ConcurrentObjectMap<String, Object> getCustomData();

}
