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
 * Sparkle animation.
 * Random characters brighten briefly.
 */
public final class SparkleFrameGenerator implements FrameGenerator {

    private final AnimationRequest request;
    private final int length;

    public SparkleFrameGenerator(@NonNull AnimationRequest request) {
        this.request = request;
        this.length = request.text().codePointCount(0, request.text().length());
    }

    @Override
    public @NonNull FrameData generateFrame(int totalTick) {

        Random random = new Random(totalTick * 17L);

        List<Color> result = new ArrayList<>(length);
        List<Color> palette = request.requestOptions().colors();

        double sparkleChance = 0.08;

        for (int i = 0; i < length; i++) {
            Color base = palette.get(i % palette.size());

            if (random.nextDouble() < sparkleChance) {
                result.add(base.scaleBrightness(1.8));
            } else {
                result.add(base);
            }
        }

        return new FrameData(result);
    }
}