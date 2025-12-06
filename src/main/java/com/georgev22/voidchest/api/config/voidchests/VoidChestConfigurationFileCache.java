package com.georgev22.voidchest.api.config.voidchests;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.maps.HashObjectMap;
import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.EntityManager;
import com.georgev22.voidchest.api.registry.EntityManagerRegistry;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import com.georgev22.voidchest.api.utilities.BukkitMinecraftUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;

public class VoidChestConfigurationFileCache {
    private final ObjectMap<String, VoidChestConfigurationFile> voidChestConfigurationFiles = new HashObjectMap<>();

    private final JavaPlugin mainPlugin = VoidChestAPI.getInstance().plugin();

    public @NotNull ObjectMap<String, VoidChestConfigurationFile> getCachedCFGs() {
        return voidChestConfigurationFiles;
    }

    public void cacheStorages() {

        BukkitMinecraftUtils.debug(mainPlugin, "Attempting to load voidchest files in memory.");
        this.voidChestConfigurationFiles.clear();

        final File folder = new File(mainPlugin.getDataFolder(), "voidchest");
        if (!folder.exists()) {
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            this.copyFilesToFolder();
            BukkitMinecraftUtils.debug(mainPlugin, "VoidChests folder didn't exist, creating one and a default.yml for it.");
        } else if (Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".yml"))).length == 0) {
            this.copyFilesToFolder();
            BukkitMinecraftUtils.debug(mainPlugin, "VoidChests folder is empty, creating a default.yml for it.");
        }

        for (File file : Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".yml")))) {
            final String voidName = file.getName().substring(0, file.getName().length() - 4);

            if (voidName.isEmpty() || !StringUtils.isAlphanumeric(voidName)) {
                BukkitMinecraftUtils.debug(mainPlugin, "Invalid voidchest file name: " + voidName);
                continue;
            }

            final VoidChestConfigurationFile voidChestFile = new VoidChestConfigurationFile(voidName);
            voidChestFile.setupFile();
            this.voidChestConfigurationFiles.put(voidName, voidChestFile);
            BukkitMinecraftUtils.debug(mainPlugin, "VoidChest file: " + voidName + " successfully loaded in memory.");

            @NotNull Optional<EntityManager<IVoidChest>> voidEntityManager = EntityManagerRegistry.getInstance().getTyped(IVoidChest.class);
            voidEntityManager.ifPresent(iVoidChestEntityManager -> iVoidChestEntityManager.getAll()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(voidChest -> Objects.nonNull(voidChest.type()))
                    .forEach(voidChest -> {
                        if (voidChest.type().equalsIgnoreCase(voidName)) {
                            voidChest.reloadVoidChest();
                        }
                    }));
        }

    }

    private void copyFilesToFolder() {
        final File someFile = new File(mainPlugin.getDataFolder(),
                "voidchest" + File.separator + "default.yml");
        try {
            //noinspection ResultOfMethodCallIgnored
            someFile.createNewFile();
        } catch (IOException e) {
            mainPlugin.getLogger().log(Level.SEVERE, "Error creating default.yml", e);
        }
        mainPlugin.saveResource("voidchest" + File.separator + "default.yml", true);
    }

    /**
     * Returns a cached or attempts to cache a possible VoidChestFile object from
     * a specific key.
     *
     * @param name The name of the voidchest file.
     * @return The cached VoidChestFile object.
     */
    @Nullable
    public VoidChestConfigurationFile getCachedStorageCFG(final String name) {
        VoidChestConfigurationFile cache = this.voidChestConfigurationFiles.get(name);
        if (cache != null) {
            return cache;
        }
        for (Entry<String, VoidChestConfigurationFile> entry : this.voidChestConfigurationFiles.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Nullable
    public VoidChestConfigurationFile getCachedStorageCFG(final @NotNull IVoidChest storage) {
        return this.getCachedStorageCFG(storage.type());
    }
}
