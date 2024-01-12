package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.library.maps.HashObjectMap;
import com.georgev22.library.utilities.Utils;
import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.event.events.booster.BoosterAddEvent;
import com.georgev22.voidchest.api.event.events.booster.BoosterRemoveEvent;
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
@ApiStatus.NonExtendable
public class Boosters {

    /**
     * List of boosters stored in this collection.
     */
    private final HashObjectMap<String, Booster> boosters;

    /**
     * Constructs an empty Boosters collection.
     */
    public Boosters() {
        this.boosters = new HashObjectMap<>();
    }

    /**
     * Constructs a Boosters collection with the specified list of boosters.
     *
     * @param boosters The list of boosters to initialize the collection.
     */
    public Boosters(List<Booster> boosters) {
        this.boosters = new HashObjectMap<>();
        boosters.forEach(this::addBooster);
    }

    /**
     * Constructs a Boosters collection with a single booster.
     *
     * @param booster The booster to be added to the collection.
     */
    public Boosters(Booster booster) {
        this.boosters = new HashObjectMap<>();
        this.addBooster(booster);
    }

    /**
     * Constructs a Boosters collection with multiple boosters.
     *
     * @param boosters The boosters to be added to the collection.
     */
    public Boosters(Booster... boosters) {
        this.boosters = new HashObjectMap<>();
        Arrays.stream(boosters).forEach(this::addBooster);
    }

    /**
     * Gets the list of boosters stored in the HashObjectMap.
     *
     * @return The list of boosters.
     */
    public List<Booster> getBoostersAsList() {
        return new ArrayList<>(this.boosters.values());
    }

    /**
     * Gets the boosters stored in the HashObjectMap.
     *
     * @return The HashObjectMap containing boosters.
     */
    public HashObjectMap<String, Booster> getBoosters() {
        return boosters;
    }

    /**
     * Adds a booster to the collection.
     * If a booster with the same plugin identifier already exists, it will be replaced.
     *
     * @param booster The booster to be added or replaced.
     * @return The Boosters object after adding or replacing the specified booster.
     */
    public Boosters addBooster(Booster booster) {
        if (booster == null) return this;
        BoosterAddEvent boosterAddEvent = new BoosterAddEvent(this, booster);
        VoidChestAPI.getInstance().eventManager().callEvent(boosterAddEvent);
        if (boosterAddEvent.isCancelled()) return this;
        this.boosters.append(booster.pluginIdentifier(), booster);
        return this;
    }

    /**
     * Removes a booster from the collection.
     *
     * @param booster The booster to be removed.
     * @return The Boosters object after removing the specified booster.
     */
    public Boosters removeBooster(Booster booster) {
        if (booster == null) return this;
        if (!this.boosters.containsKey(booster.pluginIdentifier())) return this;
        BoosterRemoveEvent boosterRemoveEvent = new BoosterRemoveEvent(this, booster);
        VoidChestAPI.getInstance().eventManager().callEvent(boosterRemoveEvent);
        if (boosterRemoveEvent.isCancelled()) return this;
        booster.booster(0d);
        booster.boostTime(0L);
        this.boosters.remove(booster.pluginIdentifier());
        return this;
    }

    /**
     * Removes a booster from the collection.
     *
     * @param pluginIdentifier The identifier of the plugin that created the booster.
     * @return The Boosters object after removing the specified booster.
     */
    public Boosters removeBooster(String pluginIdentifier) {
        if (pluginIdentifier == null) return this;
        if (!this.boosters.containsKey(pluginIdentifier)) return this;
        return this.removeBooster(this.boosters.get(pluginIdentifier));
    }

    /**
     * Calculates the total booster value from all active boosters.
     *
     * @return The total booster value.
     */
    public double booster() {
        if (this.boosters.isEmpty()) return 1;
        double booster = this.boosters.values().stream().filter(Booster::isBoosterActive).mapToDouble(value -> value.booster() > 1D ? value.booster() : 0D).sum();
        if (booster < 1) booster = 1;
        return booster;
    }

    /**
     * Calculates the total boost time from all active boosters.
     *
     * @return The total boost time in milliseconds.
     */
    public long boostTime() {
        if (this.boosters.isEmpty()) return 0;
        if (this.boosters.values().stream().noneMatch(Booster::isBoosterActive)) return 0;
        long boostTime = this.boosters.values().stream().filter(Booster::isBoosterActive).mapToLong(Booster::boostTime).sum();
        if (boostTime < 0) boostTime = 0;
        return boostTime;
    }

    /**
     * Calculates and returns the time left for active boosters.
     *
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
    public String boosterTimeLeft(String secondInput, String secondsInput, String minuteInput,
                                  String minutesInput, String hourInput, String hoursInput,
                                  String dayInput, String daysInput, String invalidInput,
                                  String noActiveBooster) {
        String returnValue;
        long boostTime = this.boostTime();
        if (this.booster() <= 1d || boostTime <= 0L || boostTime <= Instant.now().toEpochMilli()) {
            returnValue = noActiveBooster;
        } else {
            returnValue = Utils.convertSeconds(((boostTime - Instant.now().toEpochMilli()) / 1000) + 1,
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