package com.georgev22.voidchest.api.storage.data;

import com.georgev22.library.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.inventory.IPaginatedVoidInventory;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.inventory.holder.VoidInventoryHolder;
import com.georgev22.voidchest.api.storage.data.voidstorage.Abilities;
import com.georgev22.voidchest.api.storage.data.voidstorage.Charge;
import com.georgev22.voidchest.api.storage.data.voidstorage.Filter;
import com.georgev22.voidchest.api.storage.data.voidstorage.Stats;
import com.georgev22.voidchest.api.utilities.BoundingBox;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * The IVoidStorage interface provides methods for managing a VoidStorage.
 */
public interface IVoidStorage {

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
     * This inventory is paginated when infinite storage is enabled.
     *
     * @return The paginated inventory of the block associated with the VoidStorage.
     */
    @Nullable IPaginatedVoidInventory blockInventory();

    /**
     * Retrieves the whitelist inventory of the VoidStorage.
     *
     * @return The whitelist inventory of the VoidStorage.
     * @deprecated Use {@link #itemFilters()}
     * Changes to the whitelist inventory will not be reflected in the item filters.
     * The method is marked for removal but will not be removed to maintain backwards compatibility.
     */
    @Deprecated(forRemoval = true)
    @NotNull Inventory whitelistInventory();

    /**
     * Retrieves the blacklist inventory of the VoidStorage.
     *
     * @return The blacklist inventory of the VoidStorage.
     * @deprecated Use {@link #itemFilters()}
     * Changes to the blacklist inventory will not be reflected in the item filters.
     * The method is marked for removal but will not be removed to maintain backwards compatibility.
     */
    @Deprecated(forRemoval = true)
    @NotNull Inventory blacklistInventory();

    /**
     * Creates an inventory for the VoidStorage with the specified owner, name, size, and items.
     * <p>
     * The inventory will be created with the specified owner, name, size, and items.
     * If the items are null, the inventory will be created with no items.
     * <p>
     * To open the inventory, use the {@link VoidInventory#open(org.bukkit.entity.Player)} method
     * otherwise issues may occur.
     *
     * @param owner          The owner of the inventory.
     * @param inventoryName  The name of the inventory.
     * @param inventorySize  The size of the inventory.
     * @param inventoryItems The items of the inventory.
     * @return The created inventory.
     */
    @NotNull VoidInventory createInventory(
            @NotNull VoidInventoryHolder owner,
            @NotNull String inventoryName,
            int inventorySize,
            @Nullable List<SerializableItemStack> inventoryItems
    );

    /**
     * Retrieves the item filters of the VoidStorage.
     * Filters are used to determine which items can be collected from the VoidChest.
     * <p>
     * If an item is not in any of the filters, the default behavior set by the VoidChest config will be used.
     * <p>
     * Both the blacklist and whitelist inventories contain the items that are used to create the filters
     * when the VoidStorage is initialized.
     * Modifications to those methods will not be saved.
     * <p>
     * Notes: <p>
     * Modifications to the {@link #blacklistInventory()} and {@link #whitelistInventory()} inventories
     * not update the filters,
     * and changes to the filters will not be reflected in the inventories.<br>
     * This list returns the filters that players can modify.
     * Administrators should use {@link com.georgev22.voidchest.api.storage.IFilterManager}
     *
     * @return The item filters of the VoidStorage.
     */
    @NotNull List<Filter> itemFilters();

    /**
     * Retrieves the location of the block associated with the VoidStorage as a SerializableLocation.
     *
     * @return The location of the block associated with the VoidStorage.
     */
    @NotNull SerializableLocation blockLocation();

    /**
     * Retrieves the name of the VoidStorage.
     *
     * @return The name of the VoidStorage.
     */
    @NotNull String name();

    /**
     * Retrieves the bounding box that defines the area from which the VoidChest will collect items.
     * If the bounding box is not explicitly set, the area will default to the chunk where the VoidChest is placed.
     *
     * @return The bounding box defining the collection area.
     */
    @Nullable
    default BoundingBox boundingBox() throws IllegalStateException {
        return blockLocation().getBoundingBox();
    }

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
     * Retrieves the UUID of the void storage.
     *
     * @return The UUID of the void storage.
     */
    UUID storageUUID();

    /**
     * Reloads the void storage.
     */
    void reloadVoidStorage();

    /**
     * Adds custom data to the IVoidStorage with the specified key and value.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated IVoidStorage with the added custom data
     */
    default IVoidStorage addCustomData(String key, Object value) {
        this.getCustomData().append(key, value);
        return this;
    }

    /**
     * Adds custom data to the IVoidStorage with the specified key and value if the key does not already exist.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated IVoidStorage with the added custom data (if the key did not already exist)
     */
    default IVoidStorage addCustomDataIfNotExists(String key, Object value) {
        this.getCustomData().appendIfTrue(key, value, !this.getCustomData().containsKey(key));
        return this;
    }

    /**
     * Retrieves the value of the custom data associated with the specified key.
     *
     * @param key the key of the custom data
     * @param <T> the type of the value to retrieve
     * @return the value associated with the specified key, or {@code null} if the key does not exist
     */
    default <T> T getCustomData(String key) {
        return (T) getCustomData().get(key);
    }

    /**
     * Retrieves the map of custom data associated with the IVoidStorage.
     *
     * @return the {@link ConcurrentObjectMap} containing the custom data of the void storage data
     */
    ConcurrentObjectMap<String, Object> getCustomData();

}
