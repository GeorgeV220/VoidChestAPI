package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.VoidChestAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;


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
    private transient final int cachedHashCode;

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
    public SerializableBlock(String worldName, double x, double y, double z) {
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
    public SerializableBlock(String worldName, double x, double y, double z, @Nullable Material material) {
        this(worldName, x, y, z, 0f, 0f, 0, 256, (int) x >> 4, (int) x >> 4, material);
    }

    public SerializableBlock(String worldName, double x, double y, double z, float yaw, float pitch, Material material) {
        this(worldName, x, y, z, yaw, pitch, 0, 256, (int) x >> 4, (int) x >> 4, material);
    }

    public SerializableBlock(String worldName, double x, double y, double z, float yaw, float pitch, int minY, int maxY, Material material) {
        this(worldName, x, y, z, yaw, pitch, minY, maxY, (int) x >> 4, (int) x >> 4, material);
    }

    public SerializableBlock(String worldName, double x, double y, double z, float yaw, float pitch, int minY, int maxY,
                             int chunkX, int chunkZ, @Nullable Material material) {
        super(worldName, x, y, z, yaw, pitch, minY, maxY, chunkX, chunkZ);
        this.material = material;
        this.cachedHashCode = computeHashCode();
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
                location.getX(),
                location.getY(),
                location.getZ()
        );
    }

    /**
     * Creates a SerializableBlock from a string representation.
     *
     * @param string The string representation of the block's location.
     * @return The SerializableBlock, or throws {@link IllegalArgumentException} if the string is empty or invalid.
     */
    public static @NonNull SerializableBlock fromString(@NonNull String string) {
        if (string.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid block string: " + string);
        }

        String[] parts = string.split(":");

        // if there be future formats this will be changed to a switch statement
        if (("v" + VERSION).equals(parts[0])) {
            SerializableLocation location = SerializableLocation.fromString(string);

            SerializableBlock block = fromLocation(location);

            if (parts.length > 11) {
                block.setMaterial(Material.valueOf(parts[11]));
            }

            return block;
        }

        // Legacy block format:
        // world:x:y:z
        // world:x:y:z:material
        return fromLegacy(parts);
    }

    private static @NonNull SerializableBlock fromLegacy(@NonNull String @NonNull [] parts) {
        String worldName = parts[0];
        World world = Bukkit.getServer().getWorld(worldName);

        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int z = Integer.parseInt(parts[3]);

        Material material = null;

        if (parts.length > 4) {
            material = Material.valueOf(parts[4]);
        }

        if (world != null) {
            SerializableBlock block =
                    new SerializableBlock(world.getBlockAt(x, y, z));

            if (material != null) {
                block.setMaterial(material);
            }

            return block;
        }

        return new SerializableBlock(worldName, x, y, z, material);
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
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        if (this.material == null) {
            return stringBuilder.toString();
        }
        stringBuilder.append(this.material);
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return cachedHashCode;
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
    public @NonNull Optional<Block> toBlockOptional() {
        return Optional.ofNullable(this.toBlock());
    }


    /**
     * Converts the SerializableBlock back to a Block.
     *
     * @return The Block represented by this SerializableBlock, or {@code null} if the world is not found.
     */
    public @Nullable Block toBlock() {
        if (VoidChestAPI.isFolia() || !Bukkit.isPrimaryThread()) {
            return VoidChestAPI.getInstance().minecraftScheduler().createTaskForLocation(
                    this::toBlock0,
                    this.toLocation()
            ).handle((block, throwable) -> {
                if (throwable != null) {
                    VoidChestAPI.getInstance().plugin().getLogger().log(Level.SEVERE, "Failed to get block " + this, throwable);
                    return null;
                }
                return block;
            }).join();
        }
        return this.toBlock0();
    }

    public CompletableFuture<Block> toBlockAsync() {
        return VoidChestAPI.getInstance().minecraftScheduler().createTaskForLocation(
                this::toBlock0,
                this.toLocation()
        );
    }

    private @Nullable Block toBlock0() {
        World world = Bukkit.getWorld(getWorldName());
        if (world != null) {
            return world.getBlockAt(getBlockX(), getBlockY(), getBlockZ());
        }
        return null;
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
    public @NonNull Optional<Material> getMaterialOptional() {
        return Optional.ofNullable(this.getMaterial());
    }

    /**
     * Returns the material of the block.
     *
     * @return The material of the block, or {@code null} if the block is not found.
     */
    public @Nullable Material getMaterial() {
        if (this.material != null) {
            return this.material;
        }
        Block block = this.toBlock();
        if (block == null) {
            return null;
        }
        return material = block.getType();
    }

    /**
     * Sets the material of the block.
     *
     * @param material The material to set.
     */
    @ApiStatus.Internal
    protected void setMaterial(@Nullable Material material) {
        this.material = material;
    }

    /**
     * Returns the material of the block asynchronously.
     *
     * @return A CompletableFuture that completes with the material of the block, or completes exceptionally if the block is not found.
     */
    public CompletableFuture<Material> getMaterialAsync() {
        return this.toBlockAsync()
                .thenApply(block -> {
                    if (block == null) {
                        throw new IllegalArgumentException("The block is not found.");
                    }
                    return block.getType();
                });
    }

    /**
     * Sets the x-coordinate of the block.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        super.setX(x);
    }

    /**
     * Sets the y-coordinate of the block.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        super.setY(y);
    }

    /**
     * Sets the z-coordinate of the block.
     *
     * @param z The new z-coordinate.
     */
    public void setZ(int z) {
        super.setZ(z);
    }

    @Override
    protected int computeHashCode() {
        int result = super.computeHashCode();
        result = 31 * result + (material != null ? material.hashCode() : 0);
        return result;
    }

    /**
     * Converts the SerializableBlock to a BlockPos.
     *
     * @return The BlockPos represented by this SerializableBlock.
     */
    public BlockPos toBlockPos() {
        return new BlockPos(getWorldName(), getBlockX(), getBlockY(), getBlockZ());
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
