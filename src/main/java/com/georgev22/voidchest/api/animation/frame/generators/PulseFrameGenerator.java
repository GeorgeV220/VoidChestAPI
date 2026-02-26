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
 * Pulse animation.
 * Entire text pulses in brightness between darker and brighter variants of base color.
 */
public final class PulseFrameGenerator implements FrameGenerator {

    private final AnimationRequest request;
    private final int length;
    private final List<Color> pulseColors;

    public PulseFrameGenerator(@NonNull AnimationRequest request) {
        this.request = request;
        this.length = request.text().codePointCount(0, request.text().length());

        Color base = request.requestOptions().colors().getFirst();
        List<Color> up = ColorCalculations.getColorsInBetween(base.darker(), base.brighter(), computeSteps());
        List<Color> down = ColorCalculations.getColorsInBetween(base.brighter(), base.darker(), computeSteps());

        pulseColors = new ArrayList<>(up.size() + down.size());
        pulseColors.addAll(up);
        pulseColors.addAll(down);
    }

    private int computeSteps() {
        return Math.max(8, length / 2);
    }

    @Override
    public @NonNull FrameData generateFrame(int totalTick) {
        double speed = 1.0 / request.requestOptions().ticksPerFrame();
        int index = (int) (totalTick * speed) % pulseColors.size();
        Color c = pulseColors.get(index);

        List<Color> result = new ArrayList<>(length);
        for (int i = 0; i < length; i++) result.add(c);

        return new FrameData(result);
    }
}