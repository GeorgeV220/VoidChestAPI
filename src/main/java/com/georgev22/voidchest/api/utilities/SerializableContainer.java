package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.utilities.persistence.PersistentHolder;
import com.georgev22.voidchest.api.utilities.persistence.PersistentHolderType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public class SerializableContainer extends SerializableBlock implements Serializable, PersistentHolder {

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
        super(container.getBlockState().getBlock());
        Location location = container.getBlockState().getLocation();
        this.worldName = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public SerializableContainer(@NotNull World world, int x, int y, int z) {
        super(world.getName(), x, y, z);
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
     * @return The SerializableContainer, or throws {@link IllegalArgumentException} if the string is empty or invalid.
     */
    public static @NotNull SerializableContainer fromString(@NotNull String string) {
        SerializableBlock serializableBlock = SerializableBlock.fromString(string);
        Block block = serializableBlock.toBlock();
        if (block == null) {
            throw new IllegalArgumentException("Failed to get block " + serializableBlock + ".");
        }
        if (!ContainerWrapper.isStorageContainer(block.getState())) {
            throw new IllegalArgumentException("The block at this location is not a container.");
        }
        ContainerWrapper containerWrapper = new ContainerWrapper(block.getState());
        return new SerializableContainer(containerWrapper);
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
        } else {
            return CompletableFuture.failedFuture(new IllegalArgumentException("The world is not found."));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return The material of the container, or {@code null} if the container is not found.
     */
    public @Nullable Material getMaterial() {
        if (material == null) {
            ContainerWrapper container = this.toContainer();
            if (container == null) {
                return super.getMaterial();
            }
            return material = container.getBlockState().getType();
        }

        return material;
    }

    @Override
    public PersistentHolderType getType() {
        return PersistentHolderType.BLOCK_ENTITY;
    }

    @Override
    public String getPersistentId() {
        return this.toString();
    }
}
