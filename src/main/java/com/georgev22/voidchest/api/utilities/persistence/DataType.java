package com.georgev22.voidchest.api.utilities.persistence;

import org.jetbrains.annotations.Nullable;

public enum DataType {
    BYTE(Byte.class),
    SHORT(Short.class),
    INTEGER(Integer.class),
    LONG(Long.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    BOOLEAN(Boolean.class),
    STRING(String.class),
    BYTE_ARRAY(byte[].class),
    INTEGER_ARRAY(int[].class),
    LONG_ARRAY(long[].class);

    private final Class<?> primitiveClass;

    DataType(Class<?> primitiveClass) {
        this.primitiveClass = primitiveClass;
    }

    public Class<?> getPrimitiveClass() {
        return primitiveClass;
    }

    @SuppressWarnings("unchecked")
    public <T> @Nullable T convert(Object input) {
        try {
            return switch (this) {
                case BYTE -> (T) (Byte) Byte.parseByte(input.toString());
                case SHORT -> (T) (Short) Short.parseShort(input.toString());
                case INTEGER -> (T) (Integer) Integer.parseInt(input.toString());
                case LONG -> (T) (Long) Long.parseLong(input.toString());
                case FLOAT -> (T) (Float) Float.parseFloat(input.toString());
                case DOUBLE -> (T) (Double) Double.parseDouble(input.toString());
                case BOOLEAN -> {
                    if (input instanceof Boolean) {
                        yield (T) input;
                    } else if (input instanceof Number num) {
                        yield (T) Boolean.valueOf(num.intValue() != 0);
                    } else {
                        String str = input.toString().trim().toLowerCase();
                        if (str.equals("true") || str.equals("1")) {
                            yield (T) Boolean.TRUE;
                        } else if (str.equals("false") || str.equals("0")) {
                            yield (T) Boolean.FALSE;
                        } else {
                            throw new IllegalArgumentException("Invalid boolean value: " + input);
                        }
                    }
                }
                case STRING -> (T) input.toString();
                case BYTE_ARRAY, INTEGER_ARRAY, LONG_ARRAY -> (T) input;
            };
        } catch (Exception e) {
            return null;
        }
    }
}
