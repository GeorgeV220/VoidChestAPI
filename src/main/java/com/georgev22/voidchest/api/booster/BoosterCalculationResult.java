package com.georgev22.voidchest.api.booster;

import com.georgev22.voidchest.api.storage.model.Booster;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the result of a booster calculation.
 * <p>
 * This class provides detailed information about how boosters were applied,
 * including which boosters contributed and from which holders.
 *
 * @see BoosterManager#calculateDetailed(BoosterType, BigDecimal, BoosterHolder...)
 */
public final class BoosterCalculationResult {

    private final BoosterType type;
    private final BigDecimal originalValue;
    private BigDecimal finalValue;
    private final BoosterStackingStrategy strategy;
    private final List<BoosterSource> appliedBoosters;
    private final BigDecimal combinedMultiplier;

    private BoosterCalculationResult(@NonNull Builder builder) {
        this.type = builder.type;
        this.originalValue = builder.originalValue;
        this.finalValue = builder.finalValue;
        this.strategy = builder.strategy;
        this.appliedBoosters = List.copyOf(builder.appliedBoosters);
        this.combinedMultiplier = builder.combinedMultiplier;
    }

    /**
     * Returns the booster type that was calculated.
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
    public BigDecimal getOriginalValue() {
        return originalValue;
    }

    /**
     * Returns the final value after all boosters were applied.
     *
     * @return the final value
     */
    public BigDecimal getFinalValue() {
        return finalValue;
    }

    /**
     * Sets the final value after all boosters were applied.
     *
     * @param finalValue the new final value
     */
    public void setFinalValue(BigDecimal finalValue) {
        this.finalValue = finalValue;
    }

    /**
     * Returns the stacking strategy used for this calculation.
     *
     * @return the stacking strategy
     */
    public @NonNull BoosterStackingStrategy getStrategy() {
        return strategy;
    }

    /**
     * Returns the list of boosters that were applied.
     * <p>
     * The list is ordered by the holders they came from, then by booster
     * within each holder.
     *
     * @return an unmodifiable list of applied booster sources
     */
    public @NonNull List<BoosterSource> getAppliedBoosters() {
        return appliedBoosters;
    }

    /**
     * Returns the combined multiplier after stacking.
     * <p>
     * A value of 1.0 means no boosters were applied.
     *
     * @return the combined multiplier
     */
    public BigDecimal getCombinedMultiplier() {
        return combinedMultiplier;
    }

    /**
     * Checks if any boosters were applied in this calculation.
     *
     * @return {@code true} if boosters were applied, {@code false} otherwise
     */
    public boolean hadBoosters() {
        return !appliedBoosters.isEmpty();
    }

    /**
     * Returns the total increase from boosters.
     *
     * @return the increase amount (finalValue - originalValue)
     */
    public BigDecimal getIncrease() {
        return finalValue.subtract(originalValue);
    }

    /**
     * Returns the percentage increase from boosters.
     *
     * @return the percentage increase (0-100+), or 0 if no boosters
     */
    public BigDecimal getIncreasePercentage() {
        BigDecimal original = this.originalValue;
        BigDecimal fin = this.finalValue;

        if (original.compareTo(BigDecimal.ZERO) == 0) {
            return fin.compareTo(BigDecimal.ZERO) > 0
                    ? BigDecimal.valueOf(Double.POSITIVE_INFINITY)
                    : BigDecimal.ZERO;
        }

        BigDecimal difference = fin.subtract(original);

        BigDecimal ratio = difference.divide(original, BoosterStackingStrategy.MC);

        return ratio.multiply(BigDecimal.valueOf(100), BoosterStackingStrategy.MC);
    }

    @Override
    public String toString() {
        return "BoosterCalculationResult{" +
                "type=" + type +
                ", originalValue=" + originalValue +
                ", finalValue=" + finalValue +
                ", combinedMultiplier=" + combinedMultiplier +
                ", boostersApplied=" + appliedBoosters.size() +
                ", strategy=" + strategy.getClass().getSimpleName() +
                '}';
    }

    /**
     * Creates a new builder for constructing BoosterCalculationResult instances.
     *
     * @param type         the booster type
     * @param originalValue the original value
     * @return a new Builder instance
     */
    public static @NonNull Builder builder(@NonNull BoosterType type, BigDecimal originalValue) {
        return new Builder(type, originalValue);
    }

    /**
     * Builder for constructing {@link BoosterCalculationResult} instances.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {
        private final BoosterType type;
        private final BigDecimal originalValue;
        private BigDecimal finalValue;
        private BoosterStackingStrategy strategy;
        private final List<BoosterSource> appliedBoosters = new ArrayList<>();
        private BigDecimal combinedMultiplier = BigDecimal.ONE;

        private Builder(BoosterType type, BigDecimal originalValue) {
            this.type = Objects.requireNonNull(type, "type");
            this.originalValue = originalValue;
            this.finalValue = originalValue;
        }

        public Builder finalValue(BigDecimal finalValue) {
            this.finalValue = finalValue;
            return this;
        }

        public Builder strategy(@NonNull BoosterStackingStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public Builder addBooster(@NonNull Booster booster, @NonNull BoosterHolder holder) {
            this.appliedBoosters.add(new BoosterSource(booster, holder));
            return this;
        }

        public Builder combinedMultiplier(BigDecimal combinedMultiplier) {
            this.combinedMultiplier = combinedMultiplier;
            return this;
        }

        public @NonNull BoosterCalculationResult build() {
            Objects.requireNonNull(strategy, "strategy must be set");
            return new BoosterCalculationResult(this);
        }
    }

    /**
     * Represents a single booster that was applied in a calculation,
     * along with its source holder.
     */
    public static final class BoosterSource {
        private final Booster booster;
        private final BoosterHolder holder;

        public BoosterSource(@NonNull Booster booster, @NonNull BoosterHolder holder) {
            this.booster = booster;
            this.holder = holder;
        }

        /**
         * Returns the booster that was applied.
         *
         * @return the booster
         */
        public @NonNull Booster getBooster() {
            return booster;
        }

        /**
         * Returns the holder that provided this booster.
         *
         * @return the holder
         */
        public @NonNull BoosterHolder getHolder() {
            return holder;
        }

        @Override
        public String toString() {
            return "BoosterSource{" +
                    "booster=" + booster.getUniqueId() +
                    ", multiplier=" + booster.getMultiplier() +
                    ", holder=" + holder.getHolderTypeName() +
                    "(" + holder.getUniqueId() + ")" +
                    '}';
        }
    }
}