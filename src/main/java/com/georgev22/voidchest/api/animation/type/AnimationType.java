package com.georgev22.voidchest.api.animation.type;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import org.bukkit.Keyed;
import org.jspecify.annotations.NonNull;

/**
 * Defines a specific animation behavior.
 */
public interface AnimationType extends Keyed {

    @NonNull String getName();

    @NonNull FrameGenerator create(@NonNull AnimationRequest request);
}