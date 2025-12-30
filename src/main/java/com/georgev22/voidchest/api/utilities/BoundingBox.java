package com.georgev22.voidchest.api.utilities;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a bounding box in a three-dimensional space defined by minimum and maximum coordinates.
 */
@SerializableAs("VoidChestBoundingBox")
public final class BoundingBox implements Cloneable, ConfigurationSerializable {
    private int minX;
    private int minY;
    private int minZ;
    private int maxX;
    private int maxY;
    private int maxZ;


    /**
     * Constructs a bounding box with integer coordinates.
     *
     * @param minX The minimum X-coordinate.
     * @param minY The minimum Y-coordinate.
     * @param minZ The minimum Z-coordinate.
     * @param maxX The maximum X-coordinate.
     * @param maxY The maximum Y-coordinate.
     * @param maxZ The maximum Z-coordinate.
     */
    public BoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.minZ = Math.min(minZ, maxZ);
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);
        this.maxZ = Math.max(minZ, maxZ);
    }

    /**
     * Constructs a bounding box as a copy of another bounding box.
     *
     * @param other The BoundingBox to copy.
     */
    public BoundingBox(@NonNull BoundingBox other) {
        this.minX = other.minX;
        this.minY = other.minY;
        this.minZ = other.minZ;
        this.maxX = other.maxX;
        this.maxY = other.maxY;
        this.maxZ = other.maxZ;
    }

    /**
     * Constructs a bounding box based on two locations.
     *
     * @param location1 The first corner location of the bounding box.
     * @param location2 The second corner location of the bounding box.
     */
    public BoundingBox(@NonNull SerializableLocation location1, @NonNull SerializableLocation location2) {
        this.minX = (int) Math.min(location1.getX(), location2.getX());
        this.minY = (int) Math.min(location1.getY(), location2.getY());
        this.minZ = (int) Math.min(location1.getZ(), location2.getZ());
        this.maxX = (int) Math.max(location1.getX(), location2.getX());
        this.maxY = (int) Math.max(location1.getY(), location2.getY());
        this.maxZ = (int) Math.max(location1.getZ(), location2.getZ());
    }

    /**
     * Constructs a bounding box based on a center location and a range.
     *
     * @param center The center location of the bounding box.
     * @param minX   The minimum X-coordinate.
     * @param minY   The minimum Y-coordinate.
     * @param minZ   The minimum Z-coordinate.
     * @param maxX   The maximum X-coordinate.
     * @param maxY   The maximum Y-coordinate.
     * @param maxZ   The maximum Z-coordinate.
     */
    public BoundingBox(@NonNull SerializableLocation center, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = (int) (center.getX() - Math.abs(minX));
        this.minY = (int) (center.getY() - Math.abs(minY));
        this.minZ = (int) (center.getZ() - Math.abs(minZ));
        this.maxX = (int) (center.getX() + Math.abs(maxX));
        this.maxY = (int) (center.getY() + Math.abs(maxY));
        this.maxZ = (int) (center.getZ() + Math.abs(maxZ));
    }

    /**
     * Sets the bounding box.
     *
     * @param minX The minimum X-coordinate.
     * @param minY The minimum Y-coordinate.
     * @param minZ The minimum Z-coordinate.
     * @param maxX The maximum X-coordinate.
     * @param maxY The maximum Y-coordinate.
     * @param maxZ The maximum Z-coordinate.
     */
    public void setBoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    /**
     * Sets the bounding box based on a center location and a range.
     *
     * @param center The center location of the bounding box.
     * @param minX   The minimum X-coordinate.
     * @param minY   The minimum Y-coordinate.
     * @param minZ   The minimum Z-coordinate.
     * @param maxX   The maximum X-coordinate.
     * @param maxY   The maximum Y-coordinate.
     * @param maxZ   The maximum Z-coordinate.
     */
    public void setBoundingBox(@NonNull SerializableLocation center, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = (int) (center.getX() - Math.abs(minX));
        this.minY = (int) (center.getY() - Math.abs(minY));
        this.minZ = (int) (center.getZ() - Math.abs(minZ));
        this.maxX = (int) (center.getX() + Math.abs(maxX));
        this.maxY = (int) (center.getY() + Math.abs(maxY));
        this.maxZ = (int) (center.getZ() + Math.abs(maxZ));
    }

    /**
     * Gets the minimum X-coordinate of the bounding box.
     *
     * @return The minimum X-coordinate.
     */
    public int minX() {
        return minX;
    }

    /**
     * Gets the minimum Y-coordinate of the bounding box.
     *
     * @return The minimum Y-coordinate.
     */
    public int minY() {
        return minY;
    }

    /**
     * Gets the minimum Z-coordinate of the bounding box.
     *
     * @return The minimum Z-coordinate.
     */
    public int minZ() {
        return minZ;
    }

    /**
     * Gets the maximum X-coordinate of the bounding box.
     *
     * @return The maximum X-coordinate.
     */
    public int maxX() {
        return maxX;
    }

    /**
     * Gets the maximum Y-coordinate of the bounding box.
     *
     * @return The maximum Y-coordinate.
     */
    public int maxY() {
        return maxY;
    }

    /**
     * Gets the maximum Z-coordinate of the bounding box.
     *
     * @return The maximum Z-coordinate.
     */
    public int maxZ() {
        return maxZ;
    }

    /**
     * Sets the maximum X-coordinate of the bounding box.
     *
     * @param maxX The new maximum X-coordinate.
     */
    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    /**
     * Sets the maximum Y-coordinate of the bounding box.
     *
     * @param maxY The new maximum Y-coordinate.
     */
    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    /**
     * Sets the maximum Z-coordinate of the bounding box.
     *
     * @param maxZ The new maximum Z-coordinate.
     */
    public void setMaxZ(int maxZ) {
        this.maxZ = maxZ;
    }

    /**
     * Sets the minimum X-coordinate of the bounding box.
     *
     * @param minX The new minimum X-coordinate.
     */
    public void setMinX(int minX) {
        this.minX = minX;
    }

    /**
     * Sets the minimum Y-coordinate of the bounding box.
     *
     * @param minY The new minimum Y-coordinate.
     */
    public void setMinY(int minY) {
        this.minY = minY;
    }

    /**
     * Sets the minimum Z-coordinate of the bounding box.
     *
     * @param minZ The new minimum Z-coordinate.
     */
    public void setMinZ(int minZ) {
        this.minZ = minZ;
    }

    /**
     * Checks if the specified X, Y, Z coordinates are inside the bounding box.
     *
     * @param x The X-coordinate to check.
     * @param y The Y-coordinate to check.
     * @param z The Z-coordinate to check.
     * @return True if the coordinates are inside the bounding box, false otherwise.
     */
    public boolean isInside(int x, int y, int z) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    /**
     * Checks if the specified location is inside the bounding box.
     *
     * @param location The location to check.
     * @return True if the location is inside the bounding box, false otherwise.
     */
    public boolean isInside(@NonNull SerializableLocation location) {
        return isInside((int) location.getX(), (int) location.getY(), (int) location.getZ());
    }

    /**
     * Checks if the specified bounding box is inside this bounding box.
     *
     * @param other The bounding box to check.
     * @return True if the bounding box is inside this bounding box, false otherwise.
     */
    @Contract(pure = true)
    public boolean isInside(@NonNull BoundingBox other) {
        return minX <= other.minX && maxX >= other.maxX && minY <= other.minY && maxY >= other.maxY && minZ <= other.minZ && maxZ >= other.maxZ;
    }

    /**
     * Checks if the given object is equal to this BoundingBox object.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BoundingBox) obj;
        return this.minX == that.minX &&
                this.minY == that.minY &&
                this.minZ == that.minZ &&
                this.maxX == that.maxX &&
                this.maxY == that.maxY &&
                this.maxZ == that.maxZ;
    }

    /**
     * Override the default hashCode method to generate a hash code based on the
     * minX, minY, minZ, maxX, maxY, and maxZ values.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Returns a string representation of this BoundingBox object.
     *
     * @return a string representation of this BoundingBox object
     */
    @Override
    public String toString() {
        return "BoundingBox[" +
                "minX=" + minX + ", " +
                "minY=" + minY + ", " +
                "minZ=" + minZ + ", " +
                "maxX=" + maxX + ", " +
                "maxY=" + maxY + ", " +
                "maxZ=" + maxZ + ']';
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public BoundingBox clone() {
        return new BoundingBox(this);
    }

    /**
     * Creates a Map representation of this class.
     * <p>
     * This class must provide a method to restore this class, as defined in
     * the {@link ConfigurationSerializable} interface javadocs.
     *
     * @return Map containing the current state of this class
     */
    @Override
    public @NonNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("minX", minX);
        data.put("minY", minY);
        data.put("minZ", minZ);
        data.put("maxX", maxX);
        data.put("maxY", maxY);
        data.put("maxZ", maxZ);
        return data;
    }

    /**
     * Creates a BoundingBox object from serialized data in a Map.
     *
     * @param data The Map containing the serialized data.
     * @return A BoundingBox object created from the provided serialized data.
     * @throws IllegalArgumentException If the provided data is null or incomplete.
     */
    public static @NonNull BoundingBox deserialize(Map<String, Object> data) throws IllegalArgumentException {
        return valueOf(data);
    }

    /**
     * Creates a BoundingBox object from serialized data in a Map.
     *
     * @param data The Map containing the serialized data.
     * @return A BoundingBox object created from the provided serialized data.
     * @throws IllegalArgumentException If the provided data is null or incomplete.
     */
    public static @NonNull BoundingBox valueOf(Map<String, Object> data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (!data.containsKey("minX") || !data.containsKey("minY") || !data.containsKey("minZ")
                || !data.containsKey("maxX") || !data.containsKey("maxY") || !data.containsKey("maxZ")) {
            throw new IllegalArgumentException("Data must contain minX, minY, minZ, maxX, maxY, and maxZ");
        }
        int minX = (int) data.get("minX");
        int minY = (int) data.get("minY");
        int minZ = (int) data.get("minZ");
        int maxX = (int) data.get("maxX");
        int maxY = (int) data.get("maxY");
        int maxZ = (int) data.get("maxZ");
        return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

}
