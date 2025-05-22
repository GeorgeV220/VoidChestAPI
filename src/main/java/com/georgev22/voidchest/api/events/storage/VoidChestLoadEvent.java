package com.georgev22.voidchest.api.events.storage;

import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The VoidChestLoadEvent class is an event that is fired when a VoidChest is loaded.
 */
public class VoidChestLoadEvent extends VoidEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    private final @Nullable Throwable throwable;

    /**
     * Constructs a new VoidChestLoadEvent with the specified VoidChest and Throwable.
     *
     * @param voidChest The VoidChest associated with the VoidChest.
     * @param throwable   The Throwable if the load failed.
     */
    public VoidChestLoadEvent(IVoidChest voidChest, @Nullable Throwable throwable) {
        super(voidChest);
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
