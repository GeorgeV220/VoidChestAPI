package com.georgev22.voidchest.api.utilities;

import com.georgev22.voidchest.api.storage.data.VoidInventoryHolder;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
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

public class SerializableInventory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Map<Integer, Map<String, Object>> serializedContents;

    private VoidInventoryHolder inventoryHolder;

    private transient Inventory inventory;

    @Contract("_ -> new")
    public static @NotNull SerializableInventory fromInventory(Inventory inventory) {
        return new SerializableInventory(inventory, (VoidInventoryHolder) inventory.getHolder());
    }

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

    public Inventory toInventory() {
        if (inventory == null) {
            inventory = Bukkit.createInventory(inventoryHolder, inventoryHolder.inventorySize(), inventoryHolder.inventoryName());
            for (Map.Entry<Integer, Map<String, Object>> entry : serializedContents.entrySet()) {
                ItemStack itemStack = deserializeItemStack(entry.getValue());
                inventory.setItem(entry.getKey(), itemStack);
            }
        }
        return inventory;
    }

    @SneakyThrows
    private @NotNull Map<String, Object> serializeItemStack(@NotNull ItemStack itemStack) {
        Map<String, Object> serialized = new HashMap<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

        dataOutput.writeObject(itemStack);

        dataOutput.close();

        serialized.put("itemStack", Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        NBTItem nbtItem = new NBTItem(itemStack);
        if (nbtItem != null) {
            serialized.put("NBTData", nbtItem.toString());
        }
        return serialized;
    }

    @SneakyThrows
    private @NotNull ItemStack deserializeItemStack(@NotNull Map<String, Object> serialized) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decode((String) serialized.get("itemStack")));
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

        ItemStack itemStack = (ItemStack) dataInput.readObject();

        dataInput.close();
        return itemStack;
    }

    @Serial
    private void writeObject(@NotNull ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(serializedContents);
        outputStream.writeObject(inventoryHolder);
    }

    @Serial
    private void readObject(@NotNull ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        //noinspection unchecked
        serializedContents = (Map<Integer, Map<String, Object>>) inputStream.readObject();
        inventoryHolder = (VoidInventoryHolder) inputStream.readObject();
        inventory = Bukkit.createInventory(inventoryHolder, inventoryHolder.inventorySize(), inventoryHolder.inventoryName());
        for (Map.Entry<Integer, Map<String, Object>> entry : serializedContents.entrySet()) {
            ItemStack itemStack = deserializeItemStack(entry.getValue());
            inventory.setItem(entry.getKey(), itemStack);
        }
    }

    @Override
    public String toString() {
        return "SerializableInventory{" +
                "serializedContents=" + serializedContents +
                ", inventoryHolder=" + inventoryHolder +
                ", inventory=" + inventory +
                '}';
    }
}