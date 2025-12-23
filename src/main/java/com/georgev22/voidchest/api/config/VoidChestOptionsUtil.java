package com.georgev22.voidchest.api.config;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.config.voidchests.VoidChestConfigurationFile;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * Represents a typed, cached configuration option for a VoidChest.
 *
 * <p>Each option defines:</p>
 * <ul>
 *     <li>A primary configuration path</li>
 *     <li>A default value</li>
 *     <li>Optional legacy paths for backward compatibility</li>
 * </ul>
 *
 * <p>Values are resolved from {@link FileConfiguration} only once per VoidChest
 * and then cached in memory for ultra-fast access.</p>
 *
 * @param <T> the value type of this option
 */
public final class VoidChestOptionsUtil<T> {

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_LINKS
            = new VoidChestOptionsUtil<>("Mechanics.links", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_AUTO_SELL
            = new VoidChestOptionsUtil<>("Mechanics.auto sell", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_PURGE_INVALID_ITEMS
            = new VoidChestOptionsUtil<>("Mechanics.purge items", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_CHUNK_COLLECTOR
            = new VoidChestOptionsUtil<>("Mechanics.chunk collector", true);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_ENABLE_WHEN_OWNER_OFFLINE
            = new VoidChestOptionsUtil<>("Mechanics.enable when owner offline", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_CHARGE
            = new VoidChestOptionsUtil<>("Mechanics.charge", true);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_HOLOGRAM
            = new VoidChestOptionsUtil<>("Mechanics.hologram", true);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_BANK
            = new VoidChestOptionsUtil<>("Mechanics.bank", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_PREVENT_CHESTS_NEAR_VOIDCHESTS
            = new VoidChestOptionsUtil<>("Mechanics.prevent.chest near voidchests", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_PREVENT_PLACING_OTHER_VOIDCHESTS_NEAR
            = new VoidChestOptionsUtil<>("Mechanics.prevent.placing other voidchests near", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_PREVENT_VOIDCHESTS_NEAR_CHESTS
            = new VoidChestOptionsUtil<>("Mechanics.prevent.voidchests near chests", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_PREVENT_VOIDCHESTS_IN_SAME_CHUNK
            = new VoidChestOptionsUtil<>("Mechanics.prevent.placing voidchests in same chunk", true);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_PREVENT_CREATIVE_MODE_INTERACTION
            = new VoidChestOptionsUtil<>("Mechanics.prevent.creative mode interaction", true);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_PREVENT_VOIDCHEST_FROM_EXPLODING
            = new VoidChestOptionsUtil<>("Mechanics.prevent.voidchest from exploding", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_BREAK_FAILED_OTHER
            = new VoidChestOptionsUtil<>("Mechanics.break.failed other", true);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_BREAK_PERMISSION_DENY
            = new VoidChestOptionsUtil<>("Mechanics.break.permission deny", true);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_BREAK_SUCCESSFUL
            = new VoidChestOptionsUtil<>("Mechanics.break.successful", true);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_BREAK_STORE_STATS
            = new VoidChestOptionsUtil<>("Mechanics.break.store stats", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_BREAK_DROP_VOIDCHEST_ON_EXPLOSION
            = new VoidChestOptionsUtil<>("Mechanics.break.drop voidchest on explosion", false);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_PLACE_PERMISSION_DENY
            = new VoidChestOptionsUtil<>("Mechanics.place.permission deny", true);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_PLACE_SUCCESSFUL
            = new VoidChestOptionsUtil<>("Mechanics.place.successful", true);

    public static final VoidChestOptionsUtil<Boolean> MECHANICS_PLACE_DELAY_CHECK
            = new VoidChestOptionsUtil<>("Mechanics.place.delay check", false);

    public static final VoidChestOptionsUtil<Integer> MECHANICS_PLACE_LIMIT
            = new VoidChestOptionsUtil<>("Mechanics.place.limit", 5);

    public static final VoidChestOptionsUtil<String> MECHANICS_BLOCK
            = new VoidChestOptionsUtil<>("Mechanics.block", "CHEST");

    public static final VoidChestOptionsUtil<Double> MECHANICS_BOOSTER
            = new VoidChestOptionsUtil<>("Mechanics.booster", 1.0D);

    public static final VoidChestOptionsUtil<String> MECHANICS_SHIFT_CLICK_OPEN
            = new VoidChestOptionsUtil<>("Mechanics.shift click open", "CHEST");

    public static final VoidChestOptionsUtil<String> MECHANICS_RIGHT_CLICK_OPEN
            = new VoidChestOptionsUtil<>("Mechanics.right click open", "MENU");

    public static final VoidChestOptionsUtil<Integer> OPTIONS_LINKS_COUNT
            = new VoidChestOptionsUtil<>("Options.links", 3);

    public static final VoidChestOptionsUtil<Integer> OPTIONS_SELL_INTERVAL
            = new VoidChestOptionsUtil<>("Options.sell interval", 10);

    public static final VoidChestOptionsUtil<List<String>> OPTIONS_FILTER_ITEM_LORE
            = new VoidChestOptionsUtil<>(
            "Options.inventory.filter item lore",
            List.of(
                    "&7Amount:&r &a%amount%",
                    "&7Allow:&r &a%allow%",
                    "&7Ignore Item Metadata:&r &a%ignoreMetadata%",
                    "&7Ignore Item Amount:&r &a%ignoreAmount%",
                    "&8Right Click to Remove"
            ));

    public static final VoidChestOptionsUtil<List<String>> OPTIONS_SHOP_ITEM_LORE
            = new VoidChestOptionsUtil<>("Options.inventory.shop item lore", List.of("&7Price:&r &a%price%"));

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_ITEM_DROP_STRAIGHT_TO_INVENTORY
            = new VoidChestOptionsUtil<>("Options.item.drop straight to inventory", false);

    public static final VoidChestOptionsUtil<String> OPTIONS_ITEM_NAME
            = new VoidChestOptionsUtil<>("Options.item.name", "&c&l%voidchest% VoidChest");

    public static final VoidChestOptionsUtil<List<String>> OPTIONS_ITEM_LORE
            = new VoidChestOptionsUtil<>(
            "Options.item.lore",
            Arrays.asList(
                    "&7Automatically sell and or clear items that",
                    "&7are inside this voidchest. Gain money while being afk!"
            ));

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_ITEM_CUSTOM_INVENTORY_NAME
            = new VoidChestOptionsUtil<>("Options.item.inventory custom name", false);

    public static final VoidChestOptionsUtil<String> OPTIONS_ITEM_INVENTORY_DEFAULT_NAME
            = new VoidChestOptionsUtil<>("Options.item.inventory default name", "⚡ <gradient:gold:yellow><bold>%voidchest%</bold></gradient> ⚡");

    public static final VoidChestOptionsUtil<List<String>> OPTIONS_ITEM_INVENTORY_LORE
            = new VoidChestOptionsUtil<>(
            "Options.item.inventory lore",
            Arrays.asList(
                    "",
                    "<gradient:gold:yellow><bold>Location:</bold></gradient> <white>%location_world%</white> <dark_gray>" +
                            "|</dark_gray> <aqua>X%location_x%</aqua> <dark_gray>" +
                            "|</dark_gray> <green>Y%location_y%</green> <dark_gray>" +
                            "|</dark_gray> <red>Z%location_z%</red>",
                    "<gray>» <gold><bold>Left-Click</bold></gold> <gray>to open the <blue><bold>VoidChest</bold></blue> menu.</gray>",
                    "<gray>» <gold><bold>Right-Click</bold></gold> <gray>to open the <blue><bold>Links</bold></blue> menu.</gray>",
                    ""
            )
    );

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_ITEM_LINKS_CUSTOM_INVENTORY_NAME
            = new VoidChestOptionsUtil<>("Options.item.inventory links custom name", false);

    public static final VoidChestOptionsUtil<List<String>> OPTIONS_ITEM_LINKS_INVENTORY_LORE
            = new VoidChestOptionsUtil<>(
            "Options.item.inventory links lore",
            Arrays.asList(
                    "",
                    "<gradient:gold:yellow><bold>Location:</bold> " +
                            "<white>%location_world%</white>," +
                            " <aqua>X%location_x%</aqua>," +
                            " <green>Y%location_y%</green>, <red>Z%location_z%</red></gradient>",
                    "<gray>» <blue><underlined>Click here</underlined></blue> <gray>to open this link.</gray>",
                    ""
            )
    );


    public static final VoidChestOptionsUtil<String> OPTIONS_SOUND_BREAK
            = new VoidChestOptionsUtil<>("Options.sound.break", "ANVIL_BREAK");

    public static final VoidChestOptionsUtil<String> OPTIONS_SOUND_PLACE
            = new VoidChestOptionsUtil<>("Options.sound.place", "LEVEL_UP");

    public static final VoidChestOptionsUtil<String> OPTIONS_SOUND_UPGRADE_SUCCESS
            = new VoidChestOptionsUtil<>("Options.sound.upgrade success", "ORB_PICKUP");

    public static final VoidChestOptionsUtil<String> OPTIONS_SOUND_UPGRADE_FAILURE
            = new VoidChestOptionsUtil<>("Options.sound.upgrade failure", "VILLAGER_NO");

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_AUTO_SELL_FORCE_DISABLE
            = new VoidChestOptionsUtil<>("Options.auto sell force disable", false);

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_PURGE_INVALID_ITEMS_FORCE_DISABLE
            = new VoidChestOptionsUtil<>("Options.purge items force disable", false);

    public static final VoidChestOptionsUtil<String> OPTIONS_CHUNK_COLLECTOR_MODE
            = new VoidChestOptionsUtil<>("Options.chunk collector.mode", "CHUNK");

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_CHUNK_COLLECTOR_FORCE_DISABLE
            = new VoidChestOptionsUtil<>("Options.chunk collector.force disable", false);

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_CHUNK_COLLECTOR_TRANSFER_NON_SELLABLE
            = new VoidChestOptionsUtil<>("Options.chunk collector.transfer non sellables", false);

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_CHUNK_COLLECTOR_FILTERS_ENABLED
            = new VoidChestOptionsUtil<>("Options.chunk collector.filters", false);

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_CHARGE_FORCE_DISABLE
            = new VoidChestOptionsUtil<>("Options.charge.force disable", false);

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_CHARGE_BREAK_PERSISTENT_ENABLED
            = new VoidChestOptionsUtil<>("Options.charge.break persistent.enabled", true);

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_CHARGE_BREAK_PERSISTENT_SAVE_TIME
            = new VoidChestOptionsUtil<>("Options.charge.break persistent.save time", true);

    public static final VoidChestOptionsUtil<String> OPTIONS_CHARGE_HOLOGRAM_NO_FUEL
            = new VoidChestOptionsUtil<>("Options.charge.hologram.no fuel", "No Fuel");

    public static final VoidChestOptionsUtil<String> OPTIONS_CHARGE_PLACEHOLDER_NO_FUEL
            = new VoidChestOptionsUtil<>("Options.charge.placeholder.no fuel", "No Fuel");

    public static final VoidChestOptionsUtil<Integer> OPTIONS_CHARGE_MAX_TIME
            = new VoidChestOptionsUtil<>("Options.charge.max time", 172800);

    public static final VoidChestOptionsUtil<Integer> OPTIONS_CHARGE_RENEWAL_TIME
            = new VoidChestOptionsUtil<>("Options.charge.renewal time", 3600);

    public static final VoidChestOptionsUtil<Integer> OPTIONS_CHARGE_PRICE
            = new VoidChestOptionsUtil<>("Options.charge.price", 20000);

    public static final VoidChestOptionsUtil<Double> OPTIONS_HOLOGRAM_HEIGHT
            = new VoidChestOptionsUtil<>("Options.hologram.height", 3.9);

    public static final VoidChestOptionsUtil<List<String>> OPTIONS_HOLOGRAM_TEXT
            = new VoidChestOptionsUtil<>(
            "Options.hologram.text",
            Arrays.asList(
                    "&c&l%voidchest% VoidChest",
                    "",
                    "&fOwner: &c%owner%",
                    "&fBoost: &b%booster%x",
                    "&fMoney made: &a$%money%",
                    "&fItems sold: &3%items_sold%",
                    "&fItems purged: &3%items_purged%",
                    "&7VoidChest timeleft: &e%timeleft%",
                    "&7VoidChest charge: &e%charge%",
                    "",
                    "&7&oRight-Click to Open VoidChest"
            )
    );

    public static final VoidChestOptionsUtil<Boolean> OPTIONS_BANK_FORCE_DISABLE
            = new VoidChestOptionsUtil<>("Options.bank force disable", false);

    public static final VoidChestOptionsUtil<List<String>> OPTIONS_PROFIT_CALCULATOR
            = new VoidChestOptionsUtil<>("Options.profit calculator", List.of("voidchest:voidchest:1"));

    public static final VoidChestOptionsUtil<String> OPTIONS_PROFIT_CALCULATOR_SELECTOR
            = new VoidChestOptionsUtil<>("Options.profit calculator selector", "price");

    public static final VoidChestOptionsUtil<List<String>> OPTIONS_DISABLED_WORLDS
            = new VoidChestOptionsUtil<>("Options.disabled worlds", List.of());


    private final String path;
    private final T defaultValue;
    private final String[] legacyPaths;

    /**
     * Creates a new VoidChest configuration option.
     *
     * @param path         the main configuration path
     * @param defaultValue the value to use when the configuration does not define one
     * @param legacyPaths  optional legacy paths for automatic migration support
     */
    private VoidChestOptionsUtil(String path, T defaultValue, String... legacyPaths) {
        this.path = path;
        this.defaultValue = defaultValue;
        this.legacyPaths = legacyPaths;
    }

    /**
     * Returns the cached value of this option for the given VoidChest instance.
     *
     * <p>If the value has not been cached yet, it will be resolved from the configuration
     * file and stored in memory.</p>
     *
     * @param chest the VoidChest instance
     * @return the resolved option value
     */
    public T get(@NonNull IVoidChest chest) {
        return VoidChestOptionCache.get(chest, this);
    }

    /**
     * Returns the cached value of this option for the given VoidChest type.
     *
     * @param type the VoidChest type identifier
     * @return the resolved option value
     */
    public T get(@NonNull String type) {
        return VoidChestOptionCache.get(type, this);
    }

    /**
     * Resolves this option from the configuration file for a specific VoidChest type.
     * This method is used internally by the cache when the value is first requested.
     *
     * @param type the VoidChest type identifier
     * @return the resolved value or the default value if not defined
     */
    T loadFromConfig(@NonNull String type) {

        FileConfiguration cfg = VoidChestAPI.getInstance()
                .voidChestConfigurationFileCache()
                .getCachedStorageFileCFG(type);

        if (cfg == null) {
            return defaultValue;
        }

        String realPath = resolvePath(cfg);
        Object raw = cfg.get(realPath, defaultValue);
        //noinspection unchecked
        return (T) raw;
    }

    /**
     * Resolves this option from the configuration file for a VoidChest instance.
     *
     * @param chest the VoidChest instance
     * @return the resolved value
     */
    T loadFromConfig(@NonNull IVoidChest chest) {
        return loadFromConfig(chest.type());
    }

    /**
     * Determines which configuration path should be used to retrieve this option.
     *
     * <p>The main path is preferred, but if it is not present, legacy paths are checked
     * in order to maintain backward compatibility with older configuration formats.</p>
     *
     * @param cfg the configuration file
     * @return the resolved configuration path
     */
    private String resolvePath(@NonNull FileConfiguration cfg) {
        if (cfg.isSet(path)) return path;
        for (String legacy : legacyPaths) {
            if (cfg.isSet(legacy)) return legacy;
        }
        return path;
    }

    /**
     * Sets and immediately saves a new value for this option for a VoidChest instance.
     *
     * <p>The value is written to disk and should be followed by a cache invalidation
     * if the updated value needs to be reflected immediately in memory.</p>
     *
     * @param voidChest the VoidChest instance
     * @param option    the option to modify
     * @param value     the new value
     * @param <T>       the value type
     */
    public static <T> void setValue(@NonNull IVoidChest voidChest,
                                    @NonNull VoidChestOptionsUtil<T> option,
                                    T value) {
        setValue(voidChest.type(), option, value);
    }

    /**
     * Sets and immediately saves a new value for this option for a VoidChest type.
     *
     * @param type   the VoidChest type identifier
     * @param option the option to modify
     * @param value  the new value
     * @param <T>    the value type
     */
    public static <T> void setValue(@NonNull String type,
                                    @NonNull VoidChestOptionsUtil<T> option,
                                    T value) {

        VoidChestConfigurationFile cfg = VoidChestAPI.getInstance()
                .voidChestConfigurationFileCache()
                .getCachedStorageCFG(type);

        if (cfg == null) return;

        File file = cfg.getFile();
        FileConfiguration fileConfiguration = cfg.getFileConfiguration();

        String realPath = option.resolvePath(fileConfiguration);
        fileConfiguration.set(realPath, value);

        try {
            fileConfiguration.save(file);
        } catch (IOException exception) {
            VoidChestAPI.getInstance().plugin().getLogger().log(
                    Level.SEVERE,
                    "Failed to save configuration file for type: " + type,
                    exception
            );
        }
    }
}