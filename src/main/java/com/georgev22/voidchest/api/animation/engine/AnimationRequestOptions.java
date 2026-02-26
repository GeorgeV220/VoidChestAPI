package com.georgev22.voidchest.api.animation.engine;

import com.georgev22.voidchest.api.utilities.color.Color;

import java.util.List;

/**
 * Configuration options for an {@link AnimationRequest}.
 * <p>
 * This record holds the visual and timing options for animations, including text styling,
 * colors, frame count, and frame speed.
 * </p>
 *
 * <ul>
 *     <li><b>bold</b> – whether the text should be rendered in bold.</li>
 *     <li><b>colors</b> – the list of {@link Color} objects used by the animation. Animations
 *     may cycle through or scale these colors depending on the generator.</li>
 *     <li><b>ticksPerFrame</b> – the number of game ticks each frame should be displayed
 *     before moving to the next frame.</li>
 * </ul>
 *
 * @param bold          whether the text is bold
 * @param colors        list of colors for the animation
 * @param ticksPerFrame duration of each frame in ticks
 */
public record AnimationRequestOptions(
        boolean bold,
        List<Color> colors,
        int ticksPerFrame
) {
}