package com.georgev22.voidchest.api.storage.data;

import com.georgev22.library.utilities.Entity;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.storage.data.voidstorage.Abilities;
import com.georgev22.voidchest.api.storage.data.voidstorage.Charge;
import com.georgev22.voidchest.api.storage.data.voidstorage.Stats;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The IVoidStorage interface extends the Entity interface and provides methods for managing a VoidStorage.
 */
public interface IVoidStorage extends Entity {

    /**
     * Retrieves the size of the VoidStorage.
     *
     * @return The size of the VoidStorage.
     */
    int size();

    /**
     * Retrieves the block of the VoidStorage as a SerializableBlock.
     *
     * @return The block of the VoidStorage.
     */
    @NotNull SerializableBlock block();

    /**
     * Retrieves the inventory of the block associated with the VoidStorage.
     *
     * @return The inventory of the block associated with the VoidStorage.
     */
    @ApiStatus.Experimental
    @NotNull VoidInventory blockInventory();

    /**
     * Retrieves the whitelist inventory of the VoidStorage.
     *
     * @return The whitelist inventory of the VoidStorage.
     */
    @NotNull Inventory whitelistInventory();

    /**
     * Retrieves the blacklist inventory of the VoidStorage.
     *
     * @return The blacklist inventory of the VoidStorage.
     */
    @NotNull Inventory blacklistInventory();

    /**
     * Retrieves the location of the block associated with the VoidStorage as a SerializableLocation.
     *
     * @return The location of the block associated with the VoidStorage.
     */
    @NotNull SerializableLocation blockLocation();

    /**
     * Retrieves the location of the VoidStorage as a SerializableLocation.
     *
     * @return The location of the VoidStorage.
     */
    @NotNull SerializableLocation location();

    /**
     * Retrieves the name of the VoidStorage.
     *
     * @return The name of the VoidStorage.
     */
    @NotNull String name();

    /**
     * Retrieves the booster value of the VoidStorage.
     *
     * @return The booster value of the VoidStorage.
     */
    Double booster();

    /**
     * Retrieves the charge state of the VoidStorage.
     *
     * @return The charge state of the VoidStorage.
     */
    Charge charge();

    /**
     * Retrieves the abilities of the VoidStorage.
     *
     * @return The abilities of the VoidStorage.
     */
    Abilities abilities();

    /**
     * Retrieves the statistics of the VoidStorage.
     *
     * @return The statistics of the VoidStorage.
     */
    Stats stats();

    /**
     * Retrieves the owner UUID of the VoidStorage.
     *
     * @return The owner UUID of the VoidStorage.
     */
    UUID ownerUUID();

    /**
     * Reloads the void storage.
     */
    void reloadVoidStorage();

}
