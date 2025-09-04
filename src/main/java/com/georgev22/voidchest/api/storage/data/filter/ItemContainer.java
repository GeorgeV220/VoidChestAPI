package com.georgev22.voidchest.api.storage.data.filter;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a container for storing an item using its NBT data and associated options.
 */
public class ItemContainer {

    /**
     * The NBT string representation of the stored item.
     */
    private final String nbt;

    /**
     * The options associated with the stored item.
     */
    private final ItemOptions options;

    /**
     * Constructs an {@code ItemContainer} from an {@link ItemStack} and item options.
     *
     * @param item    The item to be stored.
     * @param options The options associated with the item.
     */
    public ItemContainer(ItemStack item, ItemOptions options) {
        this.nbt = itemToNBTString(item);
        this.options = options;
    }

    /**
     * Constructs an {@code ItemContainer} from an NBT string and item options.
     *
     * @param nbt     The NBT string representing the item.
     * @param options The options associated with the item.
     */
    public ItemContainer(String nbt, ItemOptions options) {
        this.nbt = nbt;
        this.options = options;
    }

    /**
     * Retrieves the stored item as an {@link ItemStack}.
     *
     * @return The deserialized {@link ItemStack}, or {@code null} if deserialization fails.
     */
    public ItemStack getItem() {
        return nbtStringToItem(nbt);
    }

    /**
     * Retrieves the NBT string representation of the stored item.
     *
     * @return The NBT string.
     */
    public String getNbt() {
        return nbt;
    }

    /**
     * Retrieves the options associated with the stored item.
     *
     * @return The item options.
     */
    public ItemOptions getOptions() {
        return options;
    }

    /**
     * Converts an {@link ItemStack} to its NBT string representation.
     *
     * @param item The item to serialize.
     * @return The NBT string representing the item.
     */
    private String itemToNBTString(ItemStack item) {
        return NBT.itemStackToNBT(item).toString();
    }

    /**
     * Converts an NBT string back to an {@link ItemStack}.
     *
     * @param nbt The NBT string to deserialize.
     * @return The deserialized {@link ItemStack}, or {@code null} if parsing fails.
     */
    @Nullable
    private ItemStack nbtStringToItem(String nbt) {
        try {
            ReadableNBT readWriteNBT = NBT.parseNBT(nbt);
            return NBT.itemStackFromNBT(readWriteNBT);
        } catch (Exception e) {
            return null;
        }
    }
}