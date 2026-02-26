package com.georgev22.voidchest.api.animation.frame.generators;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameData;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.utilities.color.Color;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Wave animation with moving colors and brightness.
 */
public final class WaveFrameGenerator implements FrameGenerator {

    private final AnimationRequest request;
    private final int length;

    public WaveFrameGenerator(@NonNull AnimationRequest request) {
        this.request = request;
        this.length = request.text().codePointCount(0, request.text().length());
    }

    @Override
    public @NonNull FrameData generateFrame(int totalTick) {

        List<Color> baseColors = request.requestOptions().colors();
        List<Color> result = new ArrayList<>(length);

        double speed = 1.0 / request.requestOptions().ticksPerFrame();
        double colorShiftSpeed = 0.2;
        double brightnessFrequency = 0.5;

        int paletteSize = baseColors.size();

        for (int i = 0; i < length; i++) {

            double shiftedIndex = (i + totalTick * colorShiftSpeed) % paletteSize;
            int fromIndex = (int) shiftedIndex;
            int toIndex = (fromIndex + 1) % paletteSize;
            double t = shiftedIndex - fromIndex;

            Color from = baseColors.get(fromIndex);
            Color to = baseColors.get(toIndex);

            Color color = from.interpolate(to, t);

            double wave = Math.sin(i * brightnessFrequency + totalTick * speed);
            double brightness = (wave + 1) / 2.0;

            result.add(color.scaleBrightness(brightness));
        }

        return new FrameData(result);
    }
}