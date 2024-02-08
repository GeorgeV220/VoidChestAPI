package com.georgev22.voidchest.api.event.events.inventory;

import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.inventory.VoidInventoryItemStack;

/**
 * The VoidInventoryClickEvent is called when a player clicks an item in a VoidInventory.
 */
public class VoidInventoryClickEvent extends VoidInventoryEvent implements Cancellable {

    private boolean cancelled;
    private final ClickType clickType;
    private final Slot slot;

    /**
     * Constructs a new VoidInventoryClickEvent.
     *
     * @param inventory The inventory that was clicked
     * @param type      The type of the click
     * @param slot      The slot that was clicked
     */
    public VoidInventoryClickEvent(VoidInventory inventory, ClickType type, Slot slot) {
        super(inventory);
        this.clickType = type;
        this.slot = slot;
    }

    /**
     * Retrieves the type of the click.
     *
     * @return The type of the click
     */
    public ClickType getClickType() {
        return clickType;
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


    /**
     * Slot class to represent a slot in an inventory.
     */
    public record Slot(int index, VoidInventoryItemStack itemStack) {

        /**
         * Constructs a new Slot object.
         *
         * @param index     The index of the slot
         * @param itemStack The item stack of the slot
         */
        public Slot {
            if (index < 0) {
                throw new IllegalArgumentException("Index cannot be negative");
            }
        }

    }

    public enum ClickType {
        LEFT,
        RIGHT,
        SHIFT_LEFT,
        SHIFT_RIGHT,
        MIDDLE,
        DROP,
        CONTROL_DROP,
        UNKNOWN
    }

}
