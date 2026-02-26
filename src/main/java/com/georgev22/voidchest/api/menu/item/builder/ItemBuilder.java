package com.georgev22.voidchest.api.menu.item.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

public final class ItemBuilder extends AbstractItemBuilder<ItemBuilder> {

    /**
     * Creates a new {@link ItemBuilder} based on the given {@link Material}.
     *
     * @param material The {@link Material}
     */
    public ItemBuilder(@NonNull Material material) {
        super(material);
    }

    /**
     * Creates a new {@link ItemBuilder} based on the given {@link Material} and amount.
     *
     * @param material The {@link Material}
     * @param amount   The amount
     */
    public ItemBuilder(@NonNull Material material, int amount) {
        super(material, amount);
    }

    /**
     * Constructs a new {@link ItemBuilder} based on the give {@link ItemStack}.
     * This will keep the {@link ItemStack} and uses it's {@link ItemMeta}
     *
     * @param base The {@link ItemStack to use as a base}
     */
    public ItemBuilder(@NonNull ItemStack base) {
        super(base);
    }

    /**
     * Creates a shallow copy of the current object.
     * <p>
     * Only the top-level fields are copied; nested objects are referenced directly.
     *
     * @return a shallow copy of type {@code T}
     */
    @Override
    public @NonNull ItemBuilder shallowCopy() {
        if (this.base == null) return new ItemBuilder(this.material, this.amount);
        return new ItemBuilder(this.base);
    }

    /**
     * Creates a deep copy of the current object.
     * <p>
     * Both top-level fields and nested objects are fully copied.
     *
     * @return a deep copy of type {@code T}
     */
    @Override
    public @NonNull ItemBuilder deepCopy() {
        if (this.base == null) return new ItemBuilder(this.material, this.amount);
        return new ItemBuilder(this.base.clone());
    }
}