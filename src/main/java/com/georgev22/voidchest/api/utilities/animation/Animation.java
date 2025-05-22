package com.georgev22.voidchest.api.utilities.animation;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.utilities.BukkitMinecraftUtils.MinecraftVersion;
import com.georgev22.voidchest.api.utilities.color.Color;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Base class for all text animations.
 * Subclasses must implement the {@link #render(String, boolean, int, int, List)} method
 * to define how the animation behaves.
 */
public abstract class Animation {

    /**
     * Indicates whether the current Minecraft version supports hex color codes.
     */
    protected static final boolean supportsHex = MinecraftVersion.getCurrentVersion().isAboveOrEqual(MinecraftVersion.V1_16_R1);

    /**
     * Caches animation frame data mapped by identifier.
     */
    protected static final ObjectMap<String, List<String>> animationCache = ObjectMap.newHashObjectMap();

    /**
     * Factory method to retrieve a specific animation implementation.
     *
     * @param animation The name of the animation (e.g., "fade", "wave").
     * @return An instance of the corresponding {@link Animation}, or a no-op implementation if unknown.
     */
    public static @NotNull Animation valueOf(@NotNull String animation) {
        return switch (animation.toLowerCase()) {
            case "fade" -> new FadingAnimation();
            case "wave" -> new WaveAnimation();
            case "rainbow" -> new RainbowAnimation();
            case "pulse" -> new PulseAnimation();
            case "typing" -> new TypingAnimation();
            case "sparkle" -> new SparkleAnimation();
            case "glitch" -> new GlitchAnimation();
            default -> new Animation() {
                @Override
                public String render(String string, boolean bold, int start, int end, @NotNull List<Color> colors) {
                    VoidChestAPI.getInstance().plugin().getLogger().warning("Unknown animation: " + animation);
                    return string;
                }
            };
        };
    }

    /**
     * Renders the animation on a given string using default timing and no bold formatting.
     *
     * @param string The text to animate.
     * @param colors The colors to use in the animation.
     * @return The rendered animated string.
     */
    public String render(String string, @NotNull List<Color> colors) {
        return render(string, false, computeStart(string), computeEnd(string), colors);
    }

    /**
     * Renders the animation on a given string using default timing and bold formatting if specified.
     *
     * @param string The text to animate.
     * @param bold   Whether to apply bold formatting to the text.
     * @param colors The colors to use in the animation.
     * @return The rendered animated string.
     */
    public String render(String string, boolean bold, @NotNull List<Color> colors) {
        return render(string, bold, computeStart(string), computeEnd(string), colors);
    }

    /**
     * Core method to render the animation.
     *
     * @param string The text to animate.
     * @param bold   Whether to apply bold formatting.
     * @param start  The animation start frame offset.
     * @param end    The animation end frame offset.
     * @param colors The colors used during animation.
     * @return The resulting animated string.
     */
    public abstract String render(String string, boolean bold, int start, int end, @NotNull List<Color> colors);

    /**
     * Retrieves the current frame from a list of precomputed frames based on system time.
     *
     * @param frames A list of frames representing animation states.
     * @return The frame corresponding to the current tick.
     */
    protected static String currentFrame(@NotNull List<String> frames) {
        long l = System.currentTimeMillis() / 50L;
        int i = (int) (l % (long) frames.size());
        return frames.get(i);
    }

    /**
     * Computes the default start offset for an animation. Subclasses can override.
     *
     * @param string The input string.
     * @return The starting frame offset.
     */
    protected int computeStart(String string) {
        return 0;
    }

    /**
     * Computes the default end offset for an animation. Subclasses can override.
     *
     * @param string The input string.
     * @return The ending frame offset.
     */
    protected int computeEnd(@NotNull String string) {
        return Math.max(10, string.length());
    }
}
