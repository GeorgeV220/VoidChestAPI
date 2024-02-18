package com.georgev22.voidchest.api.event.events.inventory;

import com.georgev22.voidchest.api.inventory.VoidInventoryItemStack;

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