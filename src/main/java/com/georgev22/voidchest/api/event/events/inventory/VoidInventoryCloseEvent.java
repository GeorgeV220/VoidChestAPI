package com.georgev22.voidchest.api.event.events.inventory;

import com.georgev22.voidchest.api.inventory.VoidInventory;
import org.bukkit.entity.Player;

/**
 * The VoidInventoryCloseEvent is called when a player closes a VoidInventory.
 */
public class VoidInventoryCloseEvent extends VoidInventoryEvent {
    private final Player player;

    /**
     * Constructs a new VoidInventoryCloseEvent.
     *
     * @param inventory The inventory that was clicked
     * @param player    The player that closed the inventory
     */
    public VoidInventoryCloseEvent(VoidInventory inventory, Player player) {
        super(inventory);
        this.player = player;
    }

    /**
     * Retrieves the player that closed the inventory.
     *
     * @return The player that closed the inventory
     */
    public Player getPlayer() {
        return player;
    }

}
