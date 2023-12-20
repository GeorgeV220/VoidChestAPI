package com.georgev22.voidchest.api.economy;

/**
 * The IEconomyManager interface provides methods for managing an economy system.
 * Note: This manager hooks into an Economy Plugin to deposit and withdraw money but also get player balances.
 */
public interface IEconomyManager {

    /**
     * Hooks the economy manager.
     */
    void hook();

    /**
     * Checks if the economy system is hooked.
     *
     * @return True if the economy system is hooked, false otherwise.
     */
    boolean isHooked();

    /**
     * Retrieves the economy instance.
     *
     * @return The economy instance as an IEconomy object.
     */
    IEconomy economy();

}
