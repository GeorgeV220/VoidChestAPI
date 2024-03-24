package com.georgev22.voidchest.api.economy;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

/**
 * The AEconomy abstract class that provides methods for managing player economy.<br>
 * Payout economy: The economy class is used to give the "rewards" when a sell is made by void chest.<br>
 * Withdrawal economy: The economy class is used to withdraw money from the player's account.<br>
 * Deposit economy: The economy class is used to deposit money into the player's account.<br>
 * <p>
 * Only one economy of each mode can be hooked.
 */
public abstract class AEconomy {

    private final List<EconomyMode> economyModes;

    public AEconomy() {
        this(EconomyMode.PAYOUT, EconomyMode.WITHDRAWAL, EconomyMode.DEPOSIT);
    }

    public AEconomy(EconomyMode... modes) {
        this.economyModes = List.of(modes);
    }

    /**
     * Withdraws the specified amount from the player's account.
     *
     * @param player The OfflinePlayer to withdraw from.
     * @param amount The amount to withdraw as a BigDecimal.
     * @return True if the withdrawal is successful, false otherwise.
     */
    public abstract boolean withdraw(@NotNull final OfflinePlayer player, final BigDecimal amount);

    /**
     * Deposits the specified amount into the player's account.
     *
     * @param player The OfflinePlayer to deposit to.
     * @param amount The amount to deposit as a BigDecimal.
     * @return True if the deposit is successful, false otherwise.
     */
    public abstract boolean deposit(@NotNull final OfflinePlayer player, final BigDecimal amount);

    /**
     * Retrieves the balance of the player's account.
     *
     * @param player The OfflinePlayer to retrieve the balance for.
     * @return The balance of the player's account as a BigDecimal.
     */
    public abstract BigDecimal getBalance(@NotNull final OfflinePlayer player);

    /**
     * Retrieves the name of the economy system.
     *
     * @return The name of the economy system as a String.
     */
    public abstract String getName();

    /**
     * Retrieves the simple name of the economy system. E.g. Vault
     *
     * @return The simple name of the economy system as a String.
     */
    public abstract String getSimpleName();

    /**
     * Sets up the economy system.
     *
     * @return True if the setup is successful, false otherwise.
     */
    public abstract boolean setupEconomy();

    /**
     * Checks if this economy is the payout economy.
     *
     * @return True if this economy is the payout economy, false otherwise.
     */
    public boolean isPayoutEconomy() {
        return this.economyModes.contains(EconomyMode.PAYOUT);
    }

    /**
     * Checks if this economy is the deposit economy.
     *
     * @return True if this economy is the deposit economy, false otherwise.
     */
    public boolean isDepositEconomy() {
        return this.economyModes.contains(EconomyMode.DEPOSIT);
    }

    /**
     * Checks if this economy is the withdrawal economy.
     *
     * @return True if this economy is the withdrawal economy, false otherwise.
     */
    public boolean isWithdrawEconomy() {
        return this.economyModes.contains(EconomyMode.WITHDRAWAL);
    }

    /**
     * Retrieves the list of economy modes.
     *
     * @return The list of economy modes.
     */
    public List<EconomyMode> getEconomyModes() {
        return economyModes;
    }
}