package com.georgev22.voidchest.api.config.voidchests;

import com.georgev22.voidchest.api.VoidChestAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public final class VoidChestTypeConfigRepository {

    private final File file;

    private final AtomicReference<Map<String, VoidChestTypeConfig>> cache =
            new AtomicReference<>(Collections.emptyMap());

    public VoidChestTypeConfigRepository(File file) {
        this.file = file;
    }

    /**
     * Initial load.
     */
    public void load() {
        reload();
    }

    /**
     * Reloads the file and atomically replaces the entire cache.
     * <p>
     * Existing readers will either see the old cache or the new cache;
     * never a partially populated one.
     */
    public void reload() {
        Map<String, VoidChestTypeConfig> loaded =
                VoidChestTypeConfigLoader.load(file);

        cache.set(
                Collections.unmodifiableMap(loaded)
        );
    }

    public VoidChestTypeConfig get(String type) {
        if (type == null) {
            return null;
        }

        return cache.get().get(
                type.toLowerCase(Locale.ROOT)
        );
    }

    public @NonNull VoidChestTypeConfig require(String type) {
        VoidChestTypeConfig config = get(type);

        if (config == null) {
            throw new IllegalArgumentException(
                    "Unknown VoidChest type: " + type
            );
        }

        return config;
    }

    public boolean contains(String type) {
        return get(type) != null;
    }

    public Set<String> getTypes() {
        return cache.get().keySet();
    }

    public Map<String, VoidChestTypeConfig> getAll() {
        return cache.get();
    }

    public int size() {
        return cache.get().size();
    }

    public <T> void setOverride(
            @NonNull String type,
            @NonNull VoidChestConfigPath<T> path,
            @NonNull T value
    ) throws IOException {

        YamlConfiguration yaml = null;
        try {
            yaml = VoidChestTypeConfigLoader.getYamlConfiguration();
        } catch (IllegalAccessException e) {
            VoidChestAPI.getInstance().plugin().getLogger().log(
                    Level.SEVERE,
                    "Failed to set override for VoidChest type: " + type,
                    e
            );
            return;
        }

        yaml.set(
                "types."
                        + normalize(type)
                        + ".overrides."
                        + path.getPath(),
                path.serialize(value)
        );

        yaml.save(file);
        reload();
    }

    public void removeOverride(
            @NonNull String type,
            @NonNull VoidChestConfigPath<?> path
    ) throws IOException {

        YamlConfiguration yaml = null;
        try {
            yaml = VoidChestTypeConfigLoader.getYamlConfiguration();
        } catch (IllegalAccessException e) {
            VoidChestAPI.getInstance().plugin().getLogger().log(
                    Level.SEVERE,
                    "Failed to remove override for VoidChest type: " + type,
                    e
            );
            return;
        }

        yaml.set(
                "types."
                        + normalize(type)
                        + ".overrides."
                        + path.getPath(),
                null
        );

        yaml.save(file);
        reload();
    }

    private static @NonNull String normalize(
            @NonNull String type
    ) {
        return type.toLowerCase(Locale.ROOT);
    }

    public void create(
            @NonNull String newVoidChestName,
            @NonNull String baseVoidChest
    ) throws IOException {
        YamlConfiguration yamlConfiguration;

        try {
            yamlConfiguration = VoidChestTypeConfigLoader.getYamlConfiguration();
        } catch (IllegalAccessException e) {
            VoidChestAPI.getInstance()
                    .plugin()
                    .getLogger()
                    .log(
                            Level.SEVERE,
                            "Failed to create a new VoidChest type "
                                    + newVoidChestName,
                            e
                    );
            return;
        }

        String newType = normalize(newVoidChestName);
        String baseType = normalize(baseVoidChest);

        String newPath = "types." + newType;
        String basePath = "types." + baseType;

        if (yamlConfiguration.contains(newPath)) {
            throw new IllegalArgumentException(
                    "VoidChest type already exists: " + newVoidChestName
            );
        }

        ConfigurationSection baseSection =
                yamlConfiguration.getConfigurationSection(basePath);

        if (baseSection == null) {
            throw new IllegalArgumentException(
                    "Unknown base VoidChest type: " + baseVoidChest
            );
        }

        ConfigurationSection newSection =
                yamlConfiguration.createSection(newPath);

        copySection(baseSection, newSection);

        if (!newSection.isConfigurationSection("overrides")) {
            newSection.createSection("overrides");
        }

        yamlConfiguration.save(file);
        reload();
    }

    private static void copySection(
            @NonNull ConfigurationSection source,
            @NonNull ConfigurationSection target
    ) {
        for (String key : source.getKeys(false)) {
            Object value = source.get(key);

            if (value instanceof ConfigurationSection sourceSection) {
                ConfigurationSection targetSection =
                        target.createSection(key);

                copySection(sourceSection, targetSection);
            } else {
                target.set(
                        key,
                        com.georgev22.voidchest.api.utilities.DeepCloner.cloneValue(value)
                );
            }
        }
    }
}