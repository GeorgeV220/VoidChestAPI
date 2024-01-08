package com.georgev22.voidchest.api.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

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
public class SerializableLocation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private transient Location location;
    private final String worldName;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    /**
     * Constructs a new SerializableLocation from the provided Bukkit Location.
     *
     * @param location The Bukkit Location to be serialized.
     */
    public SerializableLocation(@NotNull Location location) {
        this.location = location;
        this.worldName = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
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
    }

    /**
     * Creates a new SerializableLocation from a Bukkit Location.
     *
     * @param location The Bukkit Location to be serialized.
     * @return A new SerializableLocation instance.
     */
    @Contract("_ -> new")
    public static @NotNull SerializableLocation fromLocation(Location location) {
        return new SerializableLocation(location);
    }

    /**
     * Returns a string representation of the SerializableLocation.
     * The format is "world:x:y:z:pitch:yaw".
     *
     * @return A string representation of the SerializableLocation.
     */
    public @NotNull String toString() {
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
                .append(this.yaw);

        return stringBuilder.toString();
    }

    /**
     * Creates a SerializableLocation from a string representation.
     *
     * @param string The string representation of the location.
     * @return The SerializableLocation, or {@code null} if the string is empty or invalid.
     */
    public static @Nullable SerializableLocation fromString(@NotNull String string) {
        if (string.trim().isEmpty()) {
            return null;
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
            return new SerializableLocation(worldName, x, y, z, pitch, yaw);
        } else {
            return new SerializableLocation(new Location(world, x, y, z, yaw, pitch));
        }
    }

    /**
     * Converts the SerializableLocation back to a Bukkit Location.
     *
     * @return The Bukkit Location represented by this SerializableLocation.
     */
    public @Nullable Location toLocation() {
        if (location == null) {
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                location = new Location(world, x, y, z, yaw, pitch);
            }
        }
        return location;
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
     * Custom serialization method to write the object to an ObjectOutputStream.
     *
     * @param out The ObjectOutputStream to write to.
     * @throws IOException If an I/O error occurs during serialization.
     */
    @Serial
    private void writeObject(@NotNull ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * Custom deserialization method to read the object from an ObjectInputStream.
     *
     * @param in The ObjectInputStream to read from.
     * @throws IOException            If an I/O error occurs during deserialization.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    @Serial
    private void readObject(@NotNull ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        toLocation();
    }
}
