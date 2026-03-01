package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.datastructures.maps.HashObjectMap;
import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.datastructures.maps.ObjectMaps;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Utility class for safe and performant enum lookups.
 * <p>
 * Provides nullable and {@link Optional}-based accessors without throwing
 * {@link IllegalArgumentException} like {@link Enum#valueOf(Class, String)}.
 * <p>
 * Lookups are cached per enum type for O(1) access after first initialization.
 */
public final class EnumUtils {

    private EnumUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static final ObjectMap<Class<?>, ObjectMap<String, ?>> CACHE =
            ObjectMaps.newConcurrentHashObjectMap();

    private static final ObjectMap<Class<?>, ObjectMap<String, ?>> CACHE_IGNORE_CASE =
            ObjectMaps.newConcurrentHashObjectMap();

    /**
     * Returns the enum constant of the specified enum type with the specified name.
     * Returns {@code null} if the name does not match any constant.
     *
     * @param enumClass the enum class
     * @param name      the constant name
     * @param <E>       the enum type
     * @return the enum constant or {@code null} if not found
     */
    public static <E extends Enum<E>> @Nullable E valueOfNullable(Class<E> enumClass, String name) {
        Objects.requireNonNull(enumClass, "enumClass");
        if (name == null) return null;
        return getCache(enumClass, false).get(name);
    }

    /**
     * Returns the enum constant of the specified enum type with the specified name,
     * ignoring case differences. Returns {@code null} if not found.
     *
     * @param enumClass the enum class
     * @param name      the constant name
     * @param <E>       the enum type
     * @return the enum constant or {@code null} if not found
     */
    public static <E extends Enum<E>> @Nullable E valueOfNullableIgnoreCase(Class<E> enumClass, String name) {
        Objects.requireNonNull(enumClass, "enumClass");
        if (name == null) return null;
        return getCache(enumClass, true).get(name.toLowerCase(Locale.ROOT));
    }

    /**
     * Returns an {@link Optional} containing the enum constant if present.
     *
     * @param enumClass the enum class
     * @param name      the constant name
     * @param <E>       the enum type
     * @return optional containing the constant if found
     */
    public static <E extends Enum<E>> @NonNull Optional<E> valueOf(Class<E> enumClass, String name) {
        return Optional.ofNullable(valueOfNullable(enumClass, name));
    }

    /**
     * Returns an {@link Optional} containing the enum constant if present,
     * ignoring case differences.
     *
     * @param enumClass the enum class
     * @param name      the constant name
     * @param <E>       the enum type
     * @return optional containing the constant if found
     */
    public static <E extends Enum<E>> @NonNull Optional<E> valueOfIgnoreCase(Class<E> enumClass, String name) {
        return Optional.ofNullable(valueOfNullableIgnoreCase(enumClass, name));
    }

    /**
     * Returns whether the specified enum contains a constant with the given name.
     *
     * @param enumClass the enum class
     * @param name      the constant name
     * @param <E>       the enum type
     * @return true if present
     */
    public static <E extends Enum<E>> boolean contains(Class<E> enumClass, String name) {
        return valueOfNullable(enumClass, name) != null;
    }

    /**
     * Returns whether the specified enum contains a constant with the given name,
     * ignoring case differences.
     *
     * @param enumClass the enum class
     * @param name      the constant name
     * @param <E>       the enum type
     * @return true if present
     */
    public static <E extends Enum<E>> boolean containsIgnoreCase(Class<E> enumClass, String name) {
        return valueOfNullableIgnoreCase(enumClass, name) != null;
    }

    /**
     * Clears all cached enum lookup maps.
     * Intended for use during plugin reloads.
     */
    public static void clearCache() {
        CACHE.clear();
        CACHE_IGNORE_CASE.clear();
    }

    @SuppressWarnings("unchecked")
    private static <E extends Enum<E>> ObjectMap<String, E> getCache(Class<E> enumClass, boolean ignoreCase) {
        ObjectMap<Class<?>, ObjectMap<String, ?>> target = ignoreCase ? CACHE_IGNORE_CASE : CACHE;

        return (ObjectMap<String, E>) target.computeIfAbsent(enumClass, cls -> {
            E[] constants = enumClass.getEnumConstants();
            ObjectMap<String, E> map = new HashObjectMap<>(constants.length);

            for (E constant : constants) {
                String key = ignoreCase
                        ? constant.name().toLowerCase(Locale.ROOT)
                        : constant.name();
                map.put(key, constant);
            }

            return ObjectMaps.newUnmodifiableObjectMap(map);
        });
    }
}