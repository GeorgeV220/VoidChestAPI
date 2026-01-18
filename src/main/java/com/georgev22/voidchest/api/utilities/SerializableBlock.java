package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.VoidChestAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


/**
 * A serializable wrapper for Bukkit Block, representing a block's location.
 *
 * <p>The {@code SerializableBlock} class allows for easy serialization and deserialization
 * of Bukkit {@link org.bukkit.block.Block} objects by capturing the block's location details.
 * </p>
 *
 * <p>It implements the {@link Serializable} interface and includes custom methods for serialization and deserialization,
 * as well as creating a string representation of the block's location.
 * </p>
 *
 * <p>The class includes the following methods:
 * <ul>
 *     <li>{@code public SerializableBlock(@NonNull Block block)} - Constructs a new SerializableBlock from a Block.</li>
 *     <li>{@code public static @NonNull SerializableBlock fromBlock(@NonNull Block block)} - Creates a new SerializableBlock from a Block.</li>
 *     <li>{@code public @NonNull String toString()} - Converts the SerializableBlock to a string representation.</li>
 *     <li>{@code public static @Nullable SerializableBlock fromString(@NonNull String string)} - Creates a SerializableBlock from a string representation.</li>
 *     <li>{@code public static @NonNull SerializableBlock fromLocation(@NonNull Location location)} - Creates a SerializableBlock from a Location.</li>
 *     <li>{@code public Block toBlock()} - Converts the SerializableBlock back to a Block.</li>
 * </ul>
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * SerializableBlock serializableBlock = SerializableBlock.fromBlock(player.getLocation().getBlock());
 * String blockString = serializableBlock.toString();
 * SerializableBlock deserializedBlock = SerializableBlock.fromString(blockString);
 * Block originalBlock = deserializedBlock.toBlock();
 * }</pre>
 * </p>
 *
 * <p>It is recommended to handle potential exceptions or null values during the serialization and deserialization process.
 * </p>
 */
public class SerializableBlock extends SerializableLocation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private @Nullable Material material;

    /**
     * Constructs a new SerializableBlock from a Block.
     *
     * @param block The Block to be serialized.
     */
    public SerializableBlock(@NonNull Block block) {
        this(block.getLocation().getWorld().getName(), block.getX(), block.getY(), block.getZ(), block.getType());
    }

    /**
     * Constructs a new SerializableBlock from a string representation.
     *
     * @param worldName The name of the world containing the block.
     * @param x         The x-coordinate of the block.
     * @param y         The y-coordinate of the block.
     * @param z         The z-coordinate of the block.
     */
    public SerializableBlock(String worldName, int x, int y, int z) {
        this(worldName, x, y, z, null);
    }

    /**
     * Constructs a new SerializableBlock from a string representation.
     *
     * @param worldName The name of the world containing the block.
     * @param x         The x-coordinate of the block.
     * @param y         The y-coordinate of the block.
     * @param z         The z-coordinate of the block.
     */
    public SerializableBlock(String worldName, int x, int y, int z, @Nullable Material material) {
        super(worldName, x, y, z, 0, 0);
        this.material = material;
    }

    /**
     * Creates a new SerializableBlock from a Block.
     *
     * @param block The Block to be wrapped.
     * @return A new SerializableBlock instance.
     */
    @Contract("_ -> new")
    public static @NonNull SerializableBlock fromBlock(@NonNull Block block) {
        return new SerializableBlock(block);
    }

    @Contract("_ -> new")
    public static @NonNull SerializableBlock fromLocation(@NonNull SerializableLocation location) {
        return new SerializableBlock(
                location.getWorldName(),
                (int) location.getX(),
                (int) location.getY(),
                (int) location.getZ()
        );
    }

    /**
     * Creates a SerializableBlock from a string representation.
     *
     * @param string The string representation of the block's location.
     * @return A SerializableBlock instance
     * @throws IllegalArgumentException if the string is empty
     */
    public static @NonNull SerializableBlock fromString(@NonNull String string) {
        if (string.trim().isEmpty()) {
            throw new IllegalArgumentException("SerializableBlock string is empty");
        }
        String[] parts = string.split(":");
        String worldName = parts[0];
        World world = Bukkit.getServer().getWorld(worldName);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int z = Integer.parseInt(parts[3]);
        if (world != null) {
            return new SerializableBlock(world.getBlockAt(x, y, z));
        }

        // Check if we have a material
        if (parts.length > 4) {
            String materialName = parts[4];
            if (materialName != null) {
                return new SerializableBlock(worldName, x, y, z, Material.valueOf(materialName));
            }
        }

        // Maybe we should throw an exception here
        return new SerializableBlock(worldName, x, y, z);
    }

    /**
     * Creates a SerializableBlock from a Location.
     *
     * @param location The Location to be wrapped.
     * @return A new SerializableBlock instance.
     */
    public static @NonNull SerializableBlock fromLocation(@NonNull Location location) {
        return new SerializableBlock(location.getBlock());
    }

    /**
     * Converts the SerializableBlock to a string representation.
     *
     * @return A string representation of the block's location.
     */
    public @NonNull String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Location location = toLocation();
        stringBuilder
                .append(location.getWorld().getName())
                .append(":")
                .append(location.getBlockX())
                .append(":")
                .append(location.getBlockY())
                .append(":")
                .append(location.getBlockZ())
                .append(":")
                .append(this.material);

        return stringBuilder.toString();
    }

    /**
     * Attempts to convert this SerializableBlock back into a Bukkit {@link Block}.
     * <p>
     * The returned {@link Optional} will be empty if the referenced world is not
     * currently loaded or cannot be found by name.
     *
     * @return an {@link Optional} containing the resolved {@link Block} if available,
     * or {@link Optional#empty()} if the world is not loaded
     */
    public @NonNull Optional<Block> toBlock() {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            return Optional.of(world.getBlockAt((int) x, (int) y, (int) z));
        }
        return Optional.empty();
    }

    /**
     * Retrieves the {@link Material} of this block.
     * <p>
     * If the material has already been resolved, it is returned from the internal cache.
     * Otherwise, the block will be resolved via {@link #toBlock()} and the material will
     * be cached if successfully obtained.
     *
     * @return an {@link Optional} containing the block's {@link Material} if available,
     * or {@link Optional#empty()} if the block or its world cannot be resolved
     */
    public @NonNull Optional<Material> getMaterial() {
        if (this.material != null) {
            return Optional.of(this.material);
        }
        return toBlock().map(block -> {
            this.material = block.getType();
            return this.material;
        });
    }

    /**
     * Converts the SerializableBlock back to a Block asynchronously.
     *
     * @return A CompletableFuture that completes with the Block represented by this SerializableBlock, or completes exceptionally if the world is not found.
     */
    public @NonNull CompletableFuture<Block> toBlockAsync() {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            return VoidChestAPI.getInstance().minecraftScheduler()
                    .createTaskForLocation(
                            () -> world.getBlockAt(getBlockX(), getBlockY(), getBlockZ()),
                            new Location(world, x, y, z)
                    );
        } else {
            return CompletableFuture.failedFuture(new IllegalArgumentException("The world is not found."));
        }
    }

    /**
     * Returns the material of the block asynchronously.
     *
     * @return a {@link CompletableFuture} that completes with the block {@link Material},
     * or completes exceptionally if the block or its world cannot be resolved
     */
    public CompletableFuture<Material> getMaterialAsync() {
        return this.toBlockAsync()
                .thenCompose(block -> {
                    if (block == null) {
                        return CompletableFuture.failedFuture(
                                new IllegalStateException("The block could not be resolved.")
                        );
                    }
                    return CompletableFuture.completedFuture(block.getType());
                });
    }

    /**
     * Converts the SerializableBlock to a BlockPos.
     *
     * @return The BlockPos represented by this SerializableBlock.
     */
    public BlockPos toBlockPos() {
        return new BlockPos(worldName, (int) x, (int) y, (int) z);
    }

    /**
     * Lightweight immutable block position record.
     *
     * <p>Used for caching and diffing visible VoidChest blocks without holding references to live Block objects.</p>
     */
    public record BlockPos(String worldName, int x, int y, int z) {

        @Contract("_ -> new")
        public static @NonNull BlockPos of(@NonNull Block block) {
            return new BlockPos(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
        }

        public static @NonNull BlockPos of(@NonNull SerializableBlock block) {
            return new BlockPos(block.getWorldName(), block.getBlockX(), block.getBlockY(), block.getBlockZ());
        }

        /**
         * Resolves this position to a live block in the given world (prefers the provided world).
         */
        public @NonNull Block getBlock(@NonNull World preferredWorld) {
            if (preferredWorld.getName().equals(worldName)) {
                return preferredWorld.getBlockAt(x, y, z);
            }
            World world = Bukkit.getWorld(worldName);
            if (world == null) world = preferredWorld;
            return world.getBlockAt(x, y, z);
        }

        public @Contract(value = "_ -> new", pure = true)
        @NonNull Location toLocation(@NonNull World world) {
            return new Location(world, x, y, z);
        }

        @Override
        public @NonNull String toString() {
            return worldName + ":" + x + ":" + y + ":" + z;
        }
    }
}
