package com.georgev22.voidchest.api.booster;

import com.georgev22.voidchest.api.booster.strategies.*;
import org.bukkit.Keyed;
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
public interface BoosterStackingStrategy extends Keyed {

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
    BoosterStackingStrategy MULTIPLICATIVE = new MultiplicativeStrategy();

    /**
     * Additive stacking: adds the bonus portions together.
     * <p>
     * Formula: {@code 1 + (m1-1) + (m2-1) + (m3-1) + ...}
     * <p>
     * Example: 1 + (2.0-1) + (1.5-1) = 2.5
     */
    BoosterStackingStrategy ADDITIVE = new AdditiveStrategy();

    /**
     * Highest wins: uses only the highest multiplier.
     * <p>
     * Formula: {@code max(m1, m2, m3, ...)}
     * <p>
     * Example: max(2.0, 1.5) = 2.0
     */
    BoosterStackingStrategy HIGHEST = new HighestStrategy();

    /**
     * Lowest wins: uses only the lowest multiplier.
     * <p>
     * Formula: {@code min(m1, m2, m3, ...)}
     */
    BoosterStackingStrategy LOWEST = new LowestStrategy();

    /**
     * Average: uses the average of all multipliers.
     * <p>
     * Formula: {@code (m1 + m2 + m3 + ...) / n}
     */
    BoosterStackingStrategy AVERAGE = new AverageStrategy();
}