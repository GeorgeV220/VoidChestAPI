package com.georgev22.voidchest.api.economy.profit;

import com.georgev22.voidchest.api.datastructures.maps.UnmodifiableObjectMap;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a selector that determines the most appropriate {@link ProfitCalculator}
 * for a given item and amount based on a defined selection strategy.
 * <p>
 * Two strategies are currently supported:
 * <ul>
 *   <li><b>PRICE</b> — Selects the highest profit value.</li>
 *   <li><b>WEIGHT</b> — Sorts by lowest weight first and returns the first calculator
 *       that provides a profit greater than zero. If none provide a positive value,
 *       returns {@link BigDecimal#ZERO}.</li>
 * </ul>
 * <p>
 * A selector contains a set of available {@link ProfitCalculator}s with associated
 * weight values which determine selection priority.
 */
public class ProfitCalculatorSelector {

    /**
     * Map of calculators and their associated weight priority values.
     */
    private final UnmodifiableObjectMap<ProfitCalculator, Integer> calculators;

    /**
     * Creates a selector instance.
     *
     * @param calculators Map linking {@link ProfitCalculator}s to their weight priority values.
     *                    Lower weight = higher priority for selection.
     */
    public ProfitCalculatorSelector(@NonNull Map<ProfitCalculator, Integer> calculators) {
        this.calculators = new UnmodifiableObjectMap<>(calculators);
    }

    /**
     * Selects the best profit value based on the provided selection type.
     *
     * @param type      The selection strategy to apply
     * @param voidChest The VoidChest performing the calculation context
     * @param item      The item being evaluated
     * @param amount    The amount of the item being evaluated
     * @return An {@link Optional} wrapping the resulting profit value. Always present in the WEIGHT case.
     */
    public BigDecimal selectBest(@NonNull SelectorType type,
                                 @NonNull IVoidChest voidChest,
                                 @NonNull ItemStack item,
                                 @NonNull BigInteger amount) {
        return selectBest(type, voidChest, item, amount, calculators);
    }

    /**
     * Static selector method used for temporary selector evaluations without
     * instantiating a new {@link ProfitCalculatorSelector} object.
     *
     * @param type        The selection strategy
     * @param voidChest   The VoidChest performing the calculation context
     * @param item        The item being evaluated
     * @param amount      The quantity of the item
     * @param calculators The calculators to evaluate
     * @return An {@link Optional} profit value determined by the strategy
     */
    public static BigDecimal selectBest(@NonNull SelectorType type,
                                        @NonNull IVoidChest voidChest,
                                        @NonNull ItemStack item,
                                        @NonNull BigInteger amount,
                                        @NonNull Map<ProfitCalculator, Integer> calculators) {

        return switch (type) {
            case PRICE -> calculators.keySet().stream()
                    .map(calculator -> normalize(calculator.getProfit(voidChest, item, amount)))
                    .max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

            case WEIGHT -> {
                for (Map.Entry<ProfitCalculator, Integer> entry :
                        calculators.entrySet().stream()
                                .sorted(Map.Entry.comparingByValue()) // lowest weight first
                                .toList()) {

                    BigDecimal profit = normalize(entry.getKey().getProfit(voidChest, item, amount));
                    if (profit.compareTo(BigDecimal.ZERO) > 0) {
                        yield profit;
                    }
                }
                yield BigDecimal.ZERO;
            }
        };
    }

    /**
     * Retrieves the calculators and their weight mappings used by this selector.
     *
     * @return An unmodifiable view of internal calculator mappings
     */
    public @NonNull UnmodifiableObjectMap<ProfitCalculator, Integer> getEntries() {
        return calculators;
    }

    /**
     * Strategies for selecting profit calculation logic.
     */
    public enum SelectorType {
        /**
         * Selects the highest profit among all calculators.
         */
        PRICE,

        /**
         * Selects the first calculator with a positive profit after sorting by lowest weight first.
         * If no positive profit values exist, returns zero.
         */
        WEIGHT;

        /**
         * Retrieves the selector type from a string.
         *
         * @param name The name of the selector type
         * @return The selector type, or WEIGHT if the name is invalid
         */
        public static SelectorType fromString(@NonNull String name) {
            try {
                return valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                return WEIGHT;
            }
        }
    }

    /**
     * Ensures that all profit results are non-null.
     *
     * @param value The raw profit value returned by a calculator
     * @return A non-null {@link BigDecimal} (zero fallback)
     */
    private static @NonNull BigDecimal normalize(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}
