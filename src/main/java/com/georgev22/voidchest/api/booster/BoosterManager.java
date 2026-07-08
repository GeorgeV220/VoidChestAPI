package com.georgev22.voidchest.api.booster;

import com.georgev22.voidchest.api.storage.model.Booster;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.*;

/**
 * Central manager for booster calculations and strategy management.
 * <p>
 * The BoosterManager is responsible for:
 * <ul>
 *   <li>Collecting boosters from multiple holders</li>
 *   <li>Filtering boosters by type</li>
 *   <li>Applying stacking strategies</li>
 *   <li>Returning final calculated values</li>
 *   <li>Managing registered stacking strategies</li>
 *   <li>Cleaning up expired boosters</li>
 * </ul>
 * <p>
 * Example usage:
 * <pre>{@code
 * // Simple calculation with default strategy
 * BigDecimal finalPrice = boosterManager.calculate(
 *     BoosterType.SELL_PRICE,
 *     basePrice,
 *     playerData,
 *     sellChest
 * );
 *
 * // Detailed calculation for debugging
 * BoosterCalculationResult result = boosterManager.calculateDetailed(
 *     BoosterType.SELL_PRICE,
 *     basePrice,
 *     playerData,
 *     sellChest
 * );
 * System.out.println("Applied " + result.getAppliedBoosters().size() + " boosters");
 * System.out.println("Final multiplier: " + result.getCombinedMultiplier());
 *
 * // Custom strategy
 * BigDecimal value = boosterManager.calculate(
 *     BoosterType.SELL_PRICE,
 *     baseValue,
 *     BoosterStackingStrategy.ADDITIVE,
 *     playerData
 * );
 * }</pre>
 *
 * @see Booster
 * @see BoosterHolder
 * @see BoosterStackingStrategy
 * @see BoosterCalculationResult
 */
public interface BoosterManager {

    /**
     * Calculates the final value after applying boosters from all holders.
     * <p>
     * Uses the default stacking strategy (multiplicative).
     *
     * @param type      the booster type to apply
     * @param baseValue the base value before boosters
     * @param holders   the holders to collect boosters from
     * @return the final value after applying boosters
     */
    BigDecimal calculate(@NonNull BoosterType type, BigDecimal baseValue, @NonNull BoosterHolder... holders);

    /**
     * Calculates the final value after applying boosters with a specific strategy.
     *
     * @param type     the booster type to apply
     * @param baseValue the base value before boosters
     * @param strategy the stacking strategy to use
     * @param holders  the holders to collect boosters from
     * @return the final value after applying boosters
     */
    BigDecimal calculate(@NonNull BoosterType type, BigDecimal baseValue,
                         @NonNull BoosterStackingStrategy strategy,
                         @NonNull BoosterHolder... holders);

    /**
     * Calculates and returns detailed information about the calculation.
     * <p>
     * Uses the default stacking strategy (multiplicative).
     *
     * @param type      the booster type to apply
     * @param baseValue the base value before boosters
     * @param holders   the holders to collect boosters from
     * @return a detailed result object containing all calculation information
     */
    @NonNull BoosterCalculationResult calculateDetailed(@NonNull BoosterType type,
                                                        BigDecimal baseValue,
                                                        @NonNull BoosterHolder... holders);

    /**
     * Calculates and returns detailed information with a specific strategy.
     *
     * @param type      the booster type to apply
     * @param baseValue the base value before boosters
     * @param strategy  the stacking strategy to use
     * @param holders   the holders to collect boosters from
     * @return a detailed result object containing all calculation information
     */
    @NonNull BoosterCalculationResult calculateDetailed(@NonNull BoosterType type,
                                                        BigDecimal baseValue,
                                                        @NonNull BoosterStackingStrategy strategy,
                                                        @NonNull BoosterHolder... holders);

    /**
     * Calculates the combined multiplier of all active boosters from a single holder
     * using the default stacking strategy.
     * <p>
     * This method does NOT apply the multiplier to any base value — it only returns
     * the raw combined multiplier result.
     *
     * @param type   the booster type to calculate
     * @param holder the holder containing boosters
     * @return the combined multiplier (or {@code 1.0} if no boosters are present)
     * @see #calculateDetailed(BoosterType, BigDecimal, BoosterHolder...)
     * @see #calculateDetailed(BoosterType, BigDecimal, BoosterStackingStrategy, BoosterHolder...)
     */
    BigDecimal getTotalMultiplier(@NonNull BoosterType type,
                                  @NonNull BoosterHolder holder);

    /**
     * Calculates the combined multiplier of all active boosters from a single holder
     * using the specified stacking strategy.
     * <p>
     * This method does NOT apply the multiplier to any base value — it only returns
     * the raw combined multiplier result based on the given strategy.
     *
     * @param type     the booster type to calculate
     * @param strategy the stacking strategy to use for combining multipliers
     * @param holder   the holder containing boosters
     * @return the combined multiplier (or {@code 1.0} if no boosters are present)
     * @see #calculateDetailed(BoosterType, BigDecimal, BoosterHolder...)
     * @see #calculateDetailed(BoosterType, BigDecimal, BoosterStackingStrategy, BoosterHolder...)
     */
    BigDecimal getTotalMultiplier(@NonNull BoosterType type,
                                  @NonNull BoosterStackingStrategy strategy,
                                  @NonNull BoosterHolder holder);

    /**
     * Registers a stacking strategy with the given name.
     *
     * @param name     the name to register the strategy under
     * @param strategy the strategy to register
     * @throws IllegalArgumentException if a strategy with this name is already registered
     * @throws NullPointerException     if name or strategy is null
     */
    void registerStrategy(@NonNull String name, @NonNull BoosterStackingStrategy strategy);

    /**
     * Gets a registered stacking strategy by name.
     *
     * @param name the name of the strategy
     * @return an Optional containing the strategy if found, or empty if not
     */
    @NonNull Optional<BoosterStackingStrategy> getStrategy(@NonNull String name);

    /**
     * Checks if a strategy with the given name is registered.
     *
     * @param name the name to check
     * @return {@code true} if registered, {@code false} otherwise
     */
    boolean hasStrategy(@NonNull String name);

    /**
     * Returns all registered strategy names.
     *
     * @return an unmodifiable collection of strategy names
     */
    @NonNull Collection<String> getStrategyNames();

    /**
     * Sets the default stacking strategy used when no strategy is specified.
     *
     * @param strategy the default strategy
     * @throws NullPointerException if strategy is null
     */
    void setDefaultStrategy(@NonNull BoosterStackingStrategy strategy);

    /**
     * Sets the default stacking strategy by registered name.
     *
     * @param name the name of a registered strategy
     * @throws IllegalArgumentException if no strategy with this name is registered
     * @throws NullPointerException     if name is null
     */
    void setDefaultStrategy(@NonNull String name);

    /**
     * Returns the current default stacking strategy.
     *
     * @return the default strategy
     */
    @NonNull BoosterStackingStrategy getDefaultStrategy();

    /**
     * Cleans all expired boosters from the specified holders.
     *
     * @param holders the holders to clean
     * @return the total number of boosters removed
     */
    int cleanExpiredBoosters(@NonNull BoosterHolder... holders);

    /**
     * Registers a holder for periodic cleanup.
     * <p>
     * Registered holders will have their expired boosters cleaned
     * during periodic cleanup cycles.
     *
     * @param holder the holder to register
     * @see #performCleanup()
     */
    void registerHolderForCleanup(@NonNull BoosterHolder holder);

    /**
     * Unregisters a holder from periodic cleanup.
     *
     * @param holder the holder to unregister
     */
    void unregisterHolderForCleanup(@NonNull BoosterHolder holder);

    /**
     * Performs cleanup of expired boosters on all registered holders.
     *
     * @return the total number of boosters removed
     * @see #registerHolderForCleanup(BoosterHolder)
     */
    int performCleanup();

}
