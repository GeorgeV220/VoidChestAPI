package com.georgev22.voidchest.api.event.events.storage;

import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The VoidStorageSaveEvent class is an event that is fired when a VoidChest is saved.
 */
public class VoidStorageSaveEvent extends VoidEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    private final @Nullable Throwable throwable;

    /**
     * Constructs a new VoidStorageSaveEvent with the specified VoidStorage and Throwable.
     *
     * @param voidStorage The VoidStorage associated with the VoidChest.
     * @param throwable   The Throwable if the save failed.
     */
    public VoidStorageSaveEvent(IVoidStorage voidStorage, @Nullable Throwable throwable) {
        super(voidStorage);
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
