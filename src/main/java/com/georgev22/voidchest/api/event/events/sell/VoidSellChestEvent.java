package com.georgev22.voidchest.api.event.events.sell;

import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidSellChestEvent class is an event that is fired when a VoidStorage is sold.
 * It extends the VoidEvent class.
 */
public class VoidSellChestEvent extends VoidEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs a new VoidSellChestEvent with the specified VoidStorage.
     *
     * @param voidStorage The VoidStorage associated with the sold chest.
     */
    public VoidSellChestEvent(@NotNull final IVoidStorage voidStorage) {
        super(voidStorage);
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