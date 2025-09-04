package com.georgev22.voidchest.api.utilities.persistence;

import com.georgev22.voidchest.api.utilities.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ModernDataContainerWrapper implements DataContainerWrapper {

    private final PersistentDataContainer container;

    public ModernDataContainerWrapper(PersistentDataContainer container) {
        this.container = container;
    }

    @Override
    public boolean has(NamespacedKey key, DataType type) {
        return container.has(fromKey(key), toBukkitType(type));
    }

    @Override
    public void set(NamespacedKey key, @NotNull DataType type, Object value) {
        Object converted = type.convert(value);
        if (converted == null) {
            throw new IllegalArgumentException("Failed to convert value for type " + type + ": " + value);
        }
        container.set(fromKey(key), toBukkitType(type), converted);
    }

    @Override
    public <T> T get(NamespacedKey key, DataType type) {
        return container.get(fromKey(key), toBukkitType(type));
    }

    @Override
    public void remove(NamespacedKey key) {
        container.remove(fromKey(key));
    }

    @Contract("_ -> new")
    private org.bukkit.@NotNull NamespacedKey fromKey(@NotNull NamespacedKey key) {
        return new org.bukkit.NamespacedKey(key.getNamespace(), key.getKey());
    }

    @SuppressWarnings("unchecked")
    private static <T, Z> PersistentDataType<T, Z> toBukkitType(@NotNull DataType type) {
        return switch (type) {
            case BYTE -> (PersistentDataType<T, Z>) PersistentDataType.BYTE;
            case SHORT -> (PersistentDataType<T, Z>) PersistentDataType.SHORT;
            case INTEGER -> (PersistentDataType<T, Z>) PersistentDataType.INTEGER;
            case LONG -> (PersistentDataType<T, Z>) PersistentDataType.LONG;
            case FLOAT -> (PersistentDataType<T, Z>) PersistentDataType.FLOAT;
            case DOUBLE -> (PersistentDataType<T, Z>) PersistentDataType.DOUBLE;
            case BOOLEAN -> (PersistentDataType<T, Z>) PersistentDataType.BOOLEAN;
            case STRING -> (PersistentDataType<T, Z>) PersistentDataType.STRING;
            case BYTE_ARRAY -> (PersistentDataType<T, Z>) PersistentDataType.BYTE_ARRAY;
            case INTEGER_ARRAY -> (PersistentDataType<T, Z>) PersistentDataType.INTEGER_ARRAY;
            case LONG_ARRAY -> (PersistentDataType<T, Z>) PersistentDataType.LONG_ARRAY;
        };
    }

    @Override
    public <T> T apply(T object) {
        if (object instanceof BlockState blockState) {
            blockState.update();
        }
        return object;
    }
}