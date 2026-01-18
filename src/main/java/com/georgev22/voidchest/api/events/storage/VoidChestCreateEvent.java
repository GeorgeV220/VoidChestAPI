package com.georgev22.voidchest.api.events.storage;

import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * The VoidChestCreateEvent class is an event that is fired when a VoidChest is created.
 */
public class VoidChestCreateEvent extends VoidEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs a new VoidChestCreateEvent with the specified VoidChest.
     *
     * @param voidChest The VoidChest associated with the VoidChest.
     */
    public VoidChestCreateEvent(AbstractVoidChest voidChest) {
        super(voidChest);
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
    public @NonNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
