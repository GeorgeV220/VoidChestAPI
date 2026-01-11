package com.georgev22.voidchest.api.events.voidchest;

import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.link.ILink;
import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * The VoidChestLinkAddEvent class is an event that is fired when a link is added to a VoidChest.
 * It extends the VoidEvent class.
 * Cancelling this event won't prevent the link from being added.
 */
public class VoidChestLinkAddEvent extends VoidEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final ILink link;

    /**
     * Creates a new VoidChestLinkAddEvent.
     *
     * @param voidChest The voidChest associated with the event.
     * @param link      The link that was added.
     */
    public VoidChestLinkAddEvent(@NonNull AbstractVoidChest voidChest, @NonNull ILink link) {
        super(voidChest);
        this.link = link;
    }

    /**
     * Retrieves the link that was added.
     *
     * @return The link that was added.
     */
    public ILink getLink() {
        return this.link;
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
}
