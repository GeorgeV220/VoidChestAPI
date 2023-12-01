package com.georgev22.voidchest.api.handler;

import com.georgev22.library.maps.HashObjectMap;
import com.georgev22.library.maps.ObjectMap;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class VoidInventory implements Inventory {

    public abstract ObjectMap<Integer, SerializableItemStack> addItems(SerializableItemStack... items);

    public ObjectMap<Integer, ItemStack> addItems(ItemStack... items) {
        return this.addItems(
                        Arrays.stream(items).map(
                                itemStack -> new SerializableItemStack(itemStack, BigInteger.valueOf(itemStack.getAmount()))
                        ).toArray(SerializableItemStack[]::new))
                .entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getItemStack().clone(), (a, b) -> b, HashObjectMap::new
                ));
    }

}
