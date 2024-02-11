package com.georgev22.voidchest.api.inventory.holder;

import com.georgev22.voidchest.api.exceptions.InvalidInventoryTypeException;
import com.georgev22.voidchest.api.inventory.InventoryType;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.inventory.extras.NavigationButton;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import com.georgev22.voidchest.api.utilities.NullableArrayList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A class that represents a paginated void inventory holder.
 */
public abstract class PaginatedVoidInventoryHolder implements VoidInventoryHolder {

    private final String title;
    private final int rows;
    private final List<ItemStack> itemStacks;
    private final @Nullable IVoidStorage voidStorage;
    private NullableArrayList<VoidInventory> voidInventories;
    private int page = 1;

    private final List<NavigationButton> navigationButtons;

    /**
     * Creates a new paginated void inventory holder with the given parameters.
     *
     * @param voidStorage       The void storage associated with the inventory, or null if none.
     * @param title             The title of the inventory.
     * @param rows              The number of rows in the inventory.
     * @param navigationButtons The list of navigation buttons for the inventory.
     * @param itemStacks        The list of item stacks to display in the inventory.
     * @param voidInventories   The list of void inventories associated with the inventory.
     */
    public PaginatedVoidInventoryHolder(
            @Nullable IVoidStorage voidStorage,
            @NotNull String title,
            int rows,
            @NotNull List<NavigationButton> navigationButtons,
            @NotNull List<ItemStack> itemStacks,
            @NotNull NullableArrayList<VoidInventory> voidInventories
    ) {
        this.voidStorage = voidStorage;
        this.title = title;
        this.rows = rows;
        this.itemStacks = itemStacks;
        this.navigationButtons = navigationButtons;
        this.voidInventories = voidInventories;
    }

    /**
     * Returns the name of the inventory.
     *
     * @return The name of the inventory.
     */
    @Override
    public String inventoryName() {
        return this.title;
    }

    /**
     * Returns the size of the inventory in slots.
     *
     * @return The size of the inventory in slots.
     */
    @Override
    public int inventorySize() {
        return this.rows * 9;
    }

    /**
     * Returns the void storage associated with the inventory, or null if none.
     *
     * @return The void storage associated with the inventory, or null if none.
     */
    @Override
    public IVoidStorage voidStorage() {
        return this.voidStorage;
    }

    /**
     * Returns the type of the inventory.
     *
     * @return The type of the inventory.
     * @throws InvalidInventoryTypeException If the inventory type is invalid.
     */
    @Override
    public abstract InventoryType inventoryType();

    /**
     * Returns the void inventory object.
     *
     * @return The void inventory object.
     * @deprecated Use {@link #getVoidInventories()} instead.
     */
    @Deprecated
    @Override
    public @NotNull VoidInventory getInventory() {
        return this.voidInventories.get(0);
    }

    /**
     * Returns the list of void inventories.
     *
     * @return The list of void inventories.
     */
    public NullableArrayList<VoidInventory> getVoidInventories() {
        return this.voidInventories;
    }

    /**
     * Returns the VoidInventory object for the specified page or null if it doesn't exist.
     *
     * @param page The page number.
     * @return The VoidInventory object for the specified page or null if it doesn't exist.
     */
    public @Nullable VoidInventory getPage(int page) {
        if (page < 1) {
            page = 1;
        }
        return voidInventories.get(page - 1);
    }

    /**
     * Returns the current page of the inventory.
     *
     * @return The current page of the inventory.
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the current page of the inventory and updates it accordingly.
     *
     * @param page The new page of the inventory.
     */
    public void setPage(int page) {
        if (page < 1) {
            page = 1;
        }
        this.page = page;
    }

    /**
     * Returns the number of rows in the inventory.
     *
     * @return The number of rows in the inventory.
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Returns the list of item stacks to display in the inventory.
     *
     * @return The list of item stacks to display in the inventory.
     */
    public List<ItemStack> getItemStacks() {
        return this.itemStacks;
    }

    /**
     * Returns the list of navigation buttons for the inventory.
     *
     * @return The list of navigation buttons for the inventory.
     */
    public List<NavigationButton> getNavigationButtons() {
        return navigationButtons;
    }

    /**
     * Returns the title of the inventory.
     *
     * @return The title of the inventory.
     */
    public String getTitle() {
        return title;
    }
}