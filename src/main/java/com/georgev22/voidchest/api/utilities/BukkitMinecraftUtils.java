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

    public enum MinecraftVersion {
        V_1_7_R1(new SubVersionRange("1.7", 2, 4)),
        V_1_7_R2(new SubVersionRange("1.7", 5, 7)),
        V_1_7_R3(new SubVersionRange("1.7", 8, 9)),
        V_1_7_R4(new SubVersionRange("1.7", 10)),
        V1_8_R1(new SubVersionRange("1.8", 0, 1)),
        V1_8_R2(new SubVersionRange("1.8", 3, 3)),
        V1_8_R3(new SubVersionRange("1.8", 4, 9)),
        V1_9_R1(new SubVersionRange("1.9", 0, 2)),
        V1_9_R2(new SubVersionRange("1.9", 4)),
        V1_10_R1(new SubVersionRange("1.10", 0, 2)),
        V1_11_R1(new SubVersionRange("1.11", 0, 2)),
        V1_12_R1(new SubVersionRange("1.12", 0, 2)),
        V1_13_R1(new SubVersionRange("1.13", 0, 1)),
        V1_13_R2(new SubVersionRange("1.13", 2)),
        V1_14_R1(new SubVersionRange("1.14", 0, 4)),
        V1_15_R1(new SubVersionRange("1.15", 0, 2)),
        V1_16_R1(new SubVersionRange("1.16", 0, 1)),
        V1_16_R2(new SubVersionRange("1.16", 2, 3)),
        V1_16_R3(new SubVersionRange("1.16", 4, 5)),
        V1_17_R1(new SubVersionRange("1.17", 0, 1)),
        V1_18_R1(new SubVersionRange("1.18", 0, 1)),
        V1_18_R2(new SubVersionRange("1.18", 2)),
        V1_19_R1(new SubVersionRange("1.19", 0, 2)),
        V1_19_R2(new SubVersionRange("1.19", 3)),
        V1_19_R3(new SubVersionRange("1.19", 4)),
        V1_20_R1(new SubVersionRange("1.20", 0, 1)),
        V1_20_R2(new SubVersionRange("1.20", 2)),
        V1_20_R3(new SubVersionRange("1.20", 3, 4)),
        V1_20_R4(new SubVersionRange("1.20", 5, 6)),
        V1_21_R1(new SubVersionRange("1.21", 0, 1)),
        V1_21_R2(new SubVersionRange("1.21", 2, 3)),
        V1_21_R3(new SubVersionRange("1.21", 4)),
        V1_21_R4(new SubVersionRange("1.21", 5)),
        V1_21_R5(new SubVersionRange("1.21", 6, 8)),
        V1_21_R6(new SubVersionRange("1.21", 9, 10)),
        UNKNOWN(new SubVersionRange("UNKNOWN", 0, 0)),
        ;

        private static MinecraftVersion currentVersion;

        private static int versionNumber, releaseNumber;

        static {
            try {
                String bukkitVersion = Bukkit.getServer().getBukkitVersion();
                String[] versionParts = bukkitVersion.split("-")[0].split("\\.");
                if (versionParts.length >= 2) {
                    int majorVersion = Integer.parseInt(versionParts[0]);
                    int minorVersion = Integer.parseInt(versionParts[1]);
                    int patchVersion = versionParts.length >= 3 ? Integer.parseInt(versionParts[2]) : 0;
                    for (MinecraftVersion version : MinecraftVersion.values()) {
                        if (version.subVersionRange.version.equals(majorVersion + "." + minorVersion) &&
                                patchVersion >= version.subVersionRange.start && patchVersion <= version.subVersionRange.end) {
                            currentVersion = version;
                            versionNumber = Integer.parseInt(currentVersion.name().split("_")[1]);
                            releaseNumber = Integer.parseInt(currentVersion.name().split("R")[1]);
                            break;
                        }
                    }
                }
                if (currentVersion == null) {
                    currentVersion = UNKNOWN;
                }
            } catch (Exception e) {
                currentVersion = UNKNOWN;
            }
        }

        private final SubVersionRange subVersionRange;

        MinecraftVersion(SubVersionRange subVersionRange) {
            this.subVersionRange = subVersionRange;
        }

        /**
         * Returns the current minecraft server version.
         *
         * @return the current minecraft server version.
         */
        public static MinecraftVersion getCurrentVersion() {
            return currentVersion;
        }

        /**
         * Get the version number of the Minecraft server.
         *
         * @return The version number of the Minecraft server.
         */
        public static int getVersionNumber() {
            return versionNumber;
        }

        /**
         * Get the release number of the Minecraft server.
         *
         * @return The release number of the Minecraft server.
         */
        public static int getReleaseNumber() {
            return releaseNumber;
        }

        @Contract(pure = true)
        public static @NonNull String getCurrentVersionName() {
            return currentVersion.name();
        }

        @Contract(pure = true)
        public static @NonNull String getCurrentVersionNameVtoLowerCase() {
            return currentVersion.name().replace("V", "v");
        }

        /**
         * Check if the version is above or equal.
         *
         * @param minecraftVersion The {@link MinecraftVersion} to be checked.
         * @return if the minecraft version is above or equal.
         */
        public boolean isAboveOrEqual(@NonNull MinecraftVersion minecraftVersion) {
            return this.ordinal() >= minecraftVersion.ordinal();
        }

        /**
         * Check if the version is above.
         *
         * @param minecraftVersion The {@link MinecraftVersion} to be checked.
         * @return if the minecraft version is above.
         */
        public boolean isAbove(@NonNull MinecraftVersion minecraftVersion) {
            return this.ordinal() > minecraftVersion.ordinal();
        }

        /**
         * Check if the version is below or equal.
         *
         * @param minecraftVersion The {@link MinecraftVersion} to be checked.
         * @return if the minecraft version is below or equal.
         */
        public boolean isBelowOrEqual(@NonNull MinecraftVersion minecraftVersion) {
            return this.ordinal() <= minecraftVersion.ordinal();
        }

        /**
         * Check if the version is below.
         *
         * @param minecraftVersion The {@link MinecraftVersion} to be checked.
         * @return if the minecraft version is below.
         */
        public boolean isBelow(@NonNull MinecraftVersion minecraftVersion) {
            return this.ordinal() < minecraftVersion.ordinal();
        }

        /**
         * Check if the version is equal.
         *
         * @param minecraftVersion The {@link MinecraftVersion} to be checked.
         * @return if the minecraft version is equal.
         */
        public boolean isEqual(@NonNull MinecraftVersion minecraftVersion) {
            return this.ordinal() == minecraftVersion.ordinal();
        }

        /**
         * Get the sub version range.
         *
         * @return The sub version range.
         **/
        public SubVersionRange getSubVersionRange() {
            return subVersionRange;
        }

        public static class SubVersionRange {
            private final String version;
            private final int start;
            private final int end;

            SubVersionRange(String version, int patch) {
                this(version, patch, patch);
            }

            SubVersionRange(String version, int start, int end) {
                this.version = version;
                this.start = start;
                this.end = end;
            }

            public String getVersion() {
                return version;
            }

            public int getStart() {
                return start;
            }

            public int getEnd() {
                return end;
            }
        }
    }

}
