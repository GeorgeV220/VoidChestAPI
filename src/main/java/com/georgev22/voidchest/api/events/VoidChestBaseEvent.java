package com.georgev22.voidchest.api.events;

import com.georgev22.voidchest.api.VoidChestAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

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
     * <p>
     * <ul>
     *     <li>If the event is asynchronous and currently on the main thread, it will be scheduled to run asynchronously.</li>
     *     <li>If the event is synchronous and currently not on the main thread, it will be scheduled to run synchronously on the main thread.</li>
     *     <li>Otherwise, if the event is already running in the correct thread context, it will be called directly.</li>
     * </ul>
     *
     * @param <T> the type of the event
     * @return the event instance after being called
     */
    public <T extends Event> T call() {
        boolean isAsync = this.isAsynchronous();
        boolean isPrimary = Bukkit.isPrimaryThread();

        if (isAsync && isPrimary) {
            // If the event is asynchronous and running on the main thread, schedule it asynchronously
            return callAsynchronousEvent();
        } else if (!isAsync && !isPrimary) {
            // If the event is synchronous but not running on the main thread, schedule it synchronously
            return callSynchronousEvent();
        } else {
            // If the event is in the correct thread context, execute it directly
            return callEventDirectly();
        }
    }

    /**
     * Handles the execution of a synchronous event. This method can be overridden
     * if you want specific behavior for your event.
     *
     * @param <T> the type of the event
     * @return the event instance after being called
     */
    protected <T extends Event> T callSynchronousEvent() {
        // Synchronous event must run on the main thread
        //noinspection unchecked
        return (T) voidChestAPI.minecraftScheduler().runTask(voidChestAPI.plugin(), () -> {
            Bukkit.getPluginManager().callEvent(this);
            return this;
        }).handle((event, throwable) -> {
            if (throwable != null) {
                voidChestAPI.plugin()
                        .getLogger().log(Level.SEVERE, "Failed to fire synchronous event: " + this.getEventName(), throwable);
            }
            return event;
        }).join();
    }

    /**
     * Handles the execution of an asynchronous event. This method can be overridden
     * if you want specific behavior for your event.
     *
     * @param <T> the type of the event
     * @return the event instance after being called
     */
    protected <T extends Event> T callAsynchronousEvent() {
        // Asynchronous event should not run on the main thread
        //noinspection unchecked
        return (T) voidChestAPI.minecraftScheduler().runAsyncTask(voidChestAPI.plugin(), () -> {
            Bukkit.getPluginManager().callEvent(this);
            return this;
        }).handle((event, throwable) -> {
            if (throwable != null) {
                voidChestAPI.plugin()
                        .getLogger().log(Level.SEVERE, "Failed to fire async event: " + this.getEventName(), throwable);
            }
            return event;
        }).join();
    }

    /**
     * Handles the execution of the event directly when the thread context is already correct.
     *
     * @param <T> the type of the event
     * @return the event instance after being called
     */
    protected <T extends Event> T callEventDirectly() {
        Bukkit.getPluginManager().callEvent(this);
        //noinspection unchecked
        return (T) this;
    }
}
