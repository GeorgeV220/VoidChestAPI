package com.georgev22.voidchest.api.economy.player;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.List;

/**
 * The AEconomy abstract class that provides methods for managing player economy.<br>
 * Payout economy: The economy class is used to give the "rewards" when a sell is made by void chest.<br>
 * Charge economy: The economy class is used to add charge to void chests.<br>
 * Upgrades economy: The economy class is used for the upgrades in void chests.<br>
 * <p>
 * Only one economy of each mode can be hooked.
 */
public abstract class AEconomy implements Keyed {

    private final List<EconomyMode> economyModes;
    private final NamespacedKey key;

    protected AEconomy(NamespacedKey key) {
        this(key, EconomyMode.PAYOUT, EconomyMode.CHARGE, EconomyMode.UPGRADES);
    }

    public AEconomy(NamespacedKey key, EconomyMode... modes) {
        this.economyModes = List.of(modes);
        this.key = key;
    }

    /**
     * Withdraws the specified amount from the player's account.
     *
     * @param player The OfflinePlayer to withdraw from.
     * @param amount The amount to withdraw as a BigDecimal.
     * @return True if the withdrawal is successful, false otherwise.
     */
    public abstract boolean withdraw(@NonNull final OfflinePlayer player, final BigDecimal amount);

    /**
     * Deposits the specified amount into the player's account.
     *
     * @param player The OfflinePlayer to deposit to.
     * @param amount The amount to deposit as a BigDecimal.
     * @return True if the deposit is successful, false otherwise.
     */
    public abstract boolean deposit(@NonNull final OfflinePlayer player, final BigDecimal amount);

    /**
     * Retrieves the balance of the player's account.
     *
     * @param player The OfflinePlayer to retrieve the balance for.
     * @return The balance of the player's account as a BigDecimal.
     */
    public abstract BigDecimal getBalance(@NonNull final OfflinePlayer player);

    /**
     * Retrieves the name of the economy system.
     *
     * @return The name of the economy system as a String.
     */
    public abstract String getName();

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
     * Checks if this economy is the charge economy.
     *
     * @return True if this economy is the charge economy, false otherwise.
     */
    public boolean isChargeEconomy() {
        return this.economyModes.contains(EconomyMode.CHARGE);
    }

    /**
     * Checks if this economy is the upgrades' economy.
     *
     * @return True if this economy is the upgrades economy, false otherwise.
     */
    public boolean isUpgradesEconomy() {
        return this.economyModes.contains(EconomyMode.UPGRADES);
    }

    /**
     * Retrieves the list of economy modes.
     *
     * @return The list of economy modes.
     */
    public List<EconomyMode> getEconomyModes() {
        return economyModes;
    }

    @Override
    public @NonNull NamespacedKey getKey() {
        return this.key;
    }
}