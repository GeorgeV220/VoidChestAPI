package com.georgev22.voidchest.api.storage.model;

import com.georgev22.voidchest.api.booster.BoosterHolder;
import com.georgev22.voidchest.api.booster.BoosterManager;
import com.georgev22.voidchest.api.booster.BoosterType;
import com.google.gson.JsonObject;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a booster that can be applied to modify values.
 * <p>
 * Boosters are persistent entities that can be attached to any {@link BoosterHolder}.
 * They support expiration-based lifetimes and carry a multiplier value.
 * <p>
 * Example usage:
 * <pre>{@code
 * Booster booster = entityManager.create("booster", b -> {
 *     b.setType(BoosterType.SELL_PRICE);
 *     b.setMultiplier(2.0);
 *     b.setStartTime(System.currentTimeMillis());
 *     b.setEndTime(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
 * });
 * holder.addBooster(booster);
 * }</pre>
 *
 * @see BoosterHolder
 * @see BoosterType
 * @see BoosterManager
 */
public class Booster extends Entity {

    protected BoosterType type;
    protected BigDecimal multiplier;
    protected long startTime;
    protected long endTime;

    /**
     * Constructs a new Booster with the specified unique identifier.
     *
     * @param uniqueId the unique identifier for this booster
     * @throws NullPointerException if uniqueId is null
     */
    public Booster(@NonNull UUID uniqueId) {
        super(uniqueId);
    }

    /**
     * Returns the type of this booster.
     *
     * @return the booster type
     */
    public @NonNull BoosterType getType() {
        return type;
    }

    /**
     * Sets the type of this booster.
     *
     * @param type the booster type
     * @throws NullPointerException if type is null
     */
    public void setType(@NonNull BoosterType type) {
        this.type = Objects.requireNonNull(type, "type");
    }

    /**
     * Returns the multiplier value of this booster.
     * <p>
     * A multiplier of 1.0 means no change, 2.0 means double, 0.5 means half.
     *
     * @return the multiplier value
     */
    public BigDecimal getMultiplier() {
        return multiplier;
    }

    /**
     * Sets the multiplier value of this booster.
     *
     * @param multiplier the multiplier value (should typically be positive)
     */
    public void setMultiplier(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Returns the start time of this booster in milliseconds since epoch.
     *
     * @return the start time
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of this booster.
     *
     * @param startTime the start time in milliseconds since epoch
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of this booster in milliseconds since epoch.
     * <p>
     * A value of {@link #PERMANENT} (-1) indicates this booster never expires.
     *
     * @return the end time, or {@link #PERMANENT} if permanent
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of this booster.
     *
     * @param endTime the end time in milliseconds since epoch, or {@link #PERMANENT} for permanent
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /** Constant indicating a booster never expires. */
    public static final long PERMANENT = -1L;

    /**
     * Checks if this booster is permanent (never expires).
     *
     * @return {@code true} if permanent, {@code false} otherwise
     */
    public boolean isPermanent() {
        return endTime == PERMANENT;
    }

    /**
     * Checks if this booster has expired.
     * <p>
     * Permanent boosters never expire. Timed boosters expire when
     * the current time exceeds the end time.
     *
     * @return {@code true} if expired, {@code false} otherwise
     */
    public boolean isExpired() {
        if (isPermanent()) {
            return false;
        }
        return System.currentTimeMillis() > endTime;
    }

    /**
     * Checks if this booster is currently active.
     * <p>
     * A booster is active if the current time is within its start and end times.
     *
     * @return {@code true} if active, {@code false} otherwise
     */
    public boolean isActive() {
        long now = System.currentTimeMillis();
        if (now < startTime) {
            return false;
        }
        if (isPermanent()) {
            return true;
        }
        return now <= endTime;
    }

    /**
     * Returns the remaining time until expiration in milliseconds.
     * <p>
     * Returns {@link #PERMANENT} for permanent boosters,
     * 0 for expired boosters, or the remaining milliseconds otherwise.
     *
     * @return the remaining time in milliseconds, {@link #PERMANENT}, or 0
     */
    public long getRemainingTime() {
        if (isPermanent()) {
            return PERMANENT;
        }
        long remaining = endTime - System.currentTimeMillis();
        return Math.max(0, remaining);
    }

    @Override
    public @NonNull JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("uniqueId", uniqueId.toString());
        json.addProperty("type", type.toString());
        json.addProperty("multiplier", multiplier);
        json.addProperty("startTime", startTime);
        json.addProperty("endTime", endTime);
        return json;
    }

    @Override
    public @NonNull String toJsonString(boolean pretty) {
        return toJson().toString();
    }

    @Override
    public String toString() {
        return "Booster{" +
                "uniqueId=" + uniqueId +
                ", type=" + type +
                ", multiplier=" + multiplier +
                ", startTime=" + startTime +
                ", endTime=" + (isPermanent() ? "PERMANENT" : endTime) +
                '}';
    }
}