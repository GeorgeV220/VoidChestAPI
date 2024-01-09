package com.georgev22.voidchest.api.storage;

import com.georgev22.library.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The IVoidStorageManager interface extends the EntityManager interface for managing VoidStorages.
 */
public interface IVoidStorageManager {

    /**
     * Retrieves a list of all VoidStorages.
     *
     * @return A list of VoidStorages.
     */
    List<IVoidStorage> voidStorages();

    /**
     * Retrieves a CompletableFuture that resolves to the VoidStorage with the specified UUID.
     *
     * @param uuid The UUID of the VoidStorage to retrieve.
     * @return A CompletableFuture that resolves to the VoidStorage.
     */
    CompletableFuture<IVoidStorage> voidStorage(UUID uuid);

    /**
     * Retrieves the VoidStorage associated with the specified block.
     *
     * @param block The block to retrieve the VoidStorage for.
     * @return The VoidStorage associated with the block, or null if not found.
     */
    @Nullable IVoidStorage voidStorage(Block block);

    /**
     * Retrieves the VoidStorage associated with the specified block.
     *
     * @param block The block to retrieve the VoidStorage for.
     * @return The VoidStorage associated with the block, or null if not found.
     */
    @Nullable IVoidStorage voidStorage(SerializableBlock block);

    /**
     * Retrieves the VoidStorage associated with the specified location.
     *
     * @param location The location to retrieve the VoidStorage for.
     * @return The VoidStorage associated with the location, or null if not found.
     */
    @Nullable IVoidStorage voidStorage(Location location);

    /**
     * Retrieves the VoidStorage associated with the specified location.
     *
     * @param location The location to retrieve the VoidStorage for.
     * @return The VoidStorage associated with the location, or null if not found.
     */
    @Nullable IVoidStorage voidStorage(SerializableLocation location);


    /**
     * Retrieves the VoidStorage associated with the specified chunk.
     *
     * @param chunk The chunk to retrieve the VoidStorage for.
     * @return The VoidStorage associated with the chunk, or null if not found.
     * @deprecated A chunk may have multiple VoidStorages associated with it.
     * The method will not be removed, but it may return incorrect results.
     * Use {@link #voidStorage(SerializableLocation)}, {@link #voidStorage(SerializableBlock)},
     * {@link #voidStorage(Location)} or {@link #voidStorage(Block)} instead.
     */
    @Deprecated(since = "2.0.0")
    @Nullable IVoidStorage voidStorage(Chunk chunk);

    /**
     * Retrieves a list of VoidStorages associated with the specified chunk.
     *
     * @param chunk The chunk to retrieve the list of VoidStorages for.
     * @return A list of VoidStorages associated with the chunk, or an empty list if none are found.
     * @since 2.0.0
     */
    List<IVoidStorage> voidStorages(Chunk chunk);

    /**
     * Loads the {@link IVoidStorage} with the specified ID
     *
     * @param entityId the {@link UUID} of the entity to be loaded
     * @return a {@link CompletableFuture} containing the loaded {@link IVoidStorage} object
     */

    CompletableFuture<IVoidStorage> load(UUID entityId);

    /**
     * Saves the specified {@link IVoidStorage}.
     *
     * @param voidStorage the {@link IVoidStorage} to save
     * @return a {@link CompletableFuture} that completes when the {@link IVoidStorage} is saved
     */

    CompletableFuture<Void> save(IVoidStorage voidStorage);

    /**
     * Deletes the specified entity.
     *
     * @param voidStorage the {@link IVoidStorage} to delete
     * @return a {@link CompletableFuture} that completes when the {@link IVoidStorage} is deleted
     */

    CompletableFuture<Void> delete(IVoidStorage voidStorage);

    /**
     * Creates a new {@link IVoidStorage} with the specified entity ID.
     *
     * @param entityId the {@link UUID} of the entity to create
     * @return a {@link CompletableFuture} that returns the newly created {@link IVoidStorage}
     */

    CompletableFuture<IVoidStorage> createEntity(UUID entityId);

    /**
     * Determines if a {@link IVoidStorage} with the specified entity ID exists.
     *
     * @param entityId the {@link UUID} of the entity to check
     * @return a {@link CompletableFuture} that returns true if a {@link IVoidStorage} with the specified ID exists, false otherwise
     */

    CompletableFuture<Boolean> exists(UUID entityId);

    /**
     * Retrieves the {@link IVoidStorage} with the given {@link UUID}.
     * <p>
     * If the entity is already loaded, it is returned immediately.
     * If not, it is loaded
     * asynchronously and returned in a {@link CompletableFuture}.
     *
     * @param entityId the {@link UUID} of the entity to retrieve
     * @return a {@link CompletableFuture} that will contain the {@link IVoidStorage} with the given id
     */

    CompletableFuture<IVoidStorage> getEntity(UUID entityId);

    /**
     * Saves all the loaded {@link IVoidStorage}s in the {@link #getLoadedEntities()} map.
     * For each {@link IVoidStorage} in the map,
     * this method calls the {@link #save(IVoidStorage)} method to persist the {@link IVoidStorage}.
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
    ObjectMap<UUID, IVoidStorage> getLoadedEntities();
}
