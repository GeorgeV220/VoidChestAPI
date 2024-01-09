package com.georgev22.voidchest.api.storage;

import com.georgev22.library.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The IPlayerManager interface extends the EntityManager interface for managing player data.
 */
public interface IPlayerManager {

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
    List<IVoidStorage> voidStorages(IPlayerData data);

    /**
     * Loads the {@link IPlayerData} with the specified ID
     *
     * @param entityId the {@link UUID} of the {@link IPlayerData} to be loaded
     * @return a {@link CompletableFuture} containing the loaded {@link IPlayerData} object
     */

    CompletableFuture<IPlayerData> load(UUID entityId);

    /**
     * Saves the specified {@link IPlayerData}.
     *
     * @param playerData the {@link IPlayerData} to save
     * @return a {@link CompletableFuture} that completes when the {@link IPlayerData} is saved
     */

    CompletableFuture<Void> save(IPlayerData playerData);

    /**
     * Deletes the specified player data.
     *
     * @param playerData the {@link IPlayerData} to delete
     * @return a {@link CompletableFuture} that completes when the {@link IPlayerData} is deleted
     */

    CompletableFuture<Void> delete(IPlayerData playerData);

    /**
     * Creates a new {@link IPlayerData} with the specified entity ID.
     *
     * @param entityId the {@link UUID} of the @{@link IPlayerData} to create
     * @return a {@link CompletableFuture} that returns the newly created {@link IPlayerData}
     */

    CompletableFuture<IPlayerData> createEntity(UUID entityId);

    /**
     * Determines if a {@link IPlayerData} with the specified entity ID exists.
     *
     * @param entityId the {@link UUID} of the {@link IPlayerData} to check
     * @return a {@link CompletableFuture} that returns true if a {@link IPlayerData} with the specified ID exists, false otherwise
     */

    CompletableFuture<Boolean> exists(UUID entityId);

    /**
     * Retrieves the {@link IPlayerData} with the given {@link UUID}.
     * <p>
     * If the player data is already loaded, it is returned immediately.
     * If not, it is loaded
     * asynchronously and returned in a {@link CompletableFuture}.
     *
     * @param entityId the {@link UUID} of the {@link IPlayerData} to retrieve
     * @return a {@link CompletableFuture} that will contain the {@link IPlayerData} with the given id
     */

    CompletableFuture<IPlayerData> getEntity(UUID entityId);

    /**
     * Saves all the loaded {@link IPlayerData}s in the {@link #getLoadedEntities()} map.
     * For each {@link IPlayerData} in the map,
     * this method calls the {@link #save(IPlayerData)} method to persist the {@link IPlayerData}.
     */

    void saveAll();

    /**
     * Loads all the entities by retrieving their IDs and invoking the {@link #load(UUID)} method.
     */

    void loadAll();

    /**
     * Internal method. Retrieves a map of loaded entities.
     *
     * @return The map of loaded entities.
     */

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

    /**
     * Retrieves a map of placeholders for the specified player data.
     *
     * @param data The player data to retrieve placeholders for.
     * @return A map of placeholders.
     */
    ObjectMap<String, String> getPlaceHolders(final @NotNull IPlayerData data);
}
