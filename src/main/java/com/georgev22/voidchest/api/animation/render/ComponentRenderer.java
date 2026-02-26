package com.georgev22.voidchest.api.animation.render;

import com.georgev22.voidchest.api.animation.frame.FrameData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * Renders animation frames as Adventure Components.
 */
public final class ComponentRenderer implements AnimationRenderer<Component> {

    @Override
    public @NonNull Component render(@NonNull String text, @NonNull FrameData frame, boolean bold) {
        int[] codePoints;
        if (frame.hasCustomChars()) {
            List<Integer> custom = frame.codePoints();
            codePoints = custom.stream().mapToInt(Integer::intValue).toArray();
        } else {
            codePoints = text.codePoints().toArray();
        }

        TextComponent.Builder builder = Component.text();

        for (int i = 0; i < codePoints.length; i++) {

            Component c = Component.text(new String(Character.toChars(codePoints[i])))
                    .color(TextColor.color(frame.colors().get(i).getRGB()));

            if (bold) c = c.decorate(TextDecoration.BOLD);
            builder.append(c);
        }

        return builder.build();
    }
}