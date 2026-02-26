package com.georgev22.voidchest.api.animation.render;

import com.georgev22.voidchest.api.animation.frame.FrameData;
import org.jspecify.annotations.NonNull;

/**
 * Responsible for converting FrameData into a renderable output.
 */
public interface AnimationRenderer<T> {

    @NonNull T render(@NonNull String text, @NonNull FrameData frame, boolean bold);
}