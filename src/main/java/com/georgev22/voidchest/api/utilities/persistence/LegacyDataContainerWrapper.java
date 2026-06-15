package com.georgev22.voidchest.api.utilities.persistence;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.maps.HashObjectMap;
import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.utilities.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Legacy implementation of {@link DataContainerWrapper} for platforms that do
 * not provide Bukkit's PersistentDataContainer API.
 *
 * <p>This implementation stores arbitrary serializable values associated with a
 * {@link PersistentHolder}. Data is identified by {@link NamespacedKey}s and
 * validated using {@link DataType}s.</p>
 *
 * <p>All data is persisted inside the plugin's data folder using binary shard
 * files. Entries are distributed across multiple shards to reduce individual
 * file size and improve loading and saving performance.</p>
 *
 * <p>Supported holder types include blocks players
 * and custom holder implementations through the {@link PersistentHolder}
 * abstraction.</p>
 *
 * <p>This class is intended to provide functionality similar to Bukkit's
 * PersistentDataContainer on legacy Minecraft versions where the API is not
 * available.</p>
 */
public class LegacyDataContainerWrapper implements DataContainerWrapper {

    /**
     * Number of data shards to split persistent data into.
     */
    private static final int SHARD_COUNT = 10;

    /**
     * The main plugin instance.
     */
    private static final JavaPlugin plugin = VoidChestAPI.getInstance().plugin();

    /**
     * The directory where shard files are stored.
     */
    private static final File SHARD_DIR = new File(plugin.getDataFolder(), ".persistence/shards");

    /**
     * In-memory cache of all loaded shards.
     *
     * <p>The outer map is keyed by shard ID. Each shard contains a map of
     * holder storage keys to their associated persistent data.</p>
     */
    private static final Map<Integer, Map<String, ObjectMap<String, Object>>> shards = new HashMap<>();

    /**
     * The holder associated with this data container.
     */
    private final PersistentHolder holder;

    static {
        loadAllShards();
    }

    /**
     * Creates a new legacy data container wrapper for the specified holder.
     *
     * @param holder the holder whose persistent data will be managed
     */
    public LegacyDataContainerWrapper(@NotNull PersistentHolder holder) {
        this.holder = holder;
    }

    /**
     * Checks if a key exists with the expected data type.
     *
     * @param key  the namespaced key.
     * @param type the expected data type.
     * @return true if a matching key/value exists and the type matches.
     */
    @Override
    public <T> boolean has(
            @NotNull NamespacedKey key,
            DataType<T> type
    ) {
        ObjectMap<String, Object> data =
                getHolderData(holder);

        Object value = data.get(key.toString());

        return value != null &&
                type.getPrimitiveClass()
                        .isAssignableFrom(value.getClass());
    }

    /**
     * Stores a value in the data container.
     *
     * <p>The supplied value must implement {@link Serializable} in order to be
     * persisted.</p>
     *
     * @param key   the key to associate the value with
     * @param type  the value type
     * @param value the value to store
     * @throws IllegalArgumentException if the value is not serializable
     */
    @Override
    public <T> void set(
            NamespacedKey key,
            DataType<T> type,
            T value
    ) {
        if (!(value instanceof Serializable)) {
            throw new IllegalArgumentException(
                    "Value must be Serializable"
            );
        }

        ObjectMap<String, Object> holderData =
                getHolderData(holder);

        holderData.put(
                key.toString(),
                value
        );

        getShardMap(holder)
                .put(
                        holder.getStorageKey(),
                        holderData
                );

        saveShard(holder);
    }

    /**
     * Retrieves a value from the data container.
     *
     * @param key  the key.
     * @param type the expected type.
     * @param <T>  the type parameter.
     * @return the value if found and valid; otherwise, null.
     */
    @Override
    public <T> T get(
            @NotNull NamespacedKey key,
            @NotNull DataType<T> type
    ) {
        ObjectMap<String, Object> data =
                getHolderData(holder);

        Object value = data.get(key.toString());

        if (type.getPrimitiveClass().isInstance(value)) {
            return type.convert(value);
        }

        return null;
    }

    /**
     * Removes a key-value pair from the container.
     *
     * @param key the key to remove.
     */
    @Override
    public void remove(
            @NotNull NamespacedKey key
    ) {
        ObjectMap<String, Object> holderData =
                getHolderData(holder);

        holderData.remove(key.toString());

        if (holderData.isEmpty()) {
            getShardMap(holder)
                    .remove(holder.getStorageKey());
        } else {
            getShardMap(holder)
                    .put(
                            holder.getStorageKey(),
                            holderData
                    );
        }

        saveShard(holder);
    }

    /**
     * Saves the current state of the data container to disk.
     *
     * @param object the object to return.
     * @param <T>    the type of the object.
     * @return the passed-in object.
     */
    @Override
    public <T> T apply(T object) {
        saveShard(holder);
        return object;
    }

    /**
     * Retrieves the persistent data associated with the specified holder.
     *
     * <p>If no data currently exists for the holder, an empty map is returned.</p>
     *
     * @param holder the holder
     * @return the holder's persistent data map
     */
    private static ObjectMap<String, Object> getHolderData(
            PersistentHolder holder
    ) {
        return getShardMap(holder)
                .getOrDefault(
                        holder.getStorageKey(),
                        new HashObjectMap<>()
                );
    }

    /**
     * Retrieves the shard map that contains the specified holder.
     *
     * <p>If the shard does not yet exist in memory it will be created.</p>
     *
     * @param holder the holder
     * @return the shard map for the holder
     */
    private static Map<String, ObjectMap<String, Object>> getShardMap(
            PersistentHolder holder
    ) {
        int shardId = getShardId(holder);

        return shards.computeIfAbsent(
                shardId,
                k -> new HashMap<>()
        );
    }

    /**
     * Computes the shard ID for a holder.
     *
     * <p>The holder's hash code is used to distribute entries across shards.</p>
     *
     * @param holder the holder
     * @return the shard ID
     */
    private static int getShardId(@NotNull PersistentHolder holder) {
        return Math.abs(holder.hashCode() % SHARD_COUNT);
    }

    /**
     * Loads all shard files from disk into memory.
     *
     * <p>Any invalid entries encountered during loading are skipped. Errors are
     * logged but do not prevent other shards from loading.</p>
     */
    private static void loadAllShards() {
        if (!SHARD_DIR.exists()) {
            return;
        }

        File[] files = SHARD_DIR.listFiles(
                (dir, name) ->
                        name.startsWith("shard_")
                                && name.endsWith(".dat")
        );

        if (files == null) {
            return;
        }

        for (File file : files) {
            int shardId = extractShardIdFromFile(file);

            try (
                    ObjectInputStream ois =
                            new ObjectInputStream(
                                    new FileInputStream(file)
                            )
            ) {
                Object object = ois.readObject();

                if (object instanceof Map<?, ?> rawMap) {

                    Map<String, ObjectMap<String, Object>> parsed =
                            new HashMap<>();

                    for (Map.Entry<?, ?> entry : rawMap.entrySet()) {

                        if (!(entry.getKey() instanceof String key)) {
                            continue;
                        }

                        if (!(entry.getValue() instanceof Map<?, ?> rawInner)) {
                            continue;
                        }

                        ObjectMap<String, Object> map =
                                new HashObjectMap<>();

                        for (Map.Entry<?, ?> inner : rawInner.entrySet()) {

                            if (inner.getKey() instanceof String innerKey) {
                                map.put(
                                        innerKey,
                                        inner.getValue()
                                );
                            }
                        }

                        parsed.put(key, map);
                    }

                    shards.put(shardId, parsed);
                }
            } catch (Exception e) {
                plugin.getLogger().log(
                        Level.SEVERE,
                        "Failed to load shard: "
                                + file.getName(),
                        e
                );
            }
        }
    }

    /**
     * Saves the shard that contains the specified holder.
     *
     * <p>Only the shard associated with the holder is written to disk.</p>
     *
     * @param holder the holder whose shard should be saved
     */
    private static void saveShard(
            PersistentHolder holder
    ) {
        int shardId = getShardId(holder);

        File file = new File(
                SHARD_DIR,
                "shard_" + shardId + ".dat"
        );

        Map<String, ObjectMap<String, Object>> shardMap =
                shards.getOrDefault(
                        shardId,
                        new HashMap<>()
                );

        Map<String, Map<String, Object>> serializableMap =
                new HashMap<>();

        for (Map.Entry<String,
                ObjectMap<String, Object>> entry
                : shardMap.entrySet()) {

            serializableMap.put(
                    entry.getKey(),
                    new HashMap<>(entry.getValue())
            );
        }

        try {
            if (!SHARD_DIR.exists()) {
                SHARD_DIR.mkdirs();
            }

            try (
                    ObjectOutputStream oos =
                            new ObjectOutputStream(
                                    new FileOutputStream(file)
                            )
            ) {
                oos.writeObject(serializableMap);
            }

        } catch (IOException e) {
            plugin.getLogger().log(
                    Level.SEVERE,
                    "Failed to save shard: "
                            + file.getName(),
                    e
            );
        }
    }

    /**
     * Extracts a shard ID from a shard file name.
     *
     * <p>Expected format: {@code shard_<id>.dat}</p>
     *
     * @param file the shard file
     * @return the extracted shard ID, or {@code -1} if the file name is invalid
     */
    private static int extractShardIdFromFile(@NotNull File file) {
        try {
            String name = file.getName().replace("shard_", "").replace(".dat", "");
            return Integer.parseInt(name);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
