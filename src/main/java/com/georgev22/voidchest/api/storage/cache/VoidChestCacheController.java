package com.georgev22.voidchest.api.storage.cache;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.maps.UnmodifiableObjectMap;
import com.georgev22.voidchest.api.registry.EntityManagerRegistry;
import com.georgev22.voidchest.api.storage.EntityManager;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import com.google.common.collect.Sets;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
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

    private final ConcurrentObjectMap<SerializableLocation, IVoidChest> locationVoidChestCache = new ConcurrentObjectMap<>();
    private final ConcurrentObjectMap<Chunk, List<IVoidChest>> chunkVoidChestCache = new ConcurrentObjectMap<>();
    private final ConcurrentObjectMap<IPlayerData, List<IVoidChest>> playerCache = new ConcurrentObjectMap<>();
    private final Set<BlockFace> faces = Sets.immutableEnumSet(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH,
            BlockFace.SOUTH);

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
        locationVoidChestCache.put(SerializableLocation.fromLocation(location), voidChest);
    }

    /**
     * Adds a {@link IVoidChest} to the location cache.
     *
     * @param voidChest The void chest to add.
     * @param location  The location at which the storage is located.
     */
    public void addVoidChestToLocationCache(@NotNull IVoidChest voidChest, @NotNull SerializableLocation location) {
        locationVoidChestCache.put(location, voidChest);
    }

    /**
     * Removes a {@link IVoidChest} from the location cache.
     *
     * @param location The location to remove.
     */
    public void removeVoidChestFromLocationCache(@NotNull Location location) {
        locationVoidChestCache.remove(SerializableLocation.fromLocation(location));
    }

    /**
     * Provides an unmodifiable view of the location-based cache.
     *
     * @return Unmodifiable map of {@link Location} to {@link IVoidChest}.
     */
    public UnmodifiableObjectMap<SerializableLocation, IVoidChest> getLocationCache() {
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
    public UnmodifiableObjectMap<@Nullable Block, IVoidChest> getBlockCache() {
        return new UnmodifiableObjectMap<>(locationVoidChestCache.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> {
                            Location location = entry.getKey().toLocation();
                            return location != null ? location.getBlock() : null;
                        },
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
        @NotNull Optional<EntityManager<IVoidChest>> voidEntityManager = EntityManagerRegistry.getManager(IVoidChest.class);
        if (voidEntityManager.isEmpty()) return CompletableFuture.completedFuture(new ArrayList<>());
        List<UUID> currentStorageIds = playerData.voidChests();
        List<IVoidChest> cachedStorages = playerCache.getOrDefault(playerData, new ArrayList<>());
        if (cachedStorages.size() == currentStorageIds.size() &&
                cachedStorages.stream().allMatch(storage -> currentStorageIds.contains(storage.getId()))) {
            return CompletableFuture.completedFuture(cachedStorages);
        }
        return CompletableFuture.supplyAsync(() -> {
            List<IVoidChest> updatedStorages = currentStorageIds.stream()
                    .map(id -> voidEntityManager.get().getEntity(id.toString(), true))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            playerCache.put(playerData, updatedStorages);
            return updatedStorages;
        });
    }

    /**
     * Fetches an {@link Optional} containing the {@link IVoidChest} for the specified block.
     *
     * @param block The block to retrieve the void chest for.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<IVoidChest> voidChest(@NotNull Block block) {
        return voidChest(block.getLocation());
    }

    /**
     * Attempts to find a nearby {@link IVoidChest} around a double chest block.
     *
     * @param block The block to search near.
     * @return An {@link Optional} of the associated void chest, if found.
     */
    public Optional<IVoidChest> getVoidChestNear(@NotNull Block block) {
        Material chestMaterial;
        Material trappedChestMaterial;

        try {
            chestMaterial = Material.CHEST;
            trappedChestMaterial = Material.TRAPPED_CHEST;
        } catch (NoSuchFieldError | IllegalArgumentException e) {
            chestMaterial = Material.valueOf("LOCKED_CHEST");
            trappedChestMaterial = null;
        }

        if (block.getType() == chestMaterial ||
                (trappedChestMaterial != null && block.getType() == trappedChestMaterial)) {
            BlockState state = block.getState();
            if (!(state instanceof Chest chest)) {
                return Optional.empty();
            }

            Inventory inventory = chest.getInventory();
            if (!(inventory instanceof DoubleChestInventory)) {
                return Optional.empty();
            }

            for (BlockFace face : faces) {
                Block other = block.getRelative(face);
                Optional<IVoidChest> voidChest = this.voidChest(other);
                if (voidChest.isPresent()) {
                    return voidChest;
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Fetches an {@link Optional} containing the {@link IVoidChest} for the specified serializable block.
     *
     * @param serializableBlock The serializable block.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<IVoidChest> voidChest(@Nullable SerializableBlock serializableBlock) {
        if (serializableBlock == null) return Optional.empty();
        Block block = serializableBlock.toBlock();
        if (block == null) return Optional.empty();
        return voidChest(block);
    }

    /**
     * Fetches an {@link Optional} containing the {@link IVoidChest} for the specified location.
     *
     * @param location The location to retrieve the void chest for.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<IVoidChest> voidChest(@NotNull Location location) {
        return voidChest(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Fetches an {@link Optional} containing the {@link IVoidChest} for the specified serializable location.
     *
     * @param location The serializable location.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<IVoidChest> voidChest(@Nullable SerializableLocation location) {
        if (location == null) return Optional.empty();
        return voidChest(location.getWorldName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Fetches an {@link Optional} containing the {@link IVoidChest} for the specified location.
     *
     * @param worldName The name of the world.
     * @param x         The X coordinate.
     * @param y         The Y coordinate.
     * @param z         The Z coordinate.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<IVoidChest> voidChest(@NotNull String worldName, int x, int y, int z) {
        SerializableLocation sLoc = new SerializableLocation(worldName, x, y, z, 0, 0);
        IVoidChest cached = locationVoidChestCache.getOrDefault(sLoc, null);
        if (cached != null) return Optional.of(cached);

        Optional<EntityManager<IVoidChest>> voidEntityManager = EntityManagerRegistry.getManager(IVoidChest.class);
        if (voidEntityManager.isEmpty()) return Optional.empty();

        for (IVoidChest storage : voidEntityManager.get().getAll()) {
            SerializableLocation storageLocation = storage.blockLocation();
            //noinspection ConstantConditions
            if (storageLocation == null) continue;
            if (storageLocation.getWorldName().equals(sLoc.getWorldName()) &&
                    storageLocation.getBlockX() == sLoc.getBlockX() &&
                    storageLocation.getBlockY() == sLoc.getBlockY() &&
                    storageLocation.getBlockZ() == sLoc.getBlockZ()) {
                locationVoidChestCache.put(sLoc, storage);
                return Optional.of(storage);
            }
        }

        return Optional.ofNullable(locationVoidChestCache.getOrDefault(sLoc, null));
    }

    /**
     * Fetches an {@link Optional} containing any {@link IVoidChest} in the specified chunk.
     *
     * @param chunk The chunk to retrieve a void chest from.
     * @return An {@link Optional} of any void chest found in the chunk.
     */
    public Optional<IVoidChest> voidChest(@NotNull Chunk chunk) {
        //noinspection ConstantConditions
        if (chunk == null) return Optional.empty();
        return voidChests(chunk).stream().findFirst();
    }

    /**
     * Retrieves all {@link IVoidChest} instances located in a given chunk.
     *
     * @param chunk The chunk to search.
     * @return A list of {@link IVoidChest} instances.
     */
    public List<IVoidChest> voidChests(@NotNull Chunk chunk) {
        return chunkVoidChestCache.computeIfAbsent(chunk, k -> {
            List<IVoidChest> voidChests = new ArrayList<>();
            @NotNull Optional<EntityManager<IVoidChest>> optionalEntityManager = EntityManagerRegistry.getManager(IVoidChest.class);
            optionalEntityManager.ifPresent(entityManager -> {
                for (IVoidChest voidChest : entityManager.getAll()) {
                    int chunkX = voidChest.blockLocation().getChunkX();
                    int chunkZ = voidChest.blockLocation().getChunkZ();
                    if (chunkX == chunk.getX() && chunkZ == chunk.getZ()) {
                        voidChests.add(voidChest);
                    }
                }
            });
            return voidChests;
        });
    }
}
