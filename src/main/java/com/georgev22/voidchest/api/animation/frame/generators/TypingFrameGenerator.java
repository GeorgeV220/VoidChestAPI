package com.georgev22.voidchest.api.animation.frame.generators;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameData;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.utilities.color.Color;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Typing animation.
 * Characters progressively appear.
 */
public final class TypingFrameGenerator implements FrameGenerator {

    private final AnimationRequest request;
    private final int length;

    public TypingFrameGenerator(@NonNull AnimationRequest request) {
        this.request = request;
        this.length = request.text().codePointCount(0, request.text().length());
    }

    @Override
    public @NonNull FrameData generateFrame(int totalTick) {
        List<Color> result = new ArrayList<>(length);
        List<Color> palette = request.requestOptions().colors();

        double speed = 1.0 / request.requestOptions().ticksPerFrame();
        int charsToShow = Math.min(length, (int) (totalTick * speed));

        for (int i = 0; i < length; i++) {
            if (i < charsToShow) {
                result.add(palette.get(i % palette.size()));
            } else {
                result.add(Color.from(0, 0, 0));
            }
        }

        return new FrameData(result);
    }
}