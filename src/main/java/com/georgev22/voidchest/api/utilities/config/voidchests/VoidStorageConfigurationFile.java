/**
 * This interface defines the contract for handling configuration files related to Void Storages.
 * It includes methods for retrieving the file, setting up the file, reloading the file, and obtaining the file configuration.
 */
package com.georgev22.voidchest.api.utilities.config.voidchests;

import com.georgev22.library.yaml.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Interface for managing configuration files related to Void Storages.
 */
public interface VoidStorageConfigurationFile {

    /**
     * Retrieves the file associated with this configuration.
     *
     * @return The file associated with this configuration.
     */
    File getFile();

    /**
     * Sets up the configuration file, creating it if it does not exist.
     */
    void setupFile();

    /**
     * Reloads the configuration file, updating its content from the disk.
     */
    void reloadFile();

    /**
     * Retrieves the FileConfiguration associated with this configuration.
     *
     * @return The FileConfiguration associated with this configuration.
     */
    @NotNull
    FileConfiguration getFileConfiguration();
}
