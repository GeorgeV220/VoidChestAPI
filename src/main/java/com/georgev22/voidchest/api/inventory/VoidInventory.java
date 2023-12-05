package com.georgev22.voidchest.api.inventory;

import com.georgev22.library.maps.HashObjectMap;
import com.georgev22.library.maps.ObjectMap;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface VoidInventory extends Inventory {

    ObjectMap<Integer, SerializableItemStack> addItems(SerializableItemStack... items);

    default ObjectMap<Integer, ItemStack> addItems(ItemStack... items) {
        return this.addItems(
                        Arrays.stream(items).map(
                                itemStack -> new SerializableItemStack(itemStack, BigInteger.valueOf(itemStack.getAmount()))
                        ).toArray(SerializableItemStack[]::new))
                .entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getItemStack().clone(), (a, b) -> b, HashObjectMap::new
                ));
    }

    @Override
    @NotNull
    default HashMap<Integer, ItemStack> addItem(ItemStack... items) {
        return this.addItems(items).entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue, (a, b) -> b, HashObjectMap::new
        ));
    }


    List<VoidInventoryItemStack> getItems();

    default void setItems(List<VoidInventoryItemStack> items) {
        this.getItems().clear();
        this.getItems().addAll(items);
    }

    default @Nullable VoidInventoryItemStack getVoidInventoryItem(int slot) {
        if (this.getItems().size() <= slot) {
            return null;
        }
        return getItems().get(slot);
    }

}
