package com.georgev22.voidchest.api.voideconomy;

/**
 * The IVoidEconomyManager interface provides methods for managing the VoidChest economy.
 */
public interface IVoidEconomyManager {

    /**
     * Hooks into the economy system.
     */
    void hook();

    /**
     * Hooks the specified VoidEconomy instance into the economy system.
     *
     * @param instance The VoidEconomy instance to hook.
     */
    void hookVoidEconomy(IVoidEconomy instance);

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
