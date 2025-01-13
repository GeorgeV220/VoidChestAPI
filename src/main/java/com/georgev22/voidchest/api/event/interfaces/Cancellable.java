package com.georgev22.voidchest.api.event.interfaces;

/**
 * An interface representing an event that can be cancelled.
 */
public interface Cancellable {

    /**
     * Cancels the event.
     *
     * @return {@code true} if the event was successfully cancelled, {@code false} otherwise
     * @deprecated Use {@link #setCancelled(boolean)} instead
     */
    @Deprecated
    default boolean cancel() {
        setCancelled(true);
        return true;
    }

    /**
     * Sets whether the event has been cancelled.
     *
     * @param cancelled {@code true} if the event should be cancelled, {@code false} otherwise
     */
    void setCancelled(boolean cancelled);

    /**
     * Returns whether the event has been cancelled.
     *
     * @return {@code true} if the event has been cancelled, {@code false} otherwise
     */
    boolean isCancelled();

}