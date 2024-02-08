package com.georgev22.voidchest.api.event.events;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.jetbrains.annotations.Nullable;

/**
 * The VoidEvent class is an abstract base class for VoidChest related events.
 * It extends the Event class and implements the Cancellable interface.
 */
public abstract class VoidEvent extends Event implements Cancellable {

    private final IVoidStorage voidStorage;
    private boolean cancel;

    /**
     * Constructs a new VoidEvent with the specified VoidStorage.
     *
     * @param voidStorage The VoidStorage associated with the event.
     */
    public VoidEvent(@Nullable final IVoidStorage voidStorage) {
        this.voidStorage = voidStorage;
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
     * Retrieves the VoidStorage associated with the event.
     *
     * @return The VoidStorage associated with the event.
     */
    @Nullable
    public IVoidStorage getVoidStorage() {
        return this.voidStorage;
    }

}