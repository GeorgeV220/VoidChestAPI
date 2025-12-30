package com.georgev22.voidchest.api.utilities;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Nameable;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A wrapper around Bukkit container BlockState objects (e.g. chests, barrels).
 * Modern-only implementation using the directly available APIs.
 */
public class ContainerWrapper {

    private final Container container;

    /**
     * Constructs a ContainerWrapper for a container-type BlockState.
     *
     * @param blockState the BlockState to wrap
     * @throws IllegalArgumentException if the BlockState is not a container
     */
    public ContainerWrapper(@NotNull BlockState blockState) {
        if (!(blockState instanceof Container c)) {
            throw new IllegalArgumentException("BlockState is not a valid storage container");
        }
        this.container = c;
    }

    /**
     * Returns the live inventory associated with this container.
     *
     * @return Inventory instance
     */
    public Inventory getInventory() {
        return container.getInventory();
    }

    /**
     * Returns a cloned snapshot of the container's inventory.
     * Changes to the returned inventory do not affect the original.
     *
     * @return Cloned inventory snapshot
     */
    @Contract("->new")
    public Inventory getSnapshotInventory() {
        Inventory inventory = container.getInventory();
        Inventory snapshot = Bukkit.createInventory(inventory.getHolder(), inventory.getSize());

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null) {
                snapshot.setItem(i, item.clone());
            }
        }
        return snapshot;
    }

    /**
     * Returns the wrapped BlockState.
     */
    public BlockState getBlockState() {
        return container;
    }

    /**
     * Sets a custom name for this container if supported.
     *
     * @param name The name to set, or null to remove custom name.
     */
    public static void setCustomName(@NotNull BlockState blockState, @Nullable String name) {
        if (!(blockState instanceof Nameable nameable)) {
            throw new UnsupportedOperationException("BlockState does not support custom names");
        }

        //noinspection deprecation
        nameable.setCustomName(name);
        blockState.update();
    }

    public static void setCustomName(@NotNull BlockState blockState, @Nullable Component component) {
        if (!(blockState instanceof Nameable nameable)) {
            throw new UnsupportedOperationException("BlockState does not support custom names");
        }

        nameable.customName(component);
        blockState.update();
    }

    /**
     * Checks if a given BlockState is a valid storage container type.
     *
     * @param blockState the BlockState to check
     * @return true if it is a valid storage container, false otherwise
     */
    public static boolean isStorageContainer(@NotNull BlockState blockState) {
        return blockState instanceof Container;
    }
}
