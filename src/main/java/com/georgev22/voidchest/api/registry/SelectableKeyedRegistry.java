package com.georgev22.voidchest.api.registry;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

/**
 * A {@link KeyedRegistry} that supports resolving a selected entry.
 * <p>
 * Implementations of this registry can define their own selection strategy,
 * allowing a single registered value to be resolved based on configuration,
 * runtime state, or other external conditions.
 *
 * @param <T> the type of keyed values stored in this registry
 * @see KeyedRegistry
 */
public abstract class SelectableKeyedRegistry<T extends Keyed>
        extends KeyedRegistry<T>
        implements SelectableRegistry<NamespacedKey, T> {
}