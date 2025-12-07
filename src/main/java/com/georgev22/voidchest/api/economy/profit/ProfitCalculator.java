package com.georgev22.voidchest.api.economy.profit;

import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    @Deprecated
    @NotNull BigDecimal getProfit(@NotNull final ItemStack item);

    /**
     * Retrieves the profit for a specific item and amount.
     *
     * @param item   The item to calculate the profit for.
     * @param amount The amount of the item.
     * @return The calculated profit as a BigDecimal.
     */
    @Deprecated
    @NotNull BigDecimal getProfit(@NotNull final ItemStack item, @NotNull final BigInteger amount);

    /**
     * Retrieves the profit for a specific item, amount, and VoidChest.
     *
     * @param voidChest The VoidChest to calculate the profit for.
     * @param item      The item to calculate the profit for.
     * @param amount    The amount of the item.
     * @return The calculated profit as a BigDecimal.
     */
    @NotNull BigDecimal getProfit(@Nullable final IVoidChest voidChest, @NotNull final ItemStack item, @NotNull final BigInteger amount);

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
    @NotNull String getName();

    /**
     * Retrieves the plugin that registered this calculator.
     *
     * @return The plugin instance.
     */
    @NotNull Plugin getPlugin();

    /**
     * Retrieves the key of the profit calculator.
     *
     * @return The key of the profit calculator.
     */
    @NotNull NamespacedKey getKey();

}
