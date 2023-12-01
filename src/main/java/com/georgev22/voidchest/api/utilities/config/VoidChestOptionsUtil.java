package com.georgev22.voidchest.api.utilities.config;

import com.georgev22.library.utilities.Color;
import com.georgev22.library.yaml.file.FileConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum VoidChestOptionsUtil {

    MECHANICS_AUTO_SELL("Mechanics.auto sell", false, Optional.empty()),

    MECHANICS_PURGE_INVALID_ITEMS("Mechanics.purge items", false, Optional.empty()),

    MECHANICS_CHUNK_COLLECTOR("Mechanics.chunk collector", true, Optional.empty()),

    MECHANICS_ENABLE_WHEN_OWNER_OFFLINE("Mechanics.enable when owner offline", false, Optional.empty()),

    MECHANICS_INFINITE_INVENTORY("Mechanics.infinite inventory", false, Optional.empty()),

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

    OPTIONS_SELL_INTERVAL("Options.sell interval", 10L, Optional.empty()),

    OPTIONS_TITLE("Options.inventory.title", "%voidchest% Inventory", Optional.empty()),

    OPTIONS_SIZE("Options.inventory.size", 27, Optional.empty()),

    OPTIONS_INFINITE_ITEM_LORE("Options.inventory.infinite item lore", List.of("&7Amount:&r &a%amount%"), Optional.empty()),

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

    OPTIONS_SOUND_BREAK("Options.sound.break", "ANVIL_BREAK", Optional.empty()),

    OPTIONS_AUTO_SELL_FORCE_DISABLE("Options.auto sell force disable", false, Optional.empty()),

    OPTIONS_PURGE_INVALID_ITEMS_FORCE_DISABLE("Options.purge items force disable", false, Optional.empty()),

    OPTIONS_SOUND_PLACE("Options.sound.place", "LEVEL_UP", Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_FORCE_DISABLE("Options.chunk collector.force disable", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_SELL_ITEMS("Options.chunk collector.sell items", true, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_TRANSFER_NON_SELLABLES("Options.chunk collector.transfer non sellables", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_INSTANT_COLLECTOR("Options.chunk collector.instant collector.enabled", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_INSTANT_COLLECTOR_FORCE_DISABLE("Options.chunk collector.instant collector.force disable", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_BLACK_LIST("Options.chunk collector.blacklist.enabled", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_BLACK_LIST_FORCE_DISABLE("Options.chunk collector.blacklist.force disable", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_BLACK_LIST_FORCE_BLACK_LIST("Options.chunk collector.blacklist.force blacklist", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_BLACK_LIST_ITEMS("Options.chunk collector.blacklist.items", Arrays.asList("MOB_SPAWNER", "DRAGON_EGG"), Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_WHITE_LIST("Options.chunk collector.whitelist.enabled", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_WHITE_LIST_FORCE_DISABLE("Options.chunk collector.whitelist.force disable", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_WHITE_LIST_FORCE_WHITE_LIST("Options.chunk collector.whitelist.force whitelist", false, Optional.empty()),

    OPTIONS_CHUNK_COLLECTOR_WHITE_LIST_ITEMS("Options.chunk collector.whitelist.items", Arrays.asList("DIAMOND", "IRON_INGOT"), Optional.empty()),

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

    ;
    private final String pathName;
    private final Object value;
    private final Optional<String>[] oldPaths;

    @SafeVarargs
    VoidChestOptionsUtil(final String pathName, final Object value, Optional<String>... oldPaths) {
        this.pathName = pathName;
        this.value = value;
        this.oldPaths = oldPaths;
    }

    public boolean getBooleanValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getBoolean(getPath(fileConfiguration), Boolean.parseBoolean(String.valueOf(getDefaultValue())));
    }

    public Object getObjectValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.get(getPath(fileConfiguration), getDefaultValue());
    }

    public String getStringValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getString(getPath(fileConfiguration), String.valueOf(getDefaultValue()));
    }

    public @NotNull Long getLongValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getLong(getPath(fileConfiguration), Long.parseLong(String.valueOf(getDefaultValue())));
    }

    public @NotNull Integer getIntValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getInt(getPath(fileConfiguration), Integer.parseInt(String.valueOf(getDefaultValue())));
    }

    public @NotNull Double getDoubleValue(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getDouble(getPath(fileConfiguration), Double.parseDouble(String.valueOf(getDefaultValue())));
    }

    public @NotNull List<String> getStringList(@NotNull FileConfiguration fileConfiguration) {
        return fileConfiguration.getStringList(getPath(fileConfiguration));
    }

    public @NotNull ArrayList<String> getArrayStringList(@NotNull FileConfiguration fileConfiguration) {
        if (!fileConfiguration.isSet(getPath(fileConfiguration))) {
            return new ArrayList<>();
        }
        return new ArrayList<>(getStringList(fileConfiguration));
    }

    /**
     * Converts and return a String List of color codes to a List of Color classes that represent the colors.
     *
     * @return a List of Color classes that represent the colors.
     */
    public @NotNull List<Color> getColors(FileConfiguration fileConfiguration) {
        return getStringList(fileConfiguration).stream().map(Color::from).collect(Collectors.toList());
    }

    /**
     * Returns the path.
     *
     * @return the path.
     */
    public @NotNull String getPath(@NotNull FileConfiguration fileConfiguration) {
        if (fileConfiguration.get(getDefaultPath()) == null) {
            for (Optional<String> path : getOldPaths()) {
                if (path.isPresent()) {
                    if (fileConfiguration.get(path.get()) != null) {
                        return path.get();
                    }
                }
            }
        }
        return getDefaultPath();
    }

    /**
     * Returns the default path.
     *
     * @return the default path.
     */
    @Contract(pure = true)
    public @NotNull String getDefaultPath() {
        return this.pathName;
    }

    /**
     * Returns the old path if it exists.
     *
     * @return the old path if it exists.
     */
    public Optional<String>[] getOldPaths() {
        return oldPaths;
    }

    /**
     * Returns the default value if the path have no value.
     *
     * @return the default value if the path have no value.
     */
    public Object getDefaultValue() {
        return value;
    }

    public Optional<String> getOptionalStringValue(FileConfiguration fileConfiguration) {
        return Optional.ofNullable(getStringValue(fileConfiguration));
    }
}
