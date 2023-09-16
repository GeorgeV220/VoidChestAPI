package com.georgev22.voidchest.api.storage;

import com.georgev22.library.maps.ObjectMap;
import com.georgev22.library.utilities.EntityManager;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The IPlayerManager interface extends the EntityManager interface for managing player data.
 */
public interface IPlayerManager extends EntityManager<IPlayerData> {

    /**
     * Retrieves a list of all player data.
     *
     * @return A list of player data.
     */
    List<IPlayerData> playersData();

    /**
     * Retrieves an ArrayList of VoidStorages associated with the specified player data.
     *
     * @param data The player data to retrieve VoidStorages for.
     * @return An ArrayList of VoidStorages.
     */
    ArrayList<IVoidStorage> voidStorages(IPlayerData data);

    /**
     * Internal method. Retrieves a map of loaded entities.
     *
     * @return The map of loaded entities.
     */
    @Override
    @ApiStatus.Internal
    ObjectMap<UUID, IPlayerData> getLoadedEntities();

    /**
     * Attempts to stop the sell task for the specified player data.
     *
     * @param data The player data to stop the sell task for.
     */
    void attemptStopSellTask(final IPlayerData data);

    /**
     * Attempts to start the sell task for the specified player data.
     *
     * @param data The player data to start the sell task for.
     */
    void attemptStartSellTask(final IPlayerData data);

    /**
     * Retrieves a map of placeholders for the specified player data and VoidStorage.
     *
     * @param data    The player data to retrieve placeholders for.
     * @param storage The VoidStorage to retrieve placeholders for.
     * @return A map of placeholders.
     */
    ObjectMap<String, String> getPlaceHolders(final @NotNull IPlayerData data, final @NotNull IVoidStorage storage);
}
