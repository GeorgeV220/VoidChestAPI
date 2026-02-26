package com.georgev22.voidchest.api.animation.type;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.animation.frame.generators.GlitchFrameGenerator;
import com.georgev22.voidchest.api.animation.frame.generators.SparkleFrameGenerator;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;

public final class SparkleAnimationType implements AnimationType {

    private static final NamespacedKey KEY = new NamespacedKey("voidchest", "sparkle");

    @Override
    public @NonNull String getName() {
        return "Sparkle";
    }

    @Override
    public @NonNull FrameGenerator create(@NonNull AnimationRequest request) {
        return new SparkleFrameGenerator(request);
    }

    @Override
    public @NonNull NamespacedKey getKey() {
        return KEY;
    }
}