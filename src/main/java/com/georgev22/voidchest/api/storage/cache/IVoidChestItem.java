package com.georgev22.voidchest.api.storage.cache;

import org.bukkit.inventory.ItemStack;

/**
 * The IVoidChestItem interface defines methods for managing VoidChest items.
 */
public interface IVoidChestItem {

    /**
     * Retrieves the ItemStack representation of the VoidChest item.
     *
     * @return The ItemStack representing the VoidChest item.
     */
    ItemStack voidChestItem();

    /**
     * Retrieves an array of ItemStacks representing the VoidChest item with the specified amount.
     *
     * @param amount The amount of VoidChest items to retrieve.
     * @return An array of ItemStacks representing the VoidChest items.
     */
    ItemStack[] voidChestItem(int amount);

    /**
     * Retrieves the name of the VoidChest item.
     *
     * @return The name of the VoidChest item.
     */
    String name();

}
