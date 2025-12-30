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
public class WaveAnimation extends Animation {

    @Override
    public String render(String string, boolean bold, int start, int end, @NonNull List<Color> colors) {
        if (colors.isEmpty()) {
            throw new IllegalArgumentException("Not enough colors provided");
        }

        String key = "wave-" + string + "-" + bold + "-" + start + "-" + end + "-" +
                colors.stream().map(Color::getColorCode).collect(Collectors.joining("-"));

        if (animationCache.containsKey(key)) {
            return currentFrame(animationCache.get(key));
        }

        ArrayList<String> frames = new ArrayList<>();

        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            Color color2 = colors.get((i + 1) % colors.size());

            frames.addAll(Collections.nCopies(start, color.getAppliedTag(supportsHex, MinecraftColor.getClosest(color).getAppliedTag()) + (bold ? "\u00A7l" : "") + string));

            List<String> transition = new ArrayList<>();
            transition.addAll(Collections.nCopies(string.length(), color.getAppliedTag(supportsHex, MinecraftColor.getClosest(color).getAppliedTag())));
            transition.addAll(ColorCalculations.getColorsInBetween(color, color2, end).stream()
                    .map(c -> c.getAppliedTag(supportsHex, MinecraftColor.getClosest(c).getAppliedTag()))
                    .toList());
            transition.addAll(Collections.nCopies(string.length(), color2.getAppliedTag(supportsHex, MinecraftColor.getClosest(color2).getAppliedTag())));

            for (int j = 0; j <= transition.size() - string.length(); ++j) {
                StringBuilder builder = new StringBuilder();
                for (int k = 0; k < string.length(); ++k) {
                    builder.append(transition.get(j + k)).append(bold ? "\u00A7l" : "").append(string.charAt(k));
                }
                frames.add(builder.toString());
            }

            frames.addAll(Collections.nCopies(start, color2.getAppliedTag(supportsHex, MinecraftColor.getClosest(color2).getAppliedTag()) + (bold ? "\u00A7l" : "") + string));
        }

        animationCache.put(key, frames);
        return currentFrame(frames);
    }

    @Override
    protected int computeStart(String string) {
        return 1;
    }

    @Override
    protected int computeEnd(@NonNull String string) {
        return Math.max(10, string.length() * 2);
    }

}
