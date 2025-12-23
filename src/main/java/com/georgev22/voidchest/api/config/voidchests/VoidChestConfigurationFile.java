package com.georgev22.voidchest.api.config.voidchests;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.exceptions.VoidFileAlreadySetupException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Represents a single VoidChest configuration file.
 *
 * <p>This class is responsible for:</p>
 * <ul>
 *     <li>Creating the file if missing</li>
 *     <li>Copying the default configuration</li>
 *     <li>Loading and reloading YAML data</li>
 * </ul>
 *
 * <p>A configuration file may only be set up once.</p>
 */
public class VoidChestConfigurationFile {

    private final String name;
    private FileConfiguration fileConfiguration;
    private File file;
    private boolean setup;

    public VoidChestConfigurationFile(final @NonNull String name) {
        this.name = name;
    }

    /**
     * @return the underlying configuration file
     */
    public File getFile() {
        return file;
    }

    /**
     * Initializes the configuration file.
     *
     * @throws VoidFileAlreadySetupException if called more than once
     */
    public void setupFile() {
        if (setup) {
            throw new VoidFileAlreadySetupException(
                    "The void file with the name: " + name + " can't be setup twice!",
                    "If you believe this is an issue, contact GeorgeV22."
            );
        }

        final JavaPlugin plugin = VoidChestAPI.getInstance().plugin();
        final File folder = new File(plugin.getDataFolder(), "voidchest");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        this.file = new File(folder, name + ".yml");

        if (!file.exists()) {
            try {
                Files.copy(
                        Objects.requireNonNull(plugin.getResource("voidchest/default.yml")),
                        file.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create voidchest file: " + name, e);
            }
        }

        reloadFile();
        setup = true;
    }

    /**
     * Reloads the configuration from disk.
     */
    public void reloadFile() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * @return loaded {@link FileConfiguration}
     */
    @NonNull
    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
