package com.georgev22.voidchest.api.events;

import com.georgev22.voidchest.api.storage.data.IPlayerData;
import org.bukkit.event.Cancellable;
import org.jspecify.annotations.Nullable;

/**
 * The PlayerEvent class is an abstract base class for PlayerData related events.
 * It extends the Event class and implements the Cancellable interface.
 */
public abstract class PlayerEvent extends VoidChestBaseEvent {

    private final @Nullable IPlayerData playerData;

    /**
     * Constructs a new PlayerEvent with the specified PlayerData.
     *
     * @param playerData The PlayerEvent associated with the event.
     */
    public PlayerEvent(@Nullable final IPlayerData playerData) {
        this.playerData = playerData;
    }

    /**
     * Constructs a new PlayerEvent with the specified PlayerData.
     *
     * @param playerData The PlayerEvent associated with the event.
     * @param async Whether the event is asynchronous or not.
     */
    public PlayerEvent(@Nullable final IPlayerData playerData, boolean async) {
        super(async);
        this.playerData = playerData;
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
