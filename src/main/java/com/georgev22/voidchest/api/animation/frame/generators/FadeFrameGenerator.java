package com.georgev22.voidchest.api.animation.frame.generators;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameData;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.utilities.color.Color;
import com.georgev22.voidchest.api.utilities.color.ColorCalculations;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Fade animation.
 * Smoothly interpolates between multiple colors across the entire text.
 */
public final class FadeFrameGenerator implements FrameGenerator {

    private final AnimationRequest request;
    private final int charCount;
    private final List<Color> palette;

    public FadeFrameGenerator(@NonNull AnimationRequest request) {
        this.request = request;
        this.charCount = request.text().codePointCount(0, request.text().length());
        this.palette = request.requestOptions().colors();

        if (palette.isEmpty()) {
            throw new IllegalArgumentException("Colors cannot be empty");
        }
    }

    @Override
    public @NonNull FrameData generateFrame(int totalTick) {
        double speed = 1.0 / request.requestOptions().ticksPerFrame();
        double position = totalTick * speed;

        int indexFrom = (int) position % palette.size();
        int indexTo = (indexFrom + 1) % palette.size();
        float t = (float) (position - (int) position);

        Color from = palette.get(indexFrom);
        Color to = palette.get(indexTo);
        Color blended = ColorCalculations.interpolateColor(from, to, t);

        List<Color> frameColors = new ArrayList<>(charCount);
        for (int i = 0; i < charCount; i++) frameColors.add(blended);

        return new FrameData(frameColors);
    }
}