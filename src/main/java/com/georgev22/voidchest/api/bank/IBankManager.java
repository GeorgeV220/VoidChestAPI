package com.georgev22.voidchest.api.bank;

import org.jetbrains.annotations.NotNull;

/**
 * The IBankManager interface provides methods for managing a bank.
 */
public interface IBankManager {

    /**
     * Retrieves the bank instance.
     *
     * @return The bank instance as an IVoidChestBank object.
     */
    @NotNull
    IVoidChestBank bank();

    /**
     * Hooks the bank manager.
     */
    void hook();

}
