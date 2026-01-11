package com.georgev22.voidchest.api.storage.cache;

import com.georgev22.voidchest.api.datastructures.maps.ConcurrentObjectMap;
import com.georgev22.voidchest.api.datastructures.Pair;
import com.georgev22.voidchest.api.datastructures.maps.UnmodifiableObjectMap;
import com.georgev22.voidchest.api.registry.EntityManagerRegistry;
import com.georgev22.voidchest.api.storage.EntityManager;
import com.georgev22.voidchest.api.storage.model.AbstractPlayerData;
import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableBlock.BlockPos;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import com.georgev22.voidchest.api.utilities.VoidChunk;
import com.google.common.collect.Sets;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Manages a cache for {@link AbstractVoidChest} objects, storing them based on {@link Location}, {@link Chunk}, and {@link AbstractPlayerData}.
 * This allows efficient retrieval, insertion, and removal of void chests tied to various game-world objects.
 */
public class VoidChestCacheController {

    private final ConcurrentObjectMap<SerializableLocation, AbstractVoidChest> voidChestCache = new ConcurrentObjectMap<>();
    private final ConcurrentObjectMap<VoidChunk, Set<AbstractVoidChest>> chunkCache = new ConcurrentObjectMap<>();
    private final ConcurrentObjectMap<AbstractPlayerData, Set<AbstractVoidChest>> playerCache = new ConcurrentObjectMap<>();
    private final Set<BlockFace> nearBlockFaces = Sets.immutableEnumSet(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH,
            BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN);

    /**
     * Adds a {@link AbstractVoidChest} to the location cache.
     *
     * @param voidChest The void chest to add.
     * @param location  The location at which the storage is located.
     */
    public void add(@NonNull AbstractVoidChest voidChest, @NonNull Location location) {
        add(voidChest, SerializableLocation.fromLocation(location));
    }

    /**
     * Adds a {@link AbstractVoidChest} to the location cache.
     *
     * @param voidChest The void chest to add.
     * @param location  The location at which the storage is located.
     */
    public void add(@NonNull AbstractVoidChest voidChest, @NonNull SerializableLocation location) {
        voidChestCache.putIfAbsent(location, voidChest);
        chunkCache.computeIfAbsent(location.getChunk(), k -> ConcurrentHashMap.newKeySet()).add(voidChest);
    }

    /**
     * Removes a {@link AbstractVoidChest} from the location cache.
     *
     * @param location The location to remove.
     */
    public void remove(@NonNull Location location) {
        SerializableLocation sLocation = SerializableLocation.fromLocation(location);
        remove(sLocation);
    }

    /**
     * Removes the specified {@link AbstractVoidChest} from all caches.
     *
     * <p>This method removes the chest from:
     * <ul>
     *   <li>The location-based cache</li>
     *   <li>The chunk-based cache</li>
     * </ul>
     *
     * @param voidChest The void chest to remove from the cache.
     */
    public void remove(@NonNull AbstractVoidChest voidChest) {
        voidChestCache.remove(voidChest.blockLocation());
        chunkCache.getOrDefault(voidChest.blockLocation().getChunk(), ConcurrentHashMap.newKeySet()).remove(voidChest);
    }

    /**
     * Removes a {@link AbstractVoidChest} from the location cache.
     *
     * @param location The location to remove.
     */
    public void remove(@NonNull SerializableLocation location) {
        AbstractVoidChest removed = voidChestCache.remove(location);
        if (removed == null) return;
        chunkCache.getOrDefault(location.getChunk(), ConcurrentHashMap.newKeySet()).remove(removed);
    }

    /**
     * Provides an unmodifiable view of the location-based cache.
     *
     * @return Unmodifiable map of {@link Location} to {@link AbstractVoidChest}.
     */
    public UnmodifiableObjectMap<SerializableLocation, AbstractVoidChest> getCache() {
        return new UnmodifiableObjectMap<>(voidChestCache);
    }

    /**
     * Clears all cached {@link AbstractVoidChest} instances from both the location and chunk caches.
     */
    public void clearCache() {
        voidChestCache.clear();
        chunkCache.clear();
    }

    /**
     * Retrieves {@link AbstractVoidChest} instances associated with a given player asynchronously.
     *
     * @param playerData The player data to fetch storages for.
     * @return A {@link CompletableFuture} containing a list of void chests.
     */
    public CompletableFuture<List<AbstractVoidChest>> voidChests(@NonNull AbstractPlayerData playerData) {
        EntityManagerRegistry entityManagerRegistry = EntityManagerRegistry.getInstance();
        Optional<EntityManager<AbstractVoidChest>> voidEntityManager = entityManagerRegistry.getTyped(AbstractVoidChest.class);
        if (voidEntityManager.isEmpty()) return CompletableFuture.completedFuture(new ArrayList<>());
        List<UUID> currentStorageIds = playerData.voidChests();
        Set<AbstractVoidChest> cachedStorages = playerCache.getOrDefault(playerData, ConcurrentHashMap.newKeySet());
        if (cachedStorages.size() == currentStorageIds.size() &&
                cachedStorages.stream().allMatch(storage -> currentStorageIds.contains(storage.getUniqueId()))) {
            return CompletableFuture.completedFuture(new ArrayList<>(cachedStorages));
        }
        return CompletableFuture.supplyAsync(() -> {
            Set<AbstractVoidChest> updatedStorages = currentStorageIds.stream()
                    .map(id -> voidEntityManager.get().findById(id.toString()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toCollection(ConcurrentHashMap::newKeySet));

            playerCache.put(playerData, updatedStorages);
            return new ArrayList<>(updatedStorages);
        });
    }

    /**
     * Fetches an {@link Optional} containing the {@link AbstractVoidChest} for the specified block.
     *
     * @param block The block to retrieve the void chest for.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<AbstractVoidChest> get(@NonNull Block block) {
        return get(block.getLocation());
    }

    /**
     * Attempts to resolve a {@link AbstractVoidChest} associated with the given block
     * when the block is part of a double chest.
     *
     * <p>If the block is a chest and is connected to another chest block,
     * this method will resolve the neighboring chest block that forms the
     * double chest structure and return its registered {@link AbstractVoidChest}, if any.</p>
     *
     * <p>If the block is a single chest, this method behaves the same as {@link #get(Block)}.</p>
     *
     * <p>If neither the block nor its double-chest counterpart is registered as a void chest,
     * this method returns {@link Optional#empty()}.</p>
     *
     * @param block The chest block to resolve.
     * @return An {@link Optional} containing the associated {@link AbstractVoidChest} if found,
     * otherwise {@link Optional#empty()}.
     */
    public Optional<AbstractVoidChest> getAssociatedVoidChest(@NonNull Block block) {
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
     * Searches all directly adjacent blocks of the given block for a registered {@link AbstractVoidChest}.
     *
     * <p>This method checks all cardinally adjacent block faces defined in {@link #nearBlockFaces}
     * and returns the first neighboring block that is registered as a void chest.</p>
     *
     * <p>This is useful for resolving void chests when interacting with non-chest blocks
     * that are part of a void chest structure or multi-block setup.</p>
     *
     * @param block The block whose adjacent blocks will be searched.
     * @return An {@link Optional} containing the first neighboring {@link AbstractVoidChest} found,
     * otherwise {@link Optional#empty()}.
     */
    public Optional<AbstractVoidChest> findNearbyVoidChest(@NonNull Block block) {
        for (BlockFace face : nearBlockFaces) {
            Block neighbor = block.getRelative(face);
            Optional<AbstractVoidChest> voidChest = this.get(neighbor);
            if (voidChest.isPresent()) {
                return voidChest;
            }
        }
        return Optional.empty();
    }

    /**
     * Attempts to resolve a {@link AbstractVoidChest} for the given block.
     *
     * <p>This method first attempts a direct lookup using {@link #get(Block)}.
     * If no void chest is registered at the given block position, it will attempt
     * to resolve an associated void chest via {@link #getAssociatedVoidChest(Block)},
     * allowing double-chest counterparts to be resolved transparently.</p>
     *
     * @param block The block to resolve.
     * @return An {@link Optional} containing the resolved {@link AbstractVoidChest} if found,
     * otherwise {@link Optional#empty()}.
     */
    public Optional<AbstractVoidChest> getOrAssociated(@NonNull Block block) {
        return get(block).or(() -> getAssociatedVoidChest(block));
    }

    /**
     * Attempts to resolve a {@link AbstractVoidChest} for the given {@link Location}.
     *
     * <p>This method behaves the same as {@link #getOrAssociated(Block)} but accepts a location
     * instead of a block.</p>
     *
     * @param location The location to resolve.
     * @return An {@link Optional} containing the resolved {@link AbstractVoidChest} if found,
     * otherwise {@link Optional#empty()}.
     */
    public Optional<AbstractVoidChest> getOrAssociated(@NonNull Location location) {
        return get(location).or(() -> getAssociatedVoidChest(location.getBlock()));
    }

    /**
     * Attempts to resolve a {@link AbstractVoidChest} for the given {@link SerializableLocation}.
     *
     * <p>This method behaves the same as {@link #getOrAssociated(Block)} but accepts a serializable
     * location wrapper.</p>
     *
     * @param serializableLocation The serializable location to resolve.
     * @return An {@link Optional} containing the resolved {@link AbstractVoidChest} if found,
     * otherwise {@link Optional#empty()}.
     */
    public Optional<AbstractVoidChest> getOrAssociated(@NonNull SerializableLocation serializableLocation) {
        return get(serializableLocation).or(() -> getAssociatedVoidChest(serializableLocation.toLocation().getBlock()));
    }

    /**
     * Attempts to resolve a {@link AbstractVoidChest} for the given {@link SerializableBlock}.
     *
     * <p>This method behaves the same as {@link #getOrAssociated(Block)} but accepts a serializable
     * block wrapper.</p>
     *
     * @param serializableBlock The serializable block wrapper to resolve.
     * @return An {@link Optional} containing the resolved {@link AbstractVoidChest} if found,
     * otherwise {@link Optional#empty()}.
     */
    public Optional<AbstractVoidChest> getOrAssociated(@NonNull SerializableBlock serializableBlock) {
        return get(serializableBlock).or(() -> getAssociatedVoidChest(serializableBlock.toLocation().getBlock()));
    }

    /**
     * Fetches an {@link Optional} containing the {@link AbstractVoidChest} for the specified serializable block.
     *
     * @param serializableBlock The serializable block.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<AbstractVoidChest> get(@Nullable SerializableBlock serializableBlock) {
        if (serializableBlock == null) return Optional.empty();
        Block block = serializableBlock.toBlock();
        if (block == null) return Optional.empty();
        return get(block);
    }

    /**
     * Fetches an {@link Optional} containing the {@link AbstractVoidChest} for the specified location.
     *
     * @param location The location to retrieve the void chest for.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<AbstractVoidChest> get(@NonNull Location location) {
        //noinspection ConstantValue
        if (location == null) return Optional.empty();
        return get(SerializableLocation.fromLocation(location));
    }

    /**
     * Fetches an {@link Optional} containing the {@link AbstractVoidChest} for the specified serializable location.
     *
     * @param location The serializable location.
     * @return An {@link Optional} of the associated void chest.
     */
    public Optional<AbstractVoidChest> get(@NonNull SerializableLocation location) {
        //noinspection ConstantValue
        if (location == null) return Optional.empty();
        AbstractVoidChest cached = voidChestCache.get(location);
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
    public Optional<AbstractVoidChest> get(@NonNull Chunk chunk) {
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
    public Optional<AbstractVoidChest> get(@NonNull VoidChunk chunk) {
        for (AbstractVoidChest voidChest : getAll(chunk)) {
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
    public List<AbstractVoidChest> getAll(@NonNull Chunk chunk) {
        return getAll(new VoidChunk(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
    }

    /**
     * Retrieves all void chests that are stored within the given {@link VoidChunk}.
     *
     * @param chunk The virtual chunk to search in.
     * @return A list of all void chests present in the chunk.
     */
    public List<AbstractVoidChest> getAll(@NonNull VoidChunk chunk) {
        return new ArrayList<>(chunkCache.getOrDefault(chunk, ConcurrentHashMap.newKeySet()));
    }

    /**
     * Retrieves all void chests that are stored within the given {@link World}.
     *
     * @param world The world to search in.
     * @return A list of all void chests present in the world.
     */
    public List<AbstractVoidChest> getAll(@NonNull World world) {
        List<AbstractVoidChest> voidChests = new ArrayList<>();

        for (VoidChunk chunk : chunkCache.keySet()) {
            if (!chunk.getWorldName().equals(world.getName())) continue;
            voidChests.addAll(getAll(chunk));
        }

        return voidChests;
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
     * Expands a chest block into its logical double-chest pair if applicable.
     *
     * <p>If the supplied block is a single chest or not a chest at all, the returned pair will contain
     * only the given block in {@link Pair#first()} and {@code null} in {@link Pair#second()}.</p>
     *
     * <p>If the block is part of a double chest, the returned pair will contain <b>both chest halves</b>
     * ordered consistently:</p>
     * <ul>
     *   <li>{@link Pair#first()}  – the logical <b>left half</b> of the double chest</li>
     *   <li>{@link Pair#second()} – the logical <b>right half</b> of the double chest</li>
     * </ul>
     *
     * <p>This ordering is independent of which physical block was supplied and allows deterministic
     * handling of double chests (for example when camouflaging, revealing, or syncing client-side
     * block changes).</p>
     *
     * @param block the chest block to expand
     * @return a pair containing the left and right chest halves, or {@code (block, null)} if not a double chest
     */
    public @NonNull Pair<BlockPos, BlockPos> expandDoubleChest(@NonNull Block block) {
        BlockData data = block.getBlockData();
        if (!(data instanceof Chest chest)) return Pair.create(BlockPos.of(block), null);
        if (chest.getType() == Chest.Type.SINGLE) return Pair.create(BlockPos.of(block), null);

        BlockFace facing = chest.getFacing();
        Chest.Type type = chest.getType();
        BlockFace side = type.equals(Chest.Type.LEFT)
                ? rotateRight(facing)
                : rotateLeft(facing);

        Block other = block.getRelative(side);
        if (type.equals(Chest.Type.LEFT)) {
            return Pair.create(BlockPos.of(block), BlockPos.of(other));
        } else {
            return Pair.create(BlockPos.of(other), BlockPos.of(block));
        }
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
