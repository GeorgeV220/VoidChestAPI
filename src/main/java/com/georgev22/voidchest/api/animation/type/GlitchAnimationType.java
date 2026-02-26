package com.georgev22.voidchest.api.animation.type;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.animation.frame.generators.GlitchFrameGenerator;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;

public final class GlitchAnimationType implements AnimationType {

    private static final NamespacedKey KEY = new NamespacedKey("voidchest", "glitch");

    @Override
    public @NonNull String getName() {
        return "Glitch";
    }

    @Override
    public @NonNull FrameGenerator create(@NonNull AnimationRequest request) {
        return new GlitchFrameGenerator(request);
    }

    @Override
    public @NonNull NamespacedKey getKey() {
        return KEY;
    }
}