package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.datastructures.maps.TreeObjectMap;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class Utils {

    /**
     * Converts a given number of seconds into a formatted time string using provided labels.
     *
     * @param input        The number of seconds to convert.
     * @param secondInput  Singular form of "second".
     * @param secondsInput Plural form of "seconds".
     * @param minuteInput  Singular form of "minute".
     * @param minutesInput Plural form of "minutes".
     * @param hourInput    Singular form of "hour".
     * @param hoursInput   Plural form of "hours".
     * @param dayInput     Singular form of "day".
     * @param daysInput    Plural form of "days".
     * @param invalidInput Message to return if the input is invalid.
     * @return A formatted time string.
     */
    public static String convertSeconds(long input, String secondInput,
                                        String secondsInput,
                                        String minuteInput,
                                        String minutesInput,
                                        String hourInput,
                                        String hoursInput,
                                        String dayInput,
                                        String daysInput,
                                        String invalidInput) {
        if (input < 0) {
            System.out.println(
                    "An attempt to convert a negative number was made for: " + input + ", making the number absolute.");
            input = Math.abs(input);
        }

        final StringBuilder builder = new StringBuilder();

        boolean comma = false;

        /* Days */
        final long days = TimeUnit.SECONDS.toDays(input);
        if (days > 0) {
            builder.append(days).append(" ").append(days == 1 ? dayInput : daysInput);
            comma = true;
        }

        /* Hours */
        final long hours = (TimeUnit.SECONDS.toHours(input) - TimeUnit.DAYS.toHours(days));
        if (hours > 0) {
            if (comma) {
                builder.append(", ");
            }
            builder.append(hours).append(" ").append(hours == 1 ? hourInput : hoursInput);
            comma = true;
        }

        /* Minutes */
        final long minutes = (TimeUnit.SECONDS.toMinutes(input) - TimeUnit.HOURS.toMinutes(hours)
                - TimeUnit.DAYS.toMinutes(days));
        if (minutes > 0) {
            if (comma) {
                builder.append(", ");
            }
            builder.append(minutes).append(" ").append(minutes == 1 ? minuteInput : minutesInput);
            comma = true;
        }

        /* Seconds */
        final long seconds = (TimeUnit.SECONDS.toSeconds(input) - TimeUnit.MINUTES.toSeconds(minutes)
                - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days));
        if (seconds > 0) {
            if (comma) {
                builder.append(", ");
            }
            builder.append(seconds).append(" ").append(seconds == 1 ? secondInput : secondsInput);
        }

        /* Result */
        final String result = builder.toString();
        return result.isEmpty() ? invalidInput : result;
    }

    /**
     * Checks if the input is of type long
     *
     * @param input input to check if it is long
     * @return <code>true</code> if the input is long,
     * <code>false</code> if it is not
     */
    public static boolean isLong(final String input) {
        return Longs.tryParse(input.trim()) != null;
    }

    /**
     * Checks if the input is of type double
     *
     * @param input input to check if it is double
     * @return <code>true</code> if the input is double,
     * <code>false</code> if it is not
     */
    public static boolean isDouble(final String input) {
        return Doubles.tryParse(input.trim()) != null;
    }

    /**
     * Checks if the input is of type int
     *
     * @param input input to check if it is int
     * @return <code>true</code> if the input is int,
     * <code>false</code> if it is not
     */
    public static boolean isInt(final String input) {
        return Ints.tryParse(input.trim()) != null;
    }

    /**
     * Checks if the input is a List
     *
     * @param input input to check if it is a List
     * @return <code>true</code> if the input is a List,
     * <code>false</code> if it is not
     */
    public static boolean isList(final Object input) {
        return input instanceof List;
    }

    public static boolean isList(final @NotNull FileConfiguration file, final String path) {
        return Utils.isList(file.get(path));
    }

    /**
     * Translates all the placeholders of the string from the map
     *
     * @param str        the input string to translate the placeholders on
     * @param map        the map that contains all the placeholders with the replacement
     * @param ignoreCase if it is <code>true</code> all the placeholders will be replaced
     *                   in ignore case
     * @return the new string with the placeholders replaced
     */
    public static String placeHolder(String str, final Map<String, String> map, final boolean ignoreCase) {
        if (str == null) throw new IllegalArgumentException("str cannot be null");
        if (map == null) {
            return str;
        }
        for (final Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            str = ignoreCase ? replaceIgnoreCase(str, entry.getKey(), entry.getValue())
                    : str.replace(entry.getKey(), entry.getValue());
        }
        return str;
    }

    /**
     * Replaces a string, without distinguishing between lowercase and uppercase
     *
     * @param text         the input string
     * @param searchString the string to be replaced
     * @param replacement  the replacement of the string
     * @return the new string with the replacement
     */
    public static String replaceIgnoreCase(final String text, String searchString, final String replacement) {

        if (text == null || text.isEmpty()) {
            return text;
        }
        if (searchString == null || searchString.isEmpty()) {
            return text;
        }
        if (replacement == null) {
            return text;
        }

        int max = -1;

        final String searchText = text.toLowerCase();
        searchString = searchString.toLowerCase();
        int start = 0;
        int end = searchText.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        final int replacementLength = searchString.length();
        int increase = replacement.length() - replacementLength;
        increase = Math.max(increase, 0);
        increase *= 16;

        final StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != -1) {
            buf.append(text, start, end).append(replacement);
            start = end + replacementLength;
            if (--max == 0) {
                break;
            }
            end = searchText.indexOf(searchString, start);
        }
        return buf.append(text, start, text.length()).toString();
    }

    /**
     * Translates all the placeholders of the string from the map
     *
     * @param array      the input array of string to translate the placeholders on
     * @param map        the map that contains all the placeholders with the replacement
     * @param ignoreCase if it is <code>true</code> all the placeholders will be replaced
     *                   in ignore case
     * @return the new string array with the placeholders replaced
     */
    public static String @NotNull [] placeHolder(final String[] array, final Map<String, String> map, final boolean ignoreCase) {
        if (array == null) throw new IllegalArgumentException("array cannot be null");
        if (Arrays.stream(array).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("array cannot have null elements");

        final String[] newArray = Arrays.copyOf(array, array.length);
        if (map == null) {
            return newArray;
        }
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = placeHolder(newArray[i], map, ignoreCase);
        }
        return newArray;
    }

    /**
     * Translates all the placeholders of the string from the map
     *
     * @param coll       the input string list to translate the placeholders on
     * @param map        the map that contains all the placeholders with the replacement
     * @param ignoreCase if it is <code>true</code> all the placeholders will be replaced
     *                   in ignore case
     * @return the new string list with the placeholders replaced
     */
    public static List<String> placeHolder(final List<String> coll, final Map<String, String> map,
                                           final boolean ignoreCase) {
        if (coll == null) throw new IllegalArgumentException("coll cannot be null");
        if (coll.stream().anyMatch(Objects::isNull))
            throw new IllegalArgumentException("coll cannot have null elements");
        return map == null ? coll
                : coll.stream().map(str -> placeHolder(str, map, ignoreCase)).collect(Collectors.toList());
    }

    /**
     * Formats a number to a specific Locale
     *
     * @param lang  the desired locale
     * @param input the input number
     * @return the formatted String
     */
    public static String formatNumber(Locale lang, double input) {
        if (lang == null) throw new IllegalArgumentException("lang cannot be null");
        return NumberFormat.getInstance(lang).format(input);
    }

    /**
     * Formats a number to the US Locale
     *
     * @param input the input number
     * @return the formatted String
     */
    public static String formatNumber(double input) {
        return formatNumber(Locale.US, input);
    }

    /**
     * Converts a number to Roman Number format
     *
     * @param number number to convert
     * @return a string of the number at Roman Number format
     */
    public static String toRoman(int number) {
        if (number <= 0) {
            return String.valueOf(number);
        }
        TreeObjectMap<Integer, String> map = ObjectMap.newTreeObjectMap();
        map
                .append(1000, "M")
                .append(900, "CM")
                .append(500, "D")
                .append(400, "CD")
                .append(100, "C")
                .append(90, "XC")
                .append(50, "L")
                .append(40, "XL")
                .append(10, "X")
                .append(9, "IX")
                .append(5, "V")
                .append(4, "IV")
                .append(1, "I")
        ;
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
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

    /**
     * Splits the given `value` of type BigInteger into a list of BigInteger values.
     * Each element in the list represents a portion of the original value that does not exceed the limit.
     *
     * @param value The BigInteger value to split.
     * @param limit The limit to split the value into.
     * @return A list of BigInteger values representing portions of the original value.
     */
    public static @NotNull List<BigInteger> splitBigIntegerTo(@NotNull BigInteger value, int limit) {
        List<BigInteger> splitValues = new ArrayList<>();

        if (value.compareTo(BigInteger.valueOf(limit)) <= 0) {
            splitValues.add(value);
        } else {
            BigInteger maxValue = BigInteger.valueOf(limit);
            BigInteger remainingValue = value;

            while (remainingValue.compareTo(maxValue) > 0) {
                splitValues.add(maxValue);
                remainingValue = remainingValue.subtract(maxValue);
            }

            if (remainingValue.compareTo(BigInteger.ZERO) > 0) {
                splitValues.addAll(splitBigIntegerTo(remainingValue, limit));
            }
        }

        return splitValues;
    }

    /**
     * Returns the floor of the given double value.
     *
     * @param num The double value to get the floor of.
     * @return The floor of the given double value.
     */
    public static int floor(double num) {
        int floor = (int) num;
        return (double) floor == num ? floor : floor - (int) (Double.doubleToRawLongBits(num) >>> 63);
    }

    public static class Cooldown {
        private static final ObjectMap<String, Cooldown> cooldownManagerObjectMap = ObjectMap.newHashObjectMap();
        private long start;
        private final int timeInSeconds;
        private final UUID id;
        private final String cooldownName;

        public Cooldown(UUID id, String cooldownName, int timeInSeconds) {
            this.id = id;
            this.cooldownName = cooldownName;
            this.timeInSeconds = timeInSeconds;
        }

        public static boolean isInCooldown(UUID id, String cooldownName) {
            if (Cooldown.getTimeLeft(id, cooldownName) >= 1) {
                return true;
            }
            Cooldown.stop(id, cooldownName);
            return false;
        }

        private static void stop(UUID id, String cooldownName) {
            cooldownManagerObjectMap.remove(id + cooldownName);
        }

        private static Cooldown getCooldown(@NotNull UUID id, String cooldownName) {
            return cooldownManagerObjectMap.get(id + cooldownName);
        }

        public static int getTimeLeft(UUID id, String cooldownName) {
            Cooldown cooldown = Cooldown.getCooldown(id, cooldownName);
            int f = -1;
            if (cooldown != null) {
                long now = System.currentTimeMillis();
                long cooldownTime = cooldown.start;
                int r = (int) (now - cooldownTime) / 1000;
                f = (r - cooldown.timeInSeconds) * -1;
            }
            return f;
        }

        public void start() {
            this.start = System.currentTimeMillis();
            cooldownManagerObjectMap.put(this.id.toString() + this.cooldownName, this);
        }

        public static @NotNull String getTimeLeft(int secondTime) {
            TimeZone tz = Calendar.getInstance().getTimeZone();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            df.setTimeZone(tz);
            return df.format(new Date(secondTime * 1000L));
        }

        public static ObjectMap<String, Cooldown> getCooldowns() {
            return cooldownManagerObjectMap;
        }

        public static ObjectMap<String, Cooldown> appendToCooldowns(ObjectMap<String, Cooldown> cooldownObjectMap) {
            return cooldownManagerObjectMap.append(cooldownObjectMap);
        }

    }

    public static abstract class Callback<T> {

        public abstract T onSuccess();

        public abstract T onFailure();

        public T onFailure(Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
