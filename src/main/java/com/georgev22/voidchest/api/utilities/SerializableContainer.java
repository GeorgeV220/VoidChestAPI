package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.VoidChestAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public class SerializableContainer extends SerializableBlock implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new SerializableContainer from a Container.
     *
     * @param container The Container to be serialized.
     */
    public SerializableContainer(@NonNull ContainerWrapper container) {
        super(container.getBlockState().getBlock());
        Location location = container.getBlockState().getLocation();
        this.worldName = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public SerializableContainer(@NonNull World world, int x, int y, int z) {
        super(world.getBlockAt(x, y, z));
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
    public static @NonNull SerializableContainer fromBlock(@NonNull ContainerWrapper container) {
        return new SerializableContainer(container);
    }

    /**
     * Creates a SerializableContainer from a string representation.
     *
     * @param string The string representation of the container's location.
     * @return A SerializableContainer instance, or {@code null} if the string is empty or invalid.
     */
    public static @NonNull SerializableContainer fromString(@NonNull String string) {
        if (string.trim().isEmpty()) {
            throw new IllegalArgumentException("The string is empty.");
        }
        String[] parts = string.split(":");
        World world = Bukkit.getServer().getWorld(parts[0]);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int z = Integer.parseInt(parts[3]);
        if (world != null) {
            return new SerializableContainer(world, x, y, z);
        } else {
            return new SerializableContainer(Bukkit.getWorlds().getFirst(), x, y, z);
        }
    }

    /**
     * Creates a SerializableContainer from a Location.
     *
     * @param location The Location to be wrapped.
     * @return A new SerializableContainer instance.
     */
    public static @NonNull SerializableContainer fromLocation(@NonNull Location location) throws IllegalArgumentException {
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
    public @NonNull String toString() {
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
            Block block = world.getBlockAt(getBlockX(), getBlockY(), getBlockZ());
            if (ContainerWrapper.isStorageContainer(block.getState())) {
                return new ContainerWrapper(block.getState());
            }
        }
        return null;
    }

    /**
     * Converts the SerializableContainer back to a Container asynchronously.
     *
     * @return A CompletableFuture that completes with the Container represented by this SerializableContainer,
     * or completes exceptionally if the world is not found.
     */
    public @NonNull CompletableFuture<ContainerWrapper> toContainerAsync() {
        return toBlockAsync().thenApply(block -> new ContainerWrapper(block.getState()));
    }

    public @NonNull CompletableFuture<Block> toBlockAsync() {
        return super.toBlockAsync().thenApply(block -> {
            if (ContainerWrapper.isStorageContainer(block.getState())) {
                return block;
            }
            throw new IllegalArgumentException("The block at this location is not a container.");
        });
    }

}
