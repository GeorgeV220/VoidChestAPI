package com.georgev22.voidchest.api.economy;

import org.jetbrains.annotations.Nullable;

/**
 * The IEconomyManager interface provides methods for managing an economy system.
 * Note: This manager hooks into an Economy Plugin to deposit and withdraw money but also get player balances.
 */
public interface IEconomyManager {

    /**
     * Hooks the {@link AEconomy} instance into the manager.
     *
     * @param mode The economy mode to hook.
     * @return True if the economy system is hooked, false otherwise. <p>
     * If the {@link EconomyMode} is already hooked, this method will return false.
     */
    boolean hook(EconomyMode mode);

    /**
     * Hooks the {@link AEconomy} instance into the manager.
     * <p>
     * This will override any previous {@link AEconomy} instance with the same mode(s).
     *
     * @param economy The economy system to hook.
     */
    void hook(AEconomy economy);

    /**
     * Checks if the economy with the specified mode is hooked.
     *
     * @param mode The economy mode to check.
     * @return True if the economy is hooked, false otherwise.
     */
    boolean isHooked(EconomyMode mode);

    /**
     * Retrieves the economy instance with the specified mode or null if it is not hooked.
     *
     * @param mode The economy mode to retrieve.
     * @return The economy instance with the specified mode or null if it is not hooked.
     */
    @Nullable AEconomy economy(EconomyMode mode);

}
