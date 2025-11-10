package com.georgev22.voidchest.api.item;

import com.georgev22.voidchest.api.utilities.NamespacedKey;

/**
 * Represents a special interactable item that performs an action on a VoidChest
 * when used by a player.
 * <p>
 * Special items are typically used to apply upgrades, add fuel, or trigger
 * internal mechanics related to a {@code IVoidChest}. Each item is uniquely
 * identified by a {@link NamespacedKey}, allowing custom registration and
 * retrieval from item registries.
 */
public interface VoidSpecialItem {

    /**
     * Gets the unique {@link NamespacedKey} that identifies this special item.
     * <p>
     * This key is used for registration, lookup, and item recognition within the VoidChest system.
     *
     * @return the unique identifier for this special item
     */
    NamespacedKey getKey();

    /**
     * Gets the action that will be executed when this item is applied to a VoidChest.
     * <p>
     * The action defines the behavior, such as granting upgrades, adding fuel,
     * modifying storage, or performing other contextual effects.
     *
     * @return the functional action associated with this special item
     */
    VoidSpecialItemAction getAction();

}
