package com.georgev22.voidchest.api.utilities.persistence;

import com.georgev22.voidchest.api.utilities.NamespacedKey;

/**
 * Interface for a data container wrapper that allows for storing and retrieving data.
 * Implementations should ensure that changes are applied correctly, especially when
 * setting or removing data entries.
 */
public interface DataContainerWrapper {

    /**
     * Checks if the data container contains a value for the specified key and type.
     *
     * @param key  The namespaced key identifying the data.
     * @param type The type of the data being checked.
     * @return true if the data exists, false otherwise.
     */
    boolean has(NamespacedKey key, DataType type);

    /**
     * Sets the value for the specified key and type in the data container.
     * Implementations should ensure that any necessary changes are applied.
     *
     * @param key   The namespaced key identifying the data.
     * @param type  The type of the data being set.
     * @param value The value to set for the specified key.
     */
    void set(NamespacedKey key, DataType type, Object value);

    /**
     * Retrieves the value for the specified key and type from the data container.
     *
     * @param key  The namespaced key identifying the data.
     * @param type The type of the data being retrieved.
     * @param <T>  The type of the value.
     * @return The value associated with the key, or null if not found.
     */
    <T> T get(NamespacedKey key, DataType type);

    /**
     * Removes the data associated with the specified key from the data container.
     * Implementations should ensure that any necessary changes are applied.
     *
     * @param key The namespaced key identifying the data to be removed.
     */
    void remove(NamespacedKey key);

    /**
     * Applies any pending changes to the provided object. This method should be called
     * when changes are made to ensure proper application of those changes.
     *
     * @param object The object to apply changes to.
     * @param <T>    The type of the object.
     * @return The object with applied changes.
     */
    <T> T apply(T object);
}
