package com.georgev22.voidchest.api.event.events;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidEvent class is an abstract base class for VoidChest related events.
 * It extends the Event class and implements the Cancellable interface.
 */
public abstract class VoidEvent extends Event implements Cancellable {

    private final IVoidChest voidChest;
    private boolean cancel;

    /**
     * Constructs a new VoidEvent with the specified VoidChest.
     *
     * @param voidChest The VoidChest associated with the event.
     */
    public VoidEvent(@NotNull final IVoidChest voidChest) {
        this.voidChest = voidChest;
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
     * Retrieves the VoidChest associated with the event.
     *
     * @return The VoidChest associated with the event.
     */
    @NotNull
    public IVoidChest getVoidChest() {
        return this.voidChest;
    }

}
