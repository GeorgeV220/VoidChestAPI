package com.georgev22.voidchest.api.storage.data;

import com.georgev22.library.utilities.Entity;
import com.georgev22.voidchest.api.storage.data.player.Booster;

import com.georgev22.voidchest.api.storage.data.player.Stats;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The IPlayerData interface extends the Entity interface and provides methods for managing player data.
 */
public interface IPlayerData extends Entity {

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
     */
    Booster booster();

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
}
