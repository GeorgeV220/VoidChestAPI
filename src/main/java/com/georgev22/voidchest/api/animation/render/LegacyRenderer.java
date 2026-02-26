package com.georgev22.voidchest.api.animation.render;

import com.georgev22.voidchest.api.animation.frame.FrameData;
import com.georgev22.voidchest.api.utilities.BukkitMinecraftUtils;
import com.georgev22.voidchest.api.utilities.color.Color;
import com.georgev22.voidchest.api.utilities.color.MinecraftColor;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * Renders animation frames as legacy Minecraft color codes (§ codes).
 */
public final class LegacyRenderer implements AnimationRenderer<String> {

    /**
     * Indicates whether the current Minecraft version supports hex color codes.
     */
    private static final boolean supportsHex = BukkitMinecraftUtils.MinecraftVersion.getCurrentVersion().isAboveOrEqual(BukkitMinecraftUtils.MinecraftVersion.V1_16_R1);

    @Override
    public @NonNull String render(@NonNull String text, @NonNull FrameData frame, boolean bold) {
        int[] codePoints;
        if (frame.hasCustomChars()) {
            List<Integer> custom = frame.codePoints();
            codePoints = custom.stream().mapToInt(Integer::intValue).toArray();
        } else {
            codePoints = text.codePoints().toArray();
        }
        StringBuilder sb = new StringBuilder(text.length() * 2);

        for (int i = 0; i < codePoints.length; i++) {
            Color c = frame.colors().get(i);

            sb.append(c.getAppliedTag(
                    supportsHex,
                    MinecraftColor.getClosest(c).getAppliedTag()
            ));

            if (bold) sb.append("\u00A7l");

            sb.appendCodePoint(codePoints[i]);
        }

        return sb.toString();
    }
}