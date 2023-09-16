package com.georgev22.voidchest.api.banktnt;

import org.jetbrains.annotations.NotNull;

/**
 * The IBankTNTManager interface provides methods for managing Bank TNT.
 */
public interface IBankTNTManager {

    /**
     * Retrieves the Bank TNT instance.
     *
     * @return The Bank TNT instance as an IVoidChestBankTNT object.
     */
    @NotNull IVoidChestBankTNT bank();

    /**
     * Hooks the Bank TNT manager.
     */
    void hook();

}
