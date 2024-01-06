package com.georgev22.voidchest.api.event;

import com.georgev22.voidchest.api.event.interfaces.Cancellable;
import com.georgev22.voidchest.api.event.interfaces.EventListener;
import com.georgev22.voidchest.api.exceptions.EventException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

/**
 * A wrapper for event listeners, containing metadata about the listener and the method it should invoke when an event
 * occurs.
 */
public record ListenerWrapper(
        ExecutorService executorService,
        Class<?> aClass,
        EventListener listener,
        Method method,
        EventPriority eventPriority,
        boolean ignoreCancelled
) {

    /**
     * Creates a new listener wrapper instance.
     *
     * @param executorService The executor service to use for asynchronous event handling.
     * @param aClass          The class that this listener belongs to.
     * @param listener        The listener objects itself.
     * @param method          The method to invoke when an event occurs.
     * @param eventPriority   The priority of this listener relative to other listeners.
     * @param ignoreCancelled Whether this listener should ignore cancelled events.
     */
    public ListenerWrapper(
            ExecutorService executorService,
            Class<?> aClass,
            EventListener listener,
            Method method,
            EventPriority eventPriority,
            boolean ignoreCancelled
    ) {
        this.executorService = executorService;
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
            if (((Cancellable) event).isCancelled() && ignoreCancelled()) {
                return;
            }
        }

        if (event.isAsynchronous()) {
            executeAsync(() -> invokeMethod(event));
        } else {
            invokeMethod(event);
        }
    }

    private void invokeMethod(Event event) {
        try {
            method.invoke(listener, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new EventException("Event " + event.getEventName() + " failed to fire due to an error", e);
        }
    }

    private void executeAsync(@NotNull Runnable runnable) {
        executorService.execute(runnable);
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
