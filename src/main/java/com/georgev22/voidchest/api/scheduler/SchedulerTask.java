package com.georgev22.voidchest.api.scheduler;

public interface SchedulerTask {

    void cancel();

    boolean isCancelled();

    int getTaskId();

    boolean isRunning();

}
