package com.georgev22.voidchest.api.utilities.animation;

import com.georgev22.voidchest.api.utilities.color.Color;
import com.georgev22.voidchest.api.utilities.color.MinecraftColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings("UnnecessaryUnicodeEscape")
public class GlitchAnimation extends Animation {

    private static final char[] glitchChars = {'#', '@', '*', '&', '%', '!', '?', '$', '~'};
    private static final Random random = new Random();

    @Override
    public String render(String string, boolean bold, int start, int end, @NotNull List<Color> colors) {
        if (colors.isEmpty()) {
            throw new IllegalArgumentException("Glitch animation needs at least one color");
        }

        String key = "glitch-" + string + "-" + bold + "-" + start + "-" + end + "-" +
                colors.stream().map(Color::getColorCode).collect(Collectors.joining("-"));

        if (animationCache.containsKey(key)) {
            return currentFrame(animationCache.get(key));
        }

        List<String> frames = new ArrayList<>();

        for (int i = 0; i < end; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < string.length(); j++) {
                boolean glitch = random.nextDouble() < 0.3;
                char ch = glitch ? glitchChars[random.nextInt(glitchChars.length)] : string.charAt(j);
                Color color = colors.get(random.nextInt(colors.size()));
                builder.append(color.getAppliedTag(supportsHex, MinecraftColor.getClosest(color).getAppliedTag()));
                if (bold) builder.append("\u00A7l");
                builder.append(ch);
            }
            frames.add(builder.toString());
        }

        animationCache.put(key, frames);
        return currentFrame(frames);
    }

    @Override
    protected int computeEnd(@NotNull String string) {
        return Math.max(15, string.length() * 2);
    }

}
