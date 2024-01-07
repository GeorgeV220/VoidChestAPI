package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.library.utilities.Entity;
import org.bukkit.plugin.Plugin;

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
     * Retrieves the booster value.
     *
     * @param plugin The plugin that added the booster.
     * @return The booster value as a double.
     */
    double booster(Plugin plugin);

    /**
     * Sets the booster value.
     *
     * @param booster The booster value to set as a double.
     */
    void booster(double booster);

    /**
     * Sets the booster value.
     *
     * @param booster The booster value to set as a double.
     * @param plugin  The plugin that set the booster value.
     */
    void booster(double booster, Plugin plugin);

    /**
     * Retrieves the formatted time left for the booster.
     *
     * @return The formatted time left as a String.
     */
    String boosterTimeLeft();

    /**
     * Retrieves the formatted time left for the booster.
     *
     * @param plugin The plugin that added the booster.
     * @return The formatted time left as a String.
     */
    String boosterTimeLeft(Plugin plugin);

    /**
     * Retrieves the boost time in milliseconds.
     *
     * @return The boost time in milliseconds as a long.
     */
    long boostTime();

    /**
     * Retrieves the boost time in milliseconds.
     *
     * @param plugin The plugin that added the booster.
     * @return The boost time in milliseconds as a long.
     */
    long boostTime(Plugin plugin);

    /**
     * Sets the boost time in milliseconds.
     *
     * @param boostTime The boost time to set in milliseconds as a long.
     */
    void boostTime(long boostTime);

    /**
     * Sets the boost time in milliseconds.
     *
     * @param plugin    The plugin that adds the booster.
     * @param boostTime The boost time to set in milliseconds as a long.
     */
    void boostTime(long boostTime, Plugin plugin);

}