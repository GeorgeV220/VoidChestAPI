package com.georgev22.voidchest.api.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;


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
public class SerializableBlock implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The name of the world containing the block.
     */
    private String worldName;

    /**
     * The x-coordinate of the block.
     */
    private int x;

    /**
     * The y-coordinate of the block.
     */
    private int y;

    /**
     * The z-coordinate of the block.
     */
    private int z;

    /**
     * Constructs a new SerializableBlock from a Block.
     *
     * @param block The Block to be serialized.
     */
    public SerializableBlock(@NotNull Block block) {
        Location location = block.getLocation();
        this.worldName = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
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

    /**
     * Converts the SerializableBlock to a string representation.
     *
     * @return A string representation of the block's location.
     */
    public @NotNull String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Block block = this.toBlock();
        Location location = block.getLocation().clone();
        stringBuilder
                .append(location.getWorld().getName())
                .append(":")
                .append(location.getBlockX())
                .append(":")
                .append(location.getBlockY())
                .append(":")
                .append(location.getBlockZ());

        return stringBuilder.toString();
    }

    /**
     * Creates a SerializableBlock from a string representation.
     *
     * @param string The string representation of the block's location.
     * @return A SerializableBlock instance, or {@code null} if the string is empty or invalid.
     */
    public static @Nullable SerializableBlock fromString(@NotNull String string) {
        if (string.trim().isEmpty()) {
            return null;
        }
        String[] parts = string.split(":");
        World world = Bukkit.getServer().getWorld(parts[0]);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int z = Integer.parseInt(parts[3]);
        if (world != null) {
            return new SerializableBlock(world.getBlockAt(x, y, z));
        }
        return null;
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
     * Converts the SerializableBlock back to a Block.
     *
     * @return The Block represented by this SerializableBlock, or {@code null} if the world is not found.
     */
    public Block toBlock() {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            return world.getBlockAt(x, y, z);
        }
        return null;
    }

    /**
     * Returns the x-coordinate of the block.
     *
     * @return The x-coordinate of the block.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the block.
     *
     * @return The y-coordinate of the block.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the z-coordinate of the block.
     *
     * @return The z-coordinate of the block.
     */
    public int getZ() {
        return z;
    }

    /**
     * Returns the name of the world containing the block.
     *
     * @return The name of the world containing the block.
     */
    public String getWorldName() {
        return worldName;
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

    /**
     * Sets the name of the world containing the block.
     *
     * @param worldName The new name of the world.
     */
    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }
}
