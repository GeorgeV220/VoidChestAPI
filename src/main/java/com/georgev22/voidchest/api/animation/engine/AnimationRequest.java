package com.georgev22.voidchest.api.animation.engine;

import org.jspecify.annotations.NonNull;

/**
 * Immutable configuration describing a single animation request.
 * <p>
 * This record holds all the information required by a {@link com.georgev22.voidchest.api.animation.frame.FrameGenerator}
 * to generate frames for a text animation.
 * </p>
 *
 * <ul>
 *     <li><b>text</b> – the text string to animate. Characters are processed as code points to
 *     properly handle Unicode.</li>
 *     <li><b>requestOptions</b> – visual and timing options for the animation,
 *     such as colors, bold styling, frame count, and ticks per frame.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * AnimationRequestOptions options = new AnimationRequestOptions(true, List.of(Color.RED, Color.BLUE), 0, 2);
 * AnimationRequest request = new AnimationRequest("Hello", options);
 * }</pre>
 *
 * @param text           the text to animate, must not be null
 * @param requestOptions configuration options for the animation
 */
public record AnimationRequest(
        @NonNull String text,
        AnimationRequestOptions requestOptions
) {
}