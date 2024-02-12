package com.georgev22.voidchest.api.inventory.holder;

import com.georgev22.voidchest.api.exceptions.InvalidInventoryTypeException;
import com.georgev22.voidchest.api.inventory.IPaginatedVoidInventory;
import com.georgev22.voidchest.api.inventory.InventoryType;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.inventory.extras.NavigationButton;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import com.georgev22.voidchest.api.utilities.NullableArrayList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

/**
 * An interface that represents a paginated void inventory holder. <br>
 * WARNING: Avoid using Inventory methods directly for item manipulation as changes may not
 * reflect correctly in the Pagination system.
 * Use the provided add and remove methods to
 * ensure proper synchronization with the Pagination system.
 */
public interface IPaginatedVoidInventoryHolder extends VoidInventoryHolder, Iterable<VoidInventory> {

    /**
     * Returns the name of the inventory.
     *
     * @return The name of the inventory.
     */
    String inventoryName();

    /**
     * Returns the size of the inventory in slots.
     *
     * @return The size of the inventory in slots.
     */
    int inventorySize();

    /**
     * Returns the void storage associated with the inventory, or null if none.
     *
     * @return The void storage associated with the inventory, or null if none.
     */
    @Nullable IVoidStorage voidStorage();

    /**
     * Returns the type of the inventory.
     *
     * @return The type of the inventory.
     * @throws InvalidInventoryTypeException If the inventory type is invalid.
     */
    InventoryType inventoryType();

    /**
     * Returns the void inventory object.
     * <p>
     * It may return null even if it is annotated as NotNull
     *
     * @return The void inventory object.
     * @deprecated Use {@link #getVoidInventories()} instead.
     */
    @Override
    @Deprecated
    default @NotNull VoidInventory getInventory() {
        return getVoidInventories().get(0);
    }

    /**
     * Returns the paginated inventory.
     *
     * @return The paginated inventory.
     */
    @NotNull IPaginatedVoidInventory getPaginatedInventory();

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
     * Returns the number of rows in the inventory.
     *
     * @return The number of rows in the inventory.
     */
    int getRows();

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
     * Returns an iterator over elements of type {@link VoidInventory}.
     * <p>
     * Note: This iterator can return null values.
     *
     * @return An iterator over elements of type {@link VoidInventory}.
     */
    @NotNull Iterator<VoidInventory> iterator();
}
