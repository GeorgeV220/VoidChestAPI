package com.georgev22.voidchest.api.utilities.persistence;

import com.georgev22.voidchest.api.utilities.BukkitMinecraftUtils.MinecraftVersion;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Factory class for wrapping various Bukkit objects into {@link DataContainerWrapper} implementations.
 * Supports modern (PersistentDataContainer) and legacy (NBT or custom binary) systems.
 */
public class DataContainerFactory {

    /**
     * Whether the server is running a modern version that supports {@link org.bukkit.persistence.PersistentDataContainer}.
     */
    private static final boolean IS_MODERN;

    static {
        boolean modern;
        try {
            Class.forName("org.bukkit.persistence.PersistentDataContainer");
            modern = true;
        } catch (ClassNotFoundException e) {
            modern = false;
        }
        IS_MODERN = modern;
    }

    /**
     * Returns whether the server supports PersistentDataContainer.
     *
     * @return true if running a modern version, false otherwise.
     */
    public static boolean isModern() {
        return IS_MODERN;
    }

    /**
     * Wraps a supported object (PersistentDataContainer or ReadWriteNBT) in a {@link DataContainerWrapper}.
     *
     * @param object the object to wrap (must be a valid data container).
     * @return a wrapper implementation for the given object.
     * @throws IllegalArgumentException if the object is not supported.
     */
    @Contract("_ -> new")
    public static @NotNull DataContainerWrapper wrap(Object object) {
        if (IS_MODERN && object instanceof org.bukkit.persistence.PersistentDataContainer pdc) {
            return new ModernDataContainerWrapper(pdc);
        } else if (object instanceof ReadWriteNBT nbt) {
            return new NBTDataContainerWrapper(nbt);
        }
        throw new IllegalArgumentException("Unsupported container type: " + object.getClass() +
                " Expected: " + (IS_MODERN ? "PersistentDataContainer" : "ReadWriteNBT"));
    }

    /**
     * Wraps an {@link ItemStack} in a {@link DataContainerWrapper}.
     * Uses PersistentDataContainer if available, otherwise falls back to NBT.
     *
     * @param itemStack the ItemStack to wrap.
     * @return a wrapper around the item's metadata.
     */
    @Contract("_ -> new")
    public static @NotNull DataContainerWrapper wrap(ItemStack itemStack) {
        if (IS_MODERN && itemStack instanceof org.bukkit.persistence.PersistentDataHolder holder) {
            return new ModernDataContainerWrapper(holder.getPersistentDataContainer());
        } else {
            return new NBTDataContainerWrapper(NBT.itemStackToNBT(itemStack));
        }
    }

    /**
     * Wraps a {@link BlockState} in a {@link DataContainerWrapper}.
     * Uses PersistentDataContainer if available, NBT otherwise. For older versions, falls back to {@link LegacyDataContainerWrapper}.
     *
     * @param blockState the BlockState to wrap.
     * @return a data container wrapper for the given block state.
     * @see LegacyDataContainerWrapper
     */
    @Contract("_ -> new")
    public static @NotNull DataContainerWrapper wrap(BlockState blockState) {
        if (IS_MODERN && blockState instanceof org.bukkit.persistence.PersistentDataHolder holder) {
            return new ModernDataContainerWrapper(holder.getPersistentDataContainer());
        } else if (MinecraftVersion.getCurrentVersion().isAboveOrEqual(MinecraftVersion.V1_14_R1)) {
            ReadWriteNBT state = NBT.createNBTObject();
            NBT.get(blockState, state::mergeCompound);
            return new NBTDataContainerWrapper(state);
        } else {
            SerializableBlock serializableBlock = new SerializableBlock(blockState.getBlock());
            return new LegacyDataContainerWrapper(serializableBlock);
        }
    }
}
