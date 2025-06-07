package com.georgev22.voidchest.api.storage.data.player;

import com.georgev22.voidchest.api.events.booster.BoosterAddEvent;
import com.georgev22.voidchest.api.events.booster.BoosterRemoveEvent;
import com.georgev22.voidchest.api.maps.HashObjectMap;
import com.georgev22.voidchest.api.utilities.Utils;
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
     * This method will first fire a {@link BoosterAddEvent}, and if the event is cancelled,
     * the booster will not be added.
     *
     * @param booster The booster to be added or replaced.
     * @return The Boosters object after attempting to add or replace the specified booster.
     * If the event was cancelled, the booster will not be added, and the original Boosters object is returned.
     */
    public Boosters addBooster(Booster booster) {
        if (booster == null) return this;
        BoosterAddEvent boosterAddEvent = new BoosterAddEvent(this, booster).call();
        if (boosterAddEvent.isCancelled()) return this;
        return this.addBoosterSilently(booster);
    }

    /**
     * Adds a booster to the collection without firing any events.
     * If a booster with the same plugin identifier already exists, it will be replaced.
     * This method silently adds or replaces the booster without triggering any events.
     *
     * @param booster The booster to be added or replaced.
     * @return The Boosters object after adding or replacing the specified booster.
     */
    public Boosters addBoosterSilently(Booster booster) {
        if (booster == null) return this;
        this.boosters.append(booster.pluginIdentifier(), booster);
        return this;
    }

    /**
     * Removes a booster from the collection.
     * This method fires a {@link BoosterRemoveEvent}, and if the event is cancelled,
     * the booster will not be removed.
     *
     * @param booster The booster to be removed.
     * @return The Boosters object after attempting to remove the specified booster.
     * If the event was cancelled, the booster will not be removed, and the original Boosters object is returned.
     */
    public Boosters removeBooster(Booster booster) {
        if (booster == null) return this;
        if (!this.boosters.containsKey(booster.pluginIdentifier())) return this;
        BoosterRemoveEvent boosterRemoveEvent = new BoosterRemoveEvent(this, booster).call();
        if (boosterRemoveEvent.isCancelled()) return this;
        booster.booster(0d);
        booster.boostTime(0L);
        this.boosters.remove(booster.pluginIdentifier());
        return this;
    }

    /**
     * Removes a booster from the collection without firing any events.
     * This method silently removes the booster and resets its values without triggering any events.
     *
     * @param booster The booster to be removed.
     * @return The Boosters object after removing the specified booster.
     */
    public Boosters removeBoosterSilently(Booster booster) {
        if (booster == null) return this;
        if (!this.boosters.containsKey(booster.pluginIdentifier())) return this;
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
