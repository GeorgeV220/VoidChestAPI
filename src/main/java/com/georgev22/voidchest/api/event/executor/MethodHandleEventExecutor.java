package com.georgev22.voidchest.api.event.executor;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.interfaces.EventExecutor;
import com.georgev22.voidchest.api.event.interfaces.EventListener;
import com.georgev22.voidchest.api.exceptions.EventException;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MethodHandleEventExecutor implements EventExecutor {
    private final Class<? extends Event> eventClass;
    private final MethodHandle handle;
    private final Method method;

    public MethodHandleEventExecutor(@NotNull Class<? extends Event> eventClass, @NotNull MethodHandle handle) {
        this.eventClass = eventClass;
        this.handle = handle;
        this.method = null;
    }

    public MethodHandleEventExecutor(@NotNull Class<? extends Event> eventClass, @NotNull Method m) {
        this.eventClass = eventClass;
        try {
            m.setAccessible(true);
            this.handle = MethodHandles.lookup().unreflect(m);
        } catch (IllegalAccessException e) {
            throw new AssertionError("Unable to set accessible", e);
        }
        this.method = m;
    }

    @Override
    public void execute(@NotNull EventListener listener, @NotNull Event event) throws EventException {
        if (!eventClass.isInstance(event)) return;
        try {
            handle.invoke(listener, event);
        } catch (Throwable t) {
            // TODO LOGGING SneakyThrow.sneaky(t);
        }
    }

    @Override
    @NotNull
    public String toString() {
        return "MethodHandleEventExecutor['" + this.method + "']";
    }
}
