package com.georgev22.voidchest.api.events.booster;

import com.georgev22.voidchest.api.storage.model.Booster;
import com.georgev22.voidchest.api.booster.BoosterHolder;
import com.georgev22.voidchest.api.events.VoidChestBaseEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * Fired when a booster is about to be added to a holder.
 * <p>
 * This event is cancellable. If cancelled, the booster will not be added.
 *
 * @see BoosterHolder#addBooster(Booster)
 */
public class BoosterCreateEvent extends VoidChestBaseEvent implements Cancellable {

    private final Booster booster;
    private final BoosterHolder holder;
    private boolean cancelled;

    private static final HandlerList handlerList = new HandlerList();

    /**
     * Constructs a new BoosterCreateEvent.
     *
     * @param booster the booster being added
     * @param holder  the holder receiving the booster
     */
    public BoosterCreateEvent(@NonNull Booster booster, @NonNull BoosterHolder holder) {
        this.booster = booster;
        this.holder = holder;
        this.cancelled = false;
    }

    /**
     * Returns the booster being added.
     *
     * @return the booster
     */
    public @NonNull Booster getBooster() {
        return booster;
    }

    /**
     * Returns the holder that will receive the booster.
     *
     * @return the holder
     */
    public @NonNull BoosterHolder getHolder() {
        return holder;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return handlerList;
    }

    public static @NonNull HandlerList getHandlerList() {
        return handlerList;
    }
}