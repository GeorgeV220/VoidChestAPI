package com.georgev22.voidchest.api.utilities.persistence;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.maps.HashObjectMap;
import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.utilities.NamespacedKey;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * LegacyDataContainerWrapper provides binary persistence for {@link SerializableBlock} data.
 * It supports storing arbitrary serializable values keyed by {@link NamespacedKey} and {@link DataType}.
 * Data is divided across multiple binary shard files to reduce file size and memory footprint.
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
     * In-memory cache of all shards, keyed by shard ID.
     */
    private static final Map<Integer, Map<SerializableBlock, ObjectMap<String, Object>>> shards = new HashMap<>();

    /**
     * The block associated with this data wrapper instance.
     */
    private final SerializableBlock block;

    static {
        loadAllShards();
    }

    /**
     * Constructs a new LegacyDataContainerWrapper for the given block.
     *
     * @param block the block to associate data with.
     */
    public LegacyDataContainerWrapper(@NotNull SerializableBlock block) {
        this.block = block;
    }

    /**
     * Checks if a key exists with the expected data type.
     *
     * @param key  the namespaced key.
     * @param type the expected data type.
     * @return true if a matching key/value exists and the type matches.
     */
    @Override
    public boolean has(@NotNull NamespacedKey key, DataType type) {
        ObjectMap<String, Object> data = getBlockData(block);
        Object value = data.get(key.toString());
        return value != null && type.getPrimitiveClass().isAssignableFrom(value.getClass());
    }

    /**
     * Stores a value in the data container.
     *
     * @param key   the key to associate the value with.
     * @param type  the type of the value.
     * @param value the serializable value to store.
     */
    @Override
    public void set(NamespacedKey key, DataType type, Object value) {
        if (!(value instanceof Serializable)) {
            throw new IllegalArgumentException("Value must be Serializable");
        }

        ObjectMap<String, Object> blockData = getBlockData(block);
        blockData.put(key.toString(), value);
        getShardMap(block).put(block, blockData);

        saveShard(block);
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
    public <T> T get(@NotNull NamespacedKey key, @NotNull DataType type) {
        ObjectMap<String, Object> data = getBlockData(block);
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
    public void remove(@NotNull NamespacedKey key) {
        ObjectMap<String, Object> blockData = getBlockData(block);
        blockData.remove(key.toString());

        if (blockData.isEmpty()) {
            getShardMap(block).remove(block);
        } else {
            getShardMap(block).put(block, blockData);
        }

        saveShard(block);
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
        saveShard(block);
        return object;
    }

    /**
     * Gets the data map associated with the block from the appropriate shard.
     *
     * @param block the block.
     * @return the data map.
     */
    private static ObjectMap<String, Object> getBlockData(SerializableBlock block) {
        return getShardMap(block).getOrDefault(block, new HashObjectMap<>());
    }

    /**
     * Gets the shard-specific map for a block.
     *
     * @param block the block.
     * @return the shard's map.
     */
    private static Map<SerializableBlock, ObjectMap<String, Object>> getShardMap(SerializableBlock block) {
        int shardId = getShardId(block);
        return shards.computeIfAbsent(shardId, k -> new HashMap<>());
    }

    /**
     * Computes the shard ID for a given block.
     *
     * @param block the block.
     * @return the shard ID.
     */
    private static int getShardId(@NotNull SerializableBlock block) {
        return Math.abs(block.hashCode() % SHARD_COUNT);
    }

    /**
     * Loads all shard files from disk into memory.
     */
    private static void loadAllShards() {
        if (!SHARD_DIR.exists()) return;
        File[] files = SHARD_DIR.listFiles((dir, name) -> name.startsWith("shard_") && name.endsWith(".dat"));
        if (files == null) return;

        for (File file : files) {
            int shardId = extractShardIdFromFile(file);
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object readObject = ois.readObject();
                if (readObject instanceof Map<?, ?> rawMap) {
                    Map<SerializableBlock, ObjectMap<String, Object>> parsed = new HashMap<>();
                    for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                        if (entry.getKey() instanceof SerializableBlock block &&
                                entry.getValue() instanceof Map<?, ?> rawInnerMap) {

                            ObjectMap<String, Object> map = new HashObjectMap<>();
                            for (Map.Entry<?, ?> e : rawInnerMap.entrySet()) {
                                if (e.getKey() instanceof String key) {
                                    map.put(key, e.getValue());
                                }
                            }
                            parsed.put(block, map);
                        }
                    }
                    shards.put(shardId, parsed);
                }
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to load shard: " + file.getName(), e);
            }
        }
    }

    /**
     * Saves the shard file that contains the specified block.
     *
     * @param block the block to determine which shard to save.
     */
    private static void saveShard(SerializableBlock block) {
        int shardId = getShardId(block);
        File file = new File(SHARD_DIR, "shard_" + shardId + ".dat");

        Map<SerializableBlock, ObjectMap<String, Object>> shardMap = shards.getOrDefault(shardId, new HashMap<>());
        Map<SerializableBlock, Map<String, Object>> serializableMap = new HashMap<>();

        for (Map.Entry<SerializableBlock, ObjectMap<String, Object>> entry : shardMap.entrySet()) {
            serializableMap.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }

        try {
            if (!SHARD_DIR.exists()) SHARD_DIR.mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(serializableMap);
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to save shard: " + file.getName(), e);
        }
    }

    /**
     * Extracts the shard ID from the given file name.
     *
     * @param file the shard file.
     * @return the extracted shard ID, or -1 if invalid.
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
