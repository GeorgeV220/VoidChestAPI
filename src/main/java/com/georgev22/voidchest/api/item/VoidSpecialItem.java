package com.georgev22.voidchest.api.item;

import com.georgev22.voidchest.api.utilities.NamespacedKey;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents a special interactable item that performs an action on a VoidChest
 * when used by a player.
 * <p>
 * Special items are typically used to apply upgrades, add fuel, or trigger
 * internal mechanics related to a {@code IVoidChest}. Each item is uniquely
 * identified by a {@link NamespacedKey}, allowing custom registration and
 * retrieval from item registries.
 */
public abstract class VoidSpecialItem {

    /**
     * Gets the unique {@link NamespacedKey} that identifies this special item.
     * <p>
     * This key is used for registration, lookup, and item recognition within the VoidChest system.
     *
     * @return the unique identifier for this special item
     */
    public abstract NamespacedKey getKey();

    /**
     * Gets the action that will be executed when this item is applied to a VoidChest.
     * <p>
     * The action defines the behavior, such as granting upgrades, adding fuel,
     * modifying storage, or performing other contextual effects.
     *
     * @return the functional action associated with this special item
     */
    public abstract VoidSpecialItemAction getAction();

    /**
     * Applies this special item to an item stack.
     * <p>
     * This method is used to modify the item stack based on the special item's behavior.
     *
     * @param itemStack the item stack to apply the special item to
     * @return the modified item stack
     */
    public Optional<ItemStack> applyTo(@NotNull ItemStack itemStack, String @NotNull ... data) {
        //noinspection ConstantValue
        if (itemStack == null) return Optional.empty();
        if (itemStack.getType().equals(Material.AIR)) return Optional.empty();
        //noinspection ConstantValue
        if (data == null) return Optional.empty();
        if (data.length == 0) return Optional.empty();
        ReadWriteNBT nbt = NBT.itemStackToNBT(itemStack);
        nbt.setString("voidSpecialItemKey", this.getKey().toString());
        return Optional.ofNullable(applyTo0(nbt, data));
    }

    protected abstract ItemStack applyTo0(@NotNull ReadWriteNBT nbt, String @NotNull ... data);

}
