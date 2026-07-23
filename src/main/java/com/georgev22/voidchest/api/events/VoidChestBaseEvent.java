package com.georgev22.voidchest.api.events;

import com.georgev22.voidchest.api.VoidChestAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public abstract class VoidChestBaseEvent extends Event {

    protected final VoidChestAPI voidChestAPI = VoidChestAPI.getInstance();
    protected final boolean isFolia = VoidChestAPI.isFolia();

    /**
     * Default constructor for cleaner code. This constructor assumes the event
     * is synchronous.
     */
    public VoidChestBaseEvent() {
        this(false);
    }

    /**
     * Constructor used to explicitly declare an event as synchronous or asynchronous.
     *
     * @param isAsync true indicates the event will fire asynchronously, false otherwise
     */
    public VoidChestBaseEvent(boolean isAsync) {
        super(isAsync);
    }

    /**
     * Calls this event, ensuring it runs in the correct thread context.
     *
     * @param <T> the type of the event
     * @return a future that completes with the event instance once it has been fired
     */
    public <T extends Event> CompletableFuture<T> call() {
        boolean async = isAsynchronous();
        boolean primary = Bukkit.isPrimaryThread();

        if (async && primary) {
            return callAsynchronousEvent();
        }

        if (!async && !primary) {
            return callSynchronousEvent();
        }

        return CompletableFuture.completedFuture(callEventDirectly());
    }

    /**
     * Executes this synchronous event on the main thread.
     *
     * @param <T> the event type
     * @return a future completing with the event instance
     */
    @SuppressWarnings("unchecked")
    protected <T extends Event> CompletableFuture<T> callSynchronousEvent() {
        return voidChestAPI.minecraftScheduler()
                .runTask(() -> {
                    Bukkit.getPluginManager().callEvent(this);
                    return (T) this;
                })
                .whenComplete((event, throwable) -> {
                    if (throwable != null) {
                        voidChestAPI.plugin().getLogger().log(
                                Level.SEVERE,
                                "Failed to fire synchronous event: " + getEventName(),
                                throwable
                        );
                    }
                });
    }

    /**
     * Executes this asynchronous event on a worker thread.
     *
     * @param <T> the event type
     * @return a future completing with the event instance
     */
    @SuppressWarnings("unchecked")
    protected <T extends Event> CompletableFuture<T> callAsynchronousEvent() {
        return voidChestAPI.minecraftScheduler()
                .runAsyncTask(() -> {
                    Bukkit.getPluginManager().callEvent(this);
                    return (T) this;
                })
                .whenComplete((event, throwable) -> {
                    if (throwable != null) {
                        voidChestAPI.plugin().getLogger().log(
                                Level.SEVERE,
                                "Failed to fire async event: " + getEventName(),
                                throwable
                        );
                    }
                });
    }

    /**
     * Executes the event immediately in the current thread.
     *
     * @param <T> the event type
     * @return the event instance
     */
    @SuppressWarnings("unchecked")
    protected <T extends Event> T callEventDirectly() {
        Bukkit.getPluginManager().callEvent(this);
        return (T) this;
    }
}
