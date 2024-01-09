package com.georgev22.voidchest.api.storage.data.voidstorage;

/**
 * The Charge interface extends the Entity interface and provides methods for managing the charge state of a void chest.
 */
public interface Charge {

    /**
     * Retrieves the maximum charge time in seconds.
     *
     * @return The maximum charge time in seconds as a long.
     */
    long maxTimeInSeconds();

    /**
     * Checks if the charge is enabled.
     *
     * @return True if the charge is enabled, false otherwise.
     */
    boolean enabled();

    /**
     * Checks if the specified number of seconds can be added to the charge.
     *
     * @param secondsToAdd The number of seconds to add.
     * @return True if the seconds can be added to the charge, false otherwise.
     */
    boolean canAddCharge(final long secondsToAdd);

    /**
     * Checks if the void chest has fuel for charging.
     *
     * @return True if the void chest has fuel, false otherwise.
     */
    boolean hasFuel();

    /**
     * Retrieves the charge response indicating the state of the charge.
     *
     * @return The charge response as a ChargeResponse enum.
     */
    ChargeResponse chargeResponse();

    /**
     * Retrieves the current charge time in seconds.
     *
     * @return The current charge time in seconds as a long.
     */
    long chargeTime();

    /**
     * Sets the current charge time in seconds.
     *
     * @param chargeTime The charge time to set in seconds as a long.
     */
    void chargeTime(long chargeTime);

    /**
     * Adds the specified number of seconds to the current charge time.
     *
     * @param secondsToAdd The number of seconds to add to the charge time.
     */
    void addChargeTime(long secondsToAdd);

    /**
     * Retrieves the remaining charge time in seconds.
     *
     * @return The remaining charge time in seconds as a Long, or null if the charge is fully charged.
     */
    Long getChargeLeftSeconds();

    /**
     * Enum representing the charge response states.
     */
    enum ChargeResponse {
        NEED_FUEL, CHARGED
    }

}