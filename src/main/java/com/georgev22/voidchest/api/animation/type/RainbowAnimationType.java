package com.georgev22.voidchest.api.animation.type;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.animation.frame.generators.RainbowFrameGenerator;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;

public final class RainbowAnimationType implements AnimationType {

    private final static NamespacedKey KEY = new NamespacedKey("voidchest", "rainbow");

    @Override
    public @NonNull String getName() {
        return "Rainbow";
    }

    @Override
    public @NonNull FrameGenerator create(@NonNull AnimationRequest request) {
        return new RainbowFrameGenerator(request);
    }

    @Override
    public @NonNull NamespacedKey getKey() {
        return KEY;
    }
}