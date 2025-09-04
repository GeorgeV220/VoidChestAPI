package com.georgev22.voidchest.api.events;

import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidEvent class is an abstract base class for VoidChest related events.
 * It extends the Event class and implements the Cancellable interface.
 */
public abstract class VoidEvent extends VoidChestBaseEvent {

    private final IVoidChest voidChest;

    /**
     * Constructs a new VoidEvent with the specified VoidChest.
     *
     * @param voidChest The VoidChest associated with the event.
     */
    public VoidEvent(@NotNull final IVoidChest voidChest) {
        this.voidChest = voidChest;
    }

    /**
     * Constructs a new VoidEvent with the specified VoidChest.
     *
     * @param voidChest The VoidChest associated with the event.
     * @param async     Whether the event should be executed asynchronously.
     */
    public VoidEvent(@NotNull final IVoidChest voidChest, boolean async) {
        super(async);
        this.voidChest = voidChest;
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
