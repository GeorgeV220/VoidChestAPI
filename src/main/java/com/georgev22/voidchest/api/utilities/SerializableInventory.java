package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.storage.data.VoidInventoryHolder;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.SneakyThrows;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * A serializable wrapper for Bukkit Inventories, allowing for easy serialization and deserialization of inventory contents.
 *
 * <p>The {@code SerializableInventory} class facilitates the conversion of Bukkit {@link org.bukkit.inventory.Inventory} objects
 * into a serializable format, enabling storage and retrieval of inventory data. It is particularly designed for use with custom
 * implementations of the {@link com.georgev22.voidchest.api.storage.data.VoidInventoryHolder} interface.
 * </p>
 *
 * <p>It implements the {@link Serializable} interface and provides methods for converting an Inventory to a serializable format
 * and vice versa. The serialized format includes item stack data and NBT information using the NBT library.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * // Creating a SerializableInventory from a Bukkit Inventory
 * Inventory playerInventory = player.getInventory();
 * SerializableInventory serializableInventory = SerializableInventory.fromInventory(playerInventory);
 *
 * // Creating a SerializableInventory from a VoidInventoryHolder
 * VoidInventoryHolder voidInventoryHolder = new CustomVoidInventoryHolder(playerInventory);
 * SerializableInventory serializableInventory = SerializableInventory.fromInventoryHolder(voidInventoryHolder);
 *
 * // Converting back to a Bukkit Inventory
 * Inventory deserializedInventory = serializableInventory.toInventory();
 * }</pre>
 * </p>
 *
 * <p>It is recommended to handle potential exceptions during serialization and deserialization processes
 * to ensure proper error reporting and user feedback.
 * </p>
 *
 * @deprecated This class is deprecated and will be changed in a future release.
 */
@Deprecated(forRemoval = true)
public class SerializableInventory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Map<Integer, Map<String, Object>> serializedContents;

    private VoidInventoryHolder inventoryHolder;

    private transient Inventory inventory;

    /**
     * Creates a new SerializableInventory from a Bukkit Inventory.
     *
     * @param inventory The Bukkit Inventory to be serialized.
     * @return A new SerializableInventory instance.
     */
    @Contract("_ -> new")
    public static @NotNull SerializableInventory fromInventory(Inventory inventory) {
        return new SerializableInventory(inventory, (VoidInventoryHolder) inventory.getHolder());
    }

    /**
     * Creates a new SerializableInventory from a VoidInventoryHolder.
     *
     * @param inventoryHolder The VoidInventoryHolder associated with the provided Inventory.
     * @return A new SerializableInventory instance.
     */
    public static @NotNull SerializableInventory fromInventoryHolder(VoidInventoryHolder inventoryHolder) {
        return new SerializableInventory(inventoryHolder.getInventory(), inventoryHolder);
    }

    /**
     * Constructs a new SerializableInventory from the provided Bukkit Inventory and VoidInventoryHolder.
     *
     * @param inventory       The Bukkit Inventory to be serialized.
     * @param inventoryHolder The VoidInventoryHolder associated with the provided Inventory.
     * @deprecated Use alternative methods for serializing and deserializing inventories to ensure compatibility.
     */
    @Deprecated
    public SerializableInventory(@NotNull Inventory inventory, VoidInventoryHolder inventoryHolder) {
        this.serializedContents = new HashMap<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack != null) {
                serializedContents.put(i, serializeItemStack(itemStack));
            }
        }
        this.inventoryHolder = inventoryHolder;
    }

    /**
     * Returns the Bukkit Inventory represented by this SerializableInventory.
     * If the internal Inventory has not been initialized, it will be created and populated with the deserialized contents.
     *
     * @return The Bukkit Inventory represented by this SerializableInventory.
     */
    public Inventory toInventory() {
        if (inventory == null) {
            inventory();
        }
        return inventory;
    }

    /**
     * Populates the internal Bukkit Inventory during deserialization.
     */
    private void inventory() {
        inventory = inventoryHolder.getInventory();
        for (Map.Entry<Integer, Map<String, Object>> entry : serializedContents.entrySet()) {
            ItemStack itemStack = deserializeItemStack(entry.getValue());
            inventory.setItem(entry.getKey(), itemStack);
        }
    }

    /**
     * Serializes an ItemStack into a map for storage.
     *
     * @param itemStack The ItemStack to be serialized.
     * @return A map containing the serialized data of the ItemStack.
     */
    @SneakyThrows
    private @NotNull Map<String, Object> serializeItemStack(@NotNull ItemStack itemStack) {
        Map<String, Object> serialized = new HashMap<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

        dataOutput.writeObject(itemStack);

        dataOutput.close();

        serialized.put("itemStack", Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        NBTItem nbtItem = new NBTItem(itemStack);
        serialized.put("NBTData", nbtItem.toString());
        return serialized;
    }

    /**
     * Deserializes an ItemStack from a map.
     *
     * @param serialized A map containing the serialized data of the ItemStack.
     * @return The deserialized ItemStack.
     */
    @SneakyThrows
    private @NotNull ItemStack deserializeItemStack(@NotNull Map<String, Object> serialized) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decode((String) serialized.get("itemStack")));
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

        ItemStack itemStack = (ItemStack) dataInput.readObject();

        dataInput.close();
        return itemStack;
    }

    /**
     * Custom serialization method to write the object to an ObjectOutputStream.
     *
     * @param outputStream The ObjectOutputStream to write to.
     * @throws IOException If an I/O error occurs during serialization.
     */
    @Serial
    private void writeObject(@NotNull ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(serializedContents);
        outputStream.writeObject(inventoryHolder);
    }

    /**
     * Custom deserialization method to read the object from an ObjectInputStream.
     *
     * @param inputStream The ObjectInputStream to read from.
     * @throws IOException            If an I/O error occurs during deserialization.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    @Serial
    private void readObject(@NotNull ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        //noinspection unchecked
        serializedContents = (Map<Integer, Map<String, Object>>) inputStream.readObject();
        inventoryHolder = (VoidInventoryHolder) inputStream.readObject();
        inventory = inventoryHolder.getInventory();
    }

    /**
     * Returns a string representation of the SerializableInventory,
     * including serialized contents and inventory details.
     *
     * @return A string representation of the SerializableInventory.
     */
    @Override
    public String toString() {
        return "SerializableInventory{" +
                "serializedContents=" + serializedContents +
                ", inventoryHolder=" + inventoryHolder +
                ", inventory=" + inventory +
                '}';
    }
}
