package com.georgev22.voidchest.api.task;

/**
 * Represents runtime metrics of an executor.
 */
public record ExecutorMetrics(int poolSize, int activeThreads, int queuedTasks, long completedTasks) {

}