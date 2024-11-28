package com.georgev22.voidchest.api.voideconomy;

import com.georgev22.voidchest.api.storage.data.IPlayerData;

/**
 * The `SellHandler` interface provides methods to initiate the selling process for various scenarios.
 * Implementations of this interface should handle the logic for selling items or resources, potentially
 * involving player data and specific types of void chests.
 */
public interface SellHandler {

    /**
     * Initiates the selling process without any specific player data or void chest type.
     * This method may be used for generic or default selling operations.
     */
    void initiateSell();

    /**
     * Initiates the selling process for the specified player data.
     *
     * @param data The player data associated with the selling process.
     * @throws NullPointerException if the provided player data is null.
     */
    void initiateSell(final IPlayerData data);

    /**
     * Initiates the selling process for the specified void chest type.
     *
     * @param voidChestType The type of void chest involved in the selling process.
     * @throws IllegalArgumentException if the provided void chest type is null or empty.
     */
    void initiateSell(final String voidChestType);

    /**
     * Initiates the selling process for the specified player data and void chest type.
     *
     * @param data          The player data associated with the selling process.
     * @param voidChestType The type of void chest involved in the selling process.
     * @throws NullPointerException     if the provided player data is null.
     * @throws IllegalArgumentException if the provided void chest type is null or empty.
     */
    void initiateSell(final IPlayerData data, final String voidChestType);
}