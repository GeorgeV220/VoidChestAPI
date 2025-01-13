package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * The {@code VoidInventoryItemStack} class represents an item stack within a Void Chest inventory.
 * It encapsulates both a {@link SerializableItemStack} for data serialization
 * and an {@link SerializableItemStack} for the visible representation of the item in the inventory.
 *
 * <p>This class provides methods to retrieve and modify both the serializable and visible item stacks.
 * Additionally, it offers a method to obtain the original {@link SerializableItemStack} from the serializable item stack.
 * <p>
 */
public class VoidInventoryItemStack {

    /**
     * The serializable item stack used for data serialization.
     */
    private SerializableItemStack serializableItemStack;

    /**
     * The visible item stack used for the representation of the item in the inventory.
     */
    private SerializableItemStack visibleItemStack;

    /**
     * Constructs a new {@code VoidInventoryItemStack} with the specified serializable and visible item stacks.
     *
     * @param serializableItemStack The serializable item stack.
     * @param visibleItemStack      The visible item stack.
     */
    public VoidInventoryItemStack(SerializableItemStack serializableItemStack, SerializableItemStack visibleItemStack) {
        this.serializableItemStack = serializableItemStack;
        this.visibleItemStack = visibleItemStack;
    }

    /**
     * Gets the serializable item stack.
     *
     * @return The serializable item stack.
     */
    public SerializableItemStack getSerializableItemStack() {
        return serializableItemStack;
    }

    /**
     * Gets the visible item stack.
     *
     * @return The visible item stack.
     */
    public SerializableItemStack getVisibleItemStack() {
        return visibleItemStack;
    }

    /**
     * Sets the serializable item stack.
     *
     * @param serializableItemStack The new serializable item stack.
     */
    public void setSerializableItemStack(SerializableItemStack serializableItemStack) {
        this.serializableItemStack = serializableItemStack;
    }

    /**
     * Sets the visible item stack.
     *
     * @param visibleItemStack The new visible item stack.
     */
    public void setVisibleItemStack(SerializableItemStack visibleItemStack) {
        this.visibleItemStack = visibleItemStack;
    }

    /**
     * Gets the original {@link ItemStack} from the serializable item stack.
     *
     * @return The original {@link ItemStack}.
     */
    public ItemStack getOriginalItemStack() {
        return this.serializableItemStack.getItemStack();
    }
}
