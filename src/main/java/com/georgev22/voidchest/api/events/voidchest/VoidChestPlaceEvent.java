package com.georgev22.voidchest.api.events.voidchest;

import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.events.storage.VoidChestCreateEvent;
import com.georgev22.voidchest.api.events.storage.VoidChestDeleteEvent;
import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

/**
 * The VoidChestPlaceEvent is fired when a player places a VoidChest block.
 * <p>
 * <strong>Important behavior notes:</strong>
 * <ul>
 *   <li>Cancelling this event does <b>not</b> prevent {@link VoidChestCreateEvent}
 *   from executing, as creation occurs before this event is fired.</li>
 *   <li>If this event is cancelled, {@link VoidChestDeleteEvent} will still be fired
 *   to clean up the already-created VoidChest.</li>
 * </ul>
 * <p>
 * This event is primarily intended to allow plugins to react to or veto
 * the placement outcome rather than the creation lifecycle itself.
 */
public class VoidChestPlaceEvent extends VoidEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final ItemStack item;
    private boolean cancel;

    /**
     * Constructs a new VoidChestPlaceEvent.
     *
     * @param player    The player who placed the VoidChest.
     * @param item      The ItemStack representing the VoidChest.
     * @param voidChest The VoidChest instance associated with the placement.
     */
    public VoidChestPlaceEvent(
            @NonNull final Player player,
            @NonNull final ItemStack item,
            @NonNull final AbstractVoidChest voidChest
    ) {
        super(voidChest);
        this.player = player;
        this.item = item;
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

    /**
     * Retrieves the player who placed the VoidChest.
     *
     * @return The player who placed the VoidChest.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Retrieves the ItemStack representing the VoidChest.
     *
     * @return The ItemStack representing the VoidChest.
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * Checks if the event is cancelled.
     *
     * @return True if the event is cancelled, false otherwise.
     */
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    /**
     * Sets whether the event has been cancelled.
     * <p>
     * Cancelling this event will prevent the placement from completing,
     * but will <b>not</b> undo {@link VoidChestCreateEvent}. Instead,
     * {@link VoidChestDeleteEvent} will be fired afterwards.
     *
     * @param cancelled {@code true} if the event should be cancelled, {@code false} otherwise
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancel = cancelled;
    }

}
