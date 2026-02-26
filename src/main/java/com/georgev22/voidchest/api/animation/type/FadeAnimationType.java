package com.georgev22.voidchest.api.animation.type;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.animation.frame.generators.FadeFrameGenerator;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;

public final class FadeAnimationType implements AnimationType {

    private static final NamespacedKey KEY = new NamespacedKey("voidchest", "fade");

    @Override
    public @NonNull String getName() {
        return "Fade";
    }

    @Override
    public @NonNull FrameGenerator create(@NonNull AnimationRequest request) {
        return new FadeFrameGenerator(request);
    }

    @Override
    public @NonNull NamespacedKey getKey() {
        return KEY;
    }
}