package com.georgev22.voidchest.api.inventory.holder;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.exceptions.InvalidInventoryTypeException;
import com.georgev22.voidchest.api.inventory.InventoryType;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.inventory.extras.NavigationButton;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A class that represents a paginated void inventory holder.
 */
public abstract class PaginatedVoidInventoryHolder implements VoidInventoryHolder {

    private final String title; // The title of the inventory
    private final int rows; // The number of rows in the inventory
    private final List<ItemStack> itemStacks; // The list of item stacks to display in the inventory
    private final @Nullable IVoidStorage voidStorage; // The void storage associated with the inventory
    private VoidInventory voidInventory; // The void inventory object
    private int page = 1; // The current page of the inventory

    private final List<NavigationButton> navigationButtons; // The list of navigation buttons for the inventory

    /**
     * Creates a new paginated void inventory holder with the given parameters.
     *
     * @param voidStorage       The void storage associated with the inventory, or null if none.
     * @param title             The title of the inventory.
     * @param rows              The number of rows in the inventory.
     * @param navigationButtons The list of navigation buttons for the inventory.
     * @param itemStacks        The list of item stacks to display in the inventory.
     */
    public PaginatedVoidInventoryHolder(
            @Nullable IVoidStorage voidStorage,
            @NotNull String title,
            int rows,
            @NotNull List<NavigationButton> navigationButtons,
            @NotNull List<ItemStack> itemStacks
    ) {
        this.voidStorage = voidStorage;
        this.title = title;
        this.rows = rows;
        this.itemStacks = itemStacks;
        this.navigationButtons = navigationButtons;
        this.createInventory();
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
     */
    @Override
    public @NotNull VoidInventory getInventory() {
        return this.voidInventory;
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
        this.createInventory();
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

    /**
     * Creates the void inventory object.
     */
    public void createInventory() {
        this.voidInventory = VoidChestAPI.getInstance().voidInventoryUtils().createInventory(
                this,
                title + " - Page " + page,
                rows * 9
        );
    }
}