package com.georgev22.voidchest.api.utilities.animation;

import com.georgev22.voidchest.api.utilities.color.Color;
import com.georgev22.voidchest.api.utilities.color.ColorCalculations;
import com.georgev22.voidchest.api.utilities.color.MinecraftColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("UnnecessaryUnicodeEscape")
public class PulseAnimation extends Animation {

    @Override
    public String render(String string, boolean bold, int start, int end, @NotNull List<Color> colors) {
        if (colors.isEmpty()) {
            throw new IllegalArgumentException("Pulse animation needs at least one color");
        }

        String key = "pulse-" + string + "-" + bold + "-" + start + "-" + end + "-" +
                colors.stream().map(Color::getColorCode).collect(Collectors.joining("-"));

        if (animationCache.containsKey(key)) {
            return currentFrame(animationCache.get(key));
        }

        List<String> frames = new ArrayList<>();

        Color base = colors.getFirst();
        List<Color> pulse = ColorCalculations.getColorsInBetween(base.darker(), base.brighter(), end);
        pulse.addAll(ColorCalculations.getColorsInBetween(base.brighter(), base.darker(), end));

        for (Color c : pulse) {
            frames.add(
                    c.getAppliedTag(supportsHex, MinecraftColor.getClosest(c).getAppliedTag()) +
                            (bold ? "\u00A7l" : "") + string);
        }

        animationCache.put(key, frames);
        return currentFrame(frames);
    }

    @Override
    protected int computeEnd(@NotNull String string) {
        return Math.max(8, string.length() / 2);
    }

    @Override
    public @NotNull String getName() {
        return "pulse";
    }
}
