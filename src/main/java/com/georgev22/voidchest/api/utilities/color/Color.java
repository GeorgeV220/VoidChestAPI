package com.georgev22.voidchest.api.utilities.color;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a color with support for hexadecimal, RGB, and various formatting tags.
 */
@SuppressWarnings("UnnecessaryUnicodeEscape")
public class Color {

    private final String colorCode;
    private final int r;
    private final int g;
    private final int b;
    private final java.awt.Color javaColor;

    /**
     * Creates a {@link Color} instance from a hex string (with or without the # prefix).
     *
     * @param colorCode The hex color code.
     * @return A new {@link Color} instance.
     */
    @Contract("_ -> new")
    public static @NonNull Color from(String colorCode) {
        return new Color(colorCode);
    }

    /**
     * Creates a {@link Color} instance from RGB values.
     *
     * @param r Red component (0–255).
     * @param g Green component (0–255).
     * @param b Blue component (0–255).
     * @return A new {@link Color} instance.
     */
    public static @NonNull Color from(int r, int g, int b) {
        java.awt.Color javaColor = new java.awt.Color(r, g, b);
        return from(Integer.toHexString(javaColor.getRGB()).substring(2));
    }

    /**
     * Constructs a {@link Color} from a hex code.
     *
     * @param colorCode Hexadecimal color code (e.g., #ff5733 or ff5733).
     */
    private Color(@NonNull String colorCode) {
        this.colorCode = colorCode.replace("#", "");
        javaColor = new java.awt.Color(Integer.parseInt(this.colorCode, 16));
        this.r = javaColor.getRed();
        this.g = javaColor.getGreen();
        this.b = javaColor.getBlue();
    }

    /**
     * @return The hexadecimal color code (without the # prefix).
     */
    public String getColorCode() {
        return this.colorCode;
    }

    /**
     * @return The red component of the color.
     */
    public int getRed() {
        return this.r;
    }

    /**
     * @return The green component of the color.
     */
    public int getGreen() {
        return this.g;
    }

    /**
     * @return The blue component of the color.
     */
    public int getBlue() {
        return this.b;
    }

    /**
     * Returns the underlying {@link java.awt.Color} instance for this color.
     *
     * <p>This provides direct access to the Java AWT color representation,
     * allowing interoperability with APIs that require {@code java.awt.Color}.
     *
     * <p>The returned instance is immutable and safe to use externally.
     *
     * @return the {@link java.awt.Color} equivalent of this color
     */
    public java.awt.Color getJavaColor() {
        return javaColor;
    }

    /**
     * Returns the packed RGB integer value of this color.
     *
     * <p>The returned value is in the standard {@code 0xAARRGGBB} format used by
     * {@link java.awt.Color#getRGB()}, where:
     * <ul>
     *     <li>AA = Alpha (always 255 / 0xFF for this class)</li>
     *     <li>RR = Red component</li>
     *     <li>GG = Green component</li>
     *     <li>BB = Blue component</li>
     * </ul>
     *
     * <p>This value is suitable for APIs that expect a packed RGB integer,
     * such as Adventure's {@code TextColor.color(int)}.
     *
     * @return the packed ARGB integer representation of this color
     */
    public int getRGB() {
        return javaColor.getRGB();
    }

    /**
     * @return A darker version of this color.
     */
    public @NonNull Color darker() {
        return Color.from(javaColor.darker().getRed(), javaColor.darker().getGreen(), javaColor.darker().getBlue());
    }

    /**
     * @return A brighter version of this color.
     */
    public @NonNull Color brighter() {
        return Color.from(javaColor.brighter().getRed(), javaColor.brighter().getGreen(), javaColor.brighter().getBlue());
    }

    /**
     * Gets the color code formatted as a legacy Minecraft text color code or as the closest fallback.
     *
     * @param ver     Whether to return the full §x-style hex format (Minecraft 1.16+).
     * @param closest A fallback color code to return if ver is false.
     * @return The formatted color code.
     */
    public String getAppliedTag(boolean ver, String closest) {
        return ver
                ? "\u00A7x" + Arrays.stream(this.colorCode.split(""))
                .map((paramString) -> "\u00A7" + paramString)
                .collect(Collectors.joining())
                : closest;
    }

    /**
     * @return The color with a leading '#' (e.g., #ff5733).
     */
    public String toHex() {
        return "#" + this.colorCode;
    }

    /**
     * @return The color formatted in MiniMessage tag format (e.g., &lt;color:#ff5733&gt;).
     */
    public String getMiniMessageTag() {
        return "<color:#" + this.colorCode + ">";
    }

    /**
     * Wraps text in a MiniMessage color tag using this color.
     *
     * @param text The text to wrap.
     * @return The colorized MiniMessage string.
     */
    public String toMiniMessage(String text) {
        return this.getMiniMessageTag() + text + "</color>";
    }

    /**
     * Calculates the RGB difference between two {@link Color} instances.
     *
     * @param color1 The first color.
     * @param color2 The second color.
     * @return The sum of absolute RGB differences.
     */
    public static int difference(@NonNull Color color1, @NonNull Color color2) {
        return Math.abs(color1.r - color2.r)
                + Math.abs(color1.g - color2.g)
                + Math.abs(color1.b - color2.b);
    }

    /**
     * Scales the brightness of this color by a factor.
     *
     * @param factor The brightness factor (>1 to brighten, <1 to darken, 0 = black).
     * @return A new {@link Color} with adjusted brightness.
     */
    public @NonNull Color scaleBrightness(double factor) {
        int newR = (int) Math.min(255, Math.max(0, r * factor));
        int newG = (int) Math.min(255, Math.max(0, g * factor));
        int newB = (int) Math.min(255, Math.max(0, b * factor));
        return Color.from(newR, newG, newB);
    }

    /**
     * Linearly interpolates between this color and the target color.
     * <p>
     * The interpolation is calculated for each RGB channel separately using the formula:
     * <pre>
     * channel = this.channel + (to.channel - this.channel) * t
     * </pre>
     * The result is clamped to the range [0, 255] and rounded to the nearest integer.
     * </p>
     *
     * @param to the target {@link Color} to interpolate towards; must not be null
     * @param t  the interpolation factor, where 0.0 returns this color, and 1.0 returns the target color.
     *           Values outside [0.0, 1.0] will be clamped.
     * @return a new {@link Color} representing the interpolated color between this and {@code to}
     */
    public Color interpolate(@NonNull Color to, double t) {
        t = Math.min(1.0, Math.max(0.0, t));

        int newR = (int) Math.round(Math.min(255, Math.max(0, r + (to.r - r) * t)));
        int newG = (int) Math.round(Math.min(255, Math.max(0, g + (to.g - g) * t)));
        int newB = (int) Math.round(Math.min(255, Math.max(0, b + (to.b - b) * t)));

        return Color.from(newR, newG, newB);
    }

    /**
     * @return A string representation using the Minecraft §x hex format.
     */
    @Override
    public String toString() {
        return this.getAppliedTag(true, "");
    }

    /**
     * Compares this color to another for equality based on RGB and hex code.
     *
     * @param o The object to compare.
     * @return True if equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color color)) return false;
        return r == color.r && g == color.g && b == color.b && Objects.equals(colorCode, color.colorCode);
    }

    /**
     * @return A hash code based on RGB and hex code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(colorCode, r, g, b);
    }
}
