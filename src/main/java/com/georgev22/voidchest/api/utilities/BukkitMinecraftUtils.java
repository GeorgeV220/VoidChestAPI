package com.georgev22.voidchest.api.utilities;

import com.google.common.collect.Lists;
import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BukkitMinecraftUtils {

    private static boolean join = false;
    private static String disableJoinMessage = "";

    /**
     * Returns a translated string.
     *
     * @param msg The message to be translated
     * @return A translated message
     */
    public static @NonNull String colorize(final String msg) {
        String unEditedMessage = msg;
        if (unEditedMessage == null) {
            throw new IllegalArgumentException("The string can't be null!");
        }
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(unEditedMessage);
        while (matcher.find()) {
            String hexCode = unEditedMessage.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }

            unEditedMessage = unEditedMessage.replace(hexCode, builder.toString());
            matcher = pattern.matcher(unEditedMessage);
        }
        //noinspection deprecation
        return ChatColor.translateAlternateColorCodes('&', unEditedMessage);
    }

    public static String stripColor(final String msg) {
        if (msg == null) {
            throw new IllegalArgumentException("The string can't be null!");
        }
        //noinspection deprecation
        return ChatColor.stripColor(msg);
    }

    /**
     * Returns a translated string array.
     *
     * @param array Array of messages
     * @return A translated message array
     */
    public static String @NonNull [] colorize(final String... array) {
        if (array == null) {
            throw new IllegalArgumentException("The string array can't be null!");
        }
        if (Arrays.stream(array).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("The string array can't have null elements!");
        }
        final String[] newarr = Arrays.copyOf(array, array.length);
        for (int i = 0; i < newarr.length; i++) {
            newarr[i] = colorize(newarr[i]);
        }
        return newarr;
    }

    /**
     * Returns a translated string collection.
     *
     * @param coll The collection to be translated
     * @return A translated message
     */
    public static @NonNull List<String> colorize(final List<String> coll) {
        if (coll == null) {
            throw new IllegalArgumentException("The string collection can't be null!");
        }
        if (coll.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("The string collection can't have null elements!");
        }
        final List<String> newColl = Lists.newArrayList(coll);
        newColl.replaceAll(BukkitMinecraftUtils::colorize);
        return newColl;
    }

    public static ItemStack @NonNull [] getItems(final @NonNull ItemStack item, int amount) {

        final int maxSize = item.getMaxStackSize();
        if (amount <= maxSize) {
            item.setAmount(Math.max(amount, 1));
            return new ItemStack[]{item};
        }
        final List<ItemStack> resultItems = Lists.newArrayList();
        do {
            item.setAmount(Math.min(amount, maxSize));
            resultItems.add(new ItemStack(item));
            amount = amount >= maxSize ? amount - maxSize : 0;
        } while (amount != 0);
        return resultItems.toArray(new ItemStack[0]);
    }

    public static @NonNull ItemStack resetItemMeta(final @NonNull ItemStack item) {
        final ItemStack copy = item.clone();
        copy.setItemMeta(Bukkit.getItemFactory().getItemMeta(copy.getType()));
        return copy;
    }

    /**
     * Register listeners
     *
     * @param listeners Class that implements Listener interface
     */
    public static void registerListeners(Plugin plugin, Listener @NonNull ... listeners) {
        final PluginManager pm = Bukkit.getPluginManager();
        for (final Listener listener : listeners) {
            pm.registerEvents(listener, plugin);
        }
    }

    /**
     * Disallow or allow the player login to the server with a custom message.
     *
     * @param b       True -> disallow player login. False -> allow player login.
     * @param message The message to display when the player is disallowed to login.
     * @since v5.0
     */
    public static void disallowLogin(boolean b, String message) {
        join = b;
        disableJoinMessage = message;
    }

    /**
     * @return true if the player login is disallowed or false if the player login is allowed.
     * @since v5.0
     */
    public static boolean isLoginDisallowed() {
        return join;
    }

    /**
     * @return The message to display when the player is disallowed to login.
     * @since v5.0
     */
    public static String getDisallowLoginMessage() {
        return disableJoinMessage;
    }

    /**
     * Checks if the chunk containing the specified `location` is loaded in the world.
     *
     * @param loc The location to check.
     * @return `true` if the chunk is loaded, otherwise `false`.
     */
    public static boolean isChunkLoaded(final Location loc) {
        if (loc == null) {
            return false;
        }
        if (loc.getWorld() == null) {
            return false;
        }
        return loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4);
    }

    /**
     * Checks if the current environment is {@code Paper} by attempting to load the {@code io.papermc.paper.threadedregions.RegionizedServer} class.
     *
     * @return {@code true} if the environment is {@code Folia} otherwise {@code false}
     */
    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Checks if the current environment is {@code Paper} by attempting to load the
     * {@code com.destroystokyo.paper.PaperConfig"} and {@code io.papermc.paper.configuration.Configuration} classes
     *
     * @return {@code true} if the environment is {@code Paper} otherwise {@code false}
     */
    public static boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (ClassNotFoundException ignored) {
        }
        try {
            Class.forName("io.papermc.paper.configuration.Configuration");
            return true;
        } catch (ClassNotFoundException ignored) {
        }

        return false;
    }

    public static void suggestPaper(@NonNull Plugin plugin, @NonNull Level logLevel) {
        if (!isPaper()) {
            String pluginName = plugin.getDescription().getName();
            Logger logger = plugin.getLogger();
            logger.log(logLevel, "====================================================");
            logger.log(logLevel, " " + pluginName + " works better if you use Paper ");
            logger.log(logLevel, " as your server software. ");
            logger.log(logLevel, "  ");
            logger.log(logLevel, " Paper offers significant performance improvements,");
            logger.log(logLevel, " bug fixes, security enhancements and optional");
            logger.log(logLevel, " features for server owners to enhance their server.");
            logger.log(logLevel, "  ");
            logger.log(logLevel, " Paper includes Timings v2, which is significantly");
            logger.log(logLevel, " better at diagnosing lag problems over v1.");
            logger.log(logLevel, "  ");
            logger.log(logLevel, " All of your plugins should still work, and the");
            logger.log(logLevel, " Paper community will gladly help you fix any issues.");
            logger.log(logLevel, "  ");
            logger.log(logLevel, " Join the Paper Community @ https://papermc.io");

            logger.log(logLevel, "====================================================");
        }
    }

    /**
     * Represents a Minecraft server version using a numeric format (major.minor.patch).
     */
    public static final class MinecraftVersion implements Comparable<MinecraftVersion> {

        private final int major;
        private final int minor;
        private final int patch;

        /**
         * The current server version, parsed once during class initialization.
         */
        private static final MinecraftVersion CURRENT;

        static {
            CURRENT = parse(Bukkit.getServer().getBukkitVersion());
        }

        /**
         * Constructs a new {@link MinecraftVersion}.
         *
         * @param major the major version (e.g. 1 or 26)
         * @param minor the minor version (e.g. 21 or 1)
         * @param patch the patch version (e.g. 4 or 11)
         */
        public MinecraftVersion(int major, int minor, int patch) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
        }

        /**
         * Returns the current Minecraft server version.
         *
         * @return the parsed server version
         */
        public static MinecraftVersion getCurrent() {
            return CURRENT;
        }

        /**
         * Parses a Bukkit version string into a {@link MinecraftVersion}.
         * <p>
         * Examples of supported formats:
         * <ul>
         *     <li>{@code 1.21.4-R0.1-SNAPSHOT}</li>
         *     <li>{@code 26.1.1}</li>
         * </ul>
         *
         * @param bukkitVersion the raw version string from Bukkit
         * @return a parsed {@link MinecraftVersion}, or {@code 0.0.0} if parsing fails
         */
        public static MinecraftVersion parse(String bukkitVersion) {
            try {
                String versionPart = bukkitVersion.split("-")[0];
                String[] parts = versionPart.split("\\.");

                int major = parts.length > 0 ? Integer.parseInt(parts[0]) : 0;
                int minor = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
                int patch = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;

                return new MinecraftVersion(major, minor, patch);
            } catch (Exception e) {
                return new MinecraftVersion(0, 0, 0);
            }
        }

        /**
         * Compares this version to another version.
         *
         * @param other the other version
         * @return a negative value if lower, positive if higher, 0 if equal
         */
        @Override
        public int compareTo(MinecraftVersion other) {
            if (this.major != other.major) {
                return Integer.compare(this.major, other.major);
            }
            if (this.minor != other.minor) {
                return Integer.compare(this.minor, other.minor);
            }
            return Integer.compare(this.patch, other.patch);
        }

        /**
         * Checks if this version is greater than or equal to the given version.
         * Patch is ignored (assumes 0).
         *
         * @param major the major version
         * @param minor the minor version
         * @return {@code true} if this version is >= given version
         */
        public boolean isAtLeast(int major, int minor) {
            return compareTo(new MinecraftVersion(major, minor, 0)) >= 0;
        }

        /**
         * Checks if this version is greater than or equal to the given version.
         *
         * @param major the major version
         * @param minor the minor version
         * @param patch the patch version
         * @return {@code true} if this version is >= given version
         */
        public boolean isAtLeast(int major, int minor, int patch) {
            return compareTo(new MinecraftVersion(major, minor, patch)) >= 0;
        }

        /**
         * Checks if this version is strictly lower than the given version.
         * Patch is ignored (assumes 0).
         *
         * @param major the major version
         * @param minor the minor version
         * @return {@code true} if this version is < given version
         */
        public boolean isBelow(int major, int minor) {
            return isBelow(major, minor, 0);
        }


        public boolean isBelow(int major, int minor, int patch) {
            return compareTo(new MinecraftVersion(major, minor, patch)) < 0;
        }

        /**
         * Checks if this version is within a range:
         * {@code [min, max)} (inclusive lower bound, exclusive upper bound).
         *
         * @param minMajor minimum major version
         * @param minMinor minimum minor version
         * @param maxMajor maximum major version
         * @param maxMinor maximum minor version
         * @return {@code true} if within the specified range
         */
        public boolean isBetween(
                int minMajor, int minMinor,
                int maxMajor, int maxMinor
        ) {
            return isAtLeast(minMajor, minMinor)
                    && isBelow(maxMajor, maxMinor);
        }

        /**
         * @return the major version
         */
        public int getMajor() {
            return major;
        }

        /**
         * @return the minor version
         */
        public int getMinor() {
            return minor;
        }

        /**
         * @return the patch version
         */
        public int getPatch() {
            return patch;
        }

        /**
         * Returns the version in {@code major.minor.patch} format.
         *
         * @return string representation of this version
         */
        @Override
        public String toString() {
            return major + "." + minor + "." + patch;
        }
    }


}
