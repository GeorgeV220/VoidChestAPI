package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.library.utilities.Utils;
import org.jetbrains.annotations.ApiStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a collection of boosters associated with a player.
 *
 * @since 2.0.0
 */
@ApiStatus.AvailableSince(value = "2.0.0")
@ApiStatus.NonExtendable()
public class Boosters {

    /**
     * List of boosters stored in this collection.
     */
    private final List<Booster> boosters;

    /**
     * Constructs an empty Boosters collection.
     */
    public Boosters() {
        this.boosters = new ArrayList<>();
    }

    /**
     * Constructs a Boosters collection with the specified list of boosters.
     *
     * @param boosters The list of boosters to initialize the collection.
     */
    public Boosters(List<Booster> boosters) {
        this.boosters = boosters;
    }

    /**
     * Constructs a Boosters collection with a single booster.
     *
     * @param booster The booster to be added to the collection.
     */
    public Boosters(Booster booster) {
        this.boosters = new ArrayList<>();
        this.boosters.add(booster);
    }

    /**
     * Constructs a Boosters collection with multiple boosters.
     *
     * @param boosters The boosters to be added to the collection.
     */
    public Boosters(Booster... boosters) {
        this.boosters = new ArrayList<>();
        this.boosters.addAll(Arrays.asList(boosters));
    }

    /**
     * Gets the list of boosters stored in this collection.
     *
     * @return The list of boosters.
     */
    public List<Booster> getBoosters() {
        return this.boosters;
    }

    /**
     * Adds a booster to the collection.
     *
     * @param booster The booster to be added.
     * @return The Boosters object after adding the specified booster.
     */
    public Boosters addBooster(Booster booster) {
        this.boosters.add(booster);
        return this;
    }

    /**
     * Removes a booster from the collection.
     *
     * @param booster The booster to be removed.
     * @return The Boosters object after removing the specified booster.
     */
    public Boosters removeBooster(Booster booster) {
        this.boosters.remove(booster);
        return this;
    }

    /**
     * Removes a booster from the collection.
     *
     * @param pluginIdentifier The identifier of the plugin that created the booster.
     * @return The Boosters object after removing the specified booster.
     */
    public Boosters removeBooster(String pluginIdentifier) {
        this.boosters.removeIf(booster -> booster.pluginIdentifier() != null && booster.pluginIdentifier().equals(pluginIdentifier));
        return this;
    }

    /**
     * Calculates the total booster value from all active boosters.
     *
     * @return The total booster value.
     */
    public double booster() {
        return this.boosters.stream().filter(Booster::isBoosterActive).mapToDouble(Booster::booster).sum();
    }

    /**
     * Calculates the total boost time from all active boosters.
     *
     * @return The total boost time in milliseconds.
     */
    public long boostTime() {
        return this.boosters.stream().filter(Booster::isBoosterActive).mapToLong(Booster::boostTime).sum();
    }

    /**
     * Calculates and returns the time left for active boosters.
     *
     * @param input           The base time input.
     * @param secondInput     The label for seconds.
     * @param secondsInput    The label for plural seconds.
     * @param minuteInput     The label for minutes.
     * @param minutesInput    The label for plural minutes.
     * @param hourInput       The label for hours.
     * @param hoursInput      The label for plural hours.
     * @param dayInput        The label for days.
     * @param daysInput       The label for plural days.
     * @param invalidInput    The label for invalid input.
     * @param noActiveBooster The message when no active boosters are present.
     * @return The formatted time left for active boosters.
     */
    public String boosterTimeLeft(long input, String secondInput, String secondsInput, String minuteInput,
                                  String minutesInput, String hourInput, String hoursInput, String dayInput, String daysInput,
                                  String invalidInput, String noActiveBooster) {
        String returnValue;
        if (this.booster() == 1d) {
            returnValue = noActiveBooster;
        } else {
            returnValue = Utils.convertSeconds(((this.boostTime() - Instant.now().toEpochMilli()) / 1000) + 1,
                    secondInput,
                    secondsInput,
                    minuteInput,
                    minutesInput,
                    hourInput,
                    hoursInput,
                    dayInput,
                    daysInput,
                    invalidInput
            );
        }
        return returnValue;
    }
}