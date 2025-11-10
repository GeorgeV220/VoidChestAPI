package com.georgev22.voidchest.api.item;

import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidChest;

/**
 * Functional action executed when a {@link VoidSpecialItem} is applied to a VoidChest.
 * <p>
 * The action operates on both the player and the target {@link IVoidChest}, allowing
 * full control for modifying chest data or player-related states.
 * <p>
 * Example uses include:
 * <ul>
 *     <li>Adding fuel to the VoidChest</li>
 *     <li>Applying upgrades (speed, capacity, rarity filters, etc.)</li>
 *     <li>Triggering custom plugin-defined interactions</li>
 * </ul>
 */
@FunctionalInterface
public interface VoidSpecialItemAction {

    /**
     * Executes the special item action for the given player and VoidChest.
     *
     * @param playerData data representing the player using the item
     * @param voidChest  the VoidChest that the action should influence
     */
    void execute(IPlayerData playerData, IVoidChest voidChest);

}
