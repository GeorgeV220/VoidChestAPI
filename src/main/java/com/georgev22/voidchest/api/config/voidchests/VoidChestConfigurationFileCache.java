package com.georgev22.voidchest.api.config.voidchests;

import com.georgev22.voidchest.api.config.VoidChestOptionCache;
import com.georgev22.voidchest.api.datastructures.maps.HashObjectMap;
import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.registry.EntityManagerRegistry;
import com.georgev22.voidchest.api.storage.EntityManager;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.util.Optional;

/**
 * Caches all VoidChest configuration files and their corresponding
 * {@link FileConfiguration} instances in memory.
 *
 * <p>This cache is responsible for:</p>
 * <ul>
 *     <li>Creating the <b>voidchest</b> folder if missing</li>
 *     <li>Ensuring at least one default configuration exists</li>
 *     <li>Loading all valid .yml files into memory</li>
 *     <li>Reloading all {@link IVoidChest} instances that depend on a config</li>
 * </ul>
 *
 * <p>All keys are stored in lowercase to allow case-insensitive lookups.</p>
 */
public class VoidChestConfigurationFileCache {

    private final ObjectMap<String, VoidChestConfigurationFile> voidChestConfigurationFiles = new HashObjectMap<>();
    private final ObjectMap<String, FileConfiguration> voidChestFileConfigurations = new HashObjectMap<>();
    private final JavaPlugin mainPlugin;

    public VoidChestConfigurationFileCache(final @NonNull JavaPlugin javaPlugin) {
        this.mainPlugin = javaPlugin;
    }

    /**
     * @return all cached {@link VoidChestConfigurationFile} instances
     */
    @NonNull
    public ObjectMap<String, VoidChestConfigurationFile> getCachedCFGs() {
        return voidChestConfigurationFiles;
    }

    /**
     * Loads all voidchest configuration files into memory.
     *
     * <p>This method will:</p>
     * <ol>
     *     <li>Create the folder if missing</li>
     *     <li>Copy default.yml if no configs exist</li>
     *     <li>Cache all valid .yml files</li>
     *     <li>Reload all affected {@link IVoidChest} entities</li>
     * </ol>
     */
    public void cacheStorages() {
        VoidChestOptionCache.invalidateAll();
        mainPlugin.getComponentLogger().info(Component.text("Loading voidchest configuration files..."));
        voidChestConfigurationFiles.clear();
        voidChestFileConfigurations.clear();

        final File folder = new File(mainPlugin.getDataFolder(), "voidchest");
        ensureFolderAndDefaults(folder);

        final File[] files = folder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null || files.length == 0) {
            return;
        }

        final Optional<EntityManager<IVoidChest>> entityManager =
                EntityManagerRegistry.getInstance().getTyped(IVoidChest.class);

        for (File file : files) {
            final String rawName = file.getName().substring(0, file.getName().length() - 4);
            final String key = rawName.toLowerCase();

            if (!StringUtils.isAlphanumeric(rawName)) {
                mainPlugin.getComponentLogger().error(Component.text("Invalid voidchest file name: " + rawName));
                continue;
            }

            final VoidChestConfigurationFile cfg = new VoidChestConfigurationFile(rawName);
            cfg.setupFile();

            voidChestConfigurationFiles.put(key, cfg);
            voidChestFileConfigurations.put(key, cfg.getFileConfiguration());

            mainPlugin.getComponentLogger().info(Component.text("Loaded voidchest config: " + rawName));

            entityManager.ifPresent(manager ->
                    manager.getAll().forEach(voidChest -> {
                        if (voidChest != null && rawName.equalsIgnoreCase(voidChest.type())) {
                            voidChest.reloadVoidChest();
                        }
                    })
            );
        }
    }

    private void ensureFolderAndDefaults(final @NonNull File folder) {
        if (!folder.exists() && folder.mkdirs()) {
            copyDefaultFile();
            return;
        }

        File[] existing = folder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (existing == null || existing.length == 0) {
            copyDefaultFile();
        }
    }

    private void copyDefaultFile() {
        try {
            mainPlugin.saveResource("voidchest/default.yml", false);
        } catch (IllegalArgumentException ignored) {
            // Resource already exists
        }
    }

    /**
     * Returns a cached configuration file by name.
     *
     * @param name VoidChest type name
     * @return cached file or null if not found
     */
    @Nullable
    public VoidChestConfigurationFile getCachedStorageCFG(final @NonNull String name) {
        return voidChestConfigurationFiles.get(name.toLowerCase());
    }

    /**
     * @param voidChest VoidChest instance
     * @return cached file or null if not found
     */
    @Nullable
    public VoidChestConfigurationFile getCachedStorageCFG(final @NonNull IVoidChest voidChest) {
        return getCachedStorageCFG(voidChest.type());
    }

    /**
     * Returns the cached Bukkit {@link FileConfiguration}.
     *
     * @param name VoidChest type
     * @return cached configuration or null
     */
    @Nullable
    public FileConfiguration getCachedStorageFileCFG(final @NonNull String name) {
        return voidChestFileConfigurations.get(name.toLowerCase());
    }

    /**
     * @param voidChest VoidChest instance
     * @return cached configuration or null
     */
    @Nullable
    public FileConfiguration getCachedStorageFileCFG(final @NonNull IVoidChest voidChest) {
        return getCachedStorageFileCFG(voidChest.type());
    }
}
