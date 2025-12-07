package com.georgev22.voidchest.api.economy.profit;

import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * Selects the most appropriate {@link ProfitCalculator} based on a given strategy.
 * Supports selection based on either:
 * <ul>
 *     <li>{@link SelectorType#PRICE}: Select highest profit</li>
 *     <li>{@link SelectorType#WEIGHT}: Select lowest weight</li>
 * </ul>
 */
public class ProfitCalculatorSelector {

    private final Map<ProfitCalculator, Integer> calculators;

    /**
     * Creates a selector instance.
     *
     * @param calculators Map of calculators and their configured weight values
     */
    public ProfitCalculatorSelector(Map<ProfitCalculator, Integer> calculators) {
        this.calculators = Map.copyOf(calculators);
    }

    /**
     * Selects the best profit value based on the provided selection type.
     *
     * @param type      The selector strategy to use
     * @param voidChest VoidChest context
     * @param item      Target item
     * @param amount    Amount of the item
     * @return An {@link Optional} containing the best {@link BigDecimal} profit result,
     * or empty if no valid calculation could be performed.
     */
    public Optional<BigDecimal> selectBest(@NotNull SelectorType type,
                                           @NotNull IVoidChest voidChest,
                                           @NotNull ItemStack item,
                                           @NotNull BigInteger amount) {

        return switch (type) {
            case PRICE -> calculators.keySet().stream()
                    .map(calculator -> new AbstractMap.SimpleEntry<>(
                            calculator,
                            normalize(calculator.getProfit(voidChest, item, amount))
                    ))
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getValue);

            case WEIGHT -> calculators.entrySet().stream()
                    .min(Comparator.comparingInt(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .map(c -> normalize(c.getProfit(voidChest, item, amount)));

        };
    }

    /**
     * Returns immutable access to the registered calculators.
     *
     * @return Unmodifiable view of the calculator map
     */
    public Map<ProfitCalculator, Integer> getEntries() {
        return calculators;
    }

    /**
     * Available selection strategies.
     */
    public enum SelectorType {
        PRICE,
        WEIGHT
    }

    /**
     * Normalizes null profit values as zero.
     *
     * @param value Profit value
     * @return non-null BigDecimal value
     */
    private BigDecimal normalize(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}
