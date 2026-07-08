package com.georgev22.voidchest.api.events.booster;

import com.georgev22.voidchest.api.booster.BoosterCalculationResult;
import com.georgev22.voidchest.api.booster.BoosterManager;
import com.georgev22.voidchest.api.booster.BoosterType;
import com.georgev22.voidchest.api.events.VoidChestBaseEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.List;

/**
 * Fired when boosters are about to be applied to a value.
 * <p>
 * This event is cancellable. If cancelled, the original (unboosted) value
 * will be used instead.
 * <p>
 * The event can also be used to modify the final result by calling
 * {@link #setResultValue(double)}.
 *
 * @see BoosterManager#calculate(BoosterType, BigDecimal, com.georgev22.voidchest.api.booster.BoosterHolder...)
 */
public class BoosterApplyEvent extends VoidChestBaseEvent implements Cancellable {

    private final BoosterType type;
    private final double originalValue;
    private double resultValue;
    private final List<BoosterCalculationResult.BoosterSource> appliedBoosters;
    private boolean cancelled;

    private static final HandlerList handlerList = new HandlerList();

    /**
     * Constructs a new BoosterApplyEvent.
     *
     * @param type            the booster type being applied
     * @param originalValue   the value before boosters
     * @param resultValue     the value after boosters
     * @param appliedBoosters the boosters that were applied
     */
    public BoosterApplyEvent(@NonNull BoosterType type,
                             double originalValue,
                             double resultValue,
                             @NonNull List<BoosterCalculationResult.BoosterSource> appliedBoosters) {
        this.type = type;
        this.originalValue = originalValue;
        this.resultValue = resultValue;
        this.appliedBoosters = appliedBoosters;
        this.cancelled = false;
    }

    /**
     * Returns the booster type being applied.
     *
     * @return the booster type
     */
    public @NonNull BoosterType getType() {
        return type;
    }

    /**
     * Returns the original value before any boosters were applied.
     *
     * @return the original value
     */
    public double getOriginalValue() {
        return originalValue;
    }

    /**
     * Returns the calculated result value after boosters.
     * <p>
     * This value can be modified by listeners via {@link #setResultValue(double)}.
     *
     * @return the result value
     */
    public double getResultValue() {
        return resultValue;
    }

    /**
     * Sets the result value.
     * <p>
     * This allows listeners to modify the final value after all boosters
     * have been applied. Use with caution.
     *
     * @param resultValue the new result value
     */
    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }

    /**
     * Returns the list of boosters that were applied.
     *
     * @return an unmodifiable list of applied booster sources
     */
    public @NonNull List<BoosterCalculationResult.BoosterSource> getAppliedBoosters() {
        return appliedBoosters;
    }

    /**
     * Returns the combined multiplier from all applied boosters.
     *
     * @return the combined multiplier, or 1.0 if no boosters
     */
    public double getCombinedMultiplier() {
        if (originalValue == 0) {
            return resultValue == 0 ? 1.0 : Double.POSITIVE_INFINITY;
        }
        return resultValue / originalValue;
    }

    /**
     * Checks if any boosters were applied.
     *
     * @return {@code true} if boosters were applied, {@code false} otherwise
     */
    public boolean hadBoosters() {
        return !appliedBoosters.isEmpty();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}