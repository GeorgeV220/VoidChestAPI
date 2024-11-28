package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;

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
     * Adds custom data to the Booster with the specified key and value.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated Booster with the added custom data
     */
    default Booster addCustomData(String key, Object value) {
        this.getCustomData().append(key, value);
        return this;
    }

    /**
     * Adds custom data to the Booster with the specified key and value if the key does not already exist.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated Booster with the added custom data (if the key did not already exist)
     */
    default Booster addCustomDataIfNotExists(String key, Object value) {
        this.getCustomData().appendIfTrue(key, value, !this.getCustomData().containsKey(key));
        return this;
    }

    /**
     * Retrieves the value of the custom data associated with the specified key.
     *
     * @param key the key of the custom data
     * @param <T> the type of the value to retrieve
     * @return the value associated with the specified key, or {@code null} if the key does not exist
     */
    default <T> T getCustomData(String key) {
        return (T) getCustomData().get(key);
    }

    /**
     * Retrieves the map of custom data associated with the Booster.
     *
     * @return the {@link ConcurrentObjectMap} containing the custom data of the player booster
     */
    ConcurrentObjectMap<String, Object> getCustomData();

}
