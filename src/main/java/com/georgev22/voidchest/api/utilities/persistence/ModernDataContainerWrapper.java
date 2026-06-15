package com.georgev22.voidchest.api.utilities.persistence;

import com.georgev22.voidchest.api.utilities.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.georgev22.voidchest.api.utilities.persistence.BukkitDataTypeAdapter.toBukkit;

public class ModernDataContainerWrapper implements DataContainerWrapper {

    private final PersistentDataContainer container;

    public ModernDataContainerWrapper(PersistentDataContainer container) {
        this.container = container;
    }

    @Override
    public <T> boolean has(NamespacedKey key, DataType<T> type) {
        return container.has(fromKey(key), toBukkit(type));
    }

    @Override
    public <T> void set(NamespacedKey key, @NotNull DataType<T> type, T value) {
        Object converted = type.convert(value);
        if (converted == null) {
            throw new IllegalArgumentException("Failed to convert value for type " + type + ": " + value);
        }
        container.set(fromKey(key), toBukkit(type), converted);
    }

    @Override
    public <T> T get(NamespacedKey key, DataType<T> type) {
        return container.get(fromKey(key), toBukkit(type));
    }

    @Override
    public void remove(NamespacedKey key) {
        container.remove(fromKey(key));
    }

    @Contract("_ -> new")
    private org.bukkit.@NotNull NamespacedKey fromKey(@NotNull NamespacedKey key) {
        return new org.bukkit.NamespacedKey(key.getNamespace(), key.getKey());
    }

    @Override
    public <T> T apply(T object) {
        if (object instanceof BlockState blockState) {
            blockState.update();
        }
        return object;
    }
}