package com.georgev22.voidchest.api.utilities.persistence;

import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class BukkitDataTypeAdapter {

    private static final Map<DataType<?>, PersistentDataType<?, ?>> MAP = new HashMap<>();

    static {
        MAP.put(DataType.BYTE, PersistentDataType.BYTE);
        MAP.put(DataType.SHORT, PersistentDataType.SHORT);
        MAP.put(DataType.INTEGER, PersistentDataType.INTEGER);
        MAP.put(DataType.LONG, PersistentDataType.LONG);
        MAP.put(DataType.FLOAT, PersistentDataType.FLOAT);
        MAP.put(DataType.DOUBLE, PersistentDataType.DOUBLE);
        MAP.put(DataType.BOOLEAN, PersistentDataType.BOOLEAN);
        MAP.put(DataType.STRING, PersistentDataType.STRING);
        MAP.put(DataType.BYTE_ARRAY, PersistentDataType.BYTE_ARRAY);
        MAP.put(DataType.INTEGER_ARRAY, PersistentDataType.INTEGER_ARRAY);
        MAP.put(DataType.LONG_ARRAY, PersistentDataType.LONG_ARRAY);
    }

    @SuppressWarnings("unchecked")
    public static <T, Z> PersistentDataType<T, Z> toBukkit(
            @NotNull DataType<T> type
    ) {
        PersistentDataType<?, ?> bukkit = MAP.get(type);

        if (bukkit == null) {
            throw new IllegalArgumentException("No Bukkit mapping for: " + type);
        }

        return (PersistentDataType<T, Z>) bukkit;
    }

    private BukkitDataTypeAdapter() {}
}