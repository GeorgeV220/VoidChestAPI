package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.inventory.holder.PaginatedVoidInventoryHolder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a paginated void inventory that can display multiple pages of items.
 */
public interface IPaginatedVoidInventory extends VoidInventory {

    /**
     * Opens the specified page of the inventory for the player.
     *
     * @param player the player to open the inventory for
     * @param page   the page number to open, starting from 1
     */
    void open(Player player, int page);

    /**
     * Opens the next page of the inventory for the player, if it exists.
     *
     * @param player the player to open the inventory for
     */
    void openNextPage(Player player);

    /**
     * Opens the previous page of the inventory for the player, if it exists.
     *
     * @param player the player to open the inventory for
     */
    void openPreviousPage(Player player);


    /**
     * Retrieves the holder of the inventory.
     *
     * @return the holder of the inventory
     */
    @Override
    @NotNull PaginatedVoidInventoryHolder getHolder();
}