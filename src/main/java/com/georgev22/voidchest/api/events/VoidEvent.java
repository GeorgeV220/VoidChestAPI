package com.georgev22.voidchest.api.events;

import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import org.jspecify.annotations.NonNull;

/**
 * The VoidEvent class is an abstract base class for VoidChest related events.
 * It extends the Event class and implements the Cancellable interface.
 */
public abstract class VoidEvent extends VoidChestBaseEvent {

    private final AbstractVoidChest voidChest;

    /**
     * Constructs a new VoidEvent with the specified VoidChest.
     *
     * @param voidChest The VoidChest associated with the event.
     */
    public VoidEvent(@NonNull final AbstractVoidChest voidChest) {
        this.voidChest = voidChest;
    }

    /**
     * Constructs a new VoidEvent with the specified VoidChest.
     *
     * @param voidChest The VoidChest associated with the event.
     * @param async     Whether the event should be executed asynchronously.
     */
    public VoidEvent(@NonNull final AbstractVoidChest voidChest, boolean async) {
        super(async);
        this.voidChest = voidChest;
    }

    /**
     * Retrieves the VoidChest associated with the event.
     *
     * @return The VoidChest associated with the event.
     */
    @NonNull
    public AbstractVoidChest getVoidChest() {
        return this.voidChest;
    }

}
