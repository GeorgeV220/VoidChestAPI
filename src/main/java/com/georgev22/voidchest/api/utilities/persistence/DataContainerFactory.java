package com.georgev22.voidchest.api.utilities.persistence;

import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataContainer;

/**
 * Factory class for wrapping Bukkit objects into {@link DataContainerWrapper}
 * using the PersistentDataContainer API only.
 */
public class DataContainerFactory {

    private DataContainerFactory() {
        // Utility class
    }

    /**
     * Wraps a supported object that implements {@link PersistentDataHolder}.
     *
     * @param object The object to wrap.
     * @return A PDC-based wrapper.
     */
    @Contract("_ -> new")
    public static @NonNull DataContainerWrapper wrap(Object object) {
        if (object instanceof PersistentDataHolder holder) {
            return new ModernDataContainerWrapper(holder.getPersistentDataContainer());
        }

        throw new IllegalArgumentException(
                "Unsupported container type: " + object.getClass() +
                        ". Expected a PersistentDataHolder"
        );
    }

    /**
     * Wraps an {@link ItemStack} via its ItemMeta PersistentDataContainer.
     */
    @Contract("_ -> new")
    public static @NonNull DataContainerWrapper wrap(ItemStack itemStack) {
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        return new ModernDataContainerWrapper(container);
    }

    /**
     * Wraps a {@link BlockState} via its PersistentDataContainer.
     */
    @Contract("_ -> new")
    public static @NonNull DataContainerWrapper wrap(BlockState blockState) {
        if (blockState instanceof PersistentDataHolder holder) {
            return new ModernDataContainerWrapper(holder.getPersistentDataContainer());
        }

        throw new IllegalArgumentException(
                "BlockState does not support PersistentDataContainer: "
                        + blockState.getClass()
        );
    }
}
