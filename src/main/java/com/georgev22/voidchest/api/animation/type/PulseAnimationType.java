package com.georgev22.voidchest.api.animation.type;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.animation.frame.generators.GlitchFrameGenerator;
import com.georgev22.voidchest.api.animation.frame.generators.PulseFrameGenerator;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;

public final class PulseAnimationType implements AnimationType {

    private static final NamespacedKey KEY = new NamespacedKey("voidchest", "pulse");

    @Override
    public @NonNull String getName() {
        return "Pulse";
    }

    @Override
    public @NonNull FrameGenerator create(@NonNull AnimationRequest request) {
        return new PulseFrameGenerator(request);
    }

    @Override
    public @NonNull NamespacedKey getKey() {
        return KEY;
    }
}