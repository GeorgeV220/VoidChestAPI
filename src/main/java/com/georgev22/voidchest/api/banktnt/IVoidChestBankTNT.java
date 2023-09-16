package com.georgev22.voidchest.api.banktnt;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The IVoidChestBankTNT interface provides methods for managing VoidChest bank TNT.
 */
public interface IVoidChestBankTNT {

    /**
     * Deposits the specified amount of bank TNT to the bank associated with the given UUID.
     *
     * @param amount The amount of bank TNT to deposit.
     * @param id     The UUID of the bank to deposit to.
     * @return True if the deposit is successful, false otherwise.
     */
    boolean depositToBank(final int amount, final @NotNull UUID id);

    /**
     * Retrieves the name of the VoidChest bank TNT.
     *
     * @return The name of the VoidChest bank TNT as a String.
     */
    String getName();

}
