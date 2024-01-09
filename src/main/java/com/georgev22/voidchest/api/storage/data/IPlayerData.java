package com.georgev22.voidchest.api.storage.data;

import com.georgev22.library.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.storage.data.player.Booster;
import com.georgev22.voidchest.api.storage.data.player.Stats;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The IPlayerData interface provides methods for managing player data.
 */
public interface IPlayerData {

    /**
     * Retrieves the name of the player associated with the data.
     *
     * @return The name of the player as a String.
     */
    String name();

    /**
     * Retrieves the list of UUIDs of the VoidStorages associated with the player.
     *
     * @return The list of UUIDs of VoidStorages as an ArrayList.
     */
    ArrayList<UUID> voidStorages();

    /**
     * Retrieves the statistics of the player.
     *
     * @return The statistics of the player as a Stats object.
     */
    Stats stats();

    /**
     * Retrieves the booster for the player.
     *
     * @return The booster for the player as a Booster object.
     * @deprecated This will return the VoidChest booster.
     * Use {@link #boosters()} instead.
     */
    @Deprecated(since = "2.0.0")
    Booster booster();

    /**
     * Retrieves a list of boosters for the player.
     *
     * @return The list of boosters as a List.
     * @since 2.0.0
     */
    List<Booster> boosters();

    /**
     * Retrieves the current balance of the player.
     * <p>
     * Note: This method does not update the balance of the player.
     * The balance is retrieved as a BigDecimal
     * to ensure precision, especially when dealing with large values.
     * <p>
     * Important: Some Economy implementations may not return accurate balances.
     * For instance, when using Vault,
     * the limitation arises from Vault's use of double precision,
     * which can lead to inaccuracies if the balance exceeds
     * the limits of a double.
     * In such cases, consider alternative methods or implementations to handle large balances.
     *
     * @return The current balance of the player as a BigDecimal.
     */
    BigDecimal balance();

    /**
     * Closes all open VoidChest inventories for the player.
     */
    void closeVoidInventories();

    /**
     * Empties all VoidStorages associated with the player.
     */
    void emptyStorages();

    /**
     * Removes all VoidChests associated with the player.
     */
    void removeAllChests();

    /**
     * Removes the specified VoidStorage from the player's list of associated storages.
     *
     * @param voidStorage The VoidStorage to remove.
     */
    void removeVoidStorage(final IVoidStorage voidStorage);

    /**
     * Adds the specified VoidStorage to the player's list of associated storages.
     *
     * @param voidStorage The VoidStorage to add.
     */
    void addVoidStorage(final IVoidStorage voidStorage);

    /**
     * Reloads the player data.
     */
    void reloadPlayerData();

    /**
     * Retrieves the UUID of the player associated with the data.
     *
     * @return The UUID of the player.
     */
    UUID playerUUID();

    /**
     * Adds custom data to the IPlayerData with the specified key and value.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated IPlayerData with the added custom data
     */
    default IPlayerData addCustomData(String key, Object value) {
        this.getCustomData().append(key, value);
        return this;
    }

    /**
     * Adds custom data to the IPlayerData with the specified key and value if the key does not already exist.
     *
     * @param key   the key of the custom data
     * @param value the value of the custom data
     * @return the updated IPlayerData with the added custom data (if the key did not already exist)
     */
    default IPlayerData addCustomDataIfNotExists(String key, Object value) {
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
     * Retrieves the map of custom data associated with the IPlayerData.
     *
     * @return the {@link ConcurrentObjectMap} containing the custom data of the player data
     */
    ConcurrentObjectMap<String, Object> getCustomData();
}
