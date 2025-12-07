package com.georgev22.voidchest.api.config;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.utilities.color.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

public enum VoidChestOptionsUtil {

    MECHANICS_LINKS("Mechanics.links", false, Optional.empty()),

    MECHANICS_AUTO_SELL("Mechanics.auto sell", false, Optional.empty()),

    MECHANICS_PURGE_INVALID_ITEMS("Mechanics.purge items", false, Optional.empty()),

    MECHANICS_CHUNK_COLLECTOR("Mechanics.chunk collector", true, Optional.empty()),

    MECHANICS_ENABLE_WHEN_OWNER_OFFLINE("Mechanics.enable when owner offline", false, Optional.empty()),

    MECHANICS_INVENTORY_TITLE_WHITELIST("Mechanics.chest.title whitelist", "%voidchest% whitelist Inventory", Optional.empty()),

    MECHANICS_INVENTORY_TITLE_BLACKLIST("Mechanics.chest.title blacklist", "%voidchest% blacklist Inventory", Optional.empty()),

    MECHANICS_CHARGE("Mechanics.charge", true, Optional.empty()),

    MECHANICS_HOLOGRAM("Mechanics.hologram", true, Optional.empty()),

    MECHANICS_BANK("Mechanics.bank", false, Optional.empty()),

    MECHANICS_PREVENT_CHESTS_NEAR_VOIDCHESTS("Mechanics.prevent.chest near voidchests", false, Optional.empty()),

    MECHANICS_PREVENT_PLACING_OTHER_VOIDCHESTS_NEAR("Mechanics.prevent.placing other voidchests near", false, Optional.empty()),

    MECHANICS_PREVENT_VOIDCHESTS_NEAR_CHESTS("Mechanics.prevent.voidchests near chests", false, Optional.empty()),

    MECHANICS_PREVENT_VOIDCHESTS_IN_SAME_CHUNK("Mechanics.prevent.placing voidchests in same chunk", true, Optional.empty()),

    MECHANICS_PREVENT_CREATIVE_MODE_INTERACTION("Mechanics.prevent.creative mode interaction", true, Optional.empty()),

    MECHANICS_PREVENT_VOIDCHEST_FROM_EXPLODING("Mechanics.prevent.voidchest from exploding", false, Optional.empty()),

    MECHANICS_BREAK_FAILED_OTHER("Mechanics.break.failed other", true, Optional.empty()),

    MECHANICS_BREAK_PERMISSION_DENY("Mechanics.break.permission deny", true, Optional.empty()),

    MECHANICS_BREAK_SUCCESSFUL("Mechanics.break.successful", true, Optional.empty()),

    MECHANICS_BREAK_STORE_STATS("Mechanics.break.store stats", false, Optional.empty()),

    MECHANICS_BREAK_DROP_VOIDCHEST_ON_EXPLOSION("Mechanics.break.drop voidchest on explosion", false, Optional.empty()),

    MECHANICS_PLACE_PERMISSION_DENY("Mechanics.place.permission deny", true, Optional.empty()),

    MECHANICS_PLACE_SUCCESSFUL("Mechanics.place.successful", true, Optional.empty()),

    MECHANICS_PLACE_DELAY_CHECK("Mechanics.place.delay check", false, Optional.empty()),

    MECHANICS_PLACE_LIMIT("Mechanics.place.limit", 5, Optional.empty()),

    MECHANICS_BLOCK("Mechanics.block", "CHEST", Optional.empty()),

    MECHANICS_BOOSTER("Mechanics.booster", 1.0D, Optional.empty()),

    MECHANICS_SHIFT_CLICK_OPEN("Mechanics.shift click open", "CHEST", Optional.empty()),

    MECHANICS_RIGHT_CLICK_OPEN("Mechanics.right click open", "MENU", Optional.empty()),

    OPTIONS_LINKS_COUNT("Options.links", 3, Optional.empty()),

    OPTIONS_SELL_INTERVAL("Options.sell interval", 10L, Optional.empty()),

    OPTIONS_FILTER_ITEM_LORE(
            "Options.inventory.filter item lore",
            List.of(
                    "&7Amount:&r &a%amount%",
                    "&7Allow:&r &a%allow%",
                    "&7Ignore Item Metadata:&r &a%ignoreMetadata%",
                    "&7Ignore Item Amount:&r &a%ignoreAmount%",
                    "&8Right Click to Remove"
            ),
            Optional.empty()
    ),

    OPTIONS_SHOP_ITEM_LORE("Options.inventory.shop item lore", List.of("&7Price:&r &a%price%"), Optional.empty()),

    OPTIONS_ITEM_DROP_STRAIGHT_TO_INVENTORY("Options.item.drop straight to inventory", false, Optional.empty()),

    OPTIONS_ITEM_NAME("Options.item.name", "&c&l%voidchest% VoidChest", Optional.empty()),

    OPTIONS_ITEM_LORE(
            "Options.item.lore",
            Arrays.asList(
                    "&7Automatically sell and or clear items that",
                    "&7are inside this voidchest. Gain money while being afk!"
            ),
            Optional.empty()),

    OPTIONS_ITEM_CUSTOM_INVENTORY_NAME("Options.item.inventory custom name", false, Optional.empty()),

    OPTIONS_ITEM_INVENTORY_DEFAULT_NAME("Options.item.inventory default name", "⚡ <gradient:gold:yellow><bold>%voidchest%</bold></gradient> ⚡", Optional.empty()),

    OPTIONS_ITEM_INVENTORY_LORE(
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
            ),
            Optional.empty()
    ),

    OPTIONS_ITEM_LINKS_CUSTOM_INVENTORY_NAME("Options.item.inventory links custom name", false, Optional.empty()),

    OPTIONS_ITEM_LINKS_INVENTORY_LORE(
            "Options.item.inventory links lore",
            Arrays.asList(
                    "",
                    "<gradient:gold:yellow><bold>Location:</bold> " +
                            "<white>%location_world%</white>," +
                            " <aqua>X%location_x%</aqua>," +
                            " <green>Y%location_y%</green>, <red>Z%location_z%</red></gradient>",
                    "<gray>» <blue><underlined>Click here</underlined></blue> <gray>to open this link.</gray>",
                    ""
            ),
            Optional.empty()
    ),


    OPTIONS_SOUND_BREAK("Options.sound.break", "ANVIL_BREAK", Optional.empty()),

    OPTIONS_SOUND_PLACE("Options.sound.place", "LEVEL_UP", Optional.empty()),

    OPTIONS_SOUND_UPGRADE_SUCCESS("Options.sound.upgrade success", "ORB_PICKUP", Optional.empty()),

    OPTIONS_SOUND_UPGRADE_FAILURE("Options.sound.upgrade failure", "VILLAGER_NO", Optional.empty()),

    OPTIONS_AUTO_SELL_FORCE_DISABLE("Options.auto sell force disable", false, Optional.empty()),

    OPTIONS_PURGE_INVALID_ITEMS_FORCE_DISABLE("Options.purge items force disable", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_MODE("Options.chunk collector.mode", "CHUNK", Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_FORCE_DISABLE("Options.chunk collector.force disable", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_SELL_ITEMS("Options.chunk collector.sell items", true, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_TRANSFER_NON_SELLABLES("Options.chunk collector.transfer non sellables", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_FILTERS_ENABLED("Options.chunk collector.filters", false, Optional.empty()),

    OPTIONS_CHARGE_FORCE_DISABLE("Options.charge.force disable", false, Optional.empty()),

    OPTIONS_CHARGE_BREAK_PERSISTENT_ENABLED("Options.charge.break persistent.enabled", true, Optional.empty()),

    OPTIONS_CHARGE_BREAK_PERSISTENT_SAVE_TIME("Options.charge.break persistent.save time", true, Optional.empty()),

    OPTIONS_CHARGE_HOLOGRAM_NO_FUEL("Options.charge.hologram.no fuel", "No Fuel", Optional.empty()),

    OPTIONS_CHARGE_PLACEHOLDER_NO_FUEL("Options.charge.placeholder.no fuel", "No Fuel", Optional.empty()),

    OPTIONS_CHARGE_MAX_TIME("Options.charge.max time", 172800, Optional.empty()),

    OPTIONS_CHARGE_RENEWAL_TIME("Options.charge.renewal time", 3600, Optional.empty()),

    OPTIONS_CHARGE_PRICE("Options.charge.price", 20000, Optional.empty()),

    OPTIONS_HOLOGRAM_FORCE_DISABLE("Options.hologram.force disable", false, Optional.empty()),

    OPTIONS_HOLOGRAM_HEIGHT("Options.hologram.height", 3.9, Optional.empty()),

    OPTIONS_HOLOGRAM_TEXT(
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
            ), Optional.empty()
    ),

    OPTIONS_BANK_FORCE_DISABLE("Options.bank force disable", false, Optional.empty()),

    OPTIONS_PROFIT_CALCULATOR("Options.profit calculator", List.of("voidchest:voidchest:1"), Optional.empty()),

    OPTIONS_PROFIT_CALCULATOR_SELECTOR("Options.profit calculator selector", "price", Optional.empty()),

    OPTIONS_DISABLED_WORLDS("Options.disabled worlds", List.of(), Optional.empty()),

    ;
    private final String pathName;
    private final Object value;
    private final Optional<String>[] oldPaths;

    /**
     * Constructor for the configuration option.
     *
     * @param pathName the main configuration path.
     * @param value    the default value to fall back on if not set.
     * @param oldPaths optional old paths for backward compatibility.
     */
    @SafeVarargs
    VoidChestOptionsUtil(final String pathName, final Object value, Optional<String>... oldPaths) {
        this.pathName = pathName;
        this.value = value;
        this.oldPaths = oldPaths;
    }

    /**
     * Retrieves the boolean value from the configuration.
     *
     * @param fileConfiguration the configuration file.
     * @return the boolean value, or the default if not set.
     */
    public boolean getBooleanValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getBoolean(getPath(fileConfiguration), Boolean.parseBoolean(String.valueOf(getDefaultValue())));
    }

    /**
     * Retrieves the raw object value from the configuration.
     *
     * @param fileConfiguration the configuration file.
     * @return the object value, or the default if not set.
     */
    public Object getObjectValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.get(getPath(fileConfiguration), getDefaultValue());
    }

    /**
     * Retrieves the string value from the configuration.
     *
     * @param fileConfiguration the configuration file.
     * @return the string value, or the default if not set.
     */
    public String getStringValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getString(getPath(fileConfiguration), String.valueOf(getDefaultValue()));
    }

    /**
     * Retrieves the long value from the configuration.
     *
     * @param fileConfiguration the configuration file.
     * @return the long value, or the default if not set.
     */
    public @NotNull Long getLongValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getLong(getPath(fileConfiguration), Long.parseLong(String.valueOf(getDefaultValue())));
    }

    /**
     * Retrieves the integer value from the configuration.
     *
     * @param fileConfiguration the configuration file.
     * @return the integer value, or the default if not set.
     */
    public @NotNull Integer getIntValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getInt(getPath(fileConfiguration), Integer.parseInt(String.valueOf(getDefaultValue())));
    }

    /**
     * Retrieves the double value from the configuration.
     *
     * @param fileConfiguration the configuration file.
     * @return the double value, or the default if not set.
     */
    public @NotNull Double getDoubleValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getDouble(getPath(fileConfiguration), Double.parseDouble(String.valueOf(getDefaultValue())));
    }

    /**
     * Retrieves a generic list from the configuration.
     *
     * @param fileConfiguration the configuration file.
     * @return the list, or an empty list if not set.
     */
    public @NotNull List<?> getList(@NotNull FileConfiguration fileConfiguration) {
        if (!fileConfiguration.isSet(getPath(fileConfiguration))) {
            Object defaultValue = getDefaultValue();
            return (defaultValue instanceof List<?>) ? (List<?>) defaultValue : new ArrayList<>();
        }
        return fileConfiguration.getList(getPath(fileConfiguration), new ArrayList<>());
    }

    /**
     * Retrieves a list as an {@link ArrayList}.
     *
     * @param fileConfiguration the configuration file.
     * @return an {@link ArrayList} representation of the list.
     */
    public @NotNull ArrayList<?> getArrayList(@NotNull FileConfiguration fileConfiguration) {
        return new ArrayList<>(getList(fileConfiguration));
    }

    /**
     * Retrieves a list of strings from the configuration.
     *
     * @param fileConfiguration the configuration file.
     * @return a list of strings, or an empty list if not set.
     */
    public @NotNull List<String> getStringList(@NotNull FileConfiguration fileConfiguration) {
        if (!fileConfiguration.isSet(getPath(fileConfiguration))) {
            Object defaultValue = getDefaultValue();
            if (defaultValue instanceof List<?> list) {
                List<String> stringList = new ArrayList<>();
                for (Object item : list) {
                    if (item instanceof String str) {
                        stringList.add(str);
                    }
                }
                return stringList;
            }
            return new ArrayList<>();
        }
        return fileConfiguration.getStringList(getPath(fileConfiguration));
    }

    /**
     * Retrieves a list of strings as an {@link ArrayList}.
     *
     * @param fileConfiguration the configuration file.
     * @return an {@link ArrayList} of strings.
     */
    public @NotNull ArrayList<String> getStringArrayList(@NotNull FileConfiguration fileConfiguration) {
        return new ArrayList<>(getStringList(fileConfiguration));
    }

    /**
     * Retrieves a {@link ConfigurationSection} from the configuration.
     *
     * @param fileConfiguration the configuration file.
     * @return the configuration section, or a new empty one if not found.
     */
    public @NotNull ConfigurationSection getConfigurationSection(@NotNull FileConfiguration fileConfiguration) {
        ConfigurationSection section = fileConfiguration.getConfigurationSection(getPath(fileConfiguration));
        return section != null ? section : new YamlConfiguration();
    }

    /**
     * Converts a list of color codes in string form to a list of {@link Color} objects.
     *
     * @param fileConfiguration the configuration file.
     * @return a list of {@link Color} objects.
     */
    public @NotNull List<Color> getColors(@NotNull FileConfiguration fileConfiguration) {
        return getStringList(fileConfiguration).stream()
                .map(Color::from)
                .collect(Collectors.toList());
    }

    /**
     * Resolves the current active configuration path, preferring the current path,
     * and falling back to any valid old paths if the value is not found.
     *
     * @param fileConfiguration the configuration file.
     * @return the resolved path.
     */
    public @NotNull String getPath(@NotNull FileConfiguration fileConfiguration) {
        if (fileConfiguration.get(getDefaultPath()) == null) {
            for (Optional<String> path : getOldPaths()) {
                if (path.isPresent() && fileConfiguration.get(path.get()) != null) {
                    return path.get();
                }
            }
        }
        return getDefaultPath();
    }

    /**
     * Returns the primary/default path for this configuration option.
     *
     * @return the default path.
     */
    @Contract(pure = true)
    public @NotNull String getDefaultPath() {
        return this.pathName;
    }

    /**
     * Returns the old (legacy) paths that can be used to resolve a configuration value.
     *
     * @return an array of optional old paths.
     */
    public Optional<String>[] getOldPaths() {
        return oldPaths;
    }

    /**
     * Returns the default value used when the configuration does not define one.
     *
     * @return the default value.
     */
    public Object getDefaultValue() {
        return value;
    }

    /**
     * Returns an optional string value from the configuration.
     *
     * @param fileConfiguration the configuration file.
     * @return an {@link Optional} containing the string value if present.
     */
    public Optional<String> getOptionalStringValue(FileConfiguration fileConfiguration) {
        return Optional.ofNullable(getStringValue(fileConfiguration));
    }

    public static <T> void setValue(@NotNull FileConfiguration fileConfiguration, @NotNull VoidChestOptionsUtil option, @NotNull T value) {
        String path = option.getPath(fileConfiguration);
        fileConfiguration.set(path, value);
    }

    /**
     * Saves the configuration file to disk.
     *
     * @param fileConfiguration the configuration file.
     * @param file              the file to save.
     */
    public static void saveConfig(@NotNull FileConfiguration fileConfiguration, @NotNull File file) {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            VoidChestAPI.getInstance().plugin().getLogger().log(Level.SEVERE, "Failed to save config file", e);
        }
    }
}