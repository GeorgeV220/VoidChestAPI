package com.georgev22.voidchest.api.integrations.economy.balance.modules;

import com.georgev22.voidchest.api.integrations.economy.balance.AEconomy;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;

/**
 * InvalidEconomy represents an invalid economy system. VoidChest or other plugins need to replace this with their own economy system.
 */
public class InvalidEconomy extends AEconomy {

    public InvalidEconomy() {
        super(NamespacedKey.fromString("voidchest:invalid"));
    }

    /**
     * Withdraws the specified amount from the player's account.
     *
     * @param player The OfflinePlayer to withdraw from.
     * @param amount The amount to withdraw as a BigDecimal.
     * @return True if the withdrawal is successful, false otherwise.
     */
    @Override
    public boolean withdraw(@NonNull OfflinePlayer player, BigDecimal amount) {
        return false;
    }

    /**
     * Deposits the specified amount into the player's account.
     *
     * @param player The OfflinePlayer to deposit to.
     * @param amount The amount to deposit as a BigDecimal.
     * @return True if the deposit is successful, false otherwise.
     */
    @Override
    public boolean deposit(@NonNull OfflinePlayer player, BigDecimal amount) {
        return false;
    }

    /**
     * Retrieves the balance of the player's account.
     *
     * @param player The OfflinePlayer to retrieve the balance for.
     * @return The balance of the player's account as a BigDecimal.
     */
    @Override
    public BigDecimal getBalance(@NonNull OfflinePlayer player) {
        return BigDecimal.ZERO;
    }

    /**
     * Retrieves the name of the economy system.
     *
     * @return The name of the economy system as a String.
     */
    @Override
    public String getName() {
        return "InvalidEconomy";
    }

    /**
     * Sets up the economy system.
     *
     * @return True if the setup is successful, false otherwise.
     */
    @Override
    public boolean setupEconomy() {
        return false;
    }
}
