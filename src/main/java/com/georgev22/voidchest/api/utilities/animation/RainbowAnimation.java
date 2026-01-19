package com.georgev22.voidchest.api.utilities.animation;

import com.georgev22.voidchest.api.utilities.color.Color;
import com.georgev22.voidchest.api.utilities.color.ColorCalculations;
import com.georgev22.voidchest.api.utilities.color.MinecraftColor;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("UnnecessaryUnicodeEscape")
public class RainbowAnimation extends Animation {

    @Override
    public String render(String string, boolean bold, int start, int end, @NonNull List<Color> colors) {
        if (colors.size() < 2) {
            throw new IllegalArgumentException("Rainbow animation needs at least two colors");
        }

        String key = "rainbow-" + string + "-" + bold + "-" + start + "-" + end + "-" +
                colors.stream().map(Color::getColorCode).collect(Collectors.joining("-"));

        if (animationCache.containsKey(key)) {
            return currentFrame(animationCache.get(key));
        }

        List<String> frames = new ArrayList<>();
        int steps = string.length();

        for (int offset = 0; offset < steps; offset++) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < string.length(); i++) {
                Color from = colors.get((i + offset) % colors.size());
                Color to = colors.get((i + offset + 1) % colors.size());
                Color interp = ColorCalculations.interpolateColor(from, to, (float) i / steps);
                builder.append(interp.getAppliedTag(supportsHex, MinecraftColor.getClosest(interp).getAppliedTag()));
                if (bold) builder.append("\u00A7l");
                builder.append(string.charAt(i));
            }
            frames.add(builder.toString());
        }

        animationCache.put(key, frames);
        return currentFrame(frames);
    }

    @Override
    protected int computeEnd(@NonNull String string) {
        return string.length();
    }

    @Override
    public @NonNull String getName() {
        return "rainbow";
    }
}
