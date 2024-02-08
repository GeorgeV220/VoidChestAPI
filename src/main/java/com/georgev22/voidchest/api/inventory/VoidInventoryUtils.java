package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.inventory.holder.VoidInventoryHolder;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The VoidInventoryUtils interface provides methods for creating and managing VoidInventory objects.
 */
public interface VoidInventoryUtils {

    /**
     * Creates a new VoidInventory.
     *
     * @param owner         The owner of the inventory
     * @param inventoryName The name of the inventory
     * @param inventorySize The size of the inventory
     * @return The created VoidInventory
     */
    @NotNull VoidInventory createInventory(
            @Nullable VoidInventoryHolder owner,
            @NotNull String inventoryName,
            int inventorySize
    );

    /**
     * Creates a new VoidInventory.
     *
     * @param owner          The owner of the inventory
     * @param inventoryName  The name of the inventory
     * @param inventorySize  The size of the inventory
     * @param inventoryItems The items to add to the inventory
     * @return The created VoidInventory
     */
    @NotNull VoidInventory createInventory(
            @Nullable VoidInventoryHolder owner,
            @NotNull String inventoryName,
            int inventorySize,
            @NotNull List<VoidInventoryItemStack> inventoryItems
    );

    /**
     * Creates a new VoidInventory.
     *
     * @param owner                   The owner of the inventory
     * @param inventoryName           The name of the inventory
     * @param inventorySize           The size of the inventory
     * @param inventoryItems          The items to add to the inventory
     * @param infiniteInventory       if the inventory is infinite
     * @param infiniteVisibleItemLore the lore of the infinite visible items
     * @return The created VoidInventory
     */
    @NotNull VoidInventory createInventory(
            @Nullable VoidInventoryHolder owner,
            @NotNull String inventoryName,
            int inventorySize,
            @NotNull List<VoidInventoryItemStack> inventoryItems,
            boolean infiniteInventory,
            @Nullable List<String> infiniteVisibleItemLore
    );

    /**
     * Creates a new VoidInventory for the given VoidStorage.
     * <p>
     * VoidStorage parameter is required
     * to be able to create an inventory that returns {@link VoidInventory#getLocation()}
     * and also update the items stored stat for the VoidStorage.
     *
     * @param owner         The owner of the inventory
     * @param inventoryName The name of the inventory
     * @param inventorySize The size of the inventory
     * @return The created VoidInventory
     */
    @NotNull VoidInventory createInventory(
            @NotNull IVoidStorage voidStorage,
            @Nullable VoidInventoryHolder owner,
            @NotNull String inventoryName,
            int inventorySize
    );

    /**
     * Creates a new VoidInventory for the given VoidStorage.
     * <p>
     * VoidStorage parameter is required
     * to be able to create an inventory that returns {@link VoidInventory#getLocation()}
     * and also update the items stored stat for the VoidStorage.
     *
     * @param voidStorage    The {@link IVoidStorage} of the inventory
     * @param owner          The owner of the inventory
     * @param inventoryName  The name of the inventory
     * @param inventorySize  The size of the inventory
     * @param inventoryItems The items to add to the inventory
     * @return The created VoidInventory
     */
    @NotNull VoidInventory createInventory(
            @NotNull IVoidStorage voidStorage,
            @Nullable VoidInventoryHolder owner,
            @NotNull String inventoryName,
            int inventorySize,
            @NotNull List<VoidInventoryItemStack> inventoryItems
    );

    /**
     * Creates a new VoidInventory for the given VoidStorage.
     * <p>
     * VoidStorage parameter is required
     * to be able to create an inventory that returns {@link VoidInventory#getLocation()}
     * and also update the items stored stat for the VoidStorage.
     *
     * @param voidStorage             The {@link IVoidStorage} of the inventory
     * @param owner                   The owner of the inventory
     * @param inventoryName           The name of the inventory
     * @param inventorySize           The size of the inventory
     * @param inventoryItems          The items to add to the inventory
     * @param infiniteInventory       if the inventory is infinite
     * @param infiniteVisibleItemLore the lore of the infinite visible items
     * @return The created VoidInventory
     */
    @NotNull VoidInventory createInventory(
            @NotNull IVoidStorage voidStorage,
            @Nullable VoidInventoryHolder owner,
            @NotNull String inventoryName,
            int inventorySize,
            @NotNull List<VoidInventoryItemStack> inventoryItems,
            boolean infiniteInventory,
            @Nullable List<String> infiniteVisibleItemLore
    );

    /**
     * Opens the VoidInventory for the player.
     * <p>
     * Note: {@link Player#openInventory(Inventory)} won't work for VoidInventory and it may cause issues.
     *
     * @param player    The player to open the inventory for
     * @param inventory The inventory to open
     */
    default void openInventory(Player player, @NotNull VoidInventory inventory) {
        inventory.open(player);
    }

}
