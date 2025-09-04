package com.georgev22.voidchest.api.storage.cache;

import com.georgev22.voidchest.api.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.maps.UnmodifiableObjectMap;
import com.georgev22.voidchest.api.registry.EntityManagerRegistry;
import com.georgev22.voidchest.api.storage.EntityManager;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import com.georgev22.voidchest.api.utilities.VoidChunk;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Manages a cache for {@link IVoidChest} objects, storing them based on {@link Location}, {@link Chunk}, and {@link IPlayerData}.
 * This allows efficient retrieval, insertion, and removal of void chests tied to various game-world objects.
 */
public class VoidChestCacheController {

    private final ConcurrentObjectMap<SerializableLocation, IVoidChest> voidChestCache = new ConcurrentObjectMap<>();
    private final ConcurrentObjectMap<VoidChunk, Set<IVoidChest>> chunkCache = new ConcurrentObjectMap<>();
    private final ConcurrentObjectMap<IPlayerData, Set<IVoidChest>> playerCache = new ConcurrentObjectMap<>();
    private final Set<BlockFace> faces = Sets.immutableEnumSet(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH,
            BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN);

    /**
     * Adds a {@link IVoidChest} to the location cache.
     *
     * @param voidChest The void chest to add.
     * @param location  The location at which the storage is located.
     */
    public void add(@NotNull IVoidChest voidChest, @NotNull Location location) {
        add(voidChest, SerializableLocation.fromLocation(location));
    }

    /**
     * Adds a {@link IVoidChest} to the location cache.
     *
     * @param voidChest The void chest to add.
     * @param location  The location at which the storage is located.
     */
    public void add(@NotNull IVoidChest voidChest, @NotNull SerializableLocation location) {
        voidChestCache.putIfAbsent(location, voidChest);
        chunkCache.computeIfAbsent(location.getChunk(), k -> ConcurrentHashMap.newKeySet()).add(voidChest);
    }

    /**
     * Removes a {@link IVoidChest} from the location cache.
     *
     * @param location The location to remove.
     */
    public void remove(@NotNull Location location) {
        SerializableLocation sLocation = SerializableLocation.fromLocation(location);
        remove(sLocation);
    }

    public void remove(@NotNull IVoidChest voidChest) {
        voidChestCache.remove(voidChest.blockLocation());
        chunkCache.getOrDefault(voidChest.blockLocation().getChunk(), ConcurrentHashMap.newKeySet()).remove(voidChest);
    }

    /**
     * Removes a {@link IVoidChest} from the location cache.
     *
     * @param location The location to remove.
     */
    public void remove(@NotNull SerializableLocation location) {
        IVoidChest removed = voidChestCache.remove(location);
        if (removed == null) return;
        chunkCache.getOrDefault(location.getChunk(), ConcurrentHashMap.newKeySet()).remove(removed);
    }

    /**
     * Provides an unmodifiable view of the location-based cache.
     *
     * @return Unmodifiable map of {@link Location} to {@link IVoidChest}.
     */
    public UnmodifiableObjectMap<SerializableLocation, IVoidChest> getCache() {
        return new UnmodifiableObjectMap<>(voidChestCache);
    }

    /**
     * Clears all cached {@link IVoidChest} instances from both the location and chunk caches.
     */
    public void clearCache() {
        voidChestCache.clear();
        chunkCache.clear();
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
        Set<IVoidChest> cachedStorages = playerCache.getOrDefault(playerData, ConcurrentHashMap.newKeySet());
        if (cachedStorages.size() == currentStorageIds.size() &&
                cachedStorages.stream().allMatch(storage -> currentStorageIds.contains(storage.getId()))) {
            return CompletableFuture.completedFuture(new ArrayList<>(cachedStorages));
        }
        return CompletableFuture.supplyAsync(() -> {
            Set<IVoidChest> updatedStorages = currentStorageIds.stream()
                    .map(id -> voidEntityManager.get().getEntity(id.toString(), true))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toCollection(ConcurrentHashMap::newKeySet));

            playerCache.put(playerData, updatedStorages);
            return new ArrayList<>(updatedStorages);
        });
    }

    /**
     * Fetches an {@link Optional} containing the {@link IVoidChest} for the specified block.
     *
     * @param block The block to retrieve the void chest for.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<IVoidChest> get(@NotNull Block block) {
        return get(block.getLocation());
    }

    /**
     * Attempts to resolve the {@link IVoidChest} associated with the given block
     * if it is part of a double chest.
     *
     * @param block The chest block to check.
     * @return An {@link Optional} containing the associated void chest if the block
     * is part of a double chest and one side is registered, otherwise empty.
     */
    public Optional<IVoidChest> getAssociatedVoidChest(@NotNull Block block) {
        Material chestMaterial;
        Material trappedChestMaterial;

        try {
            chestMaterial = Material.CHEST;
            trappedChestMaterial = Material.TRAPPED_CHEST;
        } catch (NoSuchFieldError | IllegalArgumentException e) {
            chestMaterial = Material.valueOf("LOCKED_CHEST");
            trappedChestMaterial = null;
        }

        if (block.getType() == chestMaterial || block.getType() == trappedChestMaterial) {
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
                Optional<IVoidChest> voidChest = this.get(other);
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
    public Optional<IVoidChest> get(@Nullable SerializableBlock serializableBlock) {
        if (serializableBlock == null) return Optional.empty();
        Block block = serializableBlock.toBlock();
        if (block == null) return Optional.empty();
        return get(block);
    }

    /**
     * Fetches an {@link Optional} containing the {@link IVoidChest} for the specified location.
     *
     * @param location The location to retrieve the void chest for.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<IVoidChest> get(@NotNull Location location) {
        //noinspection ConstantValue
        if (location == null) return Optional.empty();
        return get(SerializableLocation.fromLocation(location));
    }

    /**
     * Fetches an {@link Optional} containing the {@link IVoidChest} for the specified serializable location.
     *
     * @param location The serializable location.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<IVoidChest> get(@NotNull SerializableLocation location) {
        //noinspection ConstantValue
        if (location == null) return Optional.empty();
        IVoidChest cached = voidChestCache.get(location);
        if (cached != null) return Optional.of(cached);
        return Optional.empty();
    }

    public Optional<IVoidChest> get(@NotNull Chunk chunk) {
        return get(new VoidChunk(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
    }

    public Optional<IVoidChest> get(@NotNull VoidChunk chunk) {
        for (IVoidChest voidChest : getAll(chunk)) {
            if (voidChest.blockLocation().getChunk().equals(chunk)) return Optional.of(voidChest);
        }
        return Optional.empty();
    }

    public List<IVoidChest> getAll(@NotNull Chunk chunk) {
        return getAll(new VoidChunk(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
    }

    public List<IVoidChest> getAll(@NotNull VoidChunk chunk) {
        return new ArrayList<>(chunkCache.getOrDefault(chunk, ConcurrentHashMap.newKeySet()));
    }
}
