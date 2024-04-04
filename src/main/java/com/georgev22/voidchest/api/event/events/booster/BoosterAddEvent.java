package com.georgev22.voidchest.api.event.events.booster;

import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.storage.data.player.Booster;
import com.georgev22.voidchest.api.storage.data.player.Boosters;
import org.jetbrains.annotations.NotNull;

public class BoosterAddEvent extends BoosterEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled = false;

    private final Booster boosterToAdd;

    /**
     * Constructs a new instance of BoosterAddEvent with the specified boosters' data.
     *
     * @param boosters The Boosters data associated with this event.
     */
    public BoosterAddEvent(@NotNull Boosters boosters, @NotNull Booster boosterToAdd) {
        super(boosters);
        this.boosterToAdd = boosterToAdd;
    }

    /**
     * Returns the list of boosters stored in the HashObjectMap before the change.
     *
     * @return The list of boosters.
     */
    @Override
    public Boosters getBoosters() {
        return super.getBoosters();
    }

    /**
     * Returns the booster to add.
     *
     * @return The booster to add.
     */
    public Booster getBoosterToAdd() {
        return this.boosterToAdd;
    }

    /**
     * Sets whether the event has been cancelled.
     *
     * @param cancelled {@code true} if the event should be cancelled, {@code false} otherwise
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Returns whether the event has been cancelled.
     *
     * @return {@code true} if the event has been cancelled, {@code false} otherwise
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
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
