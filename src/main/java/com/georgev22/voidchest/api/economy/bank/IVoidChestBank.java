package com.georgev22.voidchest.api.economy.bank;

import com.georgev22.voidchest.api.storage.data.IPlayerData;
import org.bukkit.Keyed;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;


/**
 * The IVoidChestBank interface provides methods for managing a VoidChest bank.
 */
public interface IVoidChestBank extends Keyed {

    /**
     * Deposits the specified amount to the VoidChest bank associated with the given player data.
     *
     * @param amount The amount to deposit as a BigDecimal.
     * @param data   The player data associated with the bank.
     * @return True if the deposit is successful, false otherwise.
     */
    boolean depositToBank(@NonNull final BigDecimal amount, @NonNull final IPlayerData data);

    /**
     * Retrieves the simple name of the VoidChest bank. E.g. SuperiorSkyblock2
     *
     * @return The simple name of the VoidChest bank as a String.
     */
    String getName();
}
