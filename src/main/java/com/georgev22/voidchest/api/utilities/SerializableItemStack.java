package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.exceptions.SerializerException;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * A serializable wrapper for Bukkit ItemStack, using NBT serialization.
 *
 * <p>The {@code SerializableItemStack} class allows for easy serialization and deserialization
 * of Bukkit {@link org.bukkit.inventory.ItemStack} objects using NBT (Named Binary Tag) serialization.
 * </p>
 *
 * <p>It implements the {@link Serializable} interface and includes custom serialization methods
 * to convert an ItemStack to a string representation during serialization and recreate the ItemStack
 * during deserialization.
 * </p>
 *
 * <p>The class includes the following methods:
 * <ul>
 *     <li>{@code static @NotNull SerializableItemStack fromItemStack(ItemStack itemStack)} - Creates a new SerializableItemStack from an ItemStack.</li>
 *     <li>{@code SerializableItemStack(ItemStack itemStack)} - Constructs a new SerializableItemStack from an ItemStack.</li>
 *     <li>{@code ItemStack getItemStack()} - Retrieves the original ItemStack.</li>
 *     <li>{@code static @NotNull SerializableItemStack fromNBT(String nbtString)} - Creates a new SerializableItemStack from a string representation in NBT format.</li>
 *     <li>{@code static @NotNull String serializeItemStack(@NotNull ItemStack itemStack)} - Serializes an ItemStack into a base32-encoded string.</li>
 *     <li>{@code static @NotNull ItemStack deserializeItemStack(@NotNull String data)} - Deserializes a base32-encoded string into an ItemStack.</li>
 *     <li>{@code static @NotNull List<String> serializeItemStacksToBase32(@NotNull List<ItemStack> itemStacks)} - Serializes a list of ItemStacks into a list of base32-encoded strings.</li>
 *     <li>{@code static @NotNull List<ItemStack> deserializeItemStacksFromBase32(@NotNull List<String> base32Strings)} - Deserializes a list of base32-encoded strings into a list of ItemStacks.</li>
 *     <li>{@code static @NotNull List<String> serializeItemStacksToNBT(@NotNull List<ItemStack> itemStacks)} - Serializes a list of ItemStacks into a list of strings in NBT format.</li>
 *     <li>{@code static @NotNull List<ItemStack> deserializeItemStacksFromNBT(@NotNull List<String> nbtDataList)} - Deserializes a list of strings in NBT format into a list of ItemStacks.</li>
 * </ul>
 * </p>
 *
 * <p>Serialization and deserialization are handled through custom methods:
 * <ul>
 *     <li>{@code private void writeObject(@NotNull ObjectOutputStream outputStream)} - Serializes the ItemStack to a string using NBT serialization.</li>
 *     <li>{@code private void readObject(@NotNull ObjectInputStream inputStream)} - Deserializes the string representation to recreate the ItemStack.</li>
 * </ul>
 * </p>
 *
 * <p>It is important to note that the ItemStack is marked as transient to prevent
 * unnecessary serialization of potentially large or complex Bukkit objects.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("itemstack.ser"))) {
 *     oos.writeObject(SerializableItemStack.fromItemStack(player.getItemInHand()));
 * } catch (IOException e) {
 *     e.printStackTrace();
 * }
 * }</pre>
 * </p>
 *
 * <p>It is recommended to handle potential exceptions, such as {@link SerializerException},
 * during the serialization and deserialization process.
 * </p>
 */
public class SerializableItemStack implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The transient ItemStack to be serialized.
     */
    private transient ItemStack itemStack;

    /**
     * Constructs a new SerializableItemStack from an ItemStack.
     *
     * @param itemStack The ItemStack to be serialized.
     */
    public SerializableItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Creates a new SerializableItemStack from an ItemStack.
     *
     * @param itemStack The ItemStack to be wrapped.
     * @return A new SerializableItemStack instance.
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull SerializableItemStack fromItemStack(ItemStack itemStack) {
        return new SerializableItemStack(itemStack);
    }

    /**
     * Creates a new SerializableItemStack from a string representation in NBT format.
     *
     * <p>The {@code fromNBT} method parses a string representation in NBT (Named Binary Tag) format
     * and constructs a new {@link SerializableItemStack} from the parsed ItemStack data.
     * </p>
     *
     * <p>It utilizes the NBT library to parse the NBT string and convert it into a Bukkit {@link ItemStack}.
     * If the deserialization process fails or the resulting ItemStack is null, a {@link SerializerException}
     * is thrown to indicate the failure.
     * </p>
     *
     * <p>Example usage:
     * <pre>{@code
     * String nbtString = "..." // NBT string representation of an ItemStack
     * try {
     *     SerializableItemStack serializableItemStack = SerializableItemStack.fromNBT(nbtString);
     *     // Use the serializableItemStack as needed
     * } catch (SerializerException e) {
     *     // Handle the exception, log the error, or take appropriate action
     *     e.printStackTrace();
     * }
     * }</pre>
     * </p>
     *
     * <p>It is recommended to handle the {@link SerializerException} and any other potential exceptions
     * during the deserialization process to ensure proper error reporting and user feedback.
     * </p>
     *
     * @param nbtString The string representation of the ItemStack in NBT format.
     * @return A new SerializableItemStack instance representing the deserialized ItemStack.
     * @throws SerializerException If there is an issue with the deserialization process.
     */
    public static @NotNull SerializableItemStack fromNBT(String nbtString) throws SerializerException {
        ReadWriteNBT readWriteNBT = NBT.parseNBT(nbtString);
        ItemStack itemStack = NBT.itemStackFromNBT(readWriteNBT);
        if (itemStack == null) {
            throw new SerializerException("Could not deserialize item stack");
        }
        return new SerializableItemStack(itemStack);
    }

    /**
     * Serializes an ItemStack into a base32-encoded string.
     *
     * @param itemStack The ItemStack to be serialized.
     * @return A base32-encoded string representing the serialized ItemStack.
     * @throws SerializerException If there is an error during the serialization process.
     */
    public static @NotNull String serializeItemStack(@NotNull ItemStack itemStack) throws SerializerException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(SerializableItemStack.fromItemStack(itemStack));
            return new BigInteger(1, bos.toByteArray()).toString(32);
        } catch (IOException e) {
            throw new SerializerException("Error during serialization of ItemStack: " + e.getMessage());
        }
    }

    /**
     * Deserializes a base32-encoded string into an ItemStack.
     *
     * @param data A base32-encoded string representing the serialized ItemStack.
     * @return The deserialized ItemStack.
     * @throws SerializerException If there is an error during the deserialization process.
     */
    public static @NotNull ItemStack deserializeItemStack(@NotNull String data) throws SerializerException {
        try {
            byte[] byteArray = new BigInteger(data, 32).toByteArray();
            try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray); ObjectInputStream ois = new ObjectInputStream(bis)) {
                SerializableItemStack serializableItemStack = (SerializableItemStack) ois.readObject();
                return serializableItemStack.getItemStack();
            } catch (ClassNotFoundException | IOException e) {
                throw new SerializerException("Error during deserialization of ItemStack: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            throw new SerializerException("Error decoding base32 data: " + e.getMessage());
        }
    }

    /**
     * Serializes a list of ItemStacks into a list of base32-encoded strings.
     *
     * @param itemStacks The list of ItemStacks to be serialized.
     * @return A list of base32-encoded strings representing the serialized ItemStacks.
     * @throws SerializerException If there is an error during the serialization process.
     */
    public static @NotNull List<String> serializeItemStacksToBase32(@NotNull List<ItemStack> itemStacks) throws SerializerException {
        List<String> base32Strings = new ArrayList<>();
        for (ItemStack itemStack : itemStacks) {
            base32Strings.add(SerializableItemStack.serializeItemStack(itemStack));
        }
        return base32Strings;
    }

    /**
     * Deserializes a list of base32-encoded strings into a list of ItemStacks.
     *
     * @param base32Strings The list of base32-encoded strings representing the serialized ItemStacks.
     * @return A list of ItemStacks representing the deserialized ItemStacks.
     * @throws SerializerException If there is an error during the deserialization process.
     */
    public static @NotNull List<ItemStack> deserializeItemStacksFromBase32(@NotNull List<String> base32Strings) throws SerializerException {
        List<ItemStack> serializableItemStacks = new ArrayList<>();
        for (String base32String : base32Strings) {
            serializableItemStacks.add(SerializableItemStack.deserializeItemStack(base32String));
        }
        return serializableItemStacks;
    }

    /**
     * Serializes a list of ItemStacks into a list of strings in NBT format.
     *
     * @param itemStacks The list of ItemStacks to be serialized.
     * @return A list of strings in NBT format representing the serialized ItemStacks.
     * @throws SerializerException If there is an error during the serialization process.
     */
    public static @NotNull List<String> serializeItemStacksToNBT(@NotNull List<ItemStack> itemStacks) throws SerializerException {
        List<String> nbtDataList = new ArrayList<>();
        for (ItemStack itemStack : itemStacks) {
            nbtDataList.add(SerializableItemStack.fromNBT(SerializableItemStack.serializeItemStack(itemStack)).toString());
        }
        return nbtDataList;
    }

    /**
     * Deserializes a list of strings in NBT format into a list of ItemStacks.
     *
     * @param nbtDataList The list of strings in NBT format representing the serialized ItemStacks.
     * @return A list of ItemStacks representing the deserialized ItemStacks.
     * @throws SerializerException If there is an error during the deserialization process.
     */
    public static @NotNull List<ItemStack> deserializeItemStacksFromNBT(@NotNull List<String> nbtDataList) throws SerializerException {
        List<ItemStack> serializableItemStacks = new ArrayList<>();
        for (String nbt : nbtDataList) {
            serializableItemStacks.add(SerializableItemStack.fromNBT(nbt).getItemStack());
        }
        return serializableItemStacks;
    }

    /**
     * Retrieves the original ItemStack.
     *
     * @return The original ItemStack.
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Custom serialization method using NBT serialization.
     *
     * @param outputStream The ObjectOutputStream to write the serialized data to.
     * @throws IOException         If an I/O error occurs during serialization.
     * @throws SerializerException If there is an issue with the serialization process.
     */
    @Serial
    private void writeObject(@NotNull ObjectOutputStream outputStream) throws IOException, SerializerException {
        try {
            outputStream.writeObject(this.toString());
        } catch (Exception e) {
            throw new SerializerException("Error during serialization of ItemStack: " + e.getMessage());
        }
    }

    /**
     * Custom deserialization method using NBT serialization.
     *
     * @param inputStream The ObjectInputStream to read the serialized data from.
     * @throws IOException            If an I/O error occurs during deserialization.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     * @throws SerializerException    If there is an issue with the deserialization process.
     */
    @Serial
    private void readObject(@NotNull ObjectInputStream inputStream) throws IOException, ClassNotFoundException, SerializerException {
        try {
            ReadWriteNBT readWriteNBT = NBT.parseNBT(inputStream.readUTF());
            ItemStack itemStack = NBT.itemStackFromNBT(readWriteNBT);
            if (itemStack == null) {
                throw new SerializerException("Could not deserialize item stack");
            }
            this.itemStack = itemStack;
        } catch (Exception e) {
            throw new SerializerException("Error during deserialization of ItemStack: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return NBTItem.convertItemtoNBT(this.itemStack).toString();
    }
}
