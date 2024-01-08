package com.georgev22.voidchest.api.event;

import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.event.interfaces.EventExecutor;
import com.georgev22.voidchest.api.event.interfaces.EventListener;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 * A wrapper for event listeners, containing metadata about the listener and the method it should invoke when an event
 * occurs.
 */
public record ListenerWrapper(
        EventExecutor eventExecutor,
        Class<?> aClass,
        EventListener listener,
        Method method,
        EventPriority eventPriority,
        boolean ignoreCancelled
) {

    /**
     * Creates a new listener wrapper instance.
     *
     * @param eventExecutor   The event executor that this listener belongs to.
     * @param aClass          The class that this listener belongs to.
     * @param listener        The listener objects itself.
     * @param method          The method to invoke when an event occurs.
     * @param eventPriority   The priority of this listener relative to other listeners.
     * @param ignoreCancelled Whether this listener should ignore cancelled events.
     */
    public ListenerWrapper(
            EventExecutor eventExecutor,
            Class<?> aClass,
            EventListener listener,
            Method method,
            EventPriority eventPriority,
            boolean ignoreCancelled
    ) {
        this.eventExecutor = eventExecutor;
        this.aClass = aClass;
        this.listener = listener;
        this.method = method;
        this.eventPriority = eventPriority;
        this.ignoreCancelled = ignoreCancelled;
        this.method.setAccessible(true);
    }

    /**
     * Invokes the wrapped listener's method with the given event.
     *
     * @param event The event to pass to the listener.
     */
    public void callEvent(@NotNull final Event event) {
        if (event instanceof Cancellable) {
            if (((Cancellable) event).isCancelled() && ignoreCancelled) {
                return;
            }
        }
        eventExecutor.execute(listener, event);
    }

    @Override
    public String toString() {
        return "ListenerWrapper{" +
                "EventManager caller=" + aClass.getSimpleName() +
                ", listener=" + listener.getClass().getSimpleName() +
                ", method=" + method.getName() +
                ", eventPriority=" + eventPriority.name() + " (slot: " + eventPriority.getValue() + ")" +
                ", ignoreCancelled=" + ignoreCancelled +
                '}';
    }
}
