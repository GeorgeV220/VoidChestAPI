package com.georgev22.voidchest.api.scheduler;

import org.jetbrains.annotations.NotNull;

/**
 * A  non-extendable interface representing a scheduler for task scheduling and cancellation.
 */
public interface MinecraftScheduler<Plugin, Location, World, Chunk, Entity> {

    /**
     * Schedules a task to be executed synchronously on the server's main thread.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed.
     */
    SchedulerTask runTask(Plugin plugin, Runnable task);

    /**
     * Schedules a task to be executed asynchronously on a separate thread.
     * This method is suitable for handling time-consuming tasks to avoid blocking the main thread.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed.
     */
    SchedulerTask runAsyncTask(Plugin plugin, Runnable task);

    /**
     * Creates a delayed task that will run the specified `task` after the given `delay`.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed after the delay.
     * @param delay  The delay before the task is executed.
     */
    SchedulerTask createDelayedTask(Plugin plugin, Runnable task, long delay);

    /**
     * Creates a repeating task that will run the specified `task` after an initial `delay`,
     * and then repeatedly execute with the given `period` between executions.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed repeatedly.
     * @param delay  The delay before the first execution.
     * @param period The time between successive executions.
     */
    SchedulerTask createRepeatingTask(Plugin plugin, Runnable task, long delay, long period);

    /**
     * Schedules an asynchronous delayed task that will run the specified `task` after the given `delay`.
     * The task will be executed on a separate thread, making it suitable for non-blocking operations.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed after the delay.
     * @param delay  The delay before the task is executed.
     */
    SchedulerTask createAsyncDelayedTask(Plugin plugin, Runnable task, long delay);

    /**
     * Schedules an asynchronous repeating task that will run the specified `task` after an initial `delay`,
     * and then repeatedly execute with the given `period` between executions.
     * The task will be executed on a separate thread, making it suitable for non-blocking operations.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed repeatedly.
     * @param delay  The delay before the first execution.
     * @param period The time between successive executions.
     */
    SchedulerTask createAsyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period);

    /**
     * Creates a delayed task for a specific world and chunk.
     *
     * @param plugin The plugin that owns this task.
     * @param task   The runnable task to execute.
     * @param world  The world in which the chunk is located.
     * @param chunk  The chunk in which the task will be executed.
     * @param delay  The delay in ticks before the task is executed.
     * @return A SchedulerTask representing the created task.
     */
    SchedulerTask createDelayedTaskForWorld(Plugin plugin, Runnable task, World world, @NotNull Chunk chunk, long delay);

    /**
     * Creates a delayed task for a specific location.
     *
     * @param plugin   The plugin that owns this task.
     * @param task     The runnable task to execute.
     * @param location The location at which the task will be executed.
     * @param delay    The delay in ticks before the task is executed.
     * @return A SchedulerTask representing the created task.
     */
    SchedulerTask createDelayedForLocation(Plugin plugin, Runnable task, Location location, long delay);

    /**
     * Creates a delayed task for a specific location.
     *
     * @param plugin  The plugin that owns this task.
     * @param task    The runnable task to execute.
     * @param retired Retire callback to run if the entity is retired before the run callback can be invoked, may be null.
     * @param entity  The entity in which the task will be executed.
     * @param delay   The delay in ticks before the task is executed.
     * @return A SchedulerTask representing the created task.
     */
    SchedulerTask createDelayedForEntity(Plugin plugin, Runnable task, Runnable retired, Entity entity, long delay);

    /**
     * Creates a task for a specific world and chunk.
     *
     * @param plugin The plugin that owns this task.
     * @param task   The runnable task to execute.
     * @param world  The world in which the chunk is located.
     * @param chunk  The chunk in which the task will be executed.
     * @return A SchedulerTask representing the created task.
     */
    SchedulerTask createTaskForWorld(Plugin plugin, Runnable task, World world, @NotNull Chunk chunk);

    /**
     * Creates a task for a specific location.
     *
     * @param plugin   The plugin that owns this task.
     * @param task     The runnable task to execute.
     * @param location The location at which the task will be executed.
     * @return A SchedulerTask representing the created task.
     */
    SchedulerTask createTaskForLocation(Plugin plugin, Runnable task, Location location);

    /**
     * Creates a task for a specific location.
     *
     * @param plugin  The plugin that owns this task.
     * @param task    The runnable task to execute.
     * @param retired Retire callback to run if the entity is retired before the run callback can be invoked, may be null.
     * @param entity  The entity in which the task will be executed.
     * @return A SchedulerTask representing the created task.
     */
    SchedulerTask createTaskForEntity(Plugin plugin, Runnable task, Runnable retired, Entity entity);

    /**
     * Creates a repeating task for a specific world and chunk.
     *
     * @param plugin The plugin that owns this task.
     * @param task   The runnable task to execute.
     * @param world  The world in which the chunk is located.
     * @param chunk  The chunk in which the task will be executed.
     * @param delay  The initial delay in ticks before the first execution.
     * @param period The period in ticks between consecutive executions.
     * @return A SchedulerTask representing the created task.
     */
    SchedulerTask createRepeatingTaskForWorld(Plugin plugin, Runnable task, World world, @NotNull Chunk chunk, long delay, long period);

    /**
     * Creates a repeating task for a specific location.
     *
     * @param plugin   The plugin that owns this task.
     * @param task     The runnable task to execute.
     * @param location The location at which the task will be executed.
     * @param delay    The initial delay in ticks before the first execution.
     * @param period   The period in ticks between consecutive executions.
     * @return A SchedulerTask representing the created task.
     */
    SchedulerTask createRepeatingTaskForLocation(Plugin plugin, Runnable task, Location location, long delay, long period);

    /**
     * Creates a repeating task for a specific location.
     *
     * @param plugin  The plugin that owns this task.
     * @param task    The runnable task to execute.
     * @param retired Retire callback to run if the entity is retired before the run callback can be invoked, may be null.
     * @param entity  The entity in which the task will be executed.
     * @param delay   The initial delay in ticks before the first execution.
     * @param period  The period in ticks between consecutive executions.
     * @return A SchedulerTask representing the created task.
     */
    SchedulerTask createRepeatingTaskForEntity(Plugin plugin, Runnable task, Runnable retired, Entity entity, long delay, long period);

    /**
     * Cancels all tasks associated with the given `plugin`.
     *
     * @param plugin The plugin whose tasks should be canceled.
     */
    void cancelTasks(Plugin plugin);

    /**
     * Gets the scheduler
     *
     * @return The scheduler
     */
    MinecraftScheduler<Plugin, Location, World, Chunk, Entity> getScheduler();

}
