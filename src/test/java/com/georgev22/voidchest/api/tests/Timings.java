package com.georgev22.voidchest.api.tests;

import org.jetbrains.annotations.NotNull;

public class Timings {

    public static long measureTime(@NotNull Runnable codeBlock) {
        long startTime = System.currentTimeMillis();
        codeBlock.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
