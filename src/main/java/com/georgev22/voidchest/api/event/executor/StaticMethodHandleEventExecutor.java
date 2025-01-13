package com.georgev22.voidchest.api.event.executor;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.interfaces.EventExecutor;
import com.georgev22.voidchest.api.event.interfaces.EventListener;
import com.georgev22.voidchest.api.exceptions.EventException;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaticMethodHandleEventExecutor implements EventExecutor {
    private final Class<? extends Event> eventClass;
    private final MethodHandle handle;
    private final Method method;

    public StaticMethodHandleEventExecutor(@NotNull Class<? extends Event> eventClass, @NotNull Method m) {
        Preconditions.checkArgument(Modifier.isStatic(m.getModifiers()), "Not a static method: %s", m);
        Preconditions.checkArgument(eventClass != null, "eventClass is null");
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
            handle.invoke(event);
        } catch (Throwable throwable) {
            Logger.getGlobal().log(Level.SEVERE, "Error executing " + method, throwable);
        }
    }

    @Override
    @NotNull
    public String toString() {
        return "StaticMethodHandleEventExecutor['" + this.method + "']";
    }
}
