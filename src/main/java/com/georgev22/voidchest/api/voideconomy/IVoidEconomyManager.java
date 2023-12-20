package com.georgev22.voidchest.api.voideconomy;

/**
 * The IVoidEconomyManager interface provides methods for managing the VoidChest economy.
 * Note: This manager hooks into a Shop plugin to retrieve item prices.
 */
public interface IVoidEconomyManager {

    /**
     * Hooks into the economy system.
     */
    void hook();

    /**
     * Checks if the economy system is hooked.
     *
     * @return True if the economy system is hooked, false otherwise.
     */
    boolean isHooked();

    /**
     * Retrieves the VoidEconomy instance.
     *
     * @return The VoidEconomy instance.
     */
    IVoidEconomy economy();

}
