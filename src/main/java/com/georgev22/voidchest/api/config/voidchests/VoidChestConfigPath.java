package com.georgev22.voidchest.api.config.voidchests;

import org.jspecify.annotations.NonNull;

import java.util.function.Function;

public final class VoidChestConfigPath<T> {

    private final String path;
    private final Function<Object, T> deserializer;
    private final Function<T, Object> serializer;

    private VoidChestConfigPath(
            @NonNull String path,
            @NonNull Function<Object, T> deserializer,
            @NonNull Function<T, Object> serializer
    ) {
        this.path = path;
        this.deserializer = deserializer;
        this.serializer = serializer;
    }

    public static <T> @NonNull VoidChestConfigPath<T> of(
            @NonNull String path,
            @NonNull Function<Object, T> deserializer,
            @NonNull Function<T, Object> serializer
    ) {
        return new VoidChestConfigPath<>(
                path,
                deserializer,
                serializer
        );
    }

    public @NonNull String getPath() {
        return path;
    }

    public T deserialize(Object value) {
        return deserializer.apply(value);
    }

    public Object serialize(T value) {
        return serializer.apply(value);
    }

    @Override
    public String toString() {
        return path;
    }
}