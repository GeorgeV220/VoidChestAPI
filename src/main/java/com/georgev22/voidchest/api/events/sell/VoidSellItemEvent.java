package com.georgev22.voidchest.api.events.sell;

import com.georgev22.voidchest.api.booster.BoosterCalculationResult;
import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

/**
 * Event fired when an item from a {@link AbstractVoidChest} inventory is about
 * to be sold.
 * <p>
 * This event provides access to:
 * <ul>
 *     <li>the {@link AbstractVoidChest} performing the sale,</li>
 *     <li>the {@link ItemStack} being sold,</li>
 *     <li>the source {@link Inventory},</li>
 *     <li>the inventory slot containing the item, and</li>
 *     <li>the complete {@link BoosterCalculationResult} describing how the
 *     final sale value was calculated.</li>
 * </ul>
 * <p>
 * Plugins may modify the {@link BoosterCalculationResult} to adjust the final
 * sale value or cancel the event entirely.
 */
public class VoidSellItemEvent extends VoidEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final ItemStack item;
    private final Inventory inventory;
    private final int slot;
    private BoosterCalculationResult boosterCalculationResult;
    private boolean cancel;

    /**
     * Constructs a new {@code VoidSellItemEvent}.
     *
     * @param voidChest the {@link AbstractVoidChest} associated with the sale
     * @param item the item about to be sold
     * @param inventory the inventory containing the item
     * @param boosterCalculationResult the complete booster calculation result for
     *                                 this sale, including the original value,
     *                                 final value and applied boosters
     * @param slot the inventory slot containing the item
     */
    public VoidSellItemEvent(@NonNull final AbstractVoidChest voidChest, @NonNull final ItemStack item,
                             @NonNull final Inventory inventory, final BoosterCalculationResult boosterCalculationResult, final int slot) {
        super(voidChest);
        this.item = item;
        this.boosterCalculationResult = boosterCalculationResult;
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
    public @NonNull HandlerList getHandlers() {
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
     * Returns the complete booster calculation result for this sale.
     * <p>
     * The returned object contains the original value, the final value after
     * boosters were applied, the combined multiplier, and information about each
     * applied booster.
     *
     * @return the booster calculation result
     */
    public BoosterCalculationResult getBoosterCalculationResult() {
        return boosterCalculationResult;
    }

    /**
     * Retrieves the inventory where the item is located.
     *
     * @return The inventory where the item is located.
     */
    @NonNull
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

    /**
     * Checks if the event is cancelled.
     *
     * @return True if the event is cancelled, false otherwise.
     */
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    /**
     * Sets whether the event has been cancelled.
     *
     * @param cancelled {@code true} if the event should be cancelled, {@code false} otherwise
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancel = cancelled;
    }

}