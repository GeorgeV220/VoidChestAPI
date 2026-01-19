package com.georgev22.voidchest.api.utilities.animation;

import com.georgev22.voidchest.api.utilities.color.Color;
import com.georgev22.voidchest.api.utilities.color.MinecraftColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings("UnnecessaryUnicodeEscape")
public class SparkleAnimation extends Animation {

    private static final Random random = new Random();

    @Override
    public String render(String string, boolean bold, int start, int end, @NotNull List<Color> colors) {
        if (colors.isEmpty()) {
            throw new IllegalArgumentException("Sparkle animation needs at least one color");
        }

        String key = "sparkle-" + string + "-" + bold + "-" + start + "-" + end + "-" +
                colors.stream().map(Color::getColorCode).collect(Collectors.joining("-"));

        if (animationCache.containsKey(key)) {
            return currentFrame(animationCache.get(key));
        }

        List<String> frames = new ArrayList<>();

        for (int i = 0; i < end; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < string.length(); j++) {
                Color chosen = (random.nextDouble() < 0.15)
                        ? Color.from("#FFFFFF")
                        : colors.get(random.nextInt(colors.size()));
                builder.append(chosen.getAppliedTag(supportsHex, MinecraftColor.getClosest(chosen).getAppliedTag()));
                if (bold) builder.append("\u00A7l");
                builder.append(string.charAt(j));
            }
            frames.add(builder.toString());
        }

        animationCache.put(key, frames);
        return currentFrame(frames);
    }

    @Override
    public @NotNull String getName() {
        return "sparkle";
    }
}
