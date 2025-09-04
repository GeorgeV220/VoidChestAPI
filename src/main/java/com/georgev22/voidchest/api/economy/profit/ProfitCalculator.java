package com.georgev22.voidchest.api.economy.profit;

import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The ProfitCalculator class is responsible for calculating profits from various shop plugins.
 */
public interface ProfitCalculator extends Comparable<ProfitCalculator> {

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
     * Returns the weight (priority) of this calculator.
     * <p>
     * Calculators with <strong>lower weight values</strong> have higher priority and will be evaluated first.
     * If multiple calculators share the same weight, the one with the highest calculated profit will be selected.
     * <p>
     * Example:
     * <ul>
     *     <li>{@code weight = 0} → highest priority</li>
     *     <li>{@code weight = 10} → lower priority</li>
     * </ul>
     *
     * @return The weight of this calculator. Defaults to 0 if not overridden.
     */
    int getWeight();

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

    /**
     * Retrieves the plugin that registered this calculator.
     *
     * @return The plugin instance.
     */
    Plugin getPlugin();

    @Override
    default int compareTo(@NotNull ProfitCalculator other) {
        int weightCompare = Integer.compare(this.getWeight(), other.getWeight());
        if (weightCompare != 0) {
            return weightCompare;
        }
        return this.getName().compareToIgnoreCase(other.getName());
    }

}
