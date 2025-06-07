package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.voidchest.api.utilities.CustomData;

/**
 * The Booster interface provides methods for managing a booster.
 */
public interface Booster {

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

    /**
     * Checks if the booster is still active.
     *
     * @return true if the booster is active, false otherwise.
     */
    boolean isBoosterActive();

    /**
     * Retrieves the identifier of the plugin that created the booster.
     * This can be the plugin name or anything else that identifies the plugin.
     * The default VoidChest plugin identifier is "VoidChest".
     *
     * @return The identifier of the plugin as a String.
     */
    String pluginIdentifier();

    /**
     * Retrieves the custom data associated with the booster.
     *
     * @return The custom data associated with the booster.
     */
    CustomData customData();

}
