package com.georgev22.voidchest.api.storage.cache;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.maps.UnmodifiableObjectMap;
import com.georgev22.voidchest.api.registry.EntityManagerRegistry;
import com.georgev22.voidchest.api.storage.EntityManager;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Manages a cache for {@link IVoidChest} objects, storing them based on {@link Location}, {@link Chunk}, and {@link IPlayerData}.
 * This allows efficient retrieval, insertion, and removal of void chests tied to various game-world objects.
 */
public class VoidChestCacheController {

    private final ConcurrentObjectMap<Location, IVoidChest> locationVoidChestCache = new ConcurrentObjectMap<>();
    private final ConcurrentObjectMap<Chunk, List<IVoidChest>> chunkVoidChestCache = new ConcurrentObjectMap<>();
    private final ConcurrentObjectMap<IPlayerData, List<IVoidChest>> playerCache = new ConcurrentObjectMap<>();

    /**
     * Adds a {@link IVoidChest} to the chunk cache.
     *
     * @param voidChest The void chest to add.
     * @param chunk     The chunk in which the storage is located.
     */
    public void addVoidChestToChunkCache(@NotNull IVoidChest voidChest, @NotNull Chunk chunk) {
        if (!chunk.isLoaded()) return;

        chunkVoidChestCache.putIfAbsent(chunk, new ArrayList<>());
        List<IVoidChest> storages = chunkVoidChestCache.get(chunk);
        if (!storages.contains(voidChest)) {
            storages.add(voidChest);
        }
    }

    /**
     * Removes a {@link IVoidChest} from the chunk cache.
     *
     * @param voidChest The void chest to remove.
     * @param chunk     The chunk from which to remove the storage.
     */
    public void removeVoidChestFromChunkCache(@NotNull IVoidChest voidChest, @NotNull Chunk chunk) {
        Optional.ofNullable(chunkVoidChestCache.get(chunk)).ifPresent(storages -> storages.remove(voidChest));
    }

    /**
     * Provides an unmodifiable view of the chunk-based cache.
     *
     * @return Unmodifiable map of {@link Chunk} to {@link IVoidChest}.
     */
    public UnmodifiableObjectMap<Chunk, List<IVoidChest>> getChunkCache() {
        return new UnmodifiableObjectMap<>(chunkVoidChestCache);
    }

    /**
     * Adds a {@link IVoidChest} to the location cache.
     *
     * @param voidChest The void chest to add.
     * @param location  The location at which the storage is located.
     */
    public void addVoidChestToLocationCache(@NotNull IVoidChest voidChest, @NotNull Location location) {
        locationVoidChestCache.put(location, voidChest);
    }

    /**
     * Removes a {@link IVoidChest} from the location cache.
     *
     * @param location The location to remove.
     */
    public void removeVoidChestFromLocationCache(@NotNull Location location) {
        locationVoidChestCache.remove(location);
    }

    /**
     * Provides an unmodifiable view of the location-based cache.
     *
     * @return Unmodifiable map of {@link Location} to {@link IVoidChest}.
     */
    public UnmodifiableObjectMap<Location, IVoidChest> getLocationCache() {
        return new UnmodifiableObjectMap<>(locationVoidChestCache);
    }

    /**
     * Adds a {@link IVoidChest} to the block's location cache.
     *
     * @param voidChest The void chest to add.
     * @param block     The block to associate with the void chest.
     */
    public void addVoidChestToBlockCache(@NotNull IVoidChest voidChest, @NotNull Block block) {
        addVoidChestToLocationCache(voidChest, block.getLocation());
    }

    /**
     * Removes a {@link IVoidChest} from the block's location cache.
     *
     * @param block The block whose location cache should be cleared.
     */
    public void removeVoidChestFromBlockCache(@NotNull Block block) {
        removeVoidChestFromLocationCache(block.getLocation());
    }

    /**
     * Retrieves an unmodifiable view of the block cache containing {@link IVoidChest} instances.
     *
     * @return An unmodifiable map of blocks and their associated void chests.
     */
    public UnmodifiableObjectMap<Block, IVoidChest> getBlockCache() {
        return new UnmodifiableObjectMap<>(locationVoidChestCache.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getBlock(),
                        Map.Entry::getValue)));
    }

    /**
     * Removes a {@link IVoidChest} from all associated caches (chunk, location, and block).
     *
     * @param voidChest The void chest to remove.
     */
    public void removeVoidChestFromAllCaches(@NotNull IVoidChest voidChest) {
        Location location = voidChest.blockLocation().toLocation();
        if (location != null) {
            removeVoidChestFromChunkCache(voidChest, location.getChunk());
        }
        locationVoidChestCache.entrySet().removeIf(entry -> entry.getValue().getId().equals(voidChest.getId()));
        chunkVoidChestCache.values().forEach(list -> list.removeIf(storage -> storage.getId().equals(voidChest.getId())));
    }

    /**
     * Clears all cached {@link IVoidChest} instances from both the location and chunk caches.
     */
    public void clearCaches() {
        locationVoidChestCache.clear();
        chunkVoidChestCache.clear();
    }

    /**
     * Retrieves {@link IVoidChest} instances associated with a given player asynchronously.
     *
     * @param playerData The player data to fetch storages for.
     * @return A {@link CompletableFuture} containing a list of void chests.
     */
    public CompletableFuture<List<IVoidChest>> voidChests(@NotNull IPlayerData playerData) {
        EntityManager<IVoidChest> voidEntityManager = EntityManagerRegistry.getManager(IVoidChest.class);
        if (voidEntityManager == null) return CompletableFuture.completedFuture(Collections.emptyList());
        List<UUID> currentStorageIds = playerData.voidChests();
        List<IVoidChest> cachedStorages = playerCache.getOrDefault(playerData, new ArrayList<>());
        if (cachedStorages.size() == currentStorageIds.size() &&
                cachedStorages.stream().allMatch(storage -> currentStorageIds.contains(storage.getId()))) {
            return CompletableFuture.completedFuture(cachedStorages);
        }
        return CompletableFuture.supplyAsync(() -> {
            List<IVoidChest> updatedStorages = currentStorageIds.stream()
                    .map(id -> voidEntityManager.getEntity(id.toString(), true))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            playerCache.put(playerData, updatedStorages);
            return updatedStorages;
        });
    }

    /**
     * Fetches a {@link IVoidChest} for the specified block.
     *
     * @param block The block to retrieve the void chest for.
     * @return The associated void chest or {@code null} if not found.
     */
    public @Nullable IVoidChest voidChest(@NotNull Block block) {
        return voidChest(block.getLocation());
    }

    /**
     * Fetches a {@link IVoidChest} for the specified serializable block.
     *
     * @param serializableBlock The serializable block.
     * @return The associated void chest or {@code null} if not found.
     */
    public @Nullable IVoidChest voidChest(@Nullable SerializableBlock serializableBlock) {
        if (serializableBlock == null) return null;
        Block block = serializableBlock.toBlock();
        if (block == null) return null;
        return voidChest(block);
    }

    /**
     * Fetches a {@link IVoidChest} for the specified location.
     *
     * @param location The location to retrieve the void chest for.
     * @return The associated void chest or {@code null} if not found.
     */
    public @Nullable IVoidChest voidChest(@Nullable Location location) {
        if (location == null) return null;
        if (!locationVoidChestCache.containsKey(location)) {
            EntityManager<IVoidChest> voidEntityManager = EntityManagerRegistry.getManager(IVoidChest.class);
            if (voidEntityManager == null) return null;

            voidEntityManager.getAll().stream()
                    .filter(storage -> storage.blockLocation().toLocation() != null)
                    .filter(storage -> location.equals(storage.blockLocation().toLocation()))
                    .findFirst().ifPresent(voidChest -> locationVoidChestCache.put(location, voidChest));

        }
        return locationVoidChestCache.get(location);
    }

    /**
     * Fetches a {@link IVoidChest} for the specified serializable location.
     *
     * @param serializableLocation The serializable location.
     * @return The associated void chest or {@code null} if not found.
     */
    public @Nullable IVoidChest voidChest(@Nullable SerializableLocation serializableLocation) {
        return serializableLocation == null ? null : voidChest(serializableLocation.toLocation());
    }

    public @Nullable IVoidChest voidChest(@Nullable Chunk chunk) {
        if (chunk == null) return null;
        return chunkVoidChestCache.getOrDefault(chunk, Collections.emptyList()).stream().findFirst().orElse(null);
    }

    public List<IVoidChest> voidChests(@NotNull Chunk chunk) {
        return chunkVoidChestCache.getOrDefault(chunk, Collections.emptyList());
    }
}
