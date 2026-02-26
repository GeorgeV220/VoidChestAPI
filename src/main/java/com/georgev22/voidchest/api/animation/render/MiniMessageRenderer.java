package com.georgev22.voidchest.api.animation.render;

import com.georgev22.voidchest.api.animation.frame.FrameData;
import com.georgev22.voidchest.api.utilities.color.Color;
import org.jspecify.annotations.NonNull;

import java.util.List;

public final class MiniMessageRenderer implements AnimationRenderer<String> {

    @Override
    public @NonNull String render(@NonNull String text, @NonNull FrameData frame, boolean bold) {
        int[] codePoints;
        if (frame.hasCustomChars()) {
            List<Integer> custom = frame.codePoints();
            codePoints = custom.stream().mapToInt(Integer::intValue).toArray();
        } else {
            codePoints = text.codePoints().toArray();
        }
        StringBuilder sb = new StringBuilder(text.length() * 12);

        if (codePoints.length == 0) return "";

        Color lastColor = frame.colors().getFirst();
        StringBuilder block = new StringBuilder();

        for (int i = 0; i < codePoints.length; i++) {

            Color currentColor = frame.colors().get(i);
            String character = new String(Character.toChars(codePoints[i]));
            if (bold) character = "<b>" + character + "</b>";

            if (currentColor.equals(lastColor)) {
                block.append(character);
            } else {
                sb.append(lastColor.toMiniMessage(block.toString()));

                block.setLength(0);
                block.append(character);
                lastColor = currentColor;
            }
        }

        sb.append(lastColor.toMiniMessage(block.toString()));

        return sb.toString();
    }
}