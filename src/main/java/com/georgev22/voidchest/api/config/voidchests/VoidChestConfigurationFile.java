package com.georgev22.voidchest.api.config.voidchests;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.exceptions.VoidFileAlreadySetupException;
import com.georgev22.voidchest.api.config.voidchests.VoidChestConfigurationFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.logging.Level;

public class VoidChestConfigurationFile {

    private final String name;
    private FileConfiguration fileConfiguration;
    private File file;
    private boolean setup = false;

    public VoidChestConfigurationFile(final String name) {
        this.name = name;

    }

    public File getFile() {
        return file;
    }

    public void setupFile() {

        if (setup) {
            throw new VoidFileAlreadySetupException(
                    "The void file with the name: " + this.name + " can't be setup twice!",
                    "If you believe this is an issue, contact GeorgeV22.");
        }

        final JavaPlugin plugin = VoidChestAPI.getInstance().plugin();

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        this.file = new File(plugin.getDataFolder(), "voidchest" + File.separator + this.name + ".yml");

        if (!this.file.exists()) {
            try {
                Files.copy(Objects.requireNonNull(plugin.getResource("voidchest/default.yml")), this.file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (final IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Error while creating a new file:", e);
            }
        }
        this.reloadFile();
        setup = true;
    }

    public void reloadFile() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    @NotNull
    public FileConfiguration getFileConfiguration() {
        return this.fileConfiguration;
    }
}
