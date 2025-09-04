package com.georgev22.voidchest.api.utilities.persistence;

import com.georgev22.voidchest.api.utilities.NamespacedKey;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class NBTDataContainerWrapper implements DataContainerWrapper {

    private final ReadWriteNBT readWriteNBT;

    public NBTDataContainerWrapper(ReadWriteNBT readWriteNBT) {
        this.readWriteNBT = readWriteNBT;
    }

    @Override
    public boolean has(NamespacedKey key, DataType type) {
        String fullKey = key.getNamespace();
        if (type == DataType.BOOLEAN) {
            return readWriteNBT.hasTag(fullKey) && readWriteNBT.getType(fullKey) == NBTType.NBTTagByte;
        }
        return readWriteNBT.hasTag(fullKey, getNBTType(type));
    }

    @Override
    public void set(NamespacedKey key, DataType type, Object value) {
        String fullKey = key.getNamespace();
        Object converted = type.convert(value);
        if (converted == null) {
            throw new IllegalArgumentException("Failed to convert value for type " + type + ": " + value);
        }

        try {
            switch (type) {
                case BYTE -> readWriteNBT.setByte(fullKey, (Byte) converted);
                case SHORT -> readWriteNBT.setShort(fullKey, (Short) converted);
                case INTEGER -> readWriteNBT.setInteger(fullKey, (Integer) converted);
                case LONG -> readWriteNBT.setLong(fullKey, (Long) converted);
                case FLOAT -> readWriteNBT.setFloat(fullKey, (Float) converted);
                case DOUBLE -> readWriteNBT.setDouble(fullKey, (Double) converted);
                case BOOLEAN -> {
                    Boolean bool = (Boolean) converted;
                    readWriteNBT.setByte(fullKey, bool ? (byte) 1 : (byte) 0);
                }
                case STRING -> readWriteNBT.setString(fullKey, (String) converted);
                case BYTE_ARRAY -> readWriteNBT.setByteArray(fullKey, (byte[]) converted);
                case INTEGER_ARRAY -> readWriteNBT.setIntArray(fullKey, (int[]) converted);
                case LONG_ARRAY -> readWriteNBT.setLongArray(fullKey, (long[]) converted);
                default -> throw new IllegalArgumentException("Unsupported type: " + type);
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid type conversion for " + type, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(@NotNull NamespacedKey key, DataType type) {
        if (!has(key, type)) return null;

        String fullKey = key.getNamespace();
        return (T) switch (type) {
            case BYTE -> readWriteNBT.getByte(fullKey);
            case SHORT -> readWriteNBT.getShort(fullKey);
            case INTEGER -> readWriteNBT.getInteger(fullKey);
            case LONG -> readWriteNBT.getLong(fullKey);
            case FLOAT -> readWriteNBT.getFloat(fullKey);
            case DOUBLE -> readWriteNBT.getDouble(fullKey);
            case BOOLEAN -> readWriteNBT.getByte(fullKey) != 0;
            case STRING -> readWriteNBT.getString(fullKey);
            case BYTE_ARRAY -> readWriteNBT.getByteArray(fullKey);
            case INTEGER_ARRAY -> readWriteNBT.getIntArray(fullKey);
            case LONG_ARRAY -> readWriteNBT.getLongArray(fullKey);
        };
    }

    @Override
    public void remove(@NotNull NamespacedKey key) {
        readWriteNBT.removeKey(key.getNamespace());
    }

    @Contract(pure = true)
    private static NBTType getNBTType(@NotNull DataType type) {
        return switch (type) {
            case BYTE, BOOLEAN -> NBTType.NBTTagByte;
            case SHORT -> NBTType.NBTTagShort;
            case INTEGER -> NBTType.NBTTagInt;
            case LONG -> NBTType.NBTTagLong;
            case FLOAT -> NBTType.NBTTagFloat;
            case DOUBLE -> NBTType.NBTTagDouble;
            case STRING -> NBTType.NBTTagString;
            case BYTE_ARRAY -> NBTType.NBTTagByteArray;
            case INTEGER_ARRAY -> NBTType.NBTTagIntArray;
            case LONG_ARRAY -> NBTType.NBTTagLongArray;
        };
    }

    @Override
    public <T> T apply(T object) {
        if (object instanceof ItemStack itemStack) {
            NBT.modify(itemStack, readWriteItemNBT -> {
                readWriteItemNBT.mergeCompound(readWriteNBT);
            });
        }
        if (object instanceof BlockState blockState) {
            NBT.modify(blockState, readWriteBlockStateNBT -> {
                readWriteBlockStateNBT.mergeCompound(readWriteNBT);
            });
        }
        return object;
    }
}