package com.georgev22.voidchest.api.storage.data;

import com.georgev22.library.utilities.Entity;
import com.georgev22.voidchest.api.storage.data.voidstorage.Abilities;
import com.georgev22.voidchest.api.storage.data.voidstorage.Charge;
import com.georgev22.voidchest.api.storage.data.voidstorage.Stats;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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
     * Retrieves the item stacks of the block associated with the VoidStorage as a List of SerializableItemStack
     *
     * @return The item stacks of the block associated with the VoidStorage.
     */
    @NotNull List<SerializableItemStack> blockInventoryItemStacks();

    /**
     * Retrieves the whitelist item stacks of the VoidStorage as a List of SerializableItemStack.
     *
     * @return The whitelist item stacks of the VoidStorage.
     */
    @NotNull List<SerializableItemStack> whitelistInventoryItemStacks();

    /**
     * Retrieves the blacklist item stacks of the VoidStorage as a List of SerializableItemStack.
     *
     * @return The blacklist item stacks of the VoidStorage.
     */
    @NotNull List<SerializableItemStack> blacklistInventoryItemStacks();

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
