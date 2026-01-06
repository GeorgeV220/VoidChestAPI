package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.utilities.BukkitMinecraftUtils.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.io.*;
import java.util.Objects;

/**
 * A serializable representation of a Bukkit Location, allowing for easy storage and retrieval of location data.
 *
 * <p>The {@code SerializableLocation} class facilitates the conversion of Bukkit {@link org.bukkit.Location} objects
 * into a serializable format, enabling storage and retrieval of location information. It provides methods for creating
 * a SerializableLocation from a Bukkit Location, converting it back to a Location, and serializing/deserializing to/from strings.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * // Creating a SerializableLocation from a Bukkit Location
 * Location originalLocation = new Location(Bukkit.getWorld("world"), 0, 64, 0);
 * SerializableLocation serializableLocation = SerializableLocation.fromLocation(originalLocation);
 *
 * // Converting back to a Bukkit Location
 * Location deserializedLocation = serializableLocation.toLocation();
 *
 * // Converting to and from string representation
 * String locationString = serializableLocation.toString();
 * SerializableLocation fromString = SerializableLocation.fromString(locationString);
 * }</pre>
 * </p>
 *
 * <p>It is recommended to handle potential exceptions during serialization and deserialization processes
 * to ensure proper error reporting and user feedback.
 * </p>
 */
public class SerializableLocation implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 2L;

    private transient Location location;
    protected String worldName;
    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;
    protected int minY;
    protected int maxY;
    protected VoidChunk chunk;

    protected transient final int cachedHashCode;

    /**
     * Constructs a new SerializableLocation from the provided Bukkit Location.
     *
     * @param location The Bukkit Location to be serialized.
     */
    public SerializableLocation(@NonNull Location location) {
        this.location = location;
        this.worldName = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.minY = MinecraftVersion.getCurrentVersion().isAboveOrEqual(MinecraftVersion.V1_17_R1) ? location.getWorld().getMinHeight() : 0;
        this.maxY = location.getWorld().getMaxHeight();
        this.chunk = new VoidChunk(worldName, (int) getX() >> 4, (int) getZ() >> 4);
        this.cachedHashCode = this.computeHashCode();
    }

    /**
     * Constructs a new SerializableLocation with explicit values.
     *
     * @param worldName The name of the world.
     * @param x         The x-coordinate.
     * @param y         The y-coordinate.
     * @param z         The z-coordinate.
     * @param yaw       The yaw angle.
     * @param pitch     The pitch angle.
     */
    public SerializableLocation(String worldName, double x, double y, double z, float yaw, float pitch) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.minY = 0;
        this.maxY = 256;
        this.chunk = new VoidChunk(worldName, (int) getX() >> 4, (int) getZ() >> 4);
        this.cachedHashCode = this.computeHashCode();
    }

    /**
     * Constructs a new SerializableLocation with explicit values.
     *
     * @param worldName The name of the world.
     * @param x         The x-coordinate.
     * @param y         The y-coordinate.
     * @param z         The z-coordinate.
     * @param yaw       The yaw angle.
     * @param pitch     The pitch angle.
     * @param minY      The minimum y-coordinate.
     * @param maxY      The maximum y-coordinate.
     */
    public SerializableLocation(String worldName, double x, double y, double z, float yaw, float pitch, int minY, int maxY) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.minY = minY;
        this.maxY = maxY;
        this.chunk = new VoidChunk(worldName, (int) getX() >> 4, (int) getZ() >> 4);
        this.cachedHashCode = this.computeHashCode();
    }

    /**
     * Constructs a new SerializableLocation with explicit values.
     *
     * @param worldName The name of the world.
     * @param x         The x-coordinate.
     * @param y         The y-coordinate.
     * @param z         The z-coordinate.
     * @param yaw       The yaw angle.
     * @param pitch     The pitch angle.
     * @param minY      The minimum y-coordinate.
     * @param maxY      The maximum y-coordinate.
     * @param chunkX    The x-coordinate of the chunk.
     * @param chunkZ    The z-coordinate of the chunk.
     */
    public SerializableLocation(String worldName, double x, double y, double z, float yaw, float pitch, int minY, int maxY, int chunkX, int chunkZ) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.minY = minY;
        this.maxY = maxY;
        this.chunk = new VoidChunk(worldName, chunkX, chunkZ);
        this.cachedHashCode = this.computeHashCode();
    }

    /**
     * Creates a new SerializableLocation from a Bukkit Location.
     *
     * @param location The Bukkit Location to be serialized.
     * @return A new SerializableLocation instance.
     */
    @Contract("_ -> new")
    public static @NonNull SerializableLocation fromLocation(Location location) {
        return new SerializableLocation(location);
    }

    /**
     * Returns a string representation of the SerializableLocation.
     * The format is "world:x:y:z:pitch:yaw".
     *
     * @return A string representation of the SerializableLocation.
     */
    public @NonNull String toString() {
        //noinspection StringBufferReplaceableByString
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(this.worldName)
                .append(":")
                .append(this.x)
                .append(":")
                .append(this.y)
                .append(":")
                .append(this.z)
                .append(":")
                .append(this.pitch)
                .append(":")
                .append(this.yaw)
                .append(":")
                .append(this.minY)
                .append(":")
                .append(this.maxY)
                .append(":")
                .append(this.chunk.getX())
                .append(":")
                .append(this.chunk.getZ());

        return stringBuilder.toString();
    }

    /**
     * Creates a SerializableLocation from a string representation.
     *
     * @param string The string representation of the location.
     * @return The SerializableLocation, or {@code null} if the string is empty or invalid.
     */
    public static @NonNull SerializableLocation fromString(@NonNull String string) {
        if (string.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid location string: " + string);
        }
        String[] parts = string.split(":");
        String worldName = parts[0];
        World world = Bukkit.getServer().getWorld(worldName);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float pitch = Float.parseFloat(parts[4]);
        float yaw = Float.parseFloat(parts[5]);
        if (world == null) {
            if (parts.length > 7) {
                int minY = Integer.parseInt(parts[6]);
                int maxY = Integer.parseInt(parts[7]);
                if (parts.length == 10) {
                    int chunkX = Integer.parseInt(parts[8]);
                    int chunkZ = Integer.parseInt(parts[9]);
                    return new SerializableLocation(worldName, x, y, z, yaw, pitch, minY, maxY, chunkX, chunkZ);
                }
                return new SerializableLocation(worldName, x, y, z, yaw, pitch, minY, maxY);
            }
        } else {
            return new SerializableLocation(new Location(world, x, y, z, yaw, pitch));
        }

        return new SerializableLocation(worldName, x, y, z, yaw, pitch);
    }

    /**
     * Converts the SerializableLocation back to a Bukkit Location.
     *
     * @return The Bukkit Location represented by this SerializableLocation.
     */
    public @NonNull Location toLocation() {
        if (location == null) {
            World world = Bukkit.getWorld(worldName);
            location = world != null
                    ? new Location(world, x, y, z, yaw, pitch)
                    : new Location(Bukkit.getWorlds().getFirst(), x, y, z, yaw, pitch);
        }
        return location.clone();
    }

    /**
     * Gets the name of the world.
     *
     * @return The world name.
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     * Gets the x-coordinate.
     *
     * @return The x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate.
     *
     * @return The y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the minimum y-coordinate.
     *
     * @return The minimum y-coordinate.
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Gets the maximum y-coordinate
     *
     * @return The maximum y-coordinate
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Gets the bounding box of the location.
     *
     * @return The bounding box of the location.
     */
    public BoundingBox getBoundingBox() {
        int minChunkX = this.chunk.getX() * 16;
        int minChunkZ = this.chunk.getZ() * 16;
        int maxChunkX = minChunkX + 15;
        int maxChunkZ = minChunkZ + 15;

        if (this.chunk.getX() < 0) {
            minChunkX++;
            maxChunkX++;
        }
        if (this.chunk.getZ() < 0) {
            minChunkZ++;
            maxChunkZ++;
        }
        return new BoundingBox(minChunkX, this.minY, minChunkZ, maxChunkX, this.maxY, maxChunkZ);
    }

    /**
     * Gets the z-coordinate.
     *
     * @return The z-coordinate.
     */
    public double getZ() {
        return z;
    }

    /**
     * Gets the pitch angle.
     *
     * @return The pitch angle.
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Gets the yaw angle.
     *
     * @return The yaw angle.
     */
    public float getYaw() {
        return yaw;
    }

    /**
     * Gets the block x-coordinate.
     *
     * @return The block x-coordinate.
     */
    public int getBlockX() {
        return Utils.floor(x);
    }

    /**
     * Gets the block y-coordinate.
     *
     * @return The block y-coordinate.
     */
    public int getBlockY() {
        return Utils.floor(y);
    }

    /**
     * Gets the block z-coordinate.
     *
     * @return The block z-coordinate.
     */
    public int getBlockZ() {
        return Utils.floor(z);
    }

    /**
     * Gets the chunk.
     *
     * @return The chunk.
     */
    public VoidChunk getChunk() {
        return chunk;
    }

    @Override
    public SerializableLocation clone() {
        try {
            return (SerializableLocation) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    /**
     * Custom serialization method to write the object to an ObjectOutputStream.
     *
     * @param out The ObjectOutputStream to write to.
     * @throws IOException If an I/O error occurs during serialization.
     */
    @Serial
    private void writeObject(@NonNull ObjectOutputStream out) throws IOException {
        out.writeUTF(toString());
    }

    /**
     * Custom deserialization method to read the object from an ObjectInputStream.
     *
     * @param in The ObjectInputStream to read from.
     * @throws IOException            If an I/O error occurs during deserialization.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    @Serial
    private void readObject(@NonNull ObjectInputStream in) throws IOException, ClassNotFoundException {
        String string = in.readUTF();
        SerializableLocation location = fromString(string);
        this.worldName = location.worldName;
        this.x = location.x;
        this.y = location.y;
        this.z = location.z;
        this.pitch = location.pitch;
        this.yaw = location.yaw;
        this.minY = location.minY;
        this.maxY = location.maxY;
        this.chunk = location.chunk;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SerializableLocation that)) return false;
        return Double.compare(x, that.x) == 0
                && Double.compare(y, that.y) == 0
                && Double.compare(z, that.z) == 0
                && Float.compare(yaw, that.yaw) == 0
                && Float.compare(pitch, that.pitch) == 0
                && minY == that.minY
                && maxY == that.maxY
                && Objects.equals(chunk, that.chunk)
                && Objects.equals(worldName, that.worldName);
    }

    @Override
    public int hashCode() {
        return cachedHashCode;
    }

    private int computeHashCode() {
        int result = worldName != null ? worldName.hashCode() : 0;

        result = 31 * result + Double.hashCode(x);
        result = 31 * result + Double.hashCode(y);
        result = 31 * result + Double.hashCode(z);

        result = 31 * result + Float.hashCode(yaw);
        result = 31 * result + Float.hashCode(pitch);
        result = 31 * result + minY;
        result = 31 * result + maxY;
        result = 31 * result + (chunk != null ? chunk.hashCode() : 0);

        return result;
    }
}
