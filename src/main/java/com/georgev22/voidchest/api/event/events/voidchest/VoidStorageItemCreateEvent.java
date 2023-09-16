package com.georgev22.voidchest.api.event.events.voidchest;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidStorageItemCreateEvent class is an event that is fired when items are created for a VoidStorage.
 * It extends the Event class.
 */
public class VoidStorageItemCreateEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final ItemStack[] items;

    /**
     * Constructs a new VoidStorageItemCreateEvent with the specified items.
     *
     * @param items The items that are created for the VoidStorage.
     */
    public VoidStorageItemCreateEvent(final ItemStack... items) {
        this.items = items;
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

    /**
     * Retrieves the items that are created for the VoidStorage.
     *
     * @return The items that are created for the VoidStorage.
     */
    public ItemStack[] getItems() {
        return items;
    }
}
