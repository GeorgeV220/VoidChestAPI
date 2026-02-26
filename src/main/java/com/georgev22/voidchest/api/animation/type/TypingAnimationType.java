package com.georgev22.voidchest.api.animation.type;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.animation.frame.generators.GlitchFrameGenerator;
import com.georgev22.voidchest.api.animation.frame.generators.TypingFrameGenerator;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;

public final class TypingAnimationType implements AnimationType {

    private static final NamespacedKey KEY = new NamespacedKey("voidchest", "typing");

    @Override
    public @NonNull String getName() {
        return "Typing";
    }

    @Override
    public @NonNull FrameGenerator create(@NonNull AnimationRequest request) {
        return new TypingFrameGenerator(request);
    }

    @Override
    public @NonNull NamespacedKey getKey() {
        return KEY;
    }
}