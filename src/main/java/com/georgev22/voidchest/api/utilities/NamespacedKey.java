package com.georgev22.voidchest.api.utilities;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Objects;

/**
 * A wrapper for {@link org.bukkit.NamespacedKey} that provides backward compatibility
 * for older Minecraft versions (e.g. 1.8.8) where {@code NamespacedKey} does not exist.
 * <p>
 * On modern versions, this class delegates to Bukkit's {@link org.bukkit.NamespacedKey}.
 * On legacy versions, it falls back to using a namespaced string format internally.
 */
public class NamespacedKey implements Comparable<NamespacedKey> {
    private final String namespace;
    private final String key;

    private @Nullable
    final org.bukkit.NamespacedKey bukkitKey;

    /**
     * Constructs a new {@code NamespacedKey} using the given plugin and key.
     * <ul>
     *   <li>On supported versions (1.13+), uses {@link org.bukkit.NamespacedKey}.</li>
     *   <li>On legacy versions, uses the plugin's name (lowercased) as the namespace.</li>
     * </ul>
     *
     * @param plugin the plugin to use as the namespace (or source of it)
     * @param key    the key name (identifier)
     */
    public NamespacedKey(@NotNull Plugin plugin, String key) {
        this(plugin.getName().toLowerCase(Locale.ROOT), key);
    }

    /**
     * Constructs a new {@code NamespacedKey} using the given namespace and key.
     * <ul>
     *   <li>On supported versions (1.13+), uses {@link org.bukkit.NamespacedKey}.</li>
     *   <li>On legacy versions, uses the plugin's name (lowercased) as the namespace.</li>
     * </ul>
     *
     * @param namespace the namespace
     * @param key       the key name (identifier)
     */
    public NamespacedKey(String namespace, String key) {
        if (isModernVersion()) {
            this.bukkitKey = new org.bukkit.NamespacedKey(namespace, key);
            this.namespace = bukkitKey.getNamespace();
            this.key = bukkitKey.getKey();
        } else {
            this.bukkitKey = null;
            this.namespace = namespace;
            this.key = key;
        }
    }

    /**
     * Returns the namespace part of the key.
     * <ul>
     *   <li>Modern: from Bukkit's NamespacedKey</li>
     *   <li>Legacy: plugin name in lowercase</li>
     * </ul>
     *
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Returns the key name (identifier).
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the Bukkit {@link org.bukkit.NamespacedKey} if available.
     * Will return {@code null} on legacy versions.
     *
     * @return the Bukkit NamespacedKey or {@code null}
     */
    public @Nullable org.bukkit.NamespacedKey asBukkit() {
        return bukkitKey;
    }

    /**
     * Checks whether this key was created using Bukkit's {@link org.bukkit.NamespacedKey}.
     *
     * @return true if Bukkit's NamespacedKey is used, false if using legacy fallback
     */
    public boolean isModern() {
        return bukkitKey != null;
    }

    /**
     * Returns a string representation in the format {@code namespace:key}.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return namespace + ":" + key;
    }

    /**
     * Checks whether the current server version supports {@link org.bukkit.NamespacedKey}.
     *
     * @return true if available, false otherwise
     */
    private static boolean isModernVersion() {
        try {
            Class.forName("org.bukkit.NamespacedKey");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Contract("_ -> new")
    public static @NotNull NamespacedKey fromString(@NotNull String string) {
        String[] parts = string.split(":");
        return new NamespacedKey(parts[0], parts[1]);
    }

    @Override
    public int compareTo(@NotNull NamespacedKey o) {
        return toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NamespacedKey that = (NamespacedKey) obj;
        return Objects.equals(namespace, that.namespace) && Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, key);
    }
}
