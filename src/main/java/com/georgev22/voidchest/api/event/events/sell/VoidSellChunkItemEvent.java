package com.georgev22.voidchest.api.event.events.sell;

import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The VoidSellChunkItemEvent class is an event that is fired
 * when an item from the chunk containing a VoidStorage is about to be sold.
 * It extends the VoidEvent class.
 * <p>
 * This event provides information about the item being sold, including the VoidStorage, dropped item, item amount,
 * and price.
 */
public class VoidSellChunkItemEvent extends VoidEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Item droppedItem;
    private BigDecimal price = BigDecimal.ZERO;
    private BigInteger itemAmount = BigInteger.ZERO;

    /**
     * Constructs a new VoidSellChunkItemEvent with the specified VoidStorage, dropped item, item amount, and price.
     *
     * @param voidStorage The VoidStorage associated with the event.
     * @param droppedItem The dropped item from the chunk about to be sold.
     * @param itemAmount  The amount of the item about to be sold.
     * @param price       The price of the item about to be sold.
     */
    public VoidSellChunkItemEvent(@NotNull final IVoidStorage voidStorage, @NotNull final Item droppedItem,
                                  final BigInteger itemAmount, final BigDecimal price) {
        super(voidStorage);
        this.droppedItem = droppedItem;
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
    public @NotNull HandlerList getHandlers() {
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
    @NotNull
    public Item getDroppedItem() {
        return droppedItem;
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

}