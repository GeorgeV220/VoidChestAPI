package com.georgev22.voidchest.api.voideconomy;

import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The IVoidEconomy interface defines methods for interacting with the VoidChest economy.
 */
public interface IVoidEconomy {

    /**
     * Initiates the selling process for a specific player's data.
     *
     * @param data The player data to initiate the sell for.
     */
    void initiateSell(final IPlayerData data);

    /**
     * Initiates the selling process for a specific VoidChest type.
     *
     * @param voidChestType The type of VoidChest to initiate the sell for.
     */
    void initiateSell(final String voidChestType);

    /**
     * Initiates the selling process for a specific player's data and VoidChest type.
     *
     * @param data          The player data to initiate the sell for.
     * @param voidChestType The type of VoidChest to initiate the sell for.
     */
    void initiateSell(final IPlayerData data, final String voidChestType);

    /**
     * Initiates the selling process.
     */
    void initiateSell();

    /**
     * Retrieves the profit for a specific item.
     *
     * @param item The item to calculate the profit for.
     * @return The calculated profit as a BigDecimal.
     */
    BigDecimal getProfit(final ItemStack item);

    /**
     * Retrieves the profit for a specific item and amount.
     *
     * @param item   The item to calculate the profit for.
     * @param amount The amount of the item.
     * @return The calculated profit as a BigDecimal.
     */
    BigDecimal getProfit(final ItemStack item, final BigInteger amount);

    /**
     * Retrieves the profit for a specific item, amount, and VoidChest.
     *
     * @param voidChest The VoidChest to calculate the profit for.
     * @param item      The item to calculate the profit for.
     * @param amount    The amount of the item.
     * @return The calculated profit as a BigDecimal.
     */
    BigDecimal getProfit(final IVoidStorage voidChest, final ItemStack item, final BigInteger amount);

    /**
     * Retrieves the name of the VoidEconomy.
     *
     * @return The name of the VoidEconomy as a String.
     */
    String getName();

    /**
     * Returns whether the VoidEconomy requires the player to be online.
     *
     * @return True if the VoidEconomy requires the player to be online, false otherwise.
     */
    boolean requiresThePlayerToBeOnline();
}
