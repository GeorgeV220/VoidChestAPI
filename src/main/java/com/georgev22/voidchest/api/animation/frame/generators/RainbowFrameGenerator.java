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
 * Rainbow animation frame generator.
 * Smooth, continuous, and allocation-minimized.
 */
public final class RainbowFrameGenerator implements FrameGenerator {

    private final AnimationRequest request;
    private final int length;

    public RainbowFrameGenerator(@NonNull AnimationRequest request) {
        this.request = request;
        this.length = request.text().codePointCount(0, request.text().length());
    }

    @Override
    public @NonNull FrameData generateFrame(int totalTick) {

        List<Color> result = new ArrayList<>(length);
        List<Color> palette = request.requestOptions().colors();

        int paletteSize = Math.max(1, palette.size());
        double speed = 1.0 / request.requestOptions().ticksPerFrame();

        for (int i = 0; i < length; i++) {

            double position = (i + totalTick * speed) % paletteSize;
            int indexFrom = (int) position;
            int indexTo = (indexFrom + 1) % paletteSize;

            double t = position - indexFrom;

            Color from = palette.get(indexFrom);
            Color to = palette.get(indexTo);

            result.add(ColorCalculations.interpolateColor(from, to, (float) t));
        }

        return new FrameData(result);
    }
}