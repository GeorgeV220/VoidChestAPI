package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.storage.data.IVoidChest;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface IVoidInventoryUtils {

    /**
     * Adds one or more items to the specified void chest.
     * <p>
     * This method accepts a variable number of {@link SerializableItemStack} objects
     * and returns a list containing all the items that were not successfully added.
     *
     * @param voidChest The void chest to which the items will be added.
     * @param items     The items to be added to the void chest. Each item must be an instance of {@link SerializableItemStack}.
     * @return A list of {@link SerializableItemStack} objects that were not successfully added to the void chest.
     */
    List<SerializableItemStack> addItemsToVoidChest(
            @NotNull IVoidChest voidChest,
            @NotNull SerializableItemStack... items
    );

    /**
     * Adds one or more items to the specified void chest.
     * <p>
     * This method accepts a variable number of {@link ItemStack} objects
     * and returns a list containing all the items that were not successfully added.
     *
     * @param voidChest The void chest to which the items will be added.
     * @param items     The items to be added to the void chest. Each item must be an instance of {@link ItemStack}.
     * @return A list of {@link ItemStack} objects that were not successfully added to the void chest.
     */
    default List<ItemStack> addItemsToVoidChest(@NotNull IVoidChest voidChest, ItemStack @NotNull ... items) {
        //noinspection ConstantValue
        if (items == null || items.length == 0) return new ArrayList<>();
        SerializableItemStack[] serializableItems = new SerializableItemStack[items.length];

        for (int i = 0; i < items.length; i++) {
            ItemStack itemStack = items[i];
            serializableItems[i] = SerializableItemStack.fromItemStack(itemStack, BigInteger.valueOf(itemStack.getAmount()));
        }

        List<SerializableItemStack> leftovers = addItemsToVoidChest(voidChest, serializableItems);

        List<ItemStack> result = new ArrayList<>(leftovers.size());
        for (SerializableItemStack leftover : leftovers) {
            result.add(leftover.getItemStack());
        }

        return result;
    }


}