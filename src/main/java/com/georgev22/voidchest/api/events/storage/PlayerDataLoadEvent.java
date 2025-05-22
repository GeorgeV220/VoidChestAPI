package com.georgev22.voidchest.api.events.storage;

import com.georgev22.voidchest.api.events.PlayerEvent;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The PlayerDataLoadEvent class is an event that is fired when a PlayerData is loaded.
 */
public class PlayerDataLoadEvent extends PlayerEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    private final @Nullable Throwable throwable;

    /**
     * Constructs a new PlayerDataLoadEvent with the specified PlayerData and Throwable.
     *
     * @param playerData The playerData associated with the PlayerData.
     * @param throwable  The Throwable if the load failed.
     */
    public PlayerDataLoadEvent(IPlayerData playerData, @Nullable Throwable throwable) {
        super(playerData);
        this.throwable = throwable;
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

    public @Nullable Throwable getThrowable() {
        return throwable;
    }
}
