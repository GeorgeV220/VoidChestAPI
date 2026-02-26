package com.georgev22.voidchest.api.animation.frame;

import com.georgev22.voidchest.api.utilities.color.Color;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds per-frame animation data: colors, and optionally characters.
 *
 * @param codePoints optional, empty if original chars
 */
public record FrameData(@NonNull List<Color> colors, @NonNull List<Integer> codePoints) {

    public FrameData(@NonNull List<Color> colors) {
        this(colors, new ArrayList<>());
    }

    public FrameData(@NonNull List<Color> colors, @NonNull List<Integer> codePoints) {
        this.colors = colors;
        this.codePoints = codePoints;
    }

    public boolean hasCustomChars() {
        return !codePoints.isEmpty();
    }
}