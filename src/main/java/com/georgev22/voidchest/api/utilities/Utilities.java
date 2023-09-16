package com.georgev22.voidchest.api.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Utilities {

    /**
     * Converts the given double `input` to a string representation.
     * If the input value has no decimal part, it returns the integer part as a string.
     * Otherwise, it returns the full double value as a string.
     *
     * @param input The double value to be converted.
     * @return The string representation of the given double value.
     */
    @Contract(pure = true)
    public static @NotNull String convertDoubleInt(final double input) {
        return input % 1d == 0 ? String.valueOf((int) input) : String.valueOf(input);
    }

    /**
     * Checks if the chunk containing the specified `location` is loaded in the world.
     *
     * @param loc The location to check.
     * @return `true` if the chunk is loaded, otherwise `false`.
     */
    public static boolean isChunkLoaded(final @NotNull Location loc) {
        return loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4);
    }

    /**
     * Splits the given `value` of type BigDecimal into a list of BigDecimal values.
     * Each element in the list represents a portion of the original value that does not exceed Double.MAX_VALUE.
     *
     * @param value The BigDecimal value to split.
     * @return A list of BigDecimal values representing portions of the original value.
     */
    @NotNull
    public static List<BigDecimal> splitBigDecimal(@NotNull BigDecimal value) {
        List<BigDecimal> splitValues = new ArrayList<>();

        if (value.compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) <= 0) {
            splitValues.add(value);
        } else {
            BigDecimal maxValue = BigDecimal.valueOf(Double.MAX_VALUE);
            BigDecimal remainingValue = value;

            while (remainingValue.compareTo(maxValue) > 0) {
                splitValues.add(maxValue);
                remainingValue = remainingValue.subtract(maxValue);
            }

            if (remainingValue.compareTo(BigDecimal.ZERO) > 0) {
                splitValues.addAll(splitBigDecimal(remainingValue));
            }
        }

        return splitValues;
    }

    /**
     * Formats the given `number` into a human-readable string representation of money using the US locale.
     *
     * @param number The number to be formatted as money.
     * @return The formatted money string.
     */
    @NotNull
    public static String formatMoney(Number number) {
        return formatMoney(number, Locale.US);
    }

    /**
     * Formats the given `amount` into a human-readable string representation of money using the specified `locale`.
     *
     * @param amount The number to be formatted as money.
     * @param locale The locale to use for formatting the money string.
     * @return The formatted money string.
     */
    @NotNull
    public static String formatMoney(Number amount, Locale locale) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(amount);
    }

    /**
     * Formats the given `input` double value into a human-readable string using the US locale.
     *
     * @param input The double value to be formatted.
     * @return The formatted string representing the number.
     */
    public static String formatNumber(double input) {
        return NumberFormat.getInstance(Locale.US).format(input);
    }

    public static @NotNull String formatNumber(Number number, String format) {
        return formatNumber(number, Locale.US, format);
    }

    public static @NotNull String formatNumber(Number number, Locale locale, String format) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setGroupingSeparator(' ');

        DecimalFormat decimalFormat = new DecimalFormat(format, symbols);
        return decimalFormat.format(number);
    }

    /**
     * Compares two Chunks to check if they are the same.
     *
     * @param chunkA The first Chunk to compare.
     * @param chunkB The second Chunk to compare.
     * @return `true` if the Chunks are equal, otherwise `false`.
     */
    public static boolean compareChunks(final @NotNull Chunk chunkA, final @NotNull Chunk chunkB) {
        if (!chunkA.getWorld().equals(chunkB.getWorld())) {
            return false;
        }
        if (chunkA.getX() != chunkB.getX()) {
            return false;
        }
        return chunkA.getZ() == chunkB.getZ();
    }

    /**
     * Generates a deterministic UUID from a given seed using the SHA-256 hash function.
     *
     * @param seed the input seed used to generate the UUID
     * @return a UUID generated from the seed
     */
    @Contract("_ -> new")
    public static @NotNull UUID generateUUID(@NotNull String seed) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(seed.getBytes(StandardCharsets.UTF_8));
            byte[] hash = md.digest();
            long msb = 0;
            long lsb = 0;
            for (int i = 0; i < 8; i++)
                msb = (msb << 8) | (hash[i] & 0xff);
            for (int i = 8; i < 16; i++)
                lsb = (lsb << 8) | (hash[i] & 0xff);
            return new UUID(msb, lsb);
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Compares two ItemStacks to check if they are the same.
     *
     * @param item1 The first ItemStack to compare.
     * @param item2 The second ItemStack to compare.
     * @return `true` if the ItemStacks are equal, otherwise `false`.
     */
    public static boolean compareItemStacks(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) {
            return false;
        }

        if (item1 == item2) {
            return true;
        }

        if (item1.getType() != item2.getType()) {
            return false;
        }

        if (item1.getDurability() != item2.getDurability()) {
            return false;
        }

        if (item1.hasItemMeta() != item2.hasItemMeta()) {
            return false;
        }

        if (item1.hasItemMeta() && item2.hasItemMeta()) {
            ItemMeta meta1 = item1.getItemMeta();
            ItemMeta meta2 = item2.getItemMeta();

            if (!Bukkit.getItemFactory().equals(meta1, meta2)) {
                return false;
            }
        }

        return item1.getAmount() == item2.getAmount();
    }

    /**
     * Checks if the current environment is "Folia" by attempting to load the "io.papermc.paper.threadedregions.RegionizedServer" class.
     *
     * @return `true` if the environment is "Folia," otherwise `false`.
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
     * Splits the given `value` of type BigInteger into a list of BigInteger values.
     * Each element in the list represents a portion of the original value that does not exceed Integer.MAX_VALUE.
     *
     * @param value The BigInteger value to split.
     * @return A list of BigInteger values representing portions of the original value.
     */
    public static @NotNull List<BigInteger> splitBigInteger(@NotNull BigInteger value) {
        List<BigInteger> splitValues = new ArrayList<>();

        if (value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0) {
            splitValues.add(value);
        } else {
            BigInteger maxValue = BigInteger.valueOf(Integer.MAX_VALUE);
            BigInteger remainingValue = value;

            while (remainingValue.compareTo(maxValue) > 0) {
                splitValues.add(maxValue);
                remainingValue = remainingValue.subtract(maxValue);
            }

            if (remainingValue.compareTo(BigInteger.ZERO) > 0) {
                splitValues.addAll(splitBigInteger(remainingValue));
            }
        }

        return splitValues;
    }
}
