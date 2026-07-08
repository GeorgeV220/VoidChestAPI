package com.georgev22.voidchest.api.booster;

import com.georgev22.voidchest.api.registry.Registries;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

/**
 * Represents a namespaced identifier for a booster type.
 * <p>
 * Booster types follow a namespaced key format: {@code namespace:key}
 * <p>
 * Examples:
 * <ul>
 *   <li>{@code voidchest:sell_price} - Built-in sell price multiplier</li>
 *   <li>{@code myplugin:tokens} - Third-party plugin type</li>
 * </ul>
 * <p>
 * Third-party plugins should use their own namespace to avoid conflicts.
 * Types do not need to be registered to be used, but registration enables
 * discovery and metadata association via {@link Registries#BOOSTER_TYPES}.
 */
public final class BoosterType implements Keyed {

    private final NamespacedKey namespacedKey;

    private BoosterType(@NonNull String namespace, @NonNull String key) {

        if (namespace.isEmpty()) {
            throw new IllegalArgumentException("Namespace cannot be empty");
        }
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be empty");
        }

        this.namespacedKey = new NamespacedKey(Objects.requireNonNull(namespace, "namespace"), Objects.requireNonNull(key, "key"));
    }

    /**
     * Creates a new booster type with the specified namespace and key.
     *
     * @param namespace the namespace (e.g., "voidchest", "myplugin")
     * @param key       the key within the namespace (e.g., "sell_price")
     * @return a new BoosterType instance
     * @throws NullPointerException     if namespace or key is null
     * @throws IllegalArgumentException if namespace or key is empty or contains invalid characters
     */
    public static @NonNull BoosterType of(@NonNull String namespace, @NonNull String key) {
        return new BoosterType(namespace, key);
    }

    /**
     * Parses a booster type from its full key string.
     *
     * @param fullKey the full key in format "namespace:key"
     * @return the parsed BoosterType
     * @throws IllegalArgumentException if the format is invalid
     * @throws NullPointerException     if fullKey is null
     */
    public static @NonNull BoosterType parse(@NonNull String fullKey) {
        Objects.requireNonNull(fullKey, "fullKey");
        int idx = fullKey.indexOf(':');
        if (idx <= 0 || idx == fullKey.length() - 1) {
            throw new IllegalArgumentException("Invalid booster type key format: " + fullKey
                    + ". Expected 'namespace:key'");
        }
        return new BoosterType(fullKey.substring(0, idx), fullKey.substring(idx + 1));
    }

    /**
     * Returns the namespace of this type.
     *
     * @return the namespace
     */
    public @NonNull String namespace() {
        return namespacedKey.namespace();
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return namespacedKey;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoosterType other)) return false;
        return namespacedKey.equals(other.namespacedKey);
    }

    @Override
    public int hashCode() {
        return namespacedKey.hashCode();
    }

    @Override
    public String toString() {
        return namespacedKey.toString();
    }

    public static BoosterType SELL_PRICE = new BoosterType("voidchest", "sellprice");
}