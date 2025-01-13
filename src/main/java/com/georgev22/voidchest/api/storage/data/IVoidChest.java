package com.georgev22.voidchest.api.storage.data;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.inventory.IPaginatedVoidInventory;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.inventory.holder.VoidInventoryHolder;
import com.georgev22.voidchest.api.storage.data.voidchest.Abilities;
import com.georgev22.voidchest.api.storage.data.voidchest.Charge;
import com.georgev22.voidchest.api.storage.data.voidchest.Filter;
import com.georgev22.voidchest.api.storage.data.voidchest.Stats;
import com.georgev22.voidchest.api.storage.data.voidchest.Upgrade;
import com.georgev22.voidchest.api.storage.IFilterManager;
import com.georgev22.voidchest.api.utilities.BoundingBox;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.UUID;

/**
 * The IVoidChest interface provides methods for managing a VoidChest.
 */
public interface IVoidChest extends Entity {

    /**
     * Retrieves the size of the VoidChest.
     *
     * @return The size of the VoidChest.
     */
    int size();

    /**
     * Retrieves the block of the VoidChest as a SerializableBlock.
     *
     * @return The block of the VoidChest.
     */
    @NotNull SerializableBlock block();

    /**
     * Retrieves the inventory of the block associated with the VoidChest.
     * This inventory is paginated when infinite storage is enabled.
     * <p>
     * This method should never return null, if returns null report it as a bug in the plugin.
     *
     * @return The paginated inventory of the block associated with the VoidChest.
     */
    @NotNull IPaginatedVoidInventory blockInventory();

    /**
     * Creates an inventory for the VoidChest with the specified owner, name, size, and items.
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
     * Retrieves the item filters of the VoidChest.
     * Filters are used to determine which items can be collected from the VoidChest.
     * <p>
     * If an item is not in any of the filters, the default behavior set by the VoidChest config will be used.
     * <p>
     * Both the blacklist and whitelist inventories contain the items that are used to create the filters
     * when the VoidChest is initialized.
     * Modifications to those methods will not be saved.
     * <p>
     * Notes: <p>
     * Administrators should use {@link IFilterManager}
     *
     * @return The item filters of the VoidChest.
     */
    @NotNull List<Filter> itemFilters();

    /**
     * Retrieves the location of the block associated with the VoidChest as a SerializableLocation.
     *
     * @return The location of the block associated with the VoidChest.
     */
    @NotNull SerializableLocation blockLocation();

    /**
     * Retrieves the config name of the VoidChest.
     *
     * @return The config name of the VoidChest.
     */
    @UnknownNullability
    @ApiStatus.Internal
    String type();

    /**
     * Retrieves the name of the VoidChest.
     * Players can change the name of the VoidChest
     *
     * @return The name of the VoidChest.
     */
    String name();

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
     * Retrieves the upgrades of the VoidChest.
     *
     * @return The upgrades of the VoidChest.
     */
    List<Upgrade<?>> upgrades();

    /**
     * Retrieves the booster value of the VoidChest.
     *
     * @return The booster value of the VoidChest.
     */
    Double booster();

    /**
     * Retrieves the charge state of the VoidChest.
     *
     * @return The charge state of the VoidChest.
     */
    Charge charge();

    /**
     * Retrieves the abilities of the VoidChest.
     *
     * @return The abilities of the VoidChest.
     */
    Abilities abilities();

    /**
     * Retrieves the statistics of the VoidChest.
     *
     * @return The statistics of the VoidChest.
     */
    Stats stats();

    /**
     * Retrieves the owner UUID of the VoidChest.
     *
     * @return The owner UUID of the VoidChest.
     */
    UUID ownerUUID();

    /**
     * Retrieves the owner name of the VoidChest.
     *
     * @return The owner name of the VoidChest.
     */
    String ownerName();

    /**
     * Retrieves the UUID of the void chest.
     *
     * @return The UUID of the void chest.
     */
    UUID getId();

    /**
     * Reloads the void chest.
     */
    void reloadVoidChest();

    /**
     * Retrieves the maximum number of links that can be added to the VoidChest.
     *
     * @return The maximum number of links that can be added to the VoidChest.
     */
    int maxLinks();

    /**
     * Adds custom data to the IVoidChest with the specified key and value.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated IVoidChest with the added custom data
     */
    default IVoidChest addCustomData(String key, Object value) {
        this.getCustomData().append(key, value);
        return this;
    }

    /**
     * Adds custom data to the IVoidChest with the specified key and value if the key does not already exist.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated IVoidChest with the added custom data (if the key did not already exist)
     */
    default IVoidChest addCustomDataIfNotExists(String key, Object value) {
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
        //noinspection unchecked
        return (T) getCustomData().get(key);
    }

    /**
     * Retrieves the map of custom data associated with the IVoidChest.
     *
     * @return the {@link ConcurrentObjectMap} containing the custom data of the void chest data
     */
    ConcurrentObjectMap<String, Object> getCustomData();

}
