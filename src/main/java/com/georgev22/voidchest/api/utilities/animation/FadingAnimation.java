package com.georgev22.voidchest.api.utilities.animation;

import com.georgev22.voidchest.api.utilities.color.Color;
import com.georgev22.voidchest.api.utilities.color.ColorCalculations;
import com.georgev22.voidchest.api.utilities.color.MinecraftColor;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("UnnecessaryUnicodeEscape")
public class FadingAnimation extends Animation {

    @Override
    public String render(String string, boolean bold, int start, int end, @NonNull List<Color> colors) {
        if (colors.isEmpty()) {
            throw new IllegalArgumentException("Not enough colors provided");
        }

        String key = "fading-" + string + "-" + bold + "-" + start + "-" + end + "-" +
                colors.stream().map(Color::getColorCode).collect(Collectors.joining("-"));

        if (animationCache.containsKey(key)) {
            return currentFrame(animationCache.get(key));
        }

        ArrayList<String> frames = new ArrayList<>();

        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            Color color2 = colors.get((i + 1) % colors.size());

            frames.addAll(Collections.nCopies(
                    start,
                    color.getAppliedTag(
                            supportsHex,
                            MinecraftColor.getClosest(color).getAppliedTag()
                    ) + (bold ? "\u00A7l" : "") + string));

            for (Color intermediate : ColorCalculations.getColorsInBetween(color, color2, end)) {
                frames.add(
                        intermediate.getAppliedTag(
                                supportsHex,
                                MinecraftColor.getClosest(color).getAppliedTag()
                        ) + (bold ? "\u00A7l" : "") + string);
            }
        }

        animationCache.put(key, frames);
        return currentFrame(frames);
    }

    @Override
    protected int computeStart(@NonNull String string) {
        return Math.max(1, string.length() / 4);
    }

    @Override
    protected int computeEnd(@NonNull String string) {
        return Math.max(5, string.length());
    }

    @Override
    public @NonNull String getName() {
        return "fade";
    }
}
