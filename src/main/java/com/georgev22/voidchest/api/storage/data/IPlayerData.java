package com.georgev22.voidchest.api.storage.data;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.economy.player.EconomyMode;
import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.data.player.Booster;
import com.georgev22.voidchest.api.storage.data.player.Boosters;
import com.georgev22.voidchest.api.storage.data.player.Stats;
import org.jetbrains.annotations.ApiStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The IPlayerData interface provides methods for managing player data.
 */
public interface IPlayerData extends Entity {

    /**
     * Retrieves the name of the player associated with the data.
     *
     * @return The name of the player as a String.
     */
    String name();

    /**
     * Retrieves the list of UUIDs of the VoidChests associated with the player.
     *
     * @return The list of UUIDs of VoidChests as an ArrayList.
     */
    ArrayList<UUID> voidChests();

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
    @ApiStatus.Obsolete(since = "2.0.0")
    Booster booster();

    /**
     * Retrieves a list of boosters for the player.
     *
     * @return The list of boosters as a List.
     * @since 2.0.0
     */
    Boosters boosters();

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
     * @param economyMode The {@link EconomyMode} to use.
     * @return The current balance of the player as a BigDecimal.
     */
    BigDecimal balance(EconomyMode economyMode);

    /**
     * Closes all open VoidChest inventories for the player.
     */
    void closeVoidInventories();

    /**
     * Empties all VoidChests associated with the player.
     */
    void emptyStorages();

    /**
     * Removes all VoidChests associated with the player.
     */
    void removeAllChests();

    /**
     * Removes the specified VoidChest from the player's list of associated storages.
     *
     * @param voidChest The VoidChest to remove.
     */
    void removeVoidChest(final IVoidChest voidChest);

    /**
     * Adds the specified VoidChest to the player's list of associated storages.
     *
     * @param voidChest The VoidChest to add.
     */
    void addVoidChest(final IVoidChest voidChest);

    /**
     * Reloads the player data.
     */
    void reloadPlayerData();

    /**
     * Retrieves the UUID of the player associated with the data.
     *
     * @return The UUID of the player.
     */
    UUID getId();

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
        //noinspection unchecked
        return (T) getCustomData().get(key);
    }

    /**
     * Retrieves the map of custom data associated with the IPlayerData.
     *
     * @return the {@link ConcurrentObjectMap} containing the custom data of the player data
     */
    ConcurrentObjectMap<String, Object> getCustomData();

    /**
     * Retrieves a map of placeholders for player and the specified VoidChest.
     *
     * @param storage The VoidChest to retrieve placeholders for.
     * @return A map of placeholders.
     */
    ObjectMap<String, String> getPlaceHolders(IVoidChest storage);
}
