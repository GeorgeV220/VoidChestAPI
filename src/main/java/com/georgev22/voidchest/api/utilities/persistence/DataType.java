package com.georgev22.voidchest.api.utilities.persistence;

import org.jetbrains.annotations.NotNull;

public abstract class DataType<T> {

    public static final DataType<Byte> BYTE = new DataType<>(Byte.class) {
        @Override
        public @NotNull Byte convert(@NotNull Object input) {
            return Byte.parseByte(input.toString());
        }
    };

    public static final DataType<Short> SHORT = new DataType<>(Short.class) {
        @Override
        public @NotNull Short convert(@NotNull Object input) {
            return Short.parseShort(input.toString());
        }
    };

    public static final DataType<Integer> INTEGER = new DataType<>(Integer.class) {
        @Override
        public @NotNull Integer convert(@NotNull Object input) {
            return Integer.parseInt(input.toString());
        }
    };

    public static final DataType<Long> LONG = new DataType<>(Long.class) {
        @Override
        public @NotNull Long convert(@NotNull Object input) {
            return Long.parseLong(input.toString());
        }
    };

    public static final DataType<Float> FLOAT = new DataType<>(Float.class) {
        @Override
        public @NotNull Float convert(@NotNull Object input) {
            return Float.parseFloat(input.toString());
        }
    };

    public static final DataType<Double> DOUBLE = new DataType<>(Double.class) {
        @Override
        public @NotNull Double convert(@NotNull Object input) {
            return Double.parseDouble(input.toString());
        }
    };

    public static final DataType<Boolean> BOOLEAN = new DataType<>(Boolean.class) {
        @Override
        public Boolean convert(@NotNull Object input) {
            if (input instanceof Boolean b) {
                return b;
            }

            if (input instanceof Number num) {
                return (num.intValue() != 0);
            }

            String str = input.toString().trim().toLowerCase();

            return switch (str) {
                case "true", "1" -> Boolean.TRUE;
                case "false", "0" -> Boolean.FALSE;
                default -> throw new IllegalArgumentException(
                        "Invalid boolean value: " + input
                );
            };
        }
    };

    public static final DataType<String> STRING = new DataType<>(String.class) {
        @Override
        public String convert(@NotNull Object input) {
            return input.toString();
        }
    };

    public static final DataType<byte[]> BYTE_ARRAY = new DataType<>(byte[].class) {
        @Override
        public byte[] convert(@NotNull Object input) {
            return (byte[]) input;
        }
    };

    public static final DataType<int[]> INTEGER_ARRAY = new DataType<>(int[].class) {
        @Override
        public int[] convert(@NotNull Object input) {
            return (int[]) input;
        }
    };

    public static final DataType<long[]> LONG_ARRAY = new DataType<>(long[].class) {
        @Override
        public long[] convert(@NotNull Object input) {
            return (long[]) input;
        }
    };

    private final Class<T> type;

    private DataType(Class<T> type) {
        this.type = type;
    }

    public Class<T> getPrimitiveClass() {
        return type;
    }

    @Override
    public String toString() {
        return type.getSimpleName();
    }

    public abstract T convert(@NotNull Object input);
}