package com.georgev22.voidchest.api.storage.data;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.maps.Pair;
import com.georgev22.voidchest.api.storage.data.voidchest.Abilities;
import com.georgev22.voidchest.api.storage.data.voidchest.Charge;
import com.georgev22.voidchest.api.storage.data.voidchest.Stats;
import com.georgev22.voidchest.api.utilities.BoundingBox;
import com.georgev22.voidchest.api.utilities.NamespacedKey;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import org.bukkit.inventory.Inventory;
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
     * Checks if the VoidChest is a container.
     *
     * @return {@code true} if the VoidChest is a container, {@code false} otherwise.
     */
    boolean isContainer();

    /**
     * Retrieves the block of the VoidChest as a SerializableBlock.
     *
     * @return The block of the VoidChest.
     */
    @NotNull SerializableBlock block();

    /**
     * Retrieves the inventory of the block associated with the VoidChest.
     * <p>
     * This method may return {@code null} if the inventory could not be retrieved.
     * Additionally, calling this method outside the main thread or the region thread in Folia
     * will result in an exception. Ensure the appropriate scheduler is used when invoking this method.
     *
     * @return The inventory of the block associated with the VoidChest, or {@code null} if unavailable.
     * @throws IllegalStateException if called outside the main thread or region thread in Folia.
     */
    @Nullable Inventory blockInventory();

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
    List<Pair<NamespacedKey, Integer>> upgrades();

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
