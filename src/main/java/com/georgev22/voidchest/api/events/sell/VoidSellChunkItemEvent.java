package com.georgev22.voidchest.api.events.sell;

import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The VoidSellChunkItemEvent class is an event that is fired
 * when an item from the chunk containing a VoidChest is about to be sold.
 * It extends the VoidEvent class.
 * <p>
 * This event provides information about the item being sold, including the VoidChest, dropped item, item amount,
 * and price.
 */
public class VoidSellChunkItemEvent extends VoidEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Item droppedItem;
    private final ItemStack droppedItemStack;
    private BigDecimal price = BigDecimal.ZERO;
    private BigInteger itemAmount = BigInteger.ZERO;
    private boolean cancel;

    /**
     * Constructs a new VoidSellChunkItemEvent with the specified VoidChest, dropped item, item amount, and price.
     *
     * @param voidChest        The VoidChest associated with the event.
     * @param droppedItem      The dropped item from the chunk about to be sold.
     * @param droppedItemStack The dropped item stack from the chunk about to be sold.
     * @param itemAmount       The amount of the item about to be sold.
     * @param price            The price of the item about to be sold.
     */
    public VoidSellChunkItemEvent(@NonNull final IVoidChest voidChest,
                                  @NonNull final Item droppedItem,
                                  @NonNull final ItemStack droppedItemStack,
                                  final BigInteger itemAmount,
                                  final BigDecimal price
    ) {
        super(voidChest);
        this.droppedItem = droppedItem;
        this.droppedItemStack = droppedItemStack;
        this.setPrice(price);
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
