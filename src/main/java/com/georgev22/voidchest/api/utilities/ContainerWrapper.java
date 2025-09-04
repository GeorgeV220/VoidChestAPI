package com.georgev22.voidchest.api.utilities;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Set;

/**
 * A wrapper around Bukkit container BlockState objects (e.g. chests, barrels),
 * supporting multiple Bukkit versions via reflection.
 */
@SuppressWarnings("ClassCanBeRecord")
public class ContainerWrapper {
    private static final Class<?> CONTAINER_INTERFACE;
    private static final Method GET_INVENTORY_METHOD;
    private static final Set<InventoryType> VALID_STORAGE_TYPES = buildValidStorageTypes();
    private static final Class<?> NAMEABLE_INTERFACE = determineNameableInterface();
    private static final Method SET_CUSTOM_NAME_METHOD = getSetCustomNameMethod();

    static {
        CONTAINER_INTERFACE = determineContainerInterface();
        GET_INVENTORY_METHOD = getInventoryMethod();
    }

    private final BlockState blockState;

    /**
     * Constructs a ContainerWrapper for a container-type BlockState.
     *
     * @param blockState the BlockState to wrap
     * @throws IllegalArgumentException if the BlockState is not a valid storage container
     */
    public ContainerWrapper(@NotNull BlockState blockState) {
        if (!isStorageContainer(blockState)) {
            throw new IllegalArgumentException("BlockState is not a valid storage container");
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
     * Sets a custom name for this container if supported.
     *
     * @param name The name to set, or null to clear the custom name.
     * @throws UnsupportedOperationException if Nameable is not supported in this Bukkit version.
     */
    public static void setCustomName(BlockState blockState, @Nullable String name) {
        if (NAMEABLE_INTERFACE == null || SET_CUSTOM_NAME_METHOD == null) {
            throw new UnsupportedOperationException("Custom names are not supported in this Bukkit version.");
        }

        if (!NAMEABLE_INTERFACE.isInstance(blockState)) {
            throw new IllegalStateException("BlockState does not implement Nameable.");
        }

        try {
            SET_CUSTOM_NAME_METHOD.invoke(NAMEABLE_INTERFACE.cast(blockState), name);
            blockState.update();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to set custom name on container", e);
        }
    }

    /**
     * Checks if a given BlockState is a valid storage container type.
     *
     * @param blockState the BlockState to check
     * @return true if it is a valid storage container, false otherwise
     */
    public static boolean isStorageContainer(@NotNull BlockState blockState) {
        if (!CONTAINER_INTERFACE.isInstance(blockState)) {
            return false;
        }
        try {
            Inventory inventory = (Inventory) GET_INVENTORY_METHOD.invoke(CONTAINER_INTERFACE.cast(blockState));
            return inventory != null && VALID_STORAGE_TYPES.contains(inventory.getType());
        } catch (IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }

    private static @NotNull Class<?> determineContainerInterface() {
        try {
            return Class.forName("org.bukkit.block.Container");
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName("org.bukkit.block.ContainerBlock");
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException("Neither Container nor ContainerBlock exist in this Bukkit version.");
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


    private static @NotNull Set<InventoryType> buildValidStorageTypes() {
        EnumSet<InventoryType> types = EnumSet.noneOf(InventoryType.class);
        addIfExists(types, "CHEST");
        addIfExists(types, "DISPENSER");
        addIfExists(types, "DROPPER");
        addIfExists(types, "HOPPER");
        addIfExists(types, "FURNACE");
        addIfExists(types, "BLAST_FURNACE");
        addIfExists(types, "SMOKER");
        addIfExists(types, "BARREL");
        addIfExists(types, "SHULKER_BOX");
        return types;
    }

    private static void addIfExists(@NotNull Set<InventoryType> types, String name) {
        try {
            InventoryType type = InventoryType.valueOf(name);
            types.add(type);
        } catch (IllegalArgumentException ignored) {
        }
    }

    private static @Nullable Class<?> determineNameableInterface() {
        try {
            return Class.forName("org.bukkit.Nameable");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static @Nullable Method getSetCustomNameMethod() {
        if (NAMEABLE_INTERFACE == null) {
            return null;
        }
        try {
            return NAMEABLE_INTERFACE.getMethod("setCustomName", String.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Nameable interface exists but setCustomName method is missing.", e);
        }
    }
}
