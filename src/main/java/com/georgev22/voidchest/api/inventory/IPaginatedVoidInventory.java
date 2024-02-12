package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.inventory.extras.NavigationButton;
import com.georgev22.voidchest.api.inventory.holder.IPaginatedVoidInventoryHolder;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import com.georgev22.voidchest.api.utilities.NullableArrayList;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a paginated void inventory that can display multiple pages of items.<br>
 * WARNING: Avoid using Inventory methods directly for item manipulation as changes may not
 * reflect correctly in the Pagination system.
 * Use the provided add and remove methods to
 * ensure proper synchronization with the Pagination system.
 */
public interface IPaginatedVoidInventory {

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
     * Adds the specified item stacks to the inventory.
     * <p>
     * The method first attempts to fill partial item stacks up to their maximum stack size
     * defined by {@link Material#getMaxStackSize()}.
     * Any remaining items are added to new slots/pages.
     * <p>
     * Note: It is recommended to use this method for item addition to ensure proper synchronization
     * with the Pagination system.
     * Avoid using {@link VoidInventory#addItem(ItemStack...)} and {@link VoidInventory#addItems(SerializableItemStack...)}
     * directly for item manipulation, as changes may not reflect correctly.
     *
     * @param itemStacks The item stacks to add.
     */
    void addItems(ItemStack... itemStacks);

    /**
     * Tries to remove all instances of the specified item stacks from all pages of the inventory.
     * The difference from {@link #remove(ItemStack...)} is that this method attempts to remove as
     * many items as possible up to the {@link ItemStack#getAmount()}.
     * <p>
     * Note: It is recommended to use this method for item removal to ensure proper synchronization
     * with the Pagination system.
     * <p>
     * Avoid using<br>
     * {@link VoidInventory#remove(ItemStack)},<br>
     * {@link VoidInventory#remove(Material)},<br>
     * {@link VoidInventory#removeItem(ItemStack...)},<br>
     * {@link VoidInventory#removeItemAnySlot(ItemStack...)}<br>
     * directly for item manipulation,
     * as changes may not reflect correctly.
     *
     * @param itemStacks The item stacks to remove.
     */
    void removeItems(ItemStack... itemStacks);

    /**
     * Removes all instances of the specified item stacks from all pages of the inventory.
     * <p>
     * To successfully remove an item stack, both the type and the amount must match.
     * <p>
     * Note: It is recommended to use this method for item removal to ensure proper synchronization
     * with the Pagination system.
     * <p>
     * Avoid using<br>
     * {@link VoidInventory#remove(ItemStack)},<br>
     * {@link VoidInventory#remove(Material)},<br>
     * {@link VoidInventory#removeItem(ItemStack...)},<br>
     * {@link VoidInventory#removeItemAnySlot(ItemStack...)}<br>
     * directly for item manipulation,
     * as changes may not reflect correctly.
     *
     * @param itemStack The item stacks to remove.
     */
    void remove(ItemStack... itemStack);

    /**
     * Removes all instances of the specified material from all pages of the inventory.
     * <p>
     * To successfully remove an item stack, the material type must match.
     * <p>
     * Note: It is recommended to use this method for item removal to ensure proper synchronization
     * with the Pagination system.
     * <p>
     * Avoid using<br>
     * {@link VoidInventory#remove(ItemStack)},<br>
     * {@link VoidInventory#remove(Material)},<br>
     * {@link VoidInventory#removeItem(ItemStack...)},<br>
     * {@link VoidInventory#removeItemAnySlot(ItemStack...)}<br>
     * directly for item manipulation,
     * as changes may not reflect correctly.
     *
     * @param material The material of the item stack to remove.
     */

    void remove(Material material);

    /**
     * Clears the current page of the inventory.
     */
    void clear();

    /**
     * Clears a specific page of the inventory.
     *
     * @param page the page number to clear. The page must be positive
     */
    void clear(int page);


    /**
     * Clears all pages of the inventory.
     */
    void clearAll();

    /**
     * Closes the inventory.
     */
    void close();

    /**
     * Retrieves the title of the inventory.
     *
     * @return the title of the inventory
     */
    String title();

    /**
     * Retrieves the number of rows in the inventory.
     *
     * @return the number of rows in the inventory
     */
    int getRows();

    /**
     * Returns the list of void inventories.
     *
     * @return The list of void inventories.
     */
    NullableArrayList<VoidInventory> getVoidInventories();

    /**
     * Returns the VoidInventory object for the specified page or null if it doesn't exist.
     *
     * @param page The page number.
     * @return The VoidInventory object for the specified page or null if it doesn't exist.
     */
    @Nullable VoidInventory getPage(int page);

    /**
     * Returns the current page of the inventory for the player.
     *
     * @param player The player to get the page for.
     * @return The current page of the inventory for the player.
     */
    int getPage(Player player);

    /**
     * Sets the active page of the inventory for the player.
     *
     * @param page   the page number to set. The page must be a positive integer.
     * @param player the player whose inventory page is being set
     */
    void setPage(int page, Player player);


    /**
     * Returns the list of item stacks to display in the inventory.
     *
     * @return The list of item stacks to display in the inventory.
     */
    NullableArrayList<ItemStack> getItemStacks();

    /**
     * Returns the list of navigation buttons for the inventory.
     *
     * @return The list of navigation buttons for the inventory.
     */
    NullableArrayList<NavigationButton> getNavigationButtons();

    /**
     * Retrieves the VoidStorage associated with the inventory or null if it doesn't exist
     *
     * @return the VoidStorage associated with the inventory
     */
    @Nullable IVoidStorage voidStorage();

    /**
     * Retrieves the holder of the inventory.
     *
     * @return the holder of the inventory
     */
    @NotNull IPaginatedVoidInventoryHolder getHolder();
}