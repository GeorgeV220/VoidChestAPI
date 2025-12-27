package com.georgev22.voidchest.api.config;

import com.georgev22.voidchest.api.utilities.color.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Central configuration access enum for VoidChest.
 *
 * <p>
 * This enum provides:
 * <ul>
 *     <li>Cached path resolution (including legacy paths)</li>
 *     <li>Cached values loaded from {@link org.bukkit.configuration.file.YamlConfiguration}</li>
 *     <li>Zero YAML access during runtime after {@link #reloadAll()}</li>
 * </ul>
 *
 * <p>
 * All configuration values must be reloaded via {@link #reloadAll()} after
 * the configuration file is reloaded.
 */
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

    ECONOMY_CHARGE("economy.charge", "voidchest:vault", Optional.empty()),

    ECONOMY_UPGRADES("economy.upgrades", "voidchest:vault", Optional.empty()),

    ECONOMY_PAYOUT("economy.payout", "voidchest:vault", Optional.empty()),

    VOID_CHEST_ECONOMY_MODE("void chest economy mode", "EXP", Optional.empty()),

    STACKER("stacker", "voidchest:none", Optional.empty()),

    LOCALE("locale", "en_US", Optional.empty()),

    IGNORE_ITEM_META("ignore item meta", true, Optional.empty()),

    BANK("bank", "voidchest:none", Optional.empty()),

    BANK_TNT("bank tnt", "voidchest:none", Optional.empty()),

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

    HOLOGRAM("hologram.plugin", "voidchest:none", Optional.empty()),

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
    private final Object defaultValue;
    private final Optional<String>[] oldPaths;
    private String resolvedPath;
    private Object cachedValue;

    @SafeVarargs
    OptionsUtil(final String pathName, final Object defaultValue, Optional<String>... oldPaths) {
        this.pathName = pathName;
        this.defaultValue = defaultValue;
        this.oldPaths = oldPaths;
    }

    /**
     * Reloads and caches all configuration options.
     *
     * <p>
     * This method must be called:
     * <ul>
     *     <li>On plugin startup</li>
     *     <li>After a configuration reload</li>
     * </ul>
     *
     * <p>
     * After this method is called, all getters operate in O(1) time
     * without accessing the YAML configuration.
     */
    public static void reloadAll() {
        for (OptionsUtil option : values()) {
            option.reload();
        }
    }

    /**
     * Reloads and caches the value of this option.
     *
     * <p>
     * The configuration path is resolved once and stored.
     * The value is then read from the configuration and cached.
     */
    public void reload() {
        resolvedPath = null;
        String path = getPath();
        Object val = fileManager.getConfig().get(path);
        cachedValue = (val != null) ? val : defaultValue;
    }

    /**
     * Returns the cached boolean value of this option.
     *
     * @return the boolean value
     */
    public boolean getBooleanValue() {
        if (cachedValue instanceof Boolean b) {
            return b;
        }
        return Boolean.parseBoolean(String.valueOf(cachedValue));
    }

    /**
     * Returns the cached integer value of this option.
     *
     * @return the integer value
     */
    public @NotNull Integer getIntValue() {
        if (cachedValue instanceof Number n) {
            return n.intValue();
        }
        return Integer.parseInt(String.valueOf(cachedValue));
    }

    /**
     * Returns the cached long value of this option.
     *
     * @return the long value
     */
    public @NotNull Long getLongValue() {
        if (cachedValue instanceof Number n) {
            return n.longValue();
        }
        return Long.parseLong(String.valueOf(cachedValue));
    }

    /**
     * Returns the cached double value of this option.
     *
     * @return the double value
     */
    public @NotNull Double getDoubleValue() {
        if (cachedValue instanceof Number n) {
            return n.doubleValue();
        }
        return Double.parseDouble(String.valueOf(cachedValue));
    }

    /**
     * Returns the cached string value of this option.
     *
     * @return the string value
     */
    public String getStringValue() {
        return String.valueOf(cachedValue);
    }

    /**
     * Returns the cached string list value of this option.
     *
     * @return a list of strings, or an empty list if the value is not a list
     */
    @SuppressWarnings("unchecked")
    public @NotNull List<String> getStringList() {
        return cachedValue instanceof List
                ? (List<String>) cachedValue
                : List.of();
    }

    /**
     * Converts the cached string list into a list of {@link Color} objects.
     *
     * @return a list of parsed colors
     */
    public @NotNull List<Color> getColors() {
        return getStringList().stream()
                .map(Color::from)
                .collect(Collectors.toList());
    }

    /**
     * Returns the cached string value wrapped in an {@link Optional}.
     *
     * @return an optional containing the string value
     */
    public @NotNull Optional<String> getOptionalStringValue() {
        return Optional.ofNullable(getStringValue());
    }

    /**
     * Resolves and returns the configuration path for this option.
     *
     * <p>
     * The resolution order is:
     * <ol>
     *     <li>Cached path</li>
     *     <li>Current path</li>
     *     <li>Legacy paths</li>
     * </ol>
     *
     * <p>
     * The resolved path is cached after the first lookup.
     *
     * @return the resolved configuration path
     */
    public @NotNull String getPath() {
        if (resolvedPath != null) {
            return resolvedPath;
        }

        String base = "Options." + pathName;
        if (fileManager.getConfig().get(base) != null) {
            return resolvedPath = base;
        }

        for (Optional<String> old : oldPaths) {
            if (old.isPresent()) {
                String oldPath = "Options." + old.get();
                if (fileManager.getConfig().get(oldPath) != null) {
                    return resolvedPath = oldPath;
                }
            }
        }

        return resolvedPath = base;
    }

    /**
     * Returns the default (current) path name without the "Options." prefix.
     *
     * @return the default path name
     */
    @Contract(pure = true)
    public @NotNull String getDefaultPath() {
        return this.pathName;
    }

    /**
     * Returns the legacy paths associated with this option.
     *
     * @return an array of legacy paths
     */
    public Optional<String>[] getOldPaths() {
        return oldPaths;
    }

    /**
     * Returns the default value of this option.
     *
     * @return the default value
     */
    public Object getDefaultValue() {
        return defaultValue;
    }
}