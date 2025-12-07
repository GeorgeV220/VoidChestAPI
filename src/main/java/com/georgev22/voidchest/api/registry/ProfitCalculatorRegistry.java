package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.economy.profit.ProfitCalculator;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

/**
 * Registry for managing {@link ProfitCalculator} implementations.
 * <p>
 * Each calculator is uniquely identified by a {@link NamespacedKey}
 * and can be used by the {@link ProfitCalculatorSelectorRegistry} to
 * construct selector logic per VoidChest type.
 * <p>
 * This registry allows plugins to register custom profit calculation
 * mechanisms for item valuation inside VoidChests.
 *
 * <p>Example registration:</p>
 * <pre>{@code
 * ProfitCalculator calculator = new MyCustomCalculator();
 * ProfitCalculatorRegistry.getInstance().register(calculator);
 * }</pre>
 *
 * <p>
 * After registering calculators, users should call
 * {@link ProfitCalculatorSelectorRegistry#reloadSelectors()}
 * to ensure selectors become aware of the newly available calculators.
 * </p>
 */
public final class ProfitCalculatorRegistry
        extends AbstractRegistry<NamespacedKey, ProfitCalculator> {

    private static final ProfitCalculatorRegistry INSTANCE = new ProfitCalculatorRegistry();

    /**
     * Private constructor for enforcing singleton usage.
     *
     * @see #getInstance()
     */
    private ProfitCalculatorRegistry() {
    }

    /**
     * Retrieves the global instance of this registry.
     *
     * @return the singleton instance
     */
    public static ProfitCalculatorRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Registers a {@link ProfitCalculator} into the registry using its assigned
     * {@link NamespacedKey} as the unique identifier.
     *
     * @param calculator the calculator instance to register
     * @throws IllegalArgumentException if a calculator is already registered
     *                                  under the same key or if the key is null
     */
    public void register(@NotNull ProfitCalculator calculator) throws IllegalArgumentException {
        super.register(calculator.getKey(), calculator);
    }

    /**
     * Registers or replaces an existing {@link ProfitCalculator} under the same key.
     * <p>
     * If a calculator with the specified key already exists, it will be overwritten.
     *
     * @param calculator the calculator instance to register or replace
     * @return {@code true} if an existing calculator was replaced,
     * {@code false} if it was newly registered
     */
    public boolean replaceOrRegister(@NotNull ProfitCalculator calculator) {
        return super.replaceOrRegister(calculator.getKey(), calculator);
    }
}
