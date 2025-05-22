package com.georgev22.voidchest.api.events.booster;

import com.georgev22.voidchest.api.events.VoidChestBaseEvent;
import com.georgev22.voidchest.api.storage.data.player.Boosters;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event triggered when the plugin accesses the boosters of a player through the IPlayerData.boosters() method.
 *
 * @since 2.0.0
 */
@ApiStatus.AvailableSince(value = "2.0.0")
public class BoosterEvent extends VoidChestBaseEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Boosters boosters;

    /**
     * Constructs a new instance of BoosterEvent with the specified boosters' data.
     *
     * @param boosters The Boosters data associated with this event.
     */
    public BoosterEvent(Boosters boosters) {
        this.boosters = boosters;
    }

    /**
     * Gets the Boosters data associated with this event.
     *
     * @return The Boosters data.
     */
    public Boosters getBoosters() {
        return boosters;
    }

    /**
     * Retrieves the HandlerList for the event.
     *
     * @return The HandlerList for the event.
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Retrieves the HandlerList for the event.
     *
     * @return The HandlerList for the event.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
