package com.georgev22.voidchest.api.task;

import com.georgev22.voidchest.api.task.tasks.ITimedTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Interface for managing ITimedTask objects, allowing them to be added, removed, stopped, and resumed.
 */
public interface ITimedTaskManager {

    /**
     * Adds a new ITimedTask object.
     *
     * @param task The ITimedTask instance.
     * @return The UUID of the added task.
     */
    UUID addObject(ITimedTask task);

    /**
     * Adds a new ITimedTask object with a Runnable action.
     *
     * @param id               The UUID of the task.
     * @param runnable         The Runnable action to execute when the countdown reaches zero.
     * @param initialCountdown The initial countdown duration in seconds.
     * @return The UUID of the added task.
     */
    UUID addObject(UUID id, Runnable runnable, int initialCountdown);

    /**
     * Adds a new ITimedTask object and starts its countdown immediately.
     *
     * @param task The ITimedTask instance.
     * @return The UUID of the added task.
     */
    UUID addObjectAndStart(@NotNull ITimedTask task);

    /**
     * Adds a new ITimedTask object with a Runnable action and starts its countdown immediately.
     *
     * @param id               The UUID of the task.
     * @param runnable         The Runnable action to execute when the countdown reaches zero.
     * @param initialCountdown The initial countdown duration in seconds.
     * @return The UUID of the added task.
     */
    UUID addObjectAndStart(UUID id, Runnable runnable, int initialCountdown);

    /**
     * Starts a task's countdown.
     *
     * @param id The UUID of the task to start.
     */
    void start(UUID id);

    /**
     * Removes a task by its UUID, stopping and canceling it.
     *
     * @param id The UUID of the task to remove.
     */
    void removeObject(UUID id);

    /**
     * Stops a task's countdown without removing it.
     *
     * @param id The UUID of the task to stop.
     */
    void stopObject(UUID id);

    /**
     * Cancels a task's countdown and the worker executor.
     *
     * @param id The UUID of the task to cancel.
     */
    void cancelObject(UUID id);

    /**
     * Resumes a previously stopped task.
     *
     * @param id The UUID of the task to resume.
     */
    void resumeObject(UUID id);

    /**
     * Starts all active ITimedTask objects.
     */
    void startAll();

    /**
     * Stops all active ITimedTask objects.
     */
    void stopAll();

    /**
     * Cancels all active ITimedTask objects and their worker executors.
     */
    void cancelAll();

    /**
     * Resumes all previously stopped ITimedTask objects.
     */
    void resumeAll();

    /**
     * Retrieves a ITimedTask object associated with the specified UUID from the collection of active objects.
     * If no task is found with the given UUID, the method returns null.
     *
     * @param id The unique identifier of the task to retrieve.
     * @return The ITimedTask object corresponding to the provided UUID, or null if no such task exists.
     */
    @Nullable ITimedTask getTask(UUID id);

    /**
     * Retrieves a read-only view of the collection of active ITimedTask objects.
     *
     * @return A read-only view of the collection of active ITimedTask objects.
     */
    @UnmodifiableView
    Map<UUID, ITimedTask> getActiveObjects();

    /**
     * Retrieves the ScheduledExecutorService used for scheduling tasks.
     *
     * @return The ScheduledExecutorService used for scheduling tasks.
     */
    ScheduledExecutorService getScheduler();
}
