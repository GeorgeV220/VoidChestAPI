package com.georgev22.voidchest.api.event.events;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import org.jetbrains.annotations.Nullable;

/**
 * The PlayerEvent class is an abstract base class for PlayerData related events.
 * It extends the Event class and implements the Cancellable interface.
 */
public abstract class PlayerEvent extends Event implements Cancellable {

    private final @Nullable IPlayerData playerData;
    private boolean cancel;


    /**
     * Constructs a new PlayerEvent with the specified PlayerData.
     *
     * @param playerData The PlayerEvent associated with the event.
     */
    public PlayerEvent(@Nullable final IPlayerData playerData) {
        this.playerData = playerData;
    }


    /**
     * Checks if the event is cancelled.
     *
     * @return True if the event is cancelled, false otherwise.
     */
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    /**
     * Sets whether the event has been cancelled.
     *
     * @param cancelled {@code true} if the event should be cancelled, {@code false} otherwise
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancel = cancelled;
    }

    /**
     * Retrieves the PlayerData associated with the event.
     *
     * @return The PlayerData associated with the event.
     */
    @Nullable
    public IPlayerData getPlayerData() {
        return playerData;
    }
}
