package com.georgev22.voidchest.api.utilities.message;

import org.bukkit.permissions.ServerOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class that manages placeholders and dynamic states for message formatting.
 * <p>
 * Supports:
 * <ul>
 *     <li>Simple string placeholders (e.g., %player%)</li>
 *     <li>Boolean states for inline switches (e.g., {autosell:enabled|disabled})</li>
 *     <li>Integration with {@link ServerOperator} for PlaceholderAPI</li>
 * </ul>
 */
public class Placeholder {

    private final Map<String, String> placeholders = new HashMap<>();
    private final Map<String, Boolean> states = new HashMap<>();
    private @Nullable ServerOperator target;

    /**
     * Creates an empty Placeholder instance.
     */
    public Placeholder() {
    }

    /**
     * Creates a Placeholder instance with a target {@link ServerOperator}.
     *
     * @param target The target player or server operator for PlaceholderAPI integration.
     */
    public Placeholder(@NotNull ServerOperator target) {
        this.target = target;
    }

    /**
     * Sets the target {@link ServerOperator} for PlaceholderAPI integration.
     *
     * @param target The target player or server operator.
     * @return The current Placeholder instance for chaining.
     */
    public Placeholder setTarget(@Nullable ServerOperator target) {
        this.target = target;
        return this;
    }

    /**
     * Adds a simple placeholder key-value pair.
     * <p>Example: addPlaceholder("%player%", "George")</p>
     *
     * @param key   The placeholder key.
     * @param value The replacement value.
     * @return The current Placeholder instance for chaining.
     */
    public Placeholder addPlaceholder(@NonNull String key, @NonNull String value) {
        placeholders.put(key, value);
        return this;
    }

    /**
     * Adds multiple placeholders from a map.
     *
     * @param map A map containing key-value pairs for placeholders.
     * @return The current Placeholder instance for chaining.
     */
    public Placeholder addPlaceholders(@NonNull Map<String, String> map) {
        placeholders.putAll(map);
        return this;
    }

    /**
     * Adds a boolean state for inline dynamic switches.
     * <p>Example: addState("autosell", true) will allow "{autosell:enabled|disabled}" to resolve to "enabled".</p>
     *
     * @param key   The state key.
     * @param state The boolean state.
     * @return The current Placeholder instance for chaining.
     */
    public Placeholder addState(@NonNull String key, boolean state) {
        states.put(key, state);
        return this;
    }

    /**
     * Adds multiple boolean states from a map.
     *
     * @param map A map containing key-boolean pairs for states.
     * @return The current Placeholder instance for chaining.
     */
    public Placeholder addStates(@NonNull Map<String, Boolean> map) {
        states.putAll(map);
        return this;
    }

    /**
     * Resolves all placeholders and states in the given input string.
     * <p>This method will replace:</p>
     * <ul>
     *     <li>Simple placeholders (e.g., %player%)</li>
     *     <li>Inline switches using boolean states (e.g., {autosell:enabled|disabled})</li>
     *     <li>Integrates with PlaceholderAPI if target is set</li>
     * </ul>
     *
     * @param input The input string containing placeholders and inline switches.
     * @return The resolved string with placeholders replaced.
     */
    public @NotNull String resolve(@NonNull String input) {
        //noinspection ConstantValue
        if (input == null) return "";

        input = PlaceholderUtils.resolveInlineSwitches(input, states);

        return PlaceholderUtils.replace(target, input, placeholders, true);
    }
}