package com.georgev22.voidchest.api.config;

import com.georgev22.voidchest.api.utilities.color.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum OptionsUtil {

    DEBUG("debug", false, Optional.empty()),

    SECRET("secret", "SECRET HERE", Optional.empty()),

    COMMAND_VOIDCHEST("commands.voidchest", true, Optional.empty()),

    COMMAND_VOIDCHEST_ADMIN("commands.voidchest admin", true, Optional.empty()),

    DATABASE_HOST("database.SQL.host", "localhost", Optional.empty()),

    DATABASE_PORT("database.SQL.port", 3306, Optional.empty()),

    DATABASE_USER("database.SQL.user", "youruser", Optional.empty()),

    DATABASE_PASSWORD("database.SQL.password", "yourpassword", Optional.empty()),

    DATABASE_DATABASE("database.SQL.database", "VoidChest", Optional.empty()),

    DATABASE_USERS_TABLE_NAME("database.SQL.users table name", "voidchest_users", Optional.empty()),

    DATABASE_VOIDCHESTS_TABLE_NAME("database.SQL.voidchests table name", "voidchest_voidchests", Optional.empty()),

    DATABASE_FILE_NAME("database.SQL.SQLite file name", "VoidChest", Optional.empty()),

    DATABASE_MONGO_HOST("database.MongoDB.host", "localhost", Optional.empty()),

    DATABASE_MONGO_PORT("database.MongoDB.port", 27017, Optional.empty()),

    DATABASE_MONGO_USER("database.MongoDB.user", "youruser", Optional.empty()),

    DATABASE_MONGO_PASSWORD("database.MongoDB.password", "yourpassword", Optional.empty()),

    DATABASE_MONGO_DATABASE("database.MongoDB.database", "VoidChest", Optional.empty()),

    DATABASE_MONGO_USERS_COLLECTION("database.MongoDB.users collection", "voidchest_users", Optional.empty()),

    DATABASE_MONGO_VOIDCHEST_COLLECTION("database.MongoDB.voidchest collection", "voidchest_voidchests", Optional.empty()),

    DATABASE_TYPE("database.type", "File", Optional.empty()),

    EXPERIMENTAL_FEATURES("experimental features", false, Optional.empty()),

    METRICS("metrics", true, Optional.empty()),

    DISCORD("discord", false, Optional.empty()),

    UPDATER("updater.enabled", true, Optional.empty()),

    ECONOMY_CHARGE("economy.charge", "Vault", Optional.of("economy")),

    ECONOMY_UPGRADES("economy.upgrades", "Vault", Optional.of("economy")),

    ECONOMY_PAYOUT("economy.payout", "Vault", Optional.of("economy")),

    VOID_CHEST_ECONOMY_MODE("void chest economy mode", "EXP", Optional.empty()),

    STACKER("stacker", "None", Optional.empty()),

    LOCALE("locale", "en_US", Optional.empty()),

    IGNORE_ITEM_META("ignore item meta", true, Optional.empty()),

    BANK("bank", "None", Optional.empty()),

    BANK_TNT("bank tnt", "None", Optional.empty()),

    TIME_FORMAT_SECOND("time format.second", "second", Optional.empty()),

    TIME_FORMAT_SECONDS("time format.seconds", "seconds", Optional.empty()),

    TIME_FORMAT_MINUTE("time format.minute", "minute", Optional.empty()),

    TIME_FORMAT_MINUTES("time format.minutes", "minutes", Optional.empty()),

    TIME_FORMAT_HOUR("time format.hour", "hour", Optional.empty()),

    TIME_FORMAT_HOURS("time format.hours", "hours", Optional.empty()),

    TIME_FORMAT_DAY("time format.day", "day", Optional.empty()),

    TIME_FORMAT_DAYS("time format.days", "days", Optional.empty()),

    TIME_FORMAT_INVALID("time format.invalid", "Invalid", Optional.empty()),

    MONEY_FORMAT("money format", "##.####", Optional.empty()),

    HOLOGRAM("hologram.plugin", "None", Optional.empty()),

    HOLOGRAM_UPDATE_INTERVAL("hologram.update interval", 1, Optional.empty()),

    CHUNK_SEE_Y_CENTRAL("chunk see.Y central", 0, Optional.empty()),

    CHUNK_SEE_Y_UP("chunk see.Y up", 0, Optional.empty()),

    CHUNK_SEE_Y_DOWN("chunk see.Y down", 0, Optional.empty()),

    CHUNK_SEE_EFFECT_TYPE("chunk see.effect.type", "VILLAGER_HAPPY", Optional.empty()),

    CHUNK_SEE_EFFECT_INTERVAL("chunk see.effect.interval", 20, Optional.empty()),

    SELL_MESSAGE("sell message.enabled", false, Optional.empty()),

    SELL_MESSAGE_INTERVAL("sell message.interval", 60, Optional.empty()),

    SELL_MESSAGE_OFFLINE("sell message.offline.enabled", false, Optional.empty()),

    SELL_MESSAGE_OFFLINE_MESSAGE_DELAY("sell message.offline.message delay", 50, Optional.empty()),

    LOAD_DELAY("load delay", 2L, Optional.empty()),
    SAVE_INTERVAL("save interval", 20, Optional.empty()),
    FILTER_MODE("filter mode", "ALLOW", Optional.empty()),
    ;
    private static final FileManager fileManager = FileManager.getInstance();
    private final String pathName;
    private final Object value;
    private final Optional<String>[] oldPaths;

    @SafeVarargs
    OptionsUtil(final String pathName, final Object value, Optional<String>... oldPaths) {
        this.pathName = pathName;
        this.value = value;
        this.oldPaths = oldPaths;
    }

    public boolean getBooleanValue() {
        return fileManager.getConfig().getBoolean(getPath(), Boolean.parseBoolean(String.valueOf(getDefaultValue())));
    }

    public Object getObjectValue() {
        return fileManager.getConfig().get(getPath(), getDefaultValue());
    }

    public String getStringValue() {
        return fileManager.getConfig().getString(getPath(), String.valueOf(getDefaultValue()));
    }

    public @NotNull Long getLongValue() {
        return fileManager.getConfig().getLong(getPath(), Long.parseLong(String.valueOf(getDefaultValue())));
    }

    public @NotNull Integer getIntValue() {
        return fileManager.getConfig().getInt(getPath(), Integer.parseInt(String.valueOf(getDefaultValue())));
    }

    public @NotNull Double getDoubleValue() {
        return fileManager.getConfig().getDouble(getPath(), Double.parseDouble(String.valueOf(getDefaultValue())));
    }

    public @NotNull List<String> getStringList() {
        return fileManager.getConfig().getStringList(getPath());
    }

    /**
     * Converts and return a String List of color codes to a List of Color classes that represent the colors.
     *
     * @return a List of Color classes that represent the colors.
     */
    public @NotNull List<Color> getColors() {
        return getStringList().stream().map(Color::from).collect(Collectors.toList());
    }

    public boolean getBooleanValue(String arg) {
        return fileManager.getConfig().getBoolean(String.format(getPath(), arg), Boolean.parseBoolean(String.valueOf(getDefaultValue())));
    }

    public Object getObjectValue(String arg) {
        return fileManager.getConfig().get(String.format(getPath(), arg), getDefaultValue());
    }

    public String getStringValue(String arg) {
        return fileManager.getConfig().getString(String.format(getPath(), arg), String.valueOf(getDefaultValue()));
    }

    public @NotNull Long getLongValue(String arg) {
        return fileManager.getConfig().getLong(String.format(getPath(), arg), Long.parseLong(String.valueOf(getDefaultValue())));
    }

    public @NotNull Integer getIntValue(String arg) {
        return fileManager.getConfig().getInt(String.format(getPath(), arg), Integer.parseInt(String.valueOf(getDefaultValue())));
    }

    public @NotNull Double getDoubleValue(String arg) {
        return fileManager.getConfig().getDouble(String.format(getPath(), arg), Double.parseDouble(String.valueOf(getDefaultValue())));
    }

    public @NotNull List<String> getStringList(String arg) {
        return fileManager.getConfig().getStringList(String.format(getPath(), arg));
    }

    /**
     * Converts and return a String List of color codes to a List of Color classes that represent the colors.
     *
     * @return a List of Color classes that represent the colors.
     */
    public @NotNull List<Color> getColors(String arg) {
        return getStringList(arg).stream().map(Color::from).collect(Collectors.toList());
    }

    /**
     * Returns the path.
     *
     * @return the path.
     */
    public @NotNull String getPath() {
        if (fileManager.getConfig().get("Options." + getDefaultPath()) == null) {
            for (Optional<String> path : getOldPaths()) {
                if (path.isPresent()) {
                    if (fileManager.getConfig().get("Options." + path.get()) != null) {
                        return "Options." + path.get();
                    }
                }
            }
        }
        return "Options." + getDefaultPath();
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

    public Optional<String> getOptionalStringValue() {
        return Optional.ofNullable(getStringValue());
    }
}
