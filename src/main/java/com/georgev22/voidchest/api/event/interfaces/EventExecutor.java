package com.georgev22.voidchest.api.event.interfaces;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.executor.MethodHandleEventExecutor;
import com.georgev22.voidchest.api.event.executor.StaticMethodHandleEventExecutor;
import com.georgev22.voidchest.api.event.executor.asm.ASMEventExecutorGenerator;
import com.georgev22.voidchest.api.event.executor.asm.ClassDefiner;
import com.georgev22.voidchest.api.exceptions.EventException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Interface defining the class for event callbacks to plugins.
 */
public interface EventExecutor {
    /**
     * Executes the event listener with the given event.
     *
     * @param listener The event listener to execute.
     * @param event    The event to pass to the listener.
     * @throws EventException If an exception occurs during event execution.
     */
    void execute(@NotNull EventListener listener, @NotNull Event event) throws EventException;

    /**
     * Map to store the association between methods and their corresponding EventExecutor classes.
     */
    ConcurrentMap<Method, Class<? extends EventExecutor>> eventExecutorMap = new ConcurrentHashMap<>();

    /**
     * Creates an EventExecutor for the given method and event class.
     *
     * @param m          The method to create an executor for.
     * @param eventClass The event class to match with the method's first parameter.
     * @return An EventExecutor instance.
     */
    @NotNull
    static EventExecutor create(@NotNull Method m, @NotNull Class<? extends Event> eventClass) {
        //noinspection ConstantValue
        if (m == null) {
            throw new IllegalArgumentException("Null method");
        }
        if (m.getParameterCount() == 0) {
            throw new IllegalArgumentException("Incorrect number of arguments for method " + m.getName());
        }
        if (m.getParameterTypes()[0] != eventClass) {
            throw new IllegalArgumentException("First parameter " + m.getParameterTypes()[0] + " doesn't match event class " + eventClass);
        }

        ClassDefiner definer = ClassDefiner.getInstance();

        if (Modifier.isStatic(m.getModifiers())) {
            return new StaticMethodHandleEventExecutor(eventClass, m);
        } else if (definer.isBypassAccessChecks() || (Modifier.isPublic(m.getDeclaringClass().getModifiers()) && Modifier.isPublic(m.getModifiers()))) {
            // Generate or retrieve the existing EventExecutor class for the Method
            Class<? extends EventExecutor> executorClass = eventExecutorMap.computeIfAbsent(m, (__ -> {
                String name = ASMEventExecutorGenerator.generateName();
                byte[] classData = ASMEventExecutorGenerator.generateEventExecutor(m, name);
                return definer.defineClass(m.getDeclaringClass().getClassLoader(), name, classData).asSubclass(EventExecutor.class);
            }));

            try {
                // Create an instance of the generated EventExecutor class
                EventExecutor asmExecutor = executorClass.getDeclaredConstructor().newInstance();

                // Define a wrapper to conform to certain requirements
                return new EventExecutor() {
                    @Override
                    public void execute(@NotNull EventListener listener, @NotNull Event event) throws EventException {
                        if (!eventClass.isInstance(event)) return;
                        asmExecutor.execute(listener, event);
                    }

                    @Override
                    @NotNull
                    public String toString() {
                        return "ASMEventExecutor['" + m + "']";
                    }
                };
            } catch (ReflectiveOperationException e) {
                throw new AssertionError("Unable to initialize generated event executor", e);
            }
        } else {
            return new MethodHandleEventExecutor(eventClass, m);
        }
    }
}
