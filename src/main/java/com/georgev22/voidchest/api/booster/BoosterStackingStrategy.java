package com.georgev22.voidchest.api.booster;

import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 * Strategy for combining multiple booster multipliers into a single value.
 * <p>
 * Different stacking strategies produce different results:
 * <pre>
 * Given: Player booster 2.0x, SellChest booster 1.5x
 *
 * MULTIPLICATIVE: 2.0 × 1.5 = 3.0x
 * ADDITIVE:       (2.0 - 1) + (1.5 - 1) + 1 = 2.5x
 * HIGHEST:        max(2.0, 1.5) = 2.0x
 * LOWEST:         min(2.0, 1.5) = 1.5x
 * AVERAGE:        (2.0 + 1.5) / 2 = 1.75x
 * </pre>
 * <p>
 * Custom strategies can be created by implementing this interface:
 * <pre>{@code
 * BoosterStackingStrategy custom = multipliers -> {
 *     // Custom logic here
 *     return result;
 * };
 * boosterManager.registerStrategy("my_custom", custom);
 * }</pre>
 *
 * @see BoosterManager#calculate(BoosterType, BigDecimal, BoosterHolder...)
 * @see BoosterManager#registerStrategy(String, BoosterStackingStrategy)
 */
@FunctionalInterface
public interface BoosterStackingStrategy {

    MathContext MC = MathContext.DECIMAL128;
    BigDecimal ONE = BigDecimal.ONE;

    /**
     * Calculates the combined multiplier from a list of individual multipliers.
     * <p>
     * If the list is empty, implementations should return {@code 1.0} (no change).
     *
     * @param multipliers the individual multipliers to combine (never null, may be empty)
     * @return the combined multiplier
     */
    BigDecimal calculate(@NonNull List<BigDecimal> multipliers);

    /**
     * Multiplicative stacking: multiplies all multipliers together.
     * <p>
     * Formula: {@code m1 × m2 × m3 × ...}
     * <p>
     * Example: 2.0 × 1.5 = 3.0
     */
    BoosterStackingStrategy MULTIPLICATIVE = multipliers -> {
        if (multipliers.isEmpty()) {
            return ONE;
        }
        BigDecimal result = ONE;
        for (BigDecimal m : multipliers) {
            result = result.multiply(m, MC);
        }
        return result;
    };

    /**
     * Additive stacking: adds the bonus portions together.
     * <p>
     * Formula: {@code 1 + (m1-1) + (m2-1) + (m3-1) + ...}
     * <p>
     * Example: 1 + (2.0-1) + (1.5-1) = 2.5
     */
    BoosterStackingStrategy ADDITIVE = multipliers -> {
        if (multipliers.isEmpty()) {
            return ONE;
        }
        BigDecimal bonusSum = BigDecimal.ZERO;
        for (BigDecimal m : multipliers) {
            bonusSum = bonusSum.add(m.subtract(ONE, MC), MC);
        }
        return ONE.add(bonusSum, MC);
    };

    /**
     * Highest wins: uses only the highest multiplier.
     * <p>
     * Formula: {@code max(m1, m2, m3, ...)}
     * <p>
     * Example: max(2.0, 1.5) = 2.0
     */
    BoosterStackingStrategy HIGHEST = multipliers -> {
        if (multipliers.isEmpty()) {
            return ONE;
        }
        BigDecimal max = multipliers.getFirst();
        for (int i = 1; i < multipliers.size(); i++) {
            BigDecimal m = multipliers.get(i);
            if (m.compareTo(max) > 0) {
                max = m;
            }
        }
        return max;
    };

    /**
     * Lowest wins: uses only the lowest multiplier.
     * <p>
     * Formula: {@code min(m1, m2, m3, ...)}
     */
    BoosterStackingStrategy LOWEST = multipliers -> {
        if (multipliers.isEmpty()) {
            return ONE;
        }
        BigDecimal min = multipliers.getFirst();
        for (int i = 1; i < multipliers.size(); i++) {
            BigDecimal m = multipliers.get(i);
            if (m.compareTo(min) < 0) {
                min = m;
            }
        }
        return min;
    };

    /**
     * Average: uses the average of all multipliers.
     * <p>
     * Formula: {@code (m1 + m2 + m3 + ...) / n}
     */
    BoosterStackingStrategy AVERAGE = multipliers -> {
        if (multipliers.isEmpty()) {
            return ONE;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal m : multipliers) {
            sum = sum.add(m, MC);
        }
        return sum.divide(BigDecimal.valueOf(multipliers.size()), MC);
    };
}