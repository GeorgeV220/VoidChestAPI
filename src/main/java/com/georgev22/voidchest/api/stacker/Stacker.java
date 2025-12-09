package com.georgev22.voidchest.api.stacker;

import org.bukkit.Keyed;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * The Stacker interface provides methods for managing item stacking.
 */
public interface Stacker extends Keyed {

    /**
     * Retrieves the actual amount of stacked items for the specified item.
     *
     * @param item The Item to retrieve the actual amount for.
     * @return The actual amount of stacked items as an integer.
     */
    BigInteger getActualAmount(@NotNull final Item item);

    /**
     * Updates the amount of stacked items for the specified item.
     *
     * @param item      The Item to update.
     * @param newAmount The new amount of stacked items to set.
     */
    void updateItem(@NotNull final Item item, @NotNull final BigInteger newAmount);

    ItemStack getItemStack(@NotNull final Item item);

    /**
     * Retrieves the simple name of the Stacker. E.g. UltimateStacker
     *
     * @return The simple name of the Stacker as a String.
     */
    String getName();

}
