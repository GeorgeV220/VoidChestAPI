package com.georgev22.voidchest.api.storage.data;

import com.georgev22.library.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.inventory.VoidInventory;
import com.georgev22.voidchest.api.storage.data.voidstorage.Abilities;
import com.georgev22.voidchest.api.storage.data.voidstorage.Charge;
import com.georgev22.voidchest.api.storage.data.voidstorage.Stats;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

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
     *
     * @return The inventory of the block associated with the VoidStorage.
     */
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
