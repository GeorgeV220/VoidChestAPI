package com.georgev22.voidchest.api.task.tasks;

import java.util.UUID;

/**
 * Interface for a timed task that counts down before executing an action.
 */
public interface ITimedTask {
    /**
     * Gets the UUID of this task.
     *
     * @return The task's unique ID.
     */
    UUID getId();

    /**
     * Gets the initial countdown time.
     *
     * @return The initial countdown duration in seconds.
     */
    int getInitialCountdown();

    /**
     * Starts the countdown. If already running, this method does nothing.
     */
    void startCountdown();

    /**
     * Executes the defined action when the countdown reaches zero.
     */
    void triggerAction();

    /**
     * Stops the countdown, preventing further time decrement.
     */
    void stop();

    /**
     * Resumes the countdown if it was previously stopped.
     */
    void resume();

    /**
     * Cancels the countdown, stopping it permanently and shutting down the worker executor.
     */
    void cancel();

    /**
     * Sets the initial countdown duration in seconds.
     *
     * @param initialCountdown The new initial countdown duration in seconds.
     */
    void setInitialCountdown(int initialCountdown);

    /**
     * Retrieves the remaining time as an integer value.
     *
     * @return The remaining time as an integer.
     */
    Integer getRemainingTime();

    /**
     * Checks whether the task associated with this instance has been cancelled.
     *
     * @return true if the task is cancelled, false otherwise.
     */
    boolean isCancelled();
}
