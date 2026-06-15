package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.VoidChestAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
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
 *     <li>{@code public SerializableBlock(@NotNull Block block)} - Constructs a new SerializableBlock from a Block.</li>
 *     <li>{@code public static @NotNull SerializableBlock fromBlock(@NotNull Block block)} - Creates a new SerializableBlock from a Block.</li>
 *     <li>{@code public @NotNull String toString()} - Converts the SerializableBlock to a string representation.</li>
 *     <li>{@code public static @Nullable SerializableBlock fromString(@NotNull String string)} - Creates a SerializableBlock from a string representation.</li>
 *     <li>{@code public static @NotNull SerializableBlock fromLocation(@NotNull Location location)} - Creates a SerializableBlock from a Location.</li>
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

    protected @Nullable Material material;

    private transient final int cachedHashCode;

    /**
     * Constructs a new SerializableBlock from a Block.
     *
     * @param block The Block to be serialized.
     */
    public SerializableBlock(@NotNull Block block) {
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
    public static @NotNull SerializableBlock fromBlock(@NotNull Block block) {
        return new SerializableBlock(block);
    }

    @Contract("_ -> new")
    public static @NotNull SerializableBlock fromLocation(@NotNull SerializableLocation location) {
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
    public static @NotNull SerializableBlock fromString(@NotNull String string) {
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

    private static @NotNull SerializableBlock fromLegacy(@NotNull String[] parts) {
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
    public static @NotNull SerializableBlock fromLocation(@NotNull Location location) {
        return new SerializableBlock(location.getBlock());
    }

    /**
     * Converts the SerializableBlock to a string representation.
     *
     * @return A string representation of the block's location.
     */
    public @NotNull String toString() {
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
     * Converts the SerializableBlock back to a Block.
     *
     * @return The Block represented by this SerializableBlock, or {@code null} if the world is not found.
     */
    public @Nullable Block toBlock() {
        if (VoidChestAPI.isFolia() || !Bukkit.isPrimaryThread()) {
            return VoidChestAPI.getInstance().minecraftScheduler().createTaskForLocation(
                    VoidChestAPI.getInstance().plugin(),
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
                VoidChestAPI.getInstance().plugin(),
                this::toBlock0,
                this.toLocation()
        );
    }

    private @Nullable Block toBlock0() {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            return world.getBlockAt(getBlockX(), getBlockY(), getBlockZ());
        }
        return null;
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
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the block.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the z-coordinate of the block.
     *
     * @param z The new z-coordinate.
     */
    public void setZ(int z) {
        this.z = z;
    }

    protected int computeHashCode() {
        int result = super.computeHashCode();
        result = 31 * result + (material != null ? material.hashCode() : 0);
        return result;
    }
}
