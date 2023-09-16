package com.georgev22.voidchest.api.economy;

/**
 * The IEconomyManager interface provides methods for managing an economy system.
 */
public interface IEconomyManager {

    /**
     * Hooks the economy manager.
     */
    void hook();

    /**
     * Retrieves the economy instance.
     *
     * @return The economy instance as an IEconomy object.
     */
    IEconomy economy();

}
