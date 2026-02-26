package com.georgev22.voidchest.api.menu.item;

import com.georgev22.voidchest.api.menu.item.builder.ItemBuilder;
import com.georgev22.voidchest.api.utilities.Copyable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public interface ItemProvider extends Supplier<@NonNull ItemStack>, Cloneable, Copyable<ItemProvider> {

    /**
     * An {@link ItemProvider} for an {@link ItemStack}.
     */
    @NonNull ItemProvider EMPTY = new ItemWrapper(new ItemStack(Material.AIR));

    /**
     * An {@link ItemProvider} for a placeholder {@link ItemStack}.
     */
    @NonNull ItemProvider PLACEHOLDER = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
            .setAmount(1)
            .setDisplayName(" ");

    /**
     * Gets the {@link ItemStack}.
     *
     * @return The {@link ItemStack}
     */
    @NonNull ItemStack get();

    /**
     * Gets a clone of the {@link ItemStack}.
     *
     * @return A cloned {@link ItemStack} so that modifications to the returned
     * item do not affect the original.
     */
    default @NonNull ItemStack getClone() {
        return get().clone();
    }

    /**
     * Creates and returns a copy of this {@link ItemProvider}.
     * <p>
     * Implementations should override this method to return a deep or shallow
     * copy depending on the internal state, ensuring that modifications to the
     * cloned instance do not affect the original instance.
     *
     * @return a cloned {@link ItemProvider} instance
     */
    ItemProvider clone();

    /**
     * Compares this {@link ItemProvider} with another object for equality.
     * <p>
     * Implementations should override this method to compare relevant internal
     * state so that two {@link ItemProvider} instances are considered equal
     * if they would provide the same {@link ItemStack} and behave identically.
     *
     * @param other the object to compare with
     * @return {@code true} if the objects are considered equal, {@code false} otherwise
     */
    boolean equals(Object other);
}
