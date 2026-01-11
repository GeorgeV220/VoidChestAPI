package com.georgev22.voidchest.api.events.voidchest;

import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

/**
 * The VoidChestPostPlaceEvent class is an event that is fired when a player places a VoidChest and the VoidChest is placed successfully.
 * It extends the VoidEvent class.
 */
public class VoidChestPostPlaceEvent extends VoidEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final ItemStack item;
    private final Block block;

    public VoidChestPostPlaceEvent(@NonNull final Player player,
                                   @NonNull final ItemStack item,
                                   @NonNull final AbstractVoidChest voidChest,
                                   @NonNull final Block block) {
        super(voidChest);
        this.player = player;
        this.item = item;
        this.block = block;
    }

    public @NonNull Player getPlayer() {
        return player;
    }

    public @NonNull ItemStack getItem() {
        return item;
    }

    public @NonNull Block getBlock() {
        return block;
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
