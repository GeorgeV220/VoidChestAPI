package com.georgev22.voidchest.api.animation.frame.generators;

import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.frame.FrameData;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.utilities.color.Color;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Glitch animation.
 * Randomly distorts characters and colors.
 */
public final class GlitchFrameGenerator implements FrameGenerator {

    private static final char[] GLITCH_CHARS = "!@#$%^&*()[]{}<>?/\\|+-=_".toCharArray();

    private final AnimationRequest request;
    private final int length;
    private final int[] originalCodePoints;

    public GlitchFrameGenerator(@NonNull AnimationRequest request) {
        this.request = request;
        this.originalCodePoints = request.text().codePoints().toArray();
        this.length = originalCodePoints.length;
    }

    @Override
    public @NonNull FrameData generateFrame(int totalTick) {
        Random random = new Random(totalTick * 31L);
        List<Color> colors = new ArrayList<>(length);
        List<Integer> chars = new ArrayList<>(length);

        List<Color> palette = request.requestOptions().colors();

        double glitchChance = 0.15;

        for (int i = 0; i < length; i++) {
            Color base = palette.get(i % palette.size());

            if (random.nextDouble() < glitchChance) {
                double factor = 0.5 + random.nextDouble();
                colors.add(base.scaleBrightness(factor));
            } else {
                colors.add(base);
            }

            if (random.nextDouble() < glitchChance) {
                char glitch = GLITCH_CHARS[random.nextInt(GLITCH_CHARS.length)];
                chars.add((int) glitch);
            } else {
                chars.add(originalCodePoints[i]);
            }
        }

        return new FrameData(colors, chars);
    }
}