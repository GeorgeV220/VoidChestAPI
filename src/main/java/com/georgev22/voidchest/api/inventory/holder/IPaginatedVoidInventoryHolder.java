package com.georgev22.voidchest.api.inventory.holder;

import com.georgev22.voidchest.api.exceptions.InvalidInventoryTypeException;
import com.georgev22.voidchest.api.inventory.IPaginatedVoidInventory;
import com.georgev22.voidchest.api.inventory.InventoryType;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
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
     * Returns the void chest associated with the inventory, or null if none.
     *
     * @return The void chest associated with the inventory, or null if none.
     */
    @Nullable IVoidChest voidChest();

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
     * @deprecated Use {@link #getPaginatedInventory()} instead.
     */
    @Override
    @Deprecated
    default @NotNull VoidInventory getInventory() {
        return getPaginatedInventory().getVoidInventories().get(0);
    }

    /**
     * Returns the paginated inventory.
     *
     * @return The paginated inventory.
     */
    @NotNull IPaginatedVoidInventory getPaginatedInventory();


    /**
     * Returns an iterator over elements of type {@link VoidInventory}.
     * <p>
     * Note: This iterator can return null values.
     *
     * @return An iterator over elements of type {@link VoidInventory}.
     */
    @NotNull Iterator<VoidInventory> iterator();
}
