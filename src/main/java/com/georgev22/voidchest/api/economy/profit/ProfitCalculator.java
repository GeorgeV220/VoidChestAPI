package com.georgev22.voidchest.api.economy.profit;

import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import org.bukkit.Keyed;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The ProfitCalculator class is responsible for calculating profits from various shop plugins.
 */
public interface ProfitCalculator extends Keyed {

    /**
     * Retrieves the profit for a specific item.
     *
     * @param item The item to calculate the profit for.
     * @return The calculated profit as a BigDecimal.
     */
    @Deprecated
    @NonNull BigDecimal getProfit(@NonNull final ItemStack item);

    /**
     * Retrieves the profit for a specific item and amount.
     *
     * @param item   The item to calculate the profit for.
     * @param amount The amount of the item.
     * @return The calculated profit as a BigDecimal.
     */
    @Deprecated
    @NonNull BigDecimal getProfit(@NonNull final ItemStack item, @NonNull final BigInteger amount);

    /**
     * Retrieves the profit for a specific item, amount, and VoidChest.
     *
     * @param voidChest The VoidChest to calculate the profit for.
     * @param item      The item to calculate the profit for.
     * @param amount    The amount of the item.
     * @return The calculated profit as a BigDecimal.
     */
    @NonNull BigDecimal getProfit(@Nullable final AbstractVoidChest voidChest, @NonNull final ItemStack item, @NonNull final BigInteger amount);

    /**
     * Returns whether the profit calculation requires the player to be online.
     *
     * @return True if the profit calculation requires the player to be online, false otherwise.
     */
    boolean requiresThePlayerToBeOnline();

    /**
     * Retrieves the name of the Profit Calculator.
     *
     * @return The name of the Profit Calculator as a String.
     */
    @NonNull String getName();

    /**
     * Invalidates the cache for the Profit Calculator.
     * <p>
     * This method is intended for internal use only and should not be called by plugins.
     * </p>
     */
    @ApiStatus.Internal
    void invalidateCache();

}
