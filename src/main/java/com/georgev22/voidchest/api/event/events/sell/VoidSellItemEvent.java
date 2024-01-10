package com.georgev22.voidchest.api.event.events.sell;

import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * The VoidSellItemEvent class is an event fired when an item from a VoidStorage inventory is about to be sold.
 * It extends the VoidEvent class.
 * <p>
 * This event provides information about the item being sold, including the VoidStorage, item, inventory, price,
 * and slot.
 */
public class VoidSellItemEvent extends VoidEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final ItemStack item;
    private final Inventory inventory;
    private final int slot;
    private BigDecimal price;

    /**
     * Constructs a new VoidSellItemEvent with the specified VoidStorage, item, inventory, price, and slot.
     *
     * @param voidStorage The VoidStorage associated with the event.
     * @param item        The item about to be sold.
     * @param inventory   The inventory where the item is located.
     * @param price       The price of the item about to be sold.
     * @param slot        The slot index of the item in the inventory.
     */
    public VoidSellItemEvent(@NotNull final IVoidStorage voidStorage, @NotNull final ItemStack item,
                             @NotNull final Inventory inventory, final BigDecimal price, final int slot) {
        super(voidStorage);
        this.item = item;
        this.price = price;
        this.inventory = inventory;
        this.slot = slot;
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
     * Retrieves the item about to be sold.
     *
     * @return The item about to be sold.
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * Retrieves the price of the item about to be sold.
     *
     * @return The price of the item about to be sold.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of the item about to be sold.
     *
     * @param price The price of the item about to be sold.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Retrieves the inventory where the item is located.
     *
     * @return The inventory where the item is located.
     */
    @NotNull
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Retrieves the slot index of the item in the inventory.
     *
     * @return The slot index of the item in the inventory.
     */
    public int getSlot() {
        return slot;
    }

}