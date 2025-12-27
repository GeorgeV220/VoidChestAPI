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
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

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
    private final Set<BlockFace> nearBlockFaces = Sets.immutableEnumSet(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH,
            BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN);
    private final Set<BlockFace> doubleChestFaces = Sets.immutableEnumSet(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH,
            BlockFace.SOUTH);

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

    /**
     * Removes the specified {@link IVoidChest} from all caches.
     *
     * <p>This method removes the chest from:
     * <ul>
     *   <li>The location-based cache</li>
     *   <li>The chunk-based cache</li>
     * </ul>
     *
     * @param voidChest The void chest to remove from the cache.
     */
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
        EntityManagerRegistry entityManagerRegistry = EntityManagerRegistry.getInstance();
        @NotNull Optional<EntityManager<IVoidChest>> voidEntityManager = entityManagerRegistry.getTyped(IVoidChest.class);
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
        if (isChestMaterial(block.getType())) {
            BlockData data = block.getBlockData();
            if (!(data instanceof Chest chest)) {
                return Optional.empty();
            }

            if (chest.getType() == Chest.Type.SINGLE) {
                return get(block);
            }

            BlockFace face = chest.getType() == Chest.Type.LEFT
                    ? rotateRight(chest.getFacing())
                    : rotateLeft(chest.getFacing());

            Block other = block.getRelative(face);
            return this.get(other);
        }
        return Optional.empty();
    }

    /**
     * Searches all directly adjacent blocks of the given block for an associated {@link IVoidChest}.
     *
     * @param block The block whose neighbors to search.
     * @return An {@link Optional} containing the first found {@link IVoidChest}, otherwise empty.
     */
    public Optional<IVoidChest> findNearbyVoidChest(@NotNull Block block) {
        for (BlockFace face : nearBlockFaces) {
            Block neighbor = block.getRelative(face);
            Optional<IVoidChest> voidChest = this.get(neighbor);
            if (voidChest.isPresent()) {
                return voidChest;
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

    /**
     * Retrieves a void chest that is stored within the given {@link Chunk}.
     *
     * <p>If multiple void chests exist in the chunk, the first matching
     * instance found is returned.
     *
     * @param chunk The chunk to search in.
     * @return An {@link Optional} containing the first found void chest in the chunk, otherwise empty.
     */
    public Optional<IVoidChest> get(@NotNull Chunk chunk) {
        return get(new VoidChunk(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
    }

    /**
     * Retrieves a void chest that is stored within the given {@link VoidChunk}.
     *
     * <p>If multiple void chests exist in the chunk, the first matching
     * instance found is returned.
     *
     * @param chunk The virtual chunk to search in.
     * @return An {@link Optional} containing the first found void chest in the chunk, otherwise empty.
     */
    public Optional<IVoidChest> get(@NotNull VoidChunk chunk) {
        for (IVoidChest voidChest : getAll(chunk)) {
            if (voidChest.blockLocation().getChunk().equals(chunk)) return Optional.of(voidChest);
        }
        return Optional.empty();
    }

    /**
     * Retrieves all void chests that are stored within the given {@link Chunk}.
     *
     * @param chunk The chunk to search in.
     * @return A list of all void chests present in the chunk.
     */
    public List<IVoidChest> getAll(@NotNull Chunk chunk) {
        return getAll(new VoidChunk(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
    }

    /**
     * Retrieves all void chests that are stored within the given {@link VoidChunk}.
     *
     * @param chunk The virtual chunk to search in.
     * @return A list of all void chests present in the chunk.
     */
    public List<IVoidChest> getAll(@NotNull VoidChunk chunk) {
        return new ArrayList<>(chunkCache.getOrDefault(chunk, ConcurrentHashMap.newKeySet()));
    }

    /**
     * Checks whether there is any container block adjacent to the given block.
     *
     * @param block The block to check around.
     * @return {@code true} if an adjacent container block exists, otherwise {@code false}.
     */
    public boolean isChestNear(Block block) {
        return nearBlockFaces.stream().anyMatch(face -> {
            BlockState state = block.getRelative(face).getState();
            return state instanceof Container;
        });
    }

    /**
     * Checks whether the given block is a chest block.
     *
     * @param block The block to check.
     * @return {@code true} if the block represents a chest, otherwise {@code false}.
     */
    public boolean isChest(@NonNull Block block) {
        return block.getBlockData() instanceof Chest;
    }

    /**
     * Checks whether the given material represents a chest-type block.
     *
     * @param material The material to check.
     * @return {@code true} if the material can represent a chest block, otherwise {@code false}.
     */
    public boolean isChestMaterial(@NonNull Material material) {
        return material.isBlock() &&
                material.createBlockData() instanceof Chest;
    }

    /**
     * Rotates the given horizontal block face 90 degrees counter-clockwise.
     *
     * @param face The face to rotate.
     * @return The rotated face, or {@link BlockFace#SELF} if the face cannot be rotated.
     */
    public BlockFace rotateLeft(@NonNull BlockFace face) {
        return switch (face) {
            case NORTH -> BlockFace.WEST;
            case SOUTH -> BlockFace.EAST;
            case EAST -> BlockFace.NORTH;
            case WEST -> BlockFace.SOUTH;
            default -> BlockFace.SELF;
        };
    }

    /**
     * Rotates the given horizontal block face 90 degrees clockwise.
     *
     * @param face The face to rotate.
     * @return The rotated face, or {@link BlockFace#SELF} if the face cannot be rotated.
     */
    public BlockFace rotateRight(@NonNull BlockFace face) {
        return switch (face) {
            case NORTH -> BlockFace.EAST;
            case SOUTH -> BlockFace.WEST;
            case EAST -> BlockFace.SOUTH;
            case WEST -> BlockFace.NORTH;
            default -> BlockFace.SELF;
        };
    }
}
