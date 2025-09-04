package com.georgev22.voidchest.api.events.voidchest;

import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.link.ILink;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidChestLinkRemoveEvent class is an event that is fired when a link is removed from a VoidChest.
 * It extends the VoidEvent class.
 * Cancelling this event won't prevent the link from being removed.
 */
public class VoidChestLinkRemoveEvent extends VoidEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final ILink link;

    /**
     * Creates a new VoidChestLinkRemoveEvent.
     *
     * @param voidChest The voidChest associated with the event.
     * @param link      The link that was added.
     */
    public VoidChestLinkRemoveEvent(@NotNull IVoidChest voidChest, @NotNull ILink link) {
        super(voidChest);
        this.link = link;
    }

    /**
     * Retrieves the link that was removed.
     *
     * @return The link that was removed.
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
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
