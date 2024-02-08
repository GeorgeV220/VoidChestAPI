package com.georgev22.voidchest.api.event.events.item;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

/**
 * This event is fired when a SerializableItemStack is spawned,
 * and the VoidStorage instant collector ability is enabled.
 *
 * <p>
 * If you have instant pickup disabled, use {@link ItemSpawnEvent} instead.
 *
 * <p>
 * Fire this event if you want to instantly collect items when the VoidStorage instant collector ability is enabled.
 * <p>
 * This event requires the VoidStorage instant collector ability to be enabled for instant item pickup.
 *
 * <p>You can use VoidChestAPI.getInstance().eventManager().callEvent(new InstantItemSpawnEvent );
 * to fire this event.
 */
public class InstantItemSpawnEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final SerializableItemStack itemStack;

    private final @Nullable Item item;
    private final @Nullable Location location;
    private boolean cancelled = false;

    /**
     * Constructs a new InstantItemSpawnEvent with the given SerializableItemStack.
     *
     * @param itemStack The SerializableItemStack being spawned.
     */
    public InstantItemSpawnEvent(SerializableItemStack itemStack) {
        this.itemStack = itemStack;
        this.item = null;
        this.location = null;
    }

    /**
     * Constructs a new InstantItemSpawnEvent with the given SerializableItemStack and location.
     *
     * @param itemStack The SerializableItemStack being spawned.
     * @param location  The location associated with the event (nullable).
     */
    public InstantItemSpawnEvent(SerializableItemStack itemStack, @Nullable Location location) {
        this.itemStack = itemStack;
        this.item = null;
        this.location = location;
    }

    /**
     * Constructs a new InstantItemSpawnEvent with the given ItemStack and amount.
     *
     * @param itemStack The base ItemStack being spawned.
     * @param amount    The amount of the ItemStack being spawned.
     */
    public InstantItemSpawnEvent(@NotNull ItemStack itemStack, @NotNull BigInteger amount) {
        this.item = null;
        this.location = null;
        this.itemStack = new SerializableItemStack(itemStack, amount);
    }

    /**
     * Constructs a new InstantItemSpawnEvent with the given ItemStack, amount and location.
     *
     * @param itemStack The base ItemStack being spawned.
     * @param amount    The amount of the ItemStack being spawned.
     * @param location  The location associated with the event (nullable).
     */
    public InstantItemSpawnEvent(@NotNull ItemStack itemStack, @NotNull BigInteger amount, @Nullable Location location) {
        this.item = null;
        this.location = location;
        this.itemStack = new SerializableItemStack(itemStack, amount);
    }

    /**
     * Constructs a new InstantItemSpawnEvent with the given Item entity and amount.
     *
     * @param item   The Item entity.
     * @param amount The amount of the ItemStack being spawned.
     */
    public InstantItemSpawnEvent(@NotNull Item item, @NotNull BigInteger amount) {
        this.item = item;
        this.location = item.getLocation();
        this.itemStack = new SerializableItemStack(item.getItemStack(), amount);
    }

    /**
     * Constructs a new InstantItemSpawnEvent with the given Item entity, amount and location.
     *
     * @param item     The Item entity.
     * @param amount   The amount of the ItemStack being spawned.
     * @param location The location associated with the event (nullable).
     */
    public InstantItemSpawnEvent(@NotNull Item item, @NotNull BigInteger amount, @Nullable Location location) {
        this.item = item;
        this.location = location;
        this.itemStack = new SerializableItemStack(item.getItemStack(), amount);
    }

    /**
     * Constructs a new InstantItemSpawnEvent with the given Item entity, ItemStack, and amount.
     *
     * @param item      The Item entity.
     * @param itemStack The base ItemStack being spawned.
     * @param amount    The amount of the ItemStack being spawned.
     */
    public InstantItemSpawnEvent(@NotNull Item item, @NotNull ItemStack itemStack, @NotNull BigInteger amount) {
        this.item = item;
        this.location = item.getLocation();
        this.itemStack = new SerializableItemStack(itemStack, amount);
    }

    /**
     * Constructs a new InstantItemSpawnEvent with the given Item entity, ItemStack, amount and location.
     *
     * @param item      The Item entity.
     * @param itemStack The base ItemStack being spawned.
     * @param amount    The amount of the ItemStack being spawned.
     * @param location  The location associated with the event (nullable).
     */
    public InstantItemSpawnEvent(@NotNull Item item, @NotNull ItemStack itemStack, @NotNull BigInteger amount, @Nullable Location location) {
        this.item = item;
        this.location = location;
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
     * Gets the Item entity associated with this event.
     *
     * @return The Item entity, or {@code null} if not applicable.
     */
    public @Nullable Item getItem() {
        return item;
    }

    /**
     * Retrieves the location associated with this object.
     *
     * @return the location of the object, or null if not available
     */
    public @Nullable Location getLocation() {
        return location;
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
     * Sets whether the event has been cancelled.
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
     * @return {@code true} if the event has been canceled, {@code false} otherwise
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
