package com.georgev22.voidchest.api.item;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * Represents a special interactable item that performs an action on a VoidChest
 * when used by a player.
 * <p>
 * Special items are typically used to apply upgrades, add fuel, or trigger
 * internal mechanics related to a {@code AbstractVoidChest}. Each item is uniquely
 * identified by a {@link NamespacedKey}, allowing custom registration and
 * retrieval from item registries.
 * <p>
 * Developers can extend this class to create custom special items using the {@link com.georgev22.voidchest.api.registry.Registries#SPECIAL_ITEM}
 */
public abstract class VoidSpecialItem implements Keyed {

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
    public Optional<ItemStack> applyTo(@NonNull ItemStack itemStack, String @NonNull ... data) {
        //noinspection ConstantValue
        if (itemStack == null) return Optional.empty();
        if (itemStack.getType().equals(Material.AIR)) return Optional.empty();
        //noinspection ConstantValue
        if (data == null) return Optional.empty();
        if (data.length == 0) return Optional.empty();
        itemStack = itemStack.clone();
        NBT.modify(itemStack, nbt -> {
            nbt.setString("voidSpecialItemKey", this.getKey().toString());
            applyTo0(nbt, data);
        });
        return Optional.of(itemStack);
    }

    protected abstract void applyTo0(@NonNull ReadWriteNBT nbt, String @NonNull ... data);

    /**
     * Retrieves the name of the Special Item
     *
     * @return The name of the Special Item as a String.
     */
    public abstract String getName();

}
