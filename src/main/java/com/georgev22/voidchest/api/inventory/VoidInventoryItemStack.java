package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import org.bukkit.inventory.ItemStack;

public class VoidInventoryItemStack {

    private SerializableItemStack serializableItemStack;

    private ItemStack visibleItemStack;

    public VoidInventoryItemStack(SerializableItemStack serializableItemStack, ItemStack visibleItemStack) {
        this.serializableItemStack = serializableItemStack;
        this.visibleItemStack = visibleItemStack;
    }

    public SerializableItemStack getSerializableItemStack() {
        return serializableItemStack;
    }

    public ItemStack getVisibleItemStack() {
        return visibleItemStack;
    }

    public void setSerializableItemStack(SerializableItemStack serializableItemStack) {
        this.serializableItemStack = serializableItemStack;
    }

    public void setVisibleItemStack(ItemStack visibleItemStack) {
        this.visibleItemStack = visibleItemStack;
    }

    public ItemStack getOriginalItemStacK() {
        return this.serializableItemStack.getItemStack();
    }

}
