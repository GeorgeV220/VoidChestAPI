package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.VoidChestAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public class SerializableContainer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The name of the world containing the container.
     */
    private String worldName;

    /**
     * The x-coordinate of the container.
     */
    private int x;

    /**
     * The y-coordinate of the container.
     */
    private int y;

    /**
     * The z-coordinate of the container.
     */
    private int z;

    /**
     * Constructs a new SerializableContainer from a Container.
     *
     * @param container The Container to be serialized.
     */
    public SerializableContainer(@NotNull ContainerWrapper container) {
        Location location = container.getBlockState().getLocation();
        this.worldName = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public SerializableContainer(@NotNull World world, int x, int y, int z) {
        this.worldName = world.getName();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new SerializableContainer from a Container.
     *
     * @param container The Container to be wrapped.
     * @return A new SerializableContainer instance.
     */
    @Contract("_ -> new")
    public static @NotNull SerializableContainer fromBlock(@NotNull ContainerWrapper container) {
        return new SerializableContainer(container);
    }

    /**
     * Creates a SerializableContainer from a string representation.
     *
     * @param string The string representation of the container's location.
     * @return A SerializableContainer instance, or {@code null} if the string is empty or invalid.
     */
    public static @Nullable SerializableContainer fromString(@NotNull String string) {
        if (string.trim().isEmpty()) {
            return null;
        }
        String[] parts = string.split(":");
        World world = Bukkit.getServer().getWorld(parts[0]);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int z = Integer.parseInt(parts[3]);
        if (world != null) {
            return new SerializableContainer(world, x, y, z);
        }
        return null;
    }

    /**
     * Creates a SerializableContainer from a Location.
     *
     * @param location The Location to be wrapped.
     * @return A new SerializableContainer instance.
     */
    public static @NotNull SerializableContainer fromLocation(@NotNull Location location) throws IllegalArgumentException {
        Block block = location.getBlock();
        if (ContainerWrapper.isStorageContainer(block.getState())) {
            ContainerWrapper container = new ContainerWrapper(block.getState());
            return new SerializableContainer(container);
        }
        throw new IllegalArgumentException("The block at this location is not a container.");
    }

    /**
     * Converts the SerializableContainer to a string representation.
     *
     * @return A string representation of the container's location.
     */
    public @Nullable String toString() {
        return this.worldName +
                ":" +
                this.x +
                ":" +
                this.y +
                ":" +
                this.z;
    }

    /**
     * Converts the SerializableContainer back to a Container.
     *
     * @return The Container represented by this SerializableContainer, or {@code null} if the world is not found.
     */
    public @Nullable ContainerWrapper toContainer() {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Block block = world.getBlockAt(x, y, z);
            if (ContainerWrapper.isStorageContainer(block.getState())) {
                return new ContainerWrapper(block.getState());
            }
        }
        return null;
    }

    /**
     * Converts the SerializableContainer back to a Container asynchronously.
     *
     * @return A CompletableFuture that completes with the Container represented by this SerializableContainer, or completes exceptionally if the world is not found.
     */
    public @NotNull CompletableFuture<ContainerWrapper> toContainerAsync() {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            return VoidChestAPI.getInstance().minecraftScheduler()
                    .createTaskForLocation(
                            VoidChestAPI.getInstance().plugin(),
                            () -> {
                                Block block = world.getBlockAt(x, y, z);
                                if (ContainerWrapper.isStorageContainer(block.getState())) {
                                    return new ContainerWrapper(block.getState());
                                } else {
                                    throw new IllegalArgumentException("The block at this location is not a container.");
                                }
                            },
                            new Location(world, x, y, z)
                    );
            //return future;
        } else {
            return CompletableFuture.failedFuture(new IllegalArgumentException("The world is not found."));
        }
    }

    /**
     * Returns the material of the container.
     *
     * @return The material of the container, or {@code null} if the container is not found.
     */
    public @Nullable Material getMaterial() {
        ContainerWrapper container = this.toContainer();
        if (container == null) {
            return null;
        }
        return container.getBlockState().getType();
    }

    /**
     * Returns the material of the container asynchronously.
     *
     * @return A CompletableFuture that completes with the material of the container, or completes exceptionally if the container is not found.
     */
    public CompletableFuture<Material> getMaterialAsync() {
        return this.toContainerAsync()
                .thenApply(container -> {
                    if (container == null) {
                        throw new IllegalArgumentException("The container is not found.");
                    }
                    return container.getBlockState().getType();
                });
    }

    /**
     * Returns the x-coordinate of the container.
     *
     * @return The x-coordinate of the container.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the container.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the container.
     *
     * @return The y-coordinate of the container.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the container.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the z-coordinate of the container.
     *
     * @return The z-coordinate of the container.
     */
    public int getZ() {
        return z;
    }

    /**
     * Sets the z-coordinate of the container.
     *
     * @param z The new z-coordinate.
     */
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * Returns the name of the world containing the container.
     *
     * @return The name of the world containing the container.
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     * Sets the name of the world containing the container.
     *
     * @param worldName The new name of the world.
     */
    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    /**
     * Converts the SerializableContainer to a Bukkit Location.
     *
     * @return A Bukkit Location representing the container's location, or {@code null} if the world is not found.
     */
    @UnknownNullability
    public Location toBukkitLocation() {
        if (Bukkit.getWorld(worldName) == null) {
            return null;
        }
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }
}
