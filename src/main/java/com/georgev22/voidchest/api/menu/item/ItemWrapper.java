package com.georgev22.voidchest.api.menu.item;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * An {@link ItemProvider} that just returns the {@link ItemStack}
 * passed to it in the constructor regardless of the {@link UUID players uuid}.
 */
public class ItemWrapper implements ItemProvider {

    private ItemStack itemStack;

    public ItemWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public @NonNull ItemStack get() {
        return itemStack;
    }

    @Override
    public ItemWrapper clone() {
        try {
            ItemWrapper clone = (ItemWrapper) super.clone();
            clone.itemStack = itemStack.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    /**
     * Creates a shallow copy of the current object.
     * <p>
     * Only the top-level fields are copied; nested objects are referenced directly.
     *
     * @return a shallow copy of type {@code T}
     */
    @Override
    public @NonNull ItemWrapper shallowCopy() {
        return new ItemWrapper(itemStack);
    }

    /**
     * Creates a deep copy of the current object.
     * <p>
     * Both top-level fields and nested objects are fully copied.
     *
     * @return a deep copy of type {@code T}
     */
    @Override
    public @NonNull ItemWrapper deepCopy() {
        return new ItemWrapper(itemStack.clone());
    }
}