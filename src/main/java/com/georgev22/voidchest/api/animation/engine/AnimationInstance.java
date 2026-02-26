package com.georgev22.voidchest.api.animation.engine;

import com.georgev22.voidchest.api.animation.frame.FrameData;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.animation.render.AnimationRenderer;
import org.jspecify.annotations.NonNull;

/**
 * Represents a running animation instance.
 * Not thread-safe. Intended for main-thread usage.
 */
public final class AnimationInstance<T> {

    private final AnimationRequest request;
    private final AnimationRenderer<T> renderer;
    private final FrameGenerator generator;

    private int totalTicks = 0;

    public AnimationInstance(@NonNull AnimationRequest request,
                             @NonNull FrameGenerator generator,
                             @NonNull AnimationRenderer<T> renderer) {
        this.request = request;
        this.generator = generator;
        this.renderer = renderer;
    }

    public @NonNull T tickAndRender() {
        totalTicks++;

        FrameData frame = generator.generateFrame(totalTicks);

        return renderer.render(
                request.text(),
                frame,
                request.requestOptions().bold()
        );
    }

    public void reset() {
        totalTicks = 0;
    }

    public AnimationRequest getRequest() {
        return request;
    }

    public FrameGenerator getGenerator() {
        return generator;
    }

    public AnimationRenderer<T> getRenderer() {
        return renderer;
    }
}