package com.georgev22.voidchest.api.animation.frame;

import org.jspecify.annotations.NonNull;

/**
 * Responsible for generating animation frame data.
 * Implementations must be stateless and deterministic.
 */
public interface FrameGenerator {

    @NonNull FrameData generateFrame(int totalTick);
}