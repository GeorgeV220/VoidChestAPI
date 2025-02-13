package com.georgev22.voidchest.api.event.events.voidchest;

import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidChestPlaceEvent class is an event that is fired when a player places a VoidChest.
 * It extends the VoidEvent class.
 */
public class VoidChestPlaceEvent extends VoidEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final ItemStack item;

    /**
     * Constructs a new VoidChestPlaceEvent with the specified player, item, and VoidChest.
     *
     * @param player      The player who placed the VoidChest.
     * @param item        The ItemStack representing the VoidChest.
     * @param voidChest The VoidChest associated with the VoidChest.
     */
    public VoidChestPlaceEvent(@NotNull final Player player, @NotNull final ItemStack item, @NotNull final IVoidChest voidChest) {
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
    public @NotNull HandlerList getHandlers() {
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

}
