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
 * This event is fired when a SerializableItemStack is spawned, and it operates independently of instant pickup settings.
 *
 * <p>If you have instant pickup enabled or disabled, this event will still work.
 *
 * <p>
 * Fire this event if you want to instantly collect items without being affected by instant pickup settings.
 * <p>
 * This event does not rely on VoidStorage instant pickup settings and will always function.
 *
 * <p>You can use VoidChestAPI.getInstance().eventManager().callEvent(new ItemSpawnEvent );
 * to fire this event.
 */
public class ItemSpawnEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private SerializableItemStack itemStack;

    private @Nullable Item item;
    private final @Nullable Location location;
    private boolean cancelled = false;

    /**
     * Constructs a new ItemSpawnEvent with the given SerializableItemStack.
     *
     * @param itemStack The SerializableItemStack being spawned.
     */
    public ItemSpawnEvent(SerializableItemStack itemStack) {
        this.itemStack = itemStack;
        this.item = null;
        this.location = null;
    }

    /**
     * Constructs a new ItemSpawnEvent with the given SerializableItemStack and location.
     *
     * @param itemStack The SerializableItemStack being spawned.
     * @param location  The location associated with the event (nullable).
     */
    public ItemSpawnEvent(SerializableItemStack itemStack, @Nullable Location location) {
        this.itemStack = itemStack;
        this.item = null;
        this.location = location;
    }

    /**
     * Constructs a new ItemSpawnEvent with the given ItemStack and amount.
     *
     * @param itemStack The base ItemStack being spawned.
     * @param amount    The amount of the ItemStack being spawned.
     */
    public ItemSpawnEvent(@NotNull ItemStack itemStack, @NotNull BigInteger amount) {
        this.item = null;
        this.location = null;
        this.itemStack = new SerializableItemStack(itemStack, amount);
    }

    /**
     * Constructs a new ItemSpawnEvent with the given ItemStack, amount and location.
     *
     * @param itemStack The base ItemStack being spawned.
     * @param amount    The amount of the ItemStack being spawned.
     * @param location  The location associated with the event (nullable).
     */
    public ItemSpawnEvent(@NotNull ItemStack itemStack, @NotNull BigInteger amount, @Nullable Location location) {
        this.item = null;
        this.location = location;
        this.itemStack = new SerializableItemStack(itemStack, amount);
    }

    /**
     * Constructs a new ItemSpawnEvent with the given Item entity and amount.
     *
     * @param item   The Item entity.
     * @param amount The amount of the ItemStack being spawned.
     */
    public ItemSpawnEvent(@NotNull Item item, @NotNull BigInteger amount) {
        this.item = item;
        this.location = item.getLocation();
        this.itemStack = new SerializableItemStack(item.getItemStack(), amount);
    }

    /**
     * Constructs a new ItemSpawnEvent with the given Item entity, amount and location.
     *
     * @param item     The Item entity.
     * @param amount   The amount of the ItemStack being spawned.
     * @param location The location associated with the event (nullable).
     */
    public ItemSpawnEvent(@NotNull Item item, @NotNull BigInteger amount, @Nullable Location location) {
        this.item = item;
        this.location = location;
        this.itemStack = new SerializableItemStack(item.getItemStack(), amount);
    }

    /**
     * Constructs a new ItemSpawnEvent with the given Item entity, ItemStack, and amount.
     *
     * @param item      The Item entity.
     * @param itemStack The base ItemStack being spawned.
     * @param amount    The amount of the ItemStack being spawned.
     */
    public ItemSpawnEvent(@NotNull Item item, @NotNull ItemStack itemStack, @NotNull BigInteger amount) {
        this.item = item;
        this.location = item.getLocation();
        this.itemStack = new SerializableItemStack(itemStack, amount);
    }

    /**
     * Constructs a new ItemSpawnEvent with the given Item entity, ItemStack, amount and location.
     *
     * @param item      The Item entity.
     * @param itemStack The base ItemStack being spawned.
     * @param amount    The amount of the ItemStack being spawned.
     * @param location  The location associated with the event (nullable).
     */
    public ItemSpawnEvent(@NotNull Item item, @NotNull ItemStack itemStack, @NotNull BigInteger amount, @Nullable Location location) {
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
     * Sets the SerializableItemStack associated with this event.
     *
     * @param itemStack The SerializableItemStack being spawned.
     */
    public void setItemStack(SerializableItemStack itemStack) {
        this.itemStack = itemStack;
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
     * Sets the Item entity associated with this event.
     *
     * @param item The Item entity, or {@code null} if not applicable.
     */
    public void setItem(@Nullable Item item) {
        this.item = item;
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
