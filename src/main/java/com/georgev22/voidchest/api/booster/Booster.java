package com.georgev22.voidchest.api.booster;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a money booster used within the VoidChest plugin.
 * <p>
 * Money boosters temporarily increase the amount of money earned from selling items
 * through VoidChests. Each booster has a unique identifier, a multiplier amount,
 * a start time, and a remaining duration until expiration.
 *
 * <p>Example usage:
 * <pre>{@code
 * UUID id = booster.boosterIdentifier();
 * BigDecimal amount = booster.amount(); // e.g. 0.5 = +50% money boost
 * if (!booster.isExpired()) {
 *     BigDecimal baseMoney = new BigDecimal("100");
 *     BigDecimal boostedMoney = baseMoney.add(baseMoney.multiply(amount));
 *     // boostedMoney = 150 if amount = 0.5
 * }
 * }</pre>
 */
public interface Booster {

    /**
     * Returns the unique identifier for this booster.
     * <p>
     * This identifier can be used to reference, modify, or remove the booster
     * from the active boosters list within VoidChest.
     *
     * @return the UUID identifying this booster
     */
    UUID boosterIdentifier();

    /**
     * Returns the money boost multiplier amount of this booster.
     * <p>
     * For example, an amount of {@code 0.5} indicates a +50% boost to the base money value.
     * <br>
     * Calculation example:
     * <pre>{@code
     * Base money: 100
     * Booster amount: 0.5
     * Final money: 100 + (100 * 0.5) = 150
     * }</pre>
     *
     * @return the boost multiplier as a BigDecimal
     */
    BigDecimal amount();

    /**
     * Returns the remaining duration of this booster in seconds.
     * <p>
     * If the booster has expired, this returns {@code 0}.
     *
     * @return time left in seconds, or 0 if expired
     */
    long timeLeft();

    /**
     * Returns the start time of this booster in milliseconds since the Unix epoch.
     * <p>
     * This indicates when the booster was first activated.
     *
     * @return the booster start time in milliseconds
     */
    long startTime();

    /**
     * Returns whether this booster has expired.
     * <p>
     * Default implementation based on {@link #timeLeft()}.
     *
     * @return true if expired, false otherwise
     */
    default boolean isExpired() {
        return timeLeft() <= 0;
    }
}
