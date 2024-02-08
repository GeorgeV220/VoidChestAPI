package com.georgev22.voidchest.api.event.events.inventory;

import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import org.bukkit.entity.Player;

/**
 * The VoidInventoryOpenEvent is called when a player opens a VoidInventory.
 */
public class VoidInventoryOpenEvent extends VoidInventoryEvent implements Cancellable {

    private boolean cancelled;
    private final Player player;

    /**
     * Constructs a new VoidInventoryOpenEvent.
     *
     * @param inventory The inventory that was clicked
     * @param player    The player that opened the inventory
     */
    public VoidInventoryOpenEvent(VoidInventory inventory, Player player) {
        super(inventory);
        this.player = player;
    }

    /**
     * Retrieves the player that opened the inventory.
     *
     * @return The player that opened the inventory
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets whether the event has been cancelled.
     * If the event is cancelled, the inventory will not be opened.
     *
     * @param cancelled {@code true} if the event should be cancelled, {@code false} otherwise
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Returns whether the event has been cancelled.
     *
     * @return {@code true} if the event has been cancelled, {@code false} otherwise
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
