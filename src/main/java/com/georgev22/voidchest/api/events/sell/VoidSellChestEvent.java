package com.georgev22.voidchest.api.events.sell;

import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * The VoidSellChestEvent class is an event fired when the sell task for a specific VoidChest starts.
 * It extends the VoidEvent class.
 * <p>
 * This event should be used for monitoring and logging purposes,
 * and no data manipulation should occur within this event.
 * <p>
 * <strong>Note: This event is not cancellable and is fired asynchronously.</strong>
 */
public class VoidSellChestEvent extends VoidEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * Constructs a new VoidSellChestEvent with the specified VoidChest.
     *
     * @param voidChest The VoidChest associated with the sold chest.
     */
    public VoidSellChestEvent(@NonNull final IVoidChest voidChest) {
        super(voidChest, true);
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