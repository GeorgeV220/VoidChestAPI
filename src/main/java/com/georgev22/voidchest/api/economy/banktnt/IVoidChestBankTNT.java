package com.georgev22.voidchest.api.economy.banktnt;

import org.bukkit.Keyed;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * The IVoidChestBankTNT interface provides methods for managing VoidChest bank TNT.
 */
public interface IVoidChestBankTNT extends Keyed {

    /**
     * Deposits the specified amount of TNT to the bank associated with the given UUID.
     *
     * @param amount The amount of TNT to deposit.
     * @param id     The UUID of the bank to deposit to.
     * @return True if the deposit is successful, false otherwise.
     */
    boolean depositToBank(final int amount, final @NonNull UUID id);

    /**
     * Retrieves the simple name of the VoidChest bank TNT. E.g. FactionsUUID
     *
     * @return The simple name of the VoidChest bank TNT as a String.
     */
    String getName();
}
