package com.georgev22.voidchest.api.maps;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

public class ConcurrentObjectMap<K, V> extends ConcurrentHashMap<K, V> implements ObjectMap<K, V> {

    /**
     * Creates an ConcurrentObjectMap instance.
     */
    public ConcurrentObjectMap() {
    }

    /**
     * Creates a ConcurrentObjectMap instance initialized with the given map.
     *
     * @param map initial map
     */
    public ConcurrentObjectMap(final ObjectMap<K, V> map) {
        putAll(map);
    }

    /**
     * Creates a ConcurrentObjectMap instance initialized with the given map.
     *
     * @param map initial map
     */
    public ConcurrentObjectMap(final Map<K, V> map) {
        putAll(map);
    }

    /**
     * Constructs a new ConcurrentObjectMap with the specified initial capacity.
     *
     * @param initialCapacity The initial capacity of the ConcurrentObjectMap.
     */
    public ConcurrentObjectMap(final int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a new ConcurrentObjectMap with the specified initial capacity and load factor.
     *
     * @param initialCapacity The initial capacity of the ConcurrentObjectMap.
     * @param loadFactor     The load factor of the ConcurrentObjectMap.
     */
    public ConcurrentObjectMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Put/replace the given key/value pair into this User and return this.  Useful for chaining puts in a single expression, e.g.
     * <pre>
     * user.append("a", 1).append("b", 2)}
     * </pre>
     *
     * @param key   key
     * @param value value
     * @return this
     */
    public ConcurrentObjectMap<K, V> append(final K key, final V value) {
        if (containsKey(key)) {
            replace(key, value);
        } else {
            put(key, value);
        }
        return this;
    }

    @Override
    public ConcurrentObjectMap<K, V> append(@NotNull Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            append(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public ConcurrentObjectMap<K, V> append(@NotNull ObjectMap<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            append(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * Put/replace the given key/value pair into ConcurrentObjectMap if boolean is true and return this.  Useful for chaining puts in a single expression, e.g.
     * <pre>
     * user.append("a", 1, check1).append("b", 2, check2)}
     * </pre>
     *
     * @param key    key
     * @param value  value
     * @param ifTrue ifTrue
     * @return this
     */
    public ConcurrentObjectMap<K, V> appendIfTrue(final K key, final V value, boolean ifTrue) {
        if (ifTrue)
            append(key, value);
        return this;
    }

    /**
     * Put/replace the given key/value pair into ObjectMap if boolean is true or not and return this.  Useful for chaining puts in a single expression, e.g.
     * <pre>
     * user.appendIfTrue("a", 1, 2, check1).appendIfTrue("b", 3, 4, check2)}
     * </pre>
     *
     * @param key          key
     * @param valueIfTrue  the value if the ifTrue is true
     * @param valueIfFalse the value if the ifTrue is false
     * @param ifTrue       ifTrue
     * @return this
     */
    public ConcurrentObjectMap<K, V> appendIfTrue(final K key, final V valueIfTrue, final V valueIfFalse, boolean ifTrue) {
        if (ifTrue) {
            append(key, valueIfTrue);
        } else {
            append(key, valueIfFalse);
        }
        return this;
    }

    /**
     * Put/replace a given map into this ObjectMap if boolean is true and return this.  Useful for chaining puts in a single expression, e.g.
     * <pre>
     * user.appendIfTrue("a", 1, check1).appendIfTrue(map, check2)}
     * </pre>
     *
     * @param map    key
     * @param ifTrue ifTrue
     * @return this
     */
    @Override
    public ConcurrentObjectMap<K, V> appendIfTrue(@NotNull Map<K, V> map, boolean ifTrue) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            appendIfTrue(entry.getKey(), entry.getValue(), ifTrue);
        }
        return this;
    }

    /**
     * Put/replace the given key/value pair into ObjectMap if boolean is true or not and return this.  Useful for chaining puts in a single expression, e.g.
     * <pre>
     * user.appendIfTrue("a", 1, 2, check1).appendIfTrue(map1, map2, check2)}
     * </pre>
     *
     * @param mapIfTrue  the map if the ifTrue is true
     * @param mapIfFalse the map if the ifTrue is false
     * @param ifTrue     ifTrue
     * @return this
     */
    @Override
    public ConcurrentObjectMap<K, V> appendIfTrue(Map<K, V> mapIfTrue, Map<K, V> mapIfFalse, boolean ifTrue) {
        if (ifTrue) {
            append(mapIfTrue);
        } else {
            append(mapIfFalse);
        }
        return this;
    }

    /**
     * Put/replace a given map into this ObjectMap if boolean is true and return this.  Useful for chaining puts in a single expression, e.g.
     * <pre>
     * user.appendIfTrue("a", 1, check1).appendIfTrue(map, check2)}
     * </pre>
     *
     * @param map    key
     * @param ifTrue ifTrue
     * @return this
     */
    @Override
    public ConcurrentObjectMap<K, V> appendIfTrue(@NotNull ObjectMap<K, V> map, boolean ifTrue) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            appendIfTrue(entry.getKey(), entry.getValue(), ifTrue);
        }
        return this;
    }

    /**
     * Put/replace the given key/value pair into ObjectMap if boolean is true or not and return this.  Useful for chaining puts in a single expression, e.g.
     * <pre>
     * user.appendIfTrue("a", 1, 2, check1).appendIfTrue(map1, map2, check2)}
     * </pre>
     *
     * @param mapIfTrue  the map if the ifTrue is true
     * @param mapIfFalse the map if the ifTrue is false
     * @param ifTrue     ifTrue
     * @return this
     */
    @Override
    public ConcurrentObjectMap<K, V> appendIfTrue(ObjectMap<K, V> mapIfTrue, Map<K, V> mapIfFalse, boolean ifTrue) {
        if (ifTrue) {
            append(mapIfTrue);
        } else {
            append(mapIfFalse);
        }
        return this;
    }

    /**
     * Removes the entry with the specified key from the ObjectMap.
     *
     * @param key the key of the entry to be removed
     * @return the modified ObjectMap with the specified entry removed, or the original ObjectMap if the key was not found
     */
    @Override
    public ConcurrentObjectMap<K, V> removeEntry(K key) {
        remove(key);
        return this;
    }

    /**
     * Removes all entries with keys present in the specified map from the ObjectMap.
     *
     * @param map the map containing the keys to be removed
     * @return the modified ObjectMap with the entries corresponding to the specified keys removed
     */
    @Override
    public ConcurrentObjectMap<K, V> removeEntries(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            remove(entry.getKey());
        }
        return this;
    }

    /**
     * Removes all entries with keys present in the specified ObjectMap from the ObjectMap.
     *
     * @param map the ObjectMap containing the keys to be removed
     * @return the modified ObjectMap with the entries corresponding to the keys in the specified ObjectMap removed
     */
    @Override
    public ConcurrentObjectMap<K, V> removeEntries(ObjectMap<K, V> map) {
        for (ObjectMap.Entry<K, V> entry : map.entrySet()) {
            remove(entry.getKey());
        }
        return this;
    }

    /**
     * Removes the entry with the specified key from the ObjectMap if the condition is true.
     *
     * @param key    the key of the entry to be removed
     * @param ifTrue the condition to check before removing the entry
     * @return the modified ObjectMap with the specified entry removed if the condition is true, or the original ObjectMap otherwise
     */
    @Override
    public ConcurrentObjectMap<K, V> removeEntryIfTrue(K key, boolean ifTrue) {
        if (ifTrue) {
            remove(key);
        }
        return this;
    }

    /**
     * Removes all entries with keys present in the specified map from the ObjectMap if the condition is true.
     *
     * @param map    the map containing the keys to be removed
     * @param ifTrue the condition to check before removing the entries
     * @return the modified ObjectMap with the entries corresponding to the keys in the specified map removed if the condition is true, or the original ObjectMap otherwise
     */
    @Override
    public ConcurrentObjectMap<K, V> removeEntriesIfTrue(Map<K, V> map, boolean ifTrue) {
        if (ifTrue) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                remove(entry.getKey());
            }
        }
        return this;
    }

    /**
     * Removes all entries with keys present in the specified ObjectMap from the ObjectMap if the condition is true.
     *
     * @param map    the ObjectMap containing the keys to be removed
     * @param ifTrue the condition to check before removing the entries
     * @return the modified ObjectMap with the entries corresponding to the keys in the specified ObjectMap removed if the condition is true, or the original ObjectMap otherwise
     */
    @Override
    public ConcurrentObjectMap<K, V> removeEntriesIfTrue(ObjectMap<K, V> map, boolean ifTrue) {
        if (ifTrue) {
            for (ObjectMap.Entry<K, V> entry : map.entrySet()) {
                remove(entry.getKey());
            }
        }
        return this;
    }

    /**
     * Gets the value of the given key as an Integer.
     *
     * @param key the key
     * @return the value as an integer, which may be null
     * @throws ClassCastException if the value is not an integer
     */
    public Integer getInteger(final Object key) {
        return getInteger(key, 0);
    }

    /**
     * Gets the value of the given key as a primitive int.
     *
     * @param key          the key
     * @param defaultValue what to return if the value is null
     * @return the value as an integer, which may be null
     * @throws ClassCastException if the value is not an integer
     */
    public int getInteger(final Object key, final int defaultValue) {
        return get(key, defaultValue);
    }

    /**
     * Gets the value of the given key as a Long.
     *
     * @param key the key
     * @return the value as a long, which may be null
     * @throws ClassCastException if the value is not an long
     */
    public Long getLong(final Object key) {
        return getLong(key, 0L);
    }

    /**
     * Gets the value of the given key as a Long.
     *
     * @param key          the key
     * @param defaultValue what to return if the value is null
     * @return the value as a long, which may be null
     * @throws ClassCastException if the value is not an long
     */
    public Long getLong(final Object key, final long defaultValue) {
        return get(key, defaultValue);
    }

    /**
     * Gets the value of the given key as a Double.
     *
     * @param key the key
     * @return the value as a double, which may be null
     * @throws ClassCastException if the value is not an double
     */
    public Double getDouble(final Object key) {
        return getDouble(key, 0D);
    }

    /**
     * Gets the value of the given key as a Double.
     *
     * @param key          the key
     * @param defaultValue what to return if the value is null
     * @return the value as a double, which may be null
     * @throws ClassCastException if the value is not an double
     */
    public Double getDouble(final Object key, final double defaultValue) {
        return get(key, defaultValue);
    }

    /**
     * Gets the value of the given key as a String.
     *
     * @param key the key
     * @return the value as a String, which may be null
     * @throws ClassCastException if the value is not a String
     */
    public String getString(final Object key) {
        return getString(key, "");
    }

    /**
     * Gets the value of the given key as a String.
     *
     * @param key          the key
     * @param defaultValue what to return if the value is null
     * @return the value as a String, which may be null
     * @throws ClassCastException if the value is not a String
     */
    public String getString(final Object key, final String defaultValue) {
        return get(key, defaultValue);
    }

    /**
     * Gets the value of the given key as a Boolean.
     *
     * @param key the key
     * @return the value as a Boolean, which may be null
     * @throws ClassCastException if the value is not an boolean
     */
    public Boolean getBoolean(final Object key) {
        return getBoolean(key, false);
    }

    /**
     * Gets the value of the given key as a primitive boolean.
     *
     * @param key          the key
     * @param defaultValue what to return if the value is null
     * @return the value as a primitive boolean
     * @throws ClassCastException if the value is not a boolean
     */
    public boolean getBoolean(final Object key, final boolean defaultValue) {
        return get(key, defaultValue);
    }

    /**
     * Gets the value of the given key as a Date.
     *
     * @param key the key
     * @return the value as a Date, which may be null
     * @throws ClassCastException if the value is not a Date
     */
    public Date getDate(final Object key) {
        return getDate(key, new Date());
    }

    /**
     * Gets the value of the given key as a Date.
     *
     * @param key          the key
     * @param defaultValue what to return if the value is null
     * @return the value as a Date, which may be null
     * @throws ClassCastException if the value is not a Date
     */
    public Date getDate(final Object key, final Date defaultValue) {
        return get(key, defaultValue);
    }

    /**
     * Gets the list value of the given key, casting the list elements to the given {@code Class<T>}.  This is useful to avoid having
     * casts in client code, though the effect is the same.
     *
     * @param key   the key
     * @param clazz the non-null class to cast the list value to
     * @param <T>   the type of the class
     * @return the list value of the given key, or null if the instance does not contain this key.
     * @throws ClassCastException if the elements in the list value of the given key is not of type T or the value is not a list
     */
    public <T> List<T> getList(Object key, Class<T> clazz) {
        return getList(key, clazz, null);
    }

    /**
     * Gets the list value of the given key, casting the list elements to {@code Class<T>} or returning the default list value if null.
     * This is useful to avoid having casts in client code, though the effect is the same.
     *
     * @param key          the key
     * @param clazz        the non-null class to cast the list value to
     * @param defaultValue what to return if the value is null
     * @param <T>          the type of the class
     * @return the list value of the given key, or the default list value if the instance does not contain this key.
     * @throws ClassCastException if the value of the given key is not of type T
     */
    public <T> List<T> getList(final Object key, final Class<T> clazz, final List<T> defaultValue) {
        List<T> value = get(key, List.class);
        if (value == null) {
            return defaultValue;
        }

        for (Object item : value) {
            if (!clazz.isAssignableFrom(item.getClass())) {
                throw new ClassCastException(format("List element cannot be cast to %s", clazz.getName()));
            }
        }
        return value;
    }

    /**
     * Gets the value of the given key, casting it to the given {@code Class<T>}.  This is useful to avoid having casts in client code,
     * though the effect is the same.  So to get the value of a key that is of type String, you would write {@code String name =
     * doc.get("name", String.class)} instead of {@code String name = (String) doc.get("x") }.
     *
     * @param key   the key
     * @param clazz the non-null class to cast the value to
     * @param <T>   the type of the class
     * @return the value of the given key, or null if the instance does not contain this key.
     * @throws ClassCastException if the value of the given key is not of type T
     */
    public <T> T get(final Object key, final Class<T> clazz) {
        return clazz.cast(get(key));
    }

    /**
     * Gets the value of the given key, casting it to {@code Class<T>} or returning the default value if null.
     * This is useful to avoid having casts in client code, though the effect is the same.
     *
     * @param key          the key
     * @param defaultValue what to return if the value is null
     * @param <T>          the type of the class
     * @return the value of the given key, or null if the instance does not contain this key.
     * @throws ClassCastException if the value of the given key is not of type T
     */
    public <T> T get(final Object key, final T defaultValue) {
        Object value = get(key);
        return value == null ? defaultValue : (T) value;
    }

}
