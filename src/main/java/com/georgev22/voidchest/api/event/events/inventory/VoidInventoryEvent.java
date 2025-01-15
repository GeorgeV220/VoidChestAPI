package com.georgev22.voidchest.api.event.events.inventory;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidInventoryEvent is the base class for all VoidInventory related events.
 * Normally, you will not (use/listen to) this event, but instead one of its subclasses.
 */
public class VoidInventoryEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final VoidInventory inventory;

    /**
     * Constructs a new VoidInventoryEvent.
     *
     * @param inventory The inventory that was clicked
     */
    public VoidInventoryEvent(VoidInventory inventory) {
        this.inventory = inventory;
    }

    public VoidInventory getInventory() {
        return inventory;
    }

    /**
     * Retrieves the HandlerList of the event.
     *
     * @return The HandlerList of the event.
     */
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Retrieves the HandlerList of the event.
     *
     * @return The HandlerList of the event.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
