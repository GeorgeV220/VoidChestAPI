package com.georgev22.voidchest.api.events.sell;

import com.georgev22.voidchest.api.booster.BoosterCalculationResult;
import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

/**
 * Event fired when an item entity from the chunk containing a {@link AbstractVoidChest}
 * is about to be sold.
 * <p>
 * This event provides access to:
 * <ul>
 *     <li>the {@link AbstractVoidChest} performing the sale,</li>
 *     <li>the dropped {@link Item} and its {@link ItemStack},</li>
 *     <li>the amount being sold, and</li>
 *     <li>the complete {@link BoosterCalculationResult} describing the sale value
 *     before and after boosters were applied.</li>
 * </ul>
 * <p>
 * Plugins may modify the {@link BoosterCalculationResult} to change the final
 * sale value before the sale is completed or cancel the event entirely.
 */
public class VoidSellChunkItemEvent extends VoidEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Item droppedItem;
    private final ItemStack droppedItemStack;
    private final BoosterCalculationResult boosterCalculationResult;
    private BigInteger itemAmount = BigInteger.ZERO;
    private boolean cancel;

    /**
     * Constructs a new {@code VoidSellChunkItemEvent}.
     *
     * @param voidChest the {@link AbstractVoidChest} associated with the sale
     * @param droppedItem the dropped item entity that is about to be sold
     * @param droppedItemStack the {@link ItemStack} represented by the dropped item
     * @param itemAmount the amount of items that will be sold
     * @param boosterCalculationResult the complete booster calculation result for this sale,
     *                                 including the original value, final value and
     *                                 applied boosters
     */
    public VoidSellChunkItemEvent(@NonNull final AbstractVoidChest voidChest,
                                  @NonNull final Item droppedItem,
                                  @NonNull final ItemStack droppedItemStack,
                                  final BigInteger itemAmount,
                                  final BoosterCalculationResult boosterCalculationResult
    ) {
        super(voidChest);
        this.droppedItem = droppedItem;
        this.droppedItemStack = droppedItemStack;
        this.boosterCalculationResult = boosterCalculationResult;
        this.setItemAmount(itemAmount);
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
     * Returns the complete booster calculation result for this sale.
     * <p>
     * The returned object contains both the original and final values as well as
     * information about every booster that contributed to the calculation.
     *
     * @return the booster calculation result
     */
    public BoosterCalculationResult getBoosterCalculationResult() {
        return boosterCalculationResult;
    }

    /**
     * Retrieves the dropped item about to be sold.
     *
     * @return The dropped item about to be sold.
     */
    @NonNull
    public Item getDroppedItem() {
        return droppedItem;
    }

    /**
     * Retrieves the dropped item stack about to be sold.
     *
     * @return The dropped item stack about to be sold.
     */
    @NonNull
    public ItemStack getDroppedItemStack() {
        return droppedItemStack;
    }

    /**
     * Retrieves the amount of the item about to be sold.
     *
     * @return The amount of the item about to be sold.
     */
    public BigInteger getItemAmount() {
        return itemAmount;
    }

    /**
     * Sets the amount of the item about to be sold.
     *
     * @param itemAmount The amount of the item about to be sold.
     */
    public void setItemAmount(BigInteger itemAmount) {
        this.itemAmount = itemAmount;
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

    @Override
    protected <T extends Event> T callSynchronousEvent() {
        Location location = getDroppedItem().getLocation();
        //noinspection ConstantValue
        if (isFolia && location != null) {
            //noinspection unchecked
            return (T) voidChestAPI.minecraftScheduler().createTaskForLocation(
                    () -> super.callSynchronousEvent(),
                    location
            ).join();
        }
        return super.callSynchronousEvent();
    }
}
