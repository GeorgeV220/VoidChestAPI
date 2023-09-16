package com.georgev22.voidchest.api.bank;

import com.georgev22.voidchest.api.storage.data.IPlayerData;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;


/**
 * The IVoidChestBank interface provides methods for managing a VoidChest bank.
 */
public interface IVoidChestBank {

    /**
     * Deposits the specified amount to the VoidChest bank associated with the given player data.
     *
     * @param amount The amount to deposit as a BigDecimal.
     * @param data   The player data associated with the bank.
     * @return True if the deposit is successful, false otherwise.
     */
    boolean depositToBank(@NotNull final BigDecimal amount, @NotNull final IPlayerData data);

    /**
     * Retrieves the name of the VoidChest bank.
     *
     * @return The name of the VoidChest bank as a String.
     */
    String getName();

}
