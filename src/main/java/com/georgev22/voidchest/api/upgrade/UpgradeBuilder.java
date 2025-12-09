package com.georgev22.voidchest.api.upgrade;

import com.georgev22.voidchest.api.registry.Registry;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Builder class for creating {@link Upgrade} instances.
 *
 * @param <U> The type of the upgrade object.
 */
public class UpgradeBuilder<U> {

    private NamespacedKey key;
    private int maxLevel = 1;
    private List<String> voidchestTypes = new ArrayList<>();
    private UpgradeSerializer<U> serializer;
    private final List<UpgradeLevel<U>> levels = new ArrayList<>();

    /**
     * Private constructor to enforce the use of {@link #builder()}.
     */
    private UpgradeBuilder() {
    }

    /**
     * Creates a new {@code UpgradeBuilder} instance.
     *
     * @param <U> The type of the upgrade object.
     * @return A new {@code UpgradeBuilder} instance.
     */
    @Contract(value = " -> new", pure = true)
    public static <U> @NotNull UpgradeBuilder<U> builder() {
        return new UpgradeBuilder<>();
    }

    /**
     * Creates a new {@link Upgrade} instance from the provided String using the provided {@link UpgradeDeserializer}.
     * <p>
     * <strong>Note:</strong> Be aware that the provided String can be a JSON, YAML or any other String format depending on the deserializer.
     * Implementations should have checks to ensure that the String is valid for the deserializer.
     *
     * @param deserializer The {@link UpgradeDeserializer} instance.
     * @param serialized The String representation of the upgrade.
     * @return A new {@link Upgrade} instance.
     */
    public static <U> Upgrade<U> deserialize(@NotNull UpgradeDeserializer<U> deserializer, String serialized) {
        return deserializer.deserialize(serialized);
    }

    /**
     * Creates a String representation of the {@link Upgrade} instance.
     * <p>
     * <strong>Note:</strong> Be aware that the provided String can be a JSON, YAML or any other String format depending on the deserializer.
     * Implementations should have checks to ensure that the String is valid for the deserializer.
     * @param upgrade The {@link Upgrade} instance.
     * @return A String representation of the {@link Upgrade} instance.
     */
    public static <U> String serialize(@NotNull Upgrade<U> upgrade) {
        return upgrade.toString();
    }

    /**
     * Creates a String representation of the {@link Upgrade} instance using the provided {@link UpgradeSerializer}.
     * <p>
     * Can be a JSON string, a YAML string or any String format depending on the serializer.
     *
     * @param upgrade The {@link Upgrade} instance.
     * @param serializer The {@link UpgradeSerializer} instance.
     * @return A String representation of the {@link Upgrade} instance.
     */
    public static <U> String serialize(Upgrade<U> upgrade, @NotNull UpgradeSerializer<U> serializer) {
        return serializer.serialize(upgrade);
    }

    /**
     * Sets the {@link NamespacedKey} for the upgrade.
     *
     * @param key The unique key identifying the upgrade.
     * @return The current {@code UpgradeBuilder} instance.
     */
    public @NotNull UpgradeBuilder<U> withKey(@NotNull NamespacedKey key) {
        this.key = key;
        return this;
    }

    /**
     * Sets the maximum level of the upgrade.
     *
     * @param maxLevel The maximum level.
     * @return The current {@code UpgradeBuilder} instance.
     */
    public @NotNull UpgradeBuilder<U> withMaxLevel(int maxLevel) {
        this.maxLevel = Math.max(1, maxLevel);
        return this;
    }

    /**
     * Specifies the applicable VoidChest types for this upgrade.
     *
     * @param voidchestTypes A list of applicable VoidChest types.
     * @return The current {@code UpgradeBuilder} instance.
     */
    public @NotNull UpgradeBuilder<U> withVoidchestTypes(@NotNull List<String> voidchestTypes) {
        this.voidchestTypes = new ArrayList<>(voidchestTypes);
        return this;
    }

    /**
     * Sets the serializer used for this upgrade.
     *
     * @param serializer The serializer instance.
     * @return The current {@code UpgradeBuilder} instance.
     */
    public @NotNull UpgradeBuilder<U> withSerializer(@NotNull UpgradeSerializer<U> serializer) {
        this.serializer = serializer;
        return this;
    }

    /**
     * Adds multiple levels to the upgrade.
     *
     * @param levels The list of upgrade levels.
     * @return The current {@code UpgradeBuilder} instance.
     */
    public @NotNull UpgradeBuilder<U> withLevels(@NotNull List<UpgradeLevel<U>> levels) {
        this.levels.addAll(levels);
        return this;
    }

    /**
     * Adds a single upgrade level.
     *
     * @param level The upgrade level to add.
     * @return The current {@code UpgradeBuilder} instance.
     */
    public @NotNull UpgradeBuilder<U> withLevel(@NotNull UpgradeLevel<U> level) {
        this.levels.add(level);
        return this;
    }

    /**
     * Creates and adds an upgrade level with the given parameters.
     *
     * @param level The level number.
     * @param price The price of the upgrade.
     * @param upgradeObject The upgrade data object.
     * @param displayItem The item displayed for this upgrade.
     * @param placeholders A map of placeholder values.
     * @return The current {@code UpgradeBuilder} instance.
     */
    public @NotNull UpgradeBuilder<U> withLevel(
            int level,
            @NotNull BigDecimal price,
            @NotNull U upgradeObject,
            @NotNull ItemStack displayItem,
            Map<String, String> placeholders
    ) {
        this.levels.add(new UpgradeLevel<>(level, price, upgradeObject, displayItem.clone(), placeholders));
        return this;
    }

    /**
     * Builds the {@link Upgrade} instance.
     *
     * @param plugin The plugin instance to use for logging.
     * @return A new {@link Upgrade} instance.
     */
    public @Nullable Upgrade<U> build(@NotNull Plugin plugin) {
        if (key == null) {
            plugin.getLogger().log(Level.SEVERE, "[VoidChest/Upgrades]: Upgrade key must be set.");
            return null;
        }
        if (maxLevel < 1) {
            plugin.getLogger().log(Level.SEVERE, "[VoidChest/Upgrades]: Max level must be at least 1.");
            return null;
        }
        if (levels.isEmpty()) {
            plugin.getLogger().log(Level.SEVERE, "[VoidChest/Upgrades]: Upgrade levels must not be empty.");
            return null;
        }
        if (serializer == null) {
            plugin.getLogger().log(Level.SEVERE, "[VoidChest/Upgrades]: Upgrade serializer must be set.");
            return null;
        }
        maxLevel = levels.stream().mapToInt(UpgradeLevel::level).max().orElse(maxLevel);
        return new Upgrade<>(key, maxLevel, Collections.unmodifiableList(voidchestTypes), Collections.unmodifiableList(levels)) {
            @Override
            public String toString() {
                return serializer.serialize(this);
            }
        };
    }

    /**
     * Builds the {@link Upgrade} instance and registers it with the {@link UpgradeRegistry}.
     *
     * @param plugin The plugin to use for logging.
     */
    public void buildAndRegister(@NotNull Plugin plugin) {
        Upgrade<U> upgrade = build(plugin);
        if (upgrade == null) {
            plugin.getLogger().log(Level.SEVERE, "[VoidChest/Upgrades]: Failed to build upgrade.");
            return;
        }
        Registry.UPGRADE.register(upgrade);
        plugin.getLogger().info("[VoidChest/Upgrades]: Registered upgrade: " + upgrade.getKey());
    }
}
