package com.georgev22.voidchest.api.event.events.inventory;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.inventory.IPaginatedVoidInventory;
import org.jetbrains.annotations.NotNull;

/**
 * This event is fired when a player switches pages in a paginated void inventory.
 */
public class PaginatedInventorySwitchPageEvent extends Event implements Cancellable {

    private final static HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;
    private final int currentPage;
    private final int destinationPage;
    private final IPaginatedVoidInventory paginatedVoidInventory;

    /**
     * Constructs a new PaginatedInventorySwitchPageEvent with the given parameters.
     *
     * @param paginatedVoidInventory the paginated void inventory that the player is viewing
     * @param currentPage            the current page number that the player is on
     * @param destinationPage        the destination page number that the player is switching to
     */
    public PaginatedInventorySwitchPageEvent(IPaginatedVoidInventory paginatedVoidInventory, int currentPage, int destinationPage) {
        this.currentPage = currentPage;
        this.destinationPage = destinationPage;
        this.paginatedVoidInventory = paginatedVoidInventory;
    }

    /**
     * Returns the current page number that the player is on.
     *
     * @return the current page number
     */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * Returns the destination page number that the player is switching to.
     *
     * @return the next page number
     */
    public int getDestinationPage() {
        return this.destinationPage;
    }

    /**
     * Returns the paginated void inventory that the player is viewing.
     *
     * @return the paginated void inventory
     */
    public IPaginatedVoidInventory getPaginatedVoidInventory() {
        return this.paginatedVoidInventory;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
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