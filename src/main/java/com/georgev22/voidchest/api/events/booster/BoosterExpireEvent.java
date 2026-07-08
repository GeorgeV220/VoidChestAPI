package com.georgev22.voidchest.api.events.booster;

import com.georgev22.voidchest.api.storage.model.Booster;
import com.georgev22.voidchest.api.booster.BoosterHolder;
import com.georgev22.voidchest.api.events.VoidChestBaseEvent;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * Fired when a booster expires and is removed from a holder.
 * <p>
 * This event is not cancellable as expiration is a natural process.
 * Listeners should use this event for notification purposes only,
 * such as sending messages to players or logging.
 *
 * @see BoosterHolder#cleanExpiredBoosters()
 */
public class BoosterExpireEvent extends VoidChestBaseEvent {

    private final Booster booster;
    private final BoosterHolder holder;
    private static final HandlerList handlerList = new HandlerList();

    /**
     * Constructs a new BoosterExpireEvent.
     *
     * @param booster the expired booster
     * @param holder  the holder that contained the booster
     */
    public BoosterExpireEvent(@NonNull Booster booster, @NonNull BoosterHolder holder) {
        this.booster = booster;
        this.holder = holder;
    }

    /**
     * Returns the expired booster.
     *
     * @return the booster
     */
    public @NonNull Booster getBooster() {
        return booster;
    }

    /**
     * Returns the holder that contained the expired booster.
     *
     * @return the holder
     */
    public @NonNull BoosterHolder getHolder() {
        return holder;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return handlerList;
    }

    public static @NonNull HandlerList getHandlerList() {
        return handlerList;
    }
}