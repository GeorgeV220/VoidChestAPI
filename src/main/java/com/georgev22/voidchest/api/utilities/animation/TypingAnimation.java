package com.georgev22.voidchest.api.utilities.animation;

import com.georgev22.voidchest.api.utilities.color.Color;
import com.georgev22.voidchest.api.utilities.color.MinecraftColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("UnnecessaryUnicodeEscape")
public class TypingAnimation extends Animation {

    @Override
    public String render(String string, boolean bold, int start, int end, @NotNull List<Color> colors) {
        if (colors.isEmpty()) {
            throw new IllegalArgumentException("Typing animation needs at least one color");
        }

        String key = "typing-" + string + "-" + bold + "-" + start + "-" + end + "-" +
                colors.stream().map(Color::getColorCode).collect(Collectors.joining("-"));

        if (animationCache.containsKey(key)) {
            return currentFrame(animationCache.get(key));
        }

        List<String> frames = new ArrayList<>();
        String prefix = colors.getFirst()
                .getAppliedTag(
                        supportsHex,
                        MinecraftColor.getClosest(colors.getFirst()).getAppliedTag()
                ) + (bold ? "\u00A7l" : "");

        for (int i = 1; i <= string.length(); i++) {
            frames.add(prefix + string.substring(0, i));
        }
        for (int i = string.length(); i >= 0; i--) {
            frames.add(prefix + string.substring(0, i));
        }

        animationCache.put(key, frames);
        return currentFrame(frames);
    }

    @Override
    protected int computeEnd(@NotNull String string) {
        return string.length() * 2;
    }

    @Override
    public @NotNull String getName() {
        return "typing";
    }
}
