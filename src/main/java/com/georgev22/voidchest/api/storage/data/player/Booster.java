package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.library.maps.ObjectMap;
import com.georgev22.library.utilities.Entity;
import org.jetbrains.annotations.ApiStatus;

/**
 * The Booster interface extends the Entity interface and provides methods for managing a booster.
 */
public interface Booster extends Entity {

    /**
     * Retrieves the booster value.
     *
     * @return The booster value as a double.
     * @deprecated Use {@link #booster(String)} instead.
     */
    @Deprecated(forRemoval = true, since = "2.0.0")
    double booster();

    /**
     * Retrieves the booster value.
     *
     * @param pluginName The plugin that added the booster.
     * @return The booster value as a double.
     */
    double booster(String pluginName);

    /**
     * Sets the booster value.
     *
     * @param booster The booster value to set as a double.
     * @deprecated Use {@link #booster(double, String)} instead.
     */
    @Deprecated(forRemoval = true, since = "2.0.0")
    void booster(double booster);

    /**
     * Sets the booster value.
     *
     * @param booster The booster value to set as a double.
     * @param plugin  The plugin that set the booster value.
     */
    void booster(double booster, String plugin);

    /**
     * Retrieves the formatted time left for the booster.
     *
     * @return The formatted time left as a String.
     * @deprecated Use {@link #boosterTimeLeft(String)} instead.
     */
    @Deprecated(forRemoval = true, since = "2.0.0")
    String boosterTimeLeft();

    /**
     * Retrieves the formatted time left for the booster.
     *
     * @param pluginName The plugin that added the booster.
     * @return The formatted time left as a String.
     */
    String boosterTimeLeft(String pluginName);

    /**
     * Retrieves the boost time in milliseconds.
     *
     * @return The boost time in milliseconds as a long.
     * @deprecated Use {@link #boostTime(String)} instead.
     */
    @Deprecated(forRemoval = true, since = "2.0.0")
    long boostTime();

    /**
     * Retrieves the boost time in milliseconds.
     *
     * @param pluginName The plugin that added the booster.
     * @return The boost time in milliseconds as a long.
     */
    long boostTime(String pluginName);

    /**
     * Sets the boost time in milliseconds.
     *
     * @param boostTime The boost time to set in milliseconds as a long.
     * @deprecated Use {@link #boostTime(long, String)} instead.
     */
    void boostTime(long boostTime);

    /**
     * Sets the boost time in milliseconds.
     *
     * @param plugin    The plugin that adds the booster.
     * @param boostTime The boost time to set in milliseconds as a long.
     */
    void boostTime(long boostTime, String plugin);

    /**
     * Checks if the booster for a specific plugin is still active.
     *
     * @param pluginName The plugin that added the booster.
     * @return true if the booster is active, false otherwise.
     */
    @ApiStatus.Experimental
    boolean isBoosterActive(String pluginName);

    /**
     * Retrieves the player's boosters.
     * The boosters are represented as a map where the key is the booster name (plugin name),
     * and the value is the corresponding booster strength.
     *
     * @return A map of boosters for the player.
     */
    @ApiStatus.Experimental
    ObjectMap<String, Double> getBoosters();

}