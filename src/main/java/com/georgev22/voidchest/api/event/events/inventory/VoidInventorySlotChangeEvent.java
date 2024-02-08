package com.georgev22.voidchest.api.event.events.inventory;

import com.georgev22.voidchest.api.event.events.inventory.VoidInventoryClickEvent.Slot;
import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.inventory.VoidInventoryItemStack;

/**
 * The VoidInventorySlotChangeEvent is called when a player changes the item in a slot in a VoidInventory.
 */
public class VoidInventorySlotChangeEvent extends VoidInventoryEvent implements Cancellable {

    private boolean cancelled;
    private final Slot slot;
    private VoidInventoryItemStack newItemStack;

    /**
     * Constructs a new VoidInventorySlotChangeEvent.
     *
     * @param inventory    The inventory that was clicked
     * @param slot         The slot that was clicked
     * @param newItemStack The new item stack that is going to be in the slot
     */
    public VoidInventorySlotChangeEvent(VoidInventory inventory, Slot slot, VoidInventoryItemStack newItemStack) {
        super(inventory);
        this.slot = slot;
        this.newItemStack = newItemStack;
    }

    /**
     * Retrieves the slot that was clicked.
     *
     * @return The slot that was clicked
     */
    public Slot getSlot() {
        return slot;
    }

    /**
     * Retrieves the new item stack that is going to be in the slot.
     *
     * @return The new item stack in the slot
     */
    public VoidInventoryItemStack getNewItemStack() {
        return newItemStack;
    }

    /**
     * Sets the new item stack that is going to be in the slot.
     *
     * @param newItemStack The new item stack in the slot
     */
    public void setNewItemStack(VoidInventoryItemStack newItemStack) {
        this.newItemStack = newItemStack;
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
     * @return {@code true} if the event has been cancelled, {@code false} otherwise
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
