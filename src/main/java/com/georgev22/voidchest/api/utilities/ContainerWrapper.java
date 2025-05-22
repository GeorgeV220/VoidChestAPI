package com.georgev22.voidchest.api.utilities;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A wrapper around Bukkit container BlockState objects (e.g. chests, barrels),
 * supporting multiple Bukkit versions via reflection.
 */
@SuppressWarnings("ClassCanBeRecord")
public class ContainerWrapper {
    private static final Class<?> CONTAINER_INTERFACE;
    private static final Method GET_INVENTORY_METHOD;

    static {
        CONTAINER_INTERFACE = determineContainerInterface();
        GET_INVENTORY_METHOD = getInventoryMethod();
    }

    private final BlockState blockState;

    /**
     * Constructs a ContainerWrapper for a container-type BlockState.
     *
     * @param blockState the BlockState to wrap
     * @throws IllegalArgumentException if the BlockState is not a container
     */
    public ContainerWrapper(@NotNull BlockState blockState) {
        if (!isContainer(blockState)) {
            throw new IllegalArgumentException("BlockState is not a container");
        }
        this.blockState = blockState;
    }

    /**
     * Returns the live inventory associated with this container.
     *
     * @return Inventory instance
     */
    public Inventory getInventory() {
        try {
            return (Inventory) GET_INVENTORY_METHOD.invoke(CONTAINER_INTERFACE.cast(blockState));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to get inventory from container", e);
        }
    }

    /**
     * Returns a cloned snapshot of the container's inventory.
     * Changes to the returned inventory do not affect the original.
     *
     * @return Cloned inventory snapshot
     */
    @Contract("->new")
    public Inventory getSnapshotInventory() {
        Inventory inventory = getInventory();
        Inventory snapshotInventory = Bukkit.createInventory(inventory.getHolder(), inventory.getSize());
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack != null) {
                snapshotInventory.setItem(i, itemStack.clone());
            }
        }
        return snapshotInventory;
    }

    /**
     * Returns the wrapped BlockState.
     *
     * @return BlockState
     */
    public BlockState getBlockState() {
        return blockState;
    }

    /**
     * Checks if a given BlockState is a container type.
     *
     * @param blockState the BlockState to check
     * @return true if it is a container, false otherwise
     */
    public static boolean isContainer(@NotNull BlockState blockState) {
        return CONTAINER_INTERFACE.isInstance(blockState);
    }

    private static @NotNull Class<?> determineContainerInterface() {
        try {
            return Class.forName("org.bukkit.block.Container");
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName("org.bukkit.block.ContainerBlock");
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException(
                        "Bukkit Container/ContainerBlock not found. Check your API version.", ex
                );
            }
        }
    }

    private static @NotNull Method getInventoryMethod() {
        try {
            return CONTAINER_INTERFACE.getMethod("getInventory");
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(
                    "Container interface missing getInventory method. This should never happen!", e
            );
        }
    }
}
