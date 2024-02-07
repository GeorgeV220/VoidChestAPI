package com.georgev22.voidchest.api.storage.data.voidstorage;

import com.georgev22.library.maps.ConcurrentObjectMap;

/**
 * The Abilities interface provides methods for managing the abilities of a void chest.
 */
public interface Abilities {

    /**
     * Checks if auto-selling is enabled.
     *
     * @return True if auto-selling is enabled, false otherwise.
     */
    boolean autoSell();

    /**
     * Sets the auto-selling state.
     *
     * @param autoSell True to enable auto-selling, false to disable it.
     */
    void autoSell(boolean autoSell);

    /**
     * Checks if purging of invalid items is enabled.
     *
     * @return True if purging of invalid items is enabled, false otherwise.
     */
    boolean purgeInvalidItems();

    /**
     * Sets the state of purging invalid items.
     *
     * @param purgeInvalidItems True to enable purging of invalid items, false to disable it.
     */
    void purgeInvalidItems(boolean purgeInvalidItems);

    /**
     * Checks if hologram display is enabled.
     *
     * @return True if hologram display is enabled, false otherwise.
     */
    boolean hologram();

    /**
     * Sets the state of hologram display.
     *
     * @param hologram True to enable hologram display, false to disable it.
     */
    void hologram(boolean hologram);

    /**
     * Checks if chunk collector is enabled.
     *
     * @return True if chunk collector is enabled, false otherwise.
     */
    boolean chunkCollector();

    /**
     * Sets the state of chunk collector.
     *
     * @param chunkCollector True to enable chunk collector, false to disable it.
     */
    void chunkCollector(boolean chunkCollector);

    /**
     * Checks if instant collector is enabled.
     *
     * @return True if instant collector is enabled, false otherwise.
     */
    boolean instantCollector();

    /**
     * Sets the state of instant collector.
     *
     * @param instantCollector True to enable instant collector, false to disable it.
     */
    void instantCollector(boolean instantCollector);

    /**
     * Checks if transfer of non-sellable items is enabled.
     *
     * @return True if transfer of non-sellable items is enabled, false otherwise.
     */
    boolean transferNonSellable();

    /**
     * Sets the state of transferring non-sellable items.
     *
     * @param transferNonSellable True to enable transferring non-sellable items, false to disable it.
     */
    void transferNonSellable(boolean transferNonSellable);

    /**
     * Checks if bank feature is enabled.
     *
     * @return True if bank feature is enabled, false otherwise.
     */
    boolean bank();

    /**
     * Sets the state of the bank feature.
     *
     * @param bank True to enable the bank feature, false to disable it.
     */
    void bank(boolean bank);

    /**
     * Checks if the user has enabled the `allow mode`.
     * If the `user allow mode` option is disabled, this will also return false.
     *
     * @return True if the user has enabled the `allow mode`, false otherwise.
     */
    boolean filtersAllowMode();

    /**
     * Sets the state of the `allow mode`.
     * If the `user allow mode` option is disabled, this has no effect.
     *
     * @param allowMode True to enable the `allow mode`, false to disable it.
     */
    void filtersAllowMode(boolean allowMode);

    /**
     * Adds custom data to the Abilities with the specified key and value.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated Abilities with the added custom data
     */
    default Abilities addCustomData(String key, Object value) {
        this.getCustomData().append(key, value);
        return this;
    }

    /**
     * Adds custom data to the Abilities with the specified key and value if the key does not already exist.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated Abilities with the added custom data (if the key did not already exist)
     */
    default Abilities addCustomDataIfNotExists(String key, Object value) {
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
     * Retrieves the map of custom data associated with the Abilities.
     *
     * @return the {@link ConcurrentObjectMap} containing the custom data of the void storage abilities
     */
    ConcurrentObjectMap<String, Object> getCustomData();

}