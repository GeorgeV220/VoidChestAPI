package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.library.utilities.Entity;

/**
 * The Booster interface extends the Entity interface and provides methods for managing a booster.
 */
public interface Booster extends Entity {

    /**
     * Retrieves the booster value.
     *
     * @return The booster value as a double.
     */
    double booster();

    /**
     * Sets the booster value.
     *
     * @param booster The booster value to set as a double.
     */
    void booster(double booster);

    /**
     * Retrieves the formatted time left for the booster.
     *
     * @return The formatted time left as a String.
     */
    String boosterTimeLeft();

    /**
     * Retrieves the boost time in milliseconds.
     *
     * @return The boost time in milliseconds as a long.
     */
    long boostTime();

    /**
     * Sets the boost time in milliseconds.
     *
     * @param boostTime The boost time to set in milliseconds as a long.
     */
    void boostTime(long boostTime);

}