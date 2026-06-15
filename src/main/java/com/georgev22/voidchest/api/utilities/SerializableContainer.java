package com.georgev22.voidchest.api.utilities;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SerializableContainer extends SerializableBlock implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new SerializableContainer from a Container.
     *
     * @param container The Container to be serialized.
     */
    public SerializableContainer(@NonNull Container container) {
        super(container.getBlock());
    }

    public SerializableContainer(@NonNull World world, int x, int y, int z) {
        super(world.getName(), x, y, z);
    }

    /**
     * Creates a new SerializableContainer from a Container.
     *
     * @param container The Container to be wrapped.
     * @return A new SerializableContainer instance.
     */
    @Contract("_ -> new")
    public static @NonNull SerializableContainer fromBlock(@NonNull Container container) {
        return new SerializableContainer(container);
    }

    /**
     * Creates a SerializableContainer from a string representation.
     *
     * @param string The string representation of the container's location.
     * @return The SerializableContainer, or throws {@link IllegalArgumentException} if the string is empty or invalid.
     */
    public static @NonNull SerializableContainer fromString(@NonNull String string) {
        SerializableBlock serializableBlock = SerializableBlock.fromString(string);
        Block block = serializableBlock.toBlock();
        if (block == null) {
            throw new IllegalArgumentException("Failed to get block " + serializableBlock + ".");
        }
        if (!(block.getState() instanceof Container container)) {
            throw new IllegalArgumentException("The block at this location is not a container.");
        }
        return new SerializableContainer(container);
    }

    /**
     * Creates a SerializableContainer from a Location.
     *
     * @param location The Location to be wrapped.
     * @return A new SerializableContainer instance.
     */
    public static @NonNull SerializableContainer fromLocation(@NonNull Location location) throws IllegalArgumentException {
        Block block = location.getBlock();
        if (block.getState() instanceof Container container) {
            return new SerializableContainer(container);
        }
        throw new IllegalArgumentException("The block at this location is not a container.");
    }

    /**
     * Converts the SerializableContainer back to a Container.
     *
     * @return The Container represented by this SerializableContainer, or {@code null} if the block is not found or is not a container.
     */
    public @Nullable Container toContainer() {
        Block block = toBlock();
        if (block == null) {
            return null;
        }
        if (block.getState() instanceof Container container) {
            return container;
        }
        return null;
    }

    /**
     * Converts the SerializableContainer back to a Container asynchronously.
     *
     * @return A CompletableFuture that completes with the Container represented by this SerializableContainer,
     * or completes exceptionally if the block is not found or is not a container.
     */
    public @NonNull CompletableFuture<Container> toContainerAsync() {
        return toBlockAsync()
                .thenApply(block -> {
                    if (block.getState() instanceof Container container) {
                        return container;
                    } else {
                        throw new CompletionException(new IllegalArgumentException("The block at this location is not a container."));
                    }
                });
    }
}
