package com.georgev22.voidchest.api.item;

import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Functional action executed when a {@link VoidSpecialItem} is applied to a VoidChest.
 * <p>
 * The action operates on both the player and the target {@link IVoidChest}, allowing
 * full control for modifying chest data or player-related states.
 * <p>
 * Example uses include:
 * <ul>
 *     <li>Adding fuel to the VoidChest</li>
 *     <li>Applying upgrades (Booster, BoundingBox size, Links, etc.)</li>
 *     <li>Triggering custom plugin-defined interactions</li>
 * </ul>
 */
@FunctionalInterface
public interface VoidSpecialItemAction {

    /**
     * Executes the special item action for the given player and VoidChest.
     *
     * @param player    the player that is using the item
     * @param voidChest the VoidChest that the action should influence
     * @param itemStack the item stack that is being used
     * @return SUCCESS if the action was successful, FAILED otherwise
     */
    Result execute(@NotNull Player player, @NotNull IVoidChest voidChest, @NotNull ItemStack itemStack);

    /**
     * The result of a {@link VoidSpecialItemAction}.
     */
    enum Result {
        SUCCESS,
        FAILED
    }

}
