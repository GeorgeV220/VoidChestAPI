package com.georgev22.voidchest.api.event.events.booster;

import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.storage.data.player.Booster;
import com.georgev22.voidchest.api.storage.data.player.Boosters;
import org.jetbrains.annotations.NotNull;

public class BoosterRemoveEvent extends BoosterEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled = false;

    private final Booster boosterToRemove;

    /**
     * Constructs a new instance of BoosterRemoveEvent with the specified boosters' data.
     *
     * @param boosters The Boosters data associated with this event.
     */
    public BoosterRemoveEvent(@NotNull Boosters boosters, @NotNull Booster boosterToRemove) {
        super(boosters);
        this.boosterToRemove = boosterToRemove;
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
     * Returns the booster to remove.
     *
     * @return The booster to remove.
     */
    public Booster getBoosterToRemove() {
        return this.boosterToRemove;
    }

    /**
     * Cancels the event.
     *
     * @return {@code true} if the event was successfully cancelled, {@code false} otherwise
     */
    @Override
    public boolean cancel() {
        return this.cancelled = true;
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
}
