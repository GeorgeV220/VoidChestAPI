package com.georgev22.voidchest.api.storage.data.voidstorage;

/**
 * The Abilities interface extends the Entity interface and provides methods for managing the abilities of a void chest.
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
     * Checks if whitelist is enabled.
     *
     * @return True if whitelist is enabled, false otherwise.
     */
    boolean whitelist();

    /**
     * Sets the state of the whitelist feature.
     *
     * @param whitelist True to enable the whitelist feature, false to disable it.
     */
    void whitelist(boolean whitelist);

    /**
     * Checks if blacklist is enabled.
     *
     * @return True if blacklist is enabled, false otherwise.
     */
    boolean blacklist();

    /**
     * Sets the state of the blacklist feature.
     *
     * @param blacklist True to enable the blacklist feature, false to disable it.
     */
    void blacklist(boolean blacklist);


}