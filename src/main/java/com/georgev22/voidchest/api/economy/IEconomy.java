package com.georgev22.voidchest.api.economy;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * The IEconomy interface provides methods for managing player economy.
 */
public interface IEconomy {

    /**
     * Withdraws the specified amount from the player's account.
     *
     * @param player The OfflinePlayer to withdraw from.
     * @param amount The amount to withdraw as a BigDecimal.
     * @return True if the withdrawal is successful, false otherwise.
     */
    boolean withdraw(@NotNull final OfflinePlayer player, final BigDecimal amount);

    /**
     * Deposits the specified amount into the player's account.
     *
     * @param player The OfflinePlayer to deposit to.
     * @param amount The amount to deposit as a BigDecimal.
     * @return True if the deposit is successful, false otherwise.
     */
    boolean deposit(@NotNull final OfflinePlayer player, final BigDecimal amount);

    /**
     * Retrieves the balance of the player's account.
     *
     * @param player The OfflinePlayer to retrieve the balance for.
     * @return The balance of the player's account as a BigDecimal.
     */
    BigDecimal getBalance(@NotNull final OfflinePlayer player);

    /**
     * Retrieves the name of the economy system.
     *
     * @return The name of the economy system as a String.
     */
    String getName();

    /**
     * Sets up the economy system.
     *
     * @return True if the setup is successful, false otherwise.
     */
    boolean setupEconomy();
}