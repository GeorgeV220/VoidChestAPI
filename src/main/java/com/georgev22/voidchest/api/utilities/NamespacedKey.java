package com.georgev22.voidchest.api.utilities;


import java.util.Locale;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a String based key which consists of two components - a namespace
 * and a key.
 * <p>
 * Namespaces may only contain lowercase alphanumeric characters, periods,
 * underscores, and hyphens.
 * <p>
 * Keys may only contain lowercase alphanumeric characters, periods,
 * underscores, hyphens, and forward slashes.
 */
public final class NamespacedKey {

    private final String namespace;
    private final String key;

    private static boolean isValidNamespaceChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '.' || c == '_' || c == '-';
    }

    private static boolean isValidKeyChar(char c) {
        return isValidNamespaceChar(c) || c == '/';
    }

    private static boolean isValidNamespace(String namespace) {
        int len = namespace.length();
        if (len == 0) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            if (!isValidNamespaceChar(namespace.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean isValidKey(String key) {
        int len = key.length();
        if (len == 0) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            if (!isValidKeyChar(key.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Create a key in the plugin's namespace.
     * <p>
     * Namespaces may only contain lowercase alphanumeric characters, periods,
     * underscores, and hyphens.
     * <p>
     * Keys may only contain lowercase alphanumeric characters, periods,
     * underscores, hyphens, and forward slashes.
     *
     * @param plugin the plugin to use for the namespace
     * @param key    the key to create
     */
    public NamespacedKey(@NotNull Plugin plugin, @NotNull String key) {
        //noinspection ConstantValue
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }

        //noinspection ConstantValue
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        this.namespace = plugin.getName().toLowerCase(Locale.ROOT);
        this.key = key.toLowerCase(Locale.ROOT);

        if (!isValidNamespace(this.namespace)) {
            throw new IllegalArgumentException("Invalid namespace. Must be [a-z0-9._-]: " + this.namespace);
        }

        if (!isValidKey(this.key)) {
            throw new IllegalArgumentException("Invalid key. Must be [a-z0-9/._-]: " + this.key);
        }

        String string = toString();
        if (string.length() > 256) {
            throw new IllegalArgumentException("NamespacedKey must be less than 256 characters (" + string + ")");
        }
    }

    @NotNull
    public String getNamespace() {
        return namespace;
    }

    @NotNull
    public String getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.namespace.hashCode();
        hash = 47 * hash + this.key.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NamespacedKey other = (NamespacedKey) obj;
        return this.namespace.equals(other.namespace) && this.key.equals(other.key);
    }

    @Override
    public String toString() {
        return this.namespace + ":" + this.key;
    }

    /**
     * Get a NamespacedKey from the supplied string.
     * <p>
     * The default namespace will be the plugin's namespace.
     *
     * @param plugin the plugin to use for the namespace
     * @param key    the key to convert to a NamespacedKey
     * @return the created NamespacedKey.
     * @throws IllegalArgumentException if the key is invalid
     */
    public static @NotNull NamespacedKey fromString(@NotNull Plugin plugin, @NotNull String key) {
        return new NamespacedKey(plugin, key);
    }
}
