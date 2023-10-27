package com.georgev22.voidchest.api.storage.data;

import com.georgev22.library.utilities.Entity;
import com.georgev22.voidchest.api.storage.data.player.Booster;
import com.georgev22.voidchest.api.storage.data.player.SellHandler;
import com.georgev22.voidchest.api.storage.data.player.Stats;

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
     * Retrieves the sell handler for the player.
     *
     * @return The sell handler for the player as a SellHandler object.
     */
    SellHandler sellHandler();

    /**
     * Retrieves the booster for the player.
     *
     * @return The booster for the player as a Booster object.
     */
    Booster booster();

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
