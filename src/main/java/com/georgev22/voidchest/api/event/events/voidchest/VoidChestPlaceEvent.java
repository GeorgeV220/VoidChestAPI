package com.georgev22.voidchest.api.event.events.voidchest;

import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * The VoidChestPlaceEvent class is an event that is fired when a player places a VoidChest.
 * It extends the VoidEvent class.
 */
public class VoidChestPlaceEvent extends VoidEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final ItemStack item;

    /**
     * Constructs a new VoidChestPlaceEvent with the specified player, item, and VoidStorage.
     *
     * @param player      The player who placed the VoidChest.
     * @param item        The ItemStack representing the VoidChest.
     * @param voidStorage The VoidStorage associated with the VoidChest.
     */
    public VoidChestPlaceEvent(@Nonnull final Player player, @Nonnull final ItemStack item, @Nonnull final IVoidStorage voidStorage) {
        super(voidStorage);
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