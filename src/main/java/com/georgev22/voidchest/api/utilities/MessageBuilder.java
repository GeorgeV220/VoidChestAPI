package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.datastructures.maps.HashObjectMap;
import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.utilities.BukkitMinecraftUtils.MinecraftVersion;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.bukkit.permissions.ServerOperator;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A utility class for building and sending styled chat messages to players in a Minecraft server environment
 * using the Adventure API. This class supports text colors, decorations, click events, hover events, and MiniMessage
 * formatting.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Create an instance of the MessageBuilder
 * MessageBuilder messageBuilder = new MessageBuilder();
 *
 * // Build a message using plain text and styles
 * messageBuilder
 *     .append("Welcome, ")
 *     .color(NamedTextColor.AQUA)
 *     .decorate(TextDecoration.BOLD)
 *     .append(player.getName())
 *     .color(NamedTextColor.GREEN)
 *     .append("! Click ")
 *     .color(NamedTextColor.YELLOW)
 *     .append("here")
 *     .color(NamedTextColor.RED)
 *     .decorate(TextDecoration.UNDERLINED)
 *     .clickEvent(ClickEvent.Action.RUN_COMMAND, "/help")
 *     .hoverEvent("Click to run /help")
 *     .append(" for more info.")
 *     .send(player);
 *
 * // Using MiniMessage for more complex formatting
 * messageBuilder
 *     .appendMiniMessage("<bold><aqua>Welcome, </aqua></bold><green>" + player.getName() + "</green>")
 *     .appendMiniMessage("<yellow> Click <underlined><red>here</red></underlined> for more info.</yellow>")
 *     .clickEvent(ClickEvent.Action.RUN_COMMAND, "/help")
 *     .hoverEvent("Click to run /help")
 *     .send(player);
 * }</pre>
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class MessageBuilder {
    private final ArrayList<TextDecoration> currentDecorations;
    private TextComponent.Builder componentBuilder;
    private TextColor currentColor = NamedTextColor.WHITE;
    private ClickEvent currentClickEvent;
    private HoverEvent<?> currentHoverEvent;
    private ObjectMap<String, String> placeholders = new HashObjectMap<>();
    private ServerOperator serverOperator;

    /**
     * Constructs a new MessageBuilder instance.
     */
    public MessageBuilder() {
        this.componentBuilder = Component.text();
        this.currentDecorations = new ArrayList<>();
    }

    /**
     * Creates a new MessageBuilder instance.
     *
     * @return A new MessageBuilder instance.
     */
    @Contract(" -> new")
    public static @NonNull MessageBuilder builder() {
        return new MessageBuilder();
    }

    public MessageBuilder context(@Nullable ServerOperator serverOperator) {
        this.serverOperator = serverOperator;
        return this;
    }

    /**
     * Appends plain text to the message and applies the current styles.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * messageBuilder
     *     .append("Hello ")
     *     .color(NamedTextColor.RED)
     *     .decorate(TextDecoration.BOLD)
     *     .append("World!");
     * }</pre>
     *
     * @param text The text to append.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder append(String text) {
        TextComponent.Builder textComponent = Component.text()
                .content(text)
                .color(currentColor);

        for (TextDecoration decoration : currentDecorations) {
            textComponent.decoration(decoration, true);
        }

        if (currentClickEvent != null) {
            textComponent.clickEvent(currentClickEvent);
        }

        if (currentHoverEvent != null) {
            textComponent.hoverEvent(currentHoverEvent);
        }

        componentBuilder.append(textComponent.build());
        return this;
    }

    /**
     * Appends a component to the message.
     *
     * @param component The component to append.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder append(Component component) {
        componentBuilder.append(component);
        return this;
    }

    /**
     * Appends MiniMessage-formatted text, allowing complex formatting using MiniMessage syntax.
     * <p>Example usage:</p>
     * <pre>{@code
     * messageBuilder
     *     .appendMiniMessage("<bold><green>Hello, World!</green></bold>")
     *     .clickEvent(ClickEvent.Action.RUN_COMMAND, "/example")
     *     .hoverEvent("Execute command /example");
     * }</pre>
     *
     * @param miniMessage The MiniMessage string to append.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder appendMiniMessage(String miniMessage) {
        return appendMiniMessage(miniMessage, null);
    }

    /**
     * Appends MiniMessage-formatted text, allowing complex formatting using MiniMessage syntax.
     * <p>Example usage:</p>
     * <pre>{@code
     * messageBuilder
     *     .appendMiniMessage("<bold><green>Hello, World!</green></bold>")
     *     .clickEvent(ClickEvent.Action.RUN_COMMAND, "/example")
     *     .hoverEvent("Execute command /example");
     * }</pre>
     *
     * @param miniMessage The MiniMessage string to append.
     * @param tagResolver A tag resolver for parsing the MiniMessage, can be null.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder appendMiniMessage(String miniMessage, TagResolver tagResolver) {
        componentBuilder.append(miniMessage(miniMessage, tagResolver)).decoration(TextDecoration.ITALIC, false);
        return this;
    }

    /**
     * Converts a MiniMessage string into a Component using an optional TagResolver.
     *
     * @param message     The MiniMessage string to convert.
     * @param tagResolver The TagResolver to use, can be null if not needed.
     * @return The converted Component.
     */
    private @NonNull Component miniMessage(String message, TagResolver tagResolver) {
        return MessageParser.miniMessage(message, tagResolver);
    }

    /**
     * Sets the color of the subsequent text.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * messageBuilder
     *     .color(NamedTextColor.BLUE)
     *     .append("This text is blue");
     * }</pre>
     *
     * @param color The TextColor to set.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder color(TextColor color) {
        this.currentColor = color;
        return this;
    }

    /**
     * Adds text decorations like bold, italic, etc.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * messageBuilder
     *     .decorate(TextDecoration.BOLD, TextDecoration.ITALIC)
     *     .append("Bold and Italic Text");
     * }</pre>
     *
     * @param decorations The TextDecorations to apply.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder decorate(TextDecoration... decorations) {
        Collections.addAll(this.currentDecorations, decorations);
        return this;
    }

    /**
     * Removes text decorations.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * messageBuilder
     *     .decorate(TextDecoration.BOLD)
     *     .append("Bold text")
     *     .removeDecorate(TextDecoration.BOLD)
     *     .append("Normal text");
     * }</pre>
     *
     * @param decorations The TextDecorations to remove.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder removeDecorate(TextDecoration... decorations) {
        this.currentDecorations.removeAll(Arrays.asList(decorations));
        return this;
    }

    /**
     * Sets a click event for the subsequent text.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * messageBuilder
     *     .append("Click me")
     *     .clickEvent(ClickEvent.Action.RUN_COMMAND, "/example");
     * }</pre>
     *
     * @param action The ClickEvent.Action to set.
     * @param value  The value associated with the action.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder clickEvent(ClickEvent.Action action, String value) {
        this.currentClickEvent = ClickEvent.clickEvent(action, value);
        return this;
    }

    /**
     * Sets a hover event for the subsequent text.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * messageBuilder
     *     .append("Hover over me")
     *     .hoverEvent("This is hover text");
     * }</pre>
     *
     * @param hoverText The text to show on hover.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder hoverEvent(String hoverText) {
        this.currentHoverEvent = HoverEvent.showText(miniMessage(hoverText, null));
        return this;
    }

    /**
     * Sends the constructed message to the specified player.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * messageBuilder
     *     .append("Hello, ")
     *     .append(player.getName())
     *     .append("!")
     *     .send(player);
     * }</pre>
     *
     * @param audience The player to send the message to.
     */
    public void send(@NonNull Audience audience) {
        audience.sendMessage(build());
    }

    /**
     * Builds the component for further use.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Component component = messageBuilder
     *     .append("Hello, World!")
     *     .build();
     * }</pre>
     *
     * @return The constructed Component.
     */
    public Component build() {
        return MessageParser.miniMessage(
                replacePlaceholders(
                        MiniMessage.miniMessage().serialize(componentBuilder.build())
                ),
                TagResolver.empty()
        );
    }

    /**
     * Serializes the built component into a string using Legacy format.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * String messageString = messageBuilder
     *     .append("Hello, World!")
     *     .buildLegacyString();
     * }</pre>
     *
     * @return The serialized Legacy string.
     */
    public String buildLegacyString() {
        if (MinecraftVersion.getCurrentVersion().isAboveOrEqual(MinecraftVersion.V1_16_R1)) {
            return LegacyComponentSerializer.builder()
                    .hexColors()
                    .useUnusualXRepeatedCharacterHexFormat()
                    .build()
                    .serialize(build());
        } else {
            return LegacyComponentSerializer.builder()
                    .build()
                    .serialize(build());
        }
    }

    /**
     * Builds the component and resets the current MessageBuilder.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Component component = messageBuilder
     *     .append("Hello, World!")
     *     .buildAndReset();
     * }</pre>
     *
     * @return The constructed Component.
     */
    public Component buildAndReset() {
        Component component = this.build();
        this.reset();
        return component;
    }

    /**
     * Builds the Legacy string and resets the current MessageBuilder.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * String legacyString = messageBuilder
     *     .append("Hello, World!")
     *     .buildAndResetLegacyString();
     * }</pre>
     *
     * @return The serialized Legacy string.
     */
    public String buildAndResetLegacyString() {
        String legacyString = this.buildLegacyString();
        this.reset();
        return legacyString;
    }

    /**
     * Serializes the built component into a string using MiniMessage format.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * String messageString = messageBuilder
     *     .append("Hello, World!")
     *     .buildString();
     * }</pre>
     *
     * @return The serialized MiniMessage string.
     */
    public String buildString() {
        return MiniMessage.miniMessage().serialize(build());
    }

    /**
     * Resets the current styles and events, restoring them to default values.
     * <p>Example usage:</p>
     * <pre>{@code
     * messageBuilder
     *     .resetStyles()
     *     .append("This text has default styling");
     * }</pre>
     *
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder resetStyles() {
        this.currentColor = NamedTextColor.WHITE;
        this.currentDecorations.clear();
        this.currentClickEvent = null;
        this.currentHoverEvent = null;
        return this;
    }

    public MessageBuilder reset() {
        this.componentBuilder = Component.text();
        this.placeholders.clear();
        return this.resetStyles();
    }

    /**
     * Sets the placeholders for the message.
     *
     * @param placeholders The placeholders to set.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder placeholders(ObjectMap<String, String> placeholders) {
        this.placeholders = new HashObjectMap<>(placeholders);
        return this;
    }

    /**
     * Clears all placeholders from the message.
     *
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder clearPlaceholders() {
        this.placeholders.clear();
        return this;
    }

    /**
     * Adds a placeholder to the message.
     *
     * @param key   The placeholder key.
     * @param value The placeholder value.
     * @return The MessageBuilder instance for method chaining.
     */
    public MessageBuilder addPlaceholder(String key, String value) {
        this.placeholders.put(key, value);
        return this;
    }

    /**
     * Replaces placeholders in the given string with their corresponding values.
     *
     * @param input The input string containing placeholders.
     * @return The input string with placeholders replaced.
     */
    private String replacePlaceholders(String input) {
        if (this.serverOperator != null) {
            return placeholderAPI(serverOperator, input, placeholders, true);
        }
        return Utils.placeHolder(input, placeholders, true);
    }


    /**
     * Translates all the placeholders of the string from the map
     *
     * @param target     target for the placeholders.
     * @param str        the input string to translate the placeholders on
     * @param map        the map that contains all the placeholders with the replacement
     * @param ignoreCase if it is <code>true</code> all the placeholders will be replaced
     *                   in ignore case
     * @return the new string with the placeholders replaced
     */
    public static String placeholderAPI(final ServerOperator target, String str, final Map<String, String> map, final boolean ignoreCase) {
        if (target == null) {
            throw new IllegalArgumentException("The target can't be null");
        }
        if (str == null) {
            throw new IllegalArgumentException("The string can't be null!");
        }
        if (map == null) {
            try {
                if (target instanceof OfflinePlayer offlinePlayer) {
                    return me.clip.placeholderapi.PlaceholderAPI.setBracketPlaceholders(offlinePlayer, str);
                }
                return str;
            } catch (Throwable error) {
                return str;
            }
        }
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            str = ignoreCase ? Utils.replaceIgnoreCase(str, entry.getKey(), entry.getValue())
                    : str.replace(entry.getKey(), entry.getValue());
        }
        try {
            if (target instanceof OfflinePlayer offlinePlayer) {
                return me.clip.placeholderapi.PlaceholderAPI.setBracketPlaceholders(offlinePlayer, str);
            }
            return str;
        } catch (Throwable error) {
            return str;
        }
    }

    /**
     * Translates all the placeholders of the string from the map
     *
     * @param target     target for the placeholders.
     * @param array      the input array of string to translate the placeholders on
     * @param map        the map that contains all the placeholders with the replacement
     * @param ignoreCase if it is <code>true</code> all the placeholders will be replaced
     *                   in ignore case
     * @return the new string array with the placeholders replaced
     */
    public static String @NonNull [] placeholderAPI(final ServerOperator target, final String[] array, final Map<String, String> map, final boolean ignoreCase) {
        if (array == null) throw new IllegalArgumentException("The string array can't be null!");
        if (Arrays.stream(array).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("The string array can't have null elements!");
        final String[] newArray = Arrays.copyOf(array, array.length);
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = placeholderAPI(target, newArray[i], map, ignoreCase);
        }
        return newArray;
    }

    /**
     * Translates all the placeholders of the string from the map
     *
     * @param target     target for the placeholders.
     * @param coll       the input string list to translate the placeholders on
     * @param map        the map that contains all the placeholders with the replacement
     * @param ignoreCase if it is <code>true</code> all the placeholders will be replaced
     *                   in ignore case
     * @return the new string list with the placeholders replaced
     */
    public static List<String> placeholderAPI(final ServerOperator target, final List<String> coll, final Map<String, String> map,
                                              final boolean ignoreCase) {
        if (coll == null) throw new IllegalArgumentException("The string collection can't be null!");
        if (coll.stream().anyMatch(Objects::isNull))
            throw new IllegalArgumentException("The string collection can't have null elements!");
        return coll.stream().map(str -> placeholderAPI(target, str, map, ignoreCase)).collect(Collectors.toList());
    }
}
