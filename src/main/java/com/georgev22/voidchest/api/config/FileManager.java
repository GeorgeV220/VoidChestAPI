package com.georgev22.voidchest.api.config;

import com.georgev22.voidchest.api.VoidChestAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class FileManager {
    private static FileManager instance;
    private final JavaPlugin voidChestPlugin = VoidChestAPI.getInstance().plugin();
    private CFG config;
    private CFG filters;
    private CFG upgrades;
    private CFG linksGUI;
    private CFG voidChestsGUI;
    private CFG shopGUI;

    private FileManager() {
    }

    public static FileManager getInstance() {
        return instance == null ? (instance = new FileManager()) : instance;
    }

    public void loadFiles() throws Exception {
        this.config = new CFG("config",
                this.voidChestPlugin.getDataFolder(),
                true,
                true,
                voidChestPlugin.getLogger(),
                voidChestPlugin.getClass()
        );
        this.filters = new CFG("gui/filters",
                this.voidChestPlugin.getDataFolder(),
                true,
                true,
                voidChestPlugin.getLogger(),
                voidChestPlugin.getClass()
        );
        this.upgrades = new CFG(
                "gui/upgrades",
                this.voidChestPlugin.getDataFolder(),
                true,
                true,
                voidChestPlugin.getLogger(),
                voidChestPlugin.getClass()
        );
        this.linksGUI = new CFG(
                "gui/links",
                this.voidChestPlugin.getDataFolder(),
                true,
                true,
                voidChestPlugin.getLogger(),
                voidChestPlugin.getClass()
        );
        this.voidChestsGUI = new CFG(
                "gui/voidchests",
                this.voidChestPlugin.getDataFolder(),
                true,
                true,
                voidChestPlugin.getLogger(),
                voidChestPlugin.getClass()
        );
        this.shopGUI = new CFG(
                "gui/shop",
                this.voidChestPlugin.getDataFolder(),
                true,
                true,
                voidChestPlugin.getLogger(),
                voidChestPlugin.getClass()
        );
    }

    public CFG configCFG() {
        return this.config;
    }

    public CFG filtersCFG() {
        return this.filters;
    }

    public CFG upgradesCFG() {
        return this.upgrades;
    }

    public CFG linksGUICFG() {
        return this.linksGUI;
    }

    public CFG voidChestsGUICFG() {
        return this.voidChestsGUI;
    }

    public CFG shopGUICFG() {
        return this.shopGUI;
    }

    public FileConfiguration getConfig() {
        return this.config.getFileConfiguration();
    }

    public FileConfiguration getFilters() {
        return this.filters.getFileConfiguration();
    }

    public FileConfiguration getUpgrades() {
        return this.upgrades.getFileConfiguration();
    }

    public FileConfiguration getLinksGUI() {
        return this.linksGUI.getFileConfiguration();
    }

    public FileConfiguration getVoidChestsGUI() {
        return this.voidChestsGUI.getFileConfiguration();
    }

    public FileConfiguration getShopGUI() {
        return this.shopGUI.getFileConfiguration();
    }

}

