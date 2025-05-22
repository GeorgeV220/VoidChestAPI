package com.georgev22.voidchest.api.economy.profit;

import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The ProfitCalculator class is responsible for calculating profits from various shop plugins.
 */
public interface ProfitCalculator {

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
    BigDecimal getProfit(final IVoidChest voidChest, final ItemStack item, final BigInteger amount);

    /**
     * Returns whether the profit calculation requires the player to be online.
     *
     * @return True if the profit calculation requires the player to be online, false otherwise.
     */
    boolean requiresThePlayerToBeOnline();

    /**
     * Retrieves the name of the VoidEconomy.
     *
     * @return The name of the VoidEconomy as a String.
     */
    String getName();

    /**
     * Retrieves the simple name of the VoidEconomy e.g. EcoShop
     *
     * @return The simple name of the VoidEconomy as a String.
     */
    String getSimpleName();
}
