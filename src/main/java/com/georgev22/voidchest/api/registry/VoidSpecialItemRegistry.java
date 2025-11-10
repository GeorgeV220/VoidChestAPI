package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.item.VoidSpecialItem;
import com.georgev22.voidchest.api.utilities.NamespacedKey;
import org.jetbrains.annotations.NotNull;

/**
 * Registry for {@link VoidSpecialItem} instances.
 * <p>
 * This registry manages all special items that can interact with a VoidChest,
 * providing lookup and registration functionality based on their
 * {@link NamespacedKey}. Registered items are typically used to apply upgrades,
 * add fuel, or trigger custom chest interactions.
 * <p>
 * The registry ensures that each special item is uniquely identified and can be
 * retrieved efficiently during item interaction events.
 *
 * @see VoidSpecialItem
 * @see NamespacedKey
 */
public class VoidSpecialItemRegistry
        extends AbstractRegistry<NamespacedKey, VoidSpecialItem> {

    /**
     * Registers a new {@link VoidSpecialItem} in this registry.
     * <p>
     * If another item with the same {@link NamespacedKey} already exists,
     * an exception will be thrown. Use {@link #replaceOrRegister(VoidSpecialItem)}
     * if you want to override existing entries instead.
     *
     * @param value the special item to register
     * @throws IllegalArgumentException if an item with the same key is already registered
     */
    @Override
    public void register(@NotNull VoidSpecialItem value) throws IllegalArgumentException {
        super.register(value.getKey(), value);
    }

    /**
     * Registers the given {@link VoidSpecialItem}, replacing any existing item with the same key.
     * <p>
     * This method ensures the item is stored under its {@link VoidSpecialItem#getKey()}
     * and returns {@code true} if a replacement occurred, or {@code false} if it was newly registered.
     *
     * @param value the special item to register or replace
     * @return {@code true} if an existing item was replaced; {@code false} if newly registered
     */
    @Override
    public boolean replaceOrRegister(@NotNull VoidSpecialItem value) {
        return super.replaceOrRegister(value.getKey(), value);
    }
}
