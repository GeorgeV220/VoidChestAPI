package com.georgev22.voidchest.api.utilities.persistence;

import com.georgev22.voidchest.api.utilities.ContainerWrapper;
import com.georgev22.voidchest.api.utilities.SerializableContainer;
import com.georgev22.voidchest.api.utilities.persistence.holder.PlayerHolder;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Factory for creating {@link DataContainerWrapper} instances for different
 * Bukkit objects across both modern (PDC) and legacy systems.
 *
 * <p>Routing rules:
 * <ul>
 *     <li>1.14+ → PersistentDataContainer (Bukkit API)</li>
 *     <li>&lt; 1.14 → Legacy binary persistence system</li>
 * </ul>
 */
public final class DataContainerFactory {

    private static final boolean MODERN;

    static {
        boolean modern;
        try {
            Class.forName("org.bukkit.persistence.PersistentDataContainer");
            modern = true;
        } catch (ClassNotFoundException e) {
            modern = false;
        }
        MODERN = modern;
    }

    /**
     * Returns whether the server supports PersistentDataContainer.
     *
     * @return true if running a modern version, false otherwise.
     */
    public static boolean isModern() {
        return MODERN;
    }

    @Contract("_ -> new")
    public static @NotNull DataContainerWrapper wrap(@NotNull Object object) {
        if (MODERN) {
            return wrapModern(object);
        }

        return wrapLegacy(object);
    }

    @Contract("null -> fail")
    private static @NotNull DataContainerWrapper wrapModern(Object object) {
        if (object instanceof org.bukkit.persistence.PersistentDataHolder holder) {
            return new ModernDataContainerWrapper(holder.getPersistentDataContainer());
        }

        throw new IllegalArgumentException(
                "Unsupported modern container type: " + object.getClass().getName()
        );
    }

    @Contract("null -> fail")
    private static @NotNull DataContainerWrapper wrapLegacy(Object object) {
        if (object instanceof OfflinePlayer player) {
            return new LegacyDataContainerWrapper(
                    new PlayerHolder(player.getUniqueId())
            );
        }

        if (object instanceof BlockState blockState && ContainerWrapper.isStorageContainer(blockState)) {
            return new LegacyDataContainerWrapper(
                    SerializableContainer.fromLocation(blockState.getLocation())
            );
        }

        if (object instanceof ContainerWrapper containerWrapper) {
            return new LegacyDataContainerWrapper(
                    new SerializableContainer(containerWrapper)
            );
        }

        if (object instanceof PersistentHolder persistentHolder) {
            return new LegacyDataContainerWrapper(persistentHolder);
        }

        throw new IllegalArgumentException(
                "Unsupported legacy container type: " + object.getClass().getName()
        );
    }
}