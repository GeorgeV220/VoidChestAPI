package com.georgev22.voidchest.api.event.events.sell;

import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidSellChestEvent class is an event fired when the sell task for a specific VoidChest starts.
 * It extends the VoidEvent class.
 * <p>
 * This event should be used for monitoring and logging purposes,
 * and no data manipulation should occur within this event.
 */
public class VoidSellChestEvent extends VoidEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs a new VoidSellChestEvent with the specified VoidChest.
     *
     * @param voidChest The VoidChest associated with the sold chest.
     */
    public VoidSellChestEvent(@NotNull final IVoidChest voidChest) {
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
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

}