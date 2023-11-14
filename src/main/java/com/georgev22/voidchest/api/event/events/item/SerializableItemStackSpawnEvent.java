/**
 * Represents an event triggered when a SerializableItemStack is spawned.
 */
package com.georgev22.voidchest.api.event.events.item;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * Represents an event triggered when a SerializableItemStack is spawned.
 * This event is called when an ItemStack is converted to a SerializableItemStack.
 */
public class SerializableItemStackSpawnEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final SerializableItemStack itemStack;

    private boolean cancelled;

    /**
     * Constructs a new SerializableItemStackSpawnEvent with the given SerializableItemStack.
     *
     * @param itemStack The SerializableItemStack being spawned.
     */
    public SerializableItemStackSpawnEvent(SerializableItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Constructs a new SerializableItemStackSpawnEvent with the given ItemStack and amount.
     *
     * @param itemStack The base ItemStack being spawned.
     * @param amount    The amount of the ItemStack being spawned.
     */
    public SerializableItemStackSpawnEvent(@NotNull ItemStack itemStack, @NotNull BigInteger amount) {
        this.itemStack = new SerializableItemStack(itemStack, amount);
    }

    /**
     * Gets the SerializableItemStack associated with this event.
     *
     * @return The SerializableItemStack being spawned.
     */
    public SerializableItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Gets the HandlerList for this event.
     *
     * @return The HandlerList for this event.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Gets the HandlerList for this event.
     *
     * @return The HandlerList for this event.
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Cancels the event.
     *
     * @return {@code true} if the event was successfully cancelled, {@code false} otherwise
     */
    @Override
    public boolean cancel() {
        return this.cancelled = true;
    }

    /**
     * Returns whether the event has been cancelled.
     *
     * @return {@code true} if the event has been cancelled, {@code false} otherwise
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
