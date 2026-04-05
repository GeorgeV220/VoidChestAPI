package com.georgev22.voidchest.api.item;

import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

        if (!isStackable() && itemStack.getAmount() > 1) {
            itemStack.setAmount(1);
            ItemMeta itemMeta = itemStack.hasItemMeta()
                    ? itemStack.getItemMeta()
                    : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
            itemMeta.setMaxStackSize(1);
            itemStack.setItemMeta(itemMeta);
        }

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

    /**
     * Gets the cooldown duration in milliseconds for this special item.
     * <p>
     * Default implementation returns 0 (no cooldown). Override this method
     * to specify a custom cooldown duration.
     *
     * @return the cooldown duration in milliseconds, 0 if no cooldown
     */
    public long getCooldownDuration() {
        return 0L;
    }

    /**
     * Checks if this special item should be consumed when used.
     * <p>
     * Default implementation returns true (consumable). Override this method
     * to make the item non-consumable.
     *
     * @return true if the item should be consumed, false otherwise
     */
    public boolean isConsumable() {
        return true;
    }

    /**
     * Checks if this special item can be stacked in inventories.
     * <p>
     * Default implementation returns true (stackable). Override this method
     * to make the item non-stackable if needed.
     *
     * @return true if the item can be stacked, false otherwise
     */
    public boolean isStackable() {
        return true;
    }

    /**
     * Consumes this special item when used on a VoidChest.
     * <p>
     * This method defines the consumption behavior of the special item,
     * such as applying upgrades, adding fuel, or triggering other effects.
     * The method is called when the item is used by a player on a VoidChest.
     *
     * @param player    the player who used the special item
     * @param voidChest the VoidChest on which the item was used
     * @param itemStack the item stack being consumed
     */
    public abstract void consume(@NonNull Player player, @NonNull AbstractVoidChest voidChest, @NonNull ItemStack itemStack);
}
