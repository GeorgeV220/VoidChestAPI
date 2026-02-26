package com.georgev22.voidchest.api.menu.state;

import com.georgev22.voidchest.api.menu.item.items.StatefulMenuItem;
import com.georgev22.voidchest.api.menu.viewer.ViewerContext;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;

/**
 * Represents a condition that determines the state of a menu item.
 */
public abstract class StateCondition implements Keyed {

    private final NamespacedKey key;

    /**
     * Creates a new state condition.
     *
     * @param key the unique namespaced key of this condition
     */
    public StateCondition(@NonNull NamespacedKey key) {
        this.key = key;
    }

    /**
     * Evaluates the condition and returns the state number for the given viewer.
     *
     * @param context  the viewer context
     * @param menuItem the menu item being evaluated
     * @return the state number to apply
     */
    public abstract int evaluate(@NonNull ViewerContext context, @NonNull StatefulMenuItem menuItem);

    /**
     * @return the unique key of this condition
     */
    public @NonNull NamespacedKey getKey() {
        return key;
    }
}
