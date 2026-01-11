package com.georgev22.voidchest.api.storage.model.voidchest;

import com.georgev22.voidchest.api.utilities.CustomData;

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
     * Retrieves the custom data associated with the void chest abilities.
     *
     * @return The custom data associated with the void chest abilities.
     */
    CustomData customData();

}
