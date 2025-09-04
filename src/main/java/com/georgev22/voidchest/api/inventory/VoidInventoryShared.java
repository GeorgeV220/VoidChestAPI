package com.georgev22.voidchest.api.inventory;

import com.georgev22.voidchest.api.maps.HashObjectMap;
import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.utilities.NullableArrayList;
import com.georgev22.voidchest.api.utilities.SerializableItemStack;
import com.georgev22.voidchest.api.utilities.UnmodifiableNullableArrayList;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.math.BigInteger;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The `VoidInventoryShared` class provides utility methods for managing and manipulating
 * inventories in the context of VoidChest. It includes methods for adding items, splitting
 * item stacks, and retrieving inventory contents.
 */
public class VoidInventoryShared {
    /**
     * Adds an array of {@link SerializableItemStack} items to the specified inventory.
     * If the inventory does not have enough space, the leftover items are returned.
     *
     * @param inventory The inventory to which the items will be added.
     * @param items     The array of {@link SerializableItemStack} items to add.
     * @return A {@link ObjectMap} containing the leftover items that could not be added.
     */
    public static @NotNull ObjectMap<Integer, SerializableItemStack> addItemsToInventory(
            Inventory inventory,
            SerializableItemStack @NotNull ... items
    ) {
        return addItemsToInventory0(inventory, items);
    }

    /**
     * Adds an array of {@link SerializableItemStack} items to the specified inventory.
     * Returns a list of leftover items that could not be added due to insufficient space.
     *
     * @param inventory      The inventory to which the items will be added.
     * @param itemsToBeAdded The array of {@link SerializableItemStack} items to add.
     * @return A list of {@link SerializableItemStack} items that could not be added.
     */
    @Contract("_, _ -> new")
    public static @NotNull List<SerializableItemStack> addItemsAndReturnLeftovers(
            Inventory inventory,
            SerializableItemStack @NotNull ... itemsToBeAdded
    ) {
        Collection<SerializableItemStack> itemStacks = Stream.of(itemsToBeAdded).toList();
        itemStacks = addItemsToInventory(inventory, itemStacks.toArray(new SerializableItemStack[0])).values();

        return new ArrayList<>(itemStacks);
    }

    /**
     * Adds an array of {@link ItemStack} items to the specified inventory.
     * Converts the {@link ItemStack} items to {@link SerializableItemStack} for processing.
     * Returns a list of leftover items that could not be added due to insufficient space.
     *
     * @param inventory  The inventory to which the items will be added.
     * @param itemStacks The array of {@link ItemStack} items to add.
     * @return A list of {@link ItemStack} items that could not be added.
     */
    public static List<ItemStack> addItemStacksAndReturnLeftovers(
            Inventory inventory,
            ItemStack... itemStacks
    ) {
        return addItemsAndReturnLeftovers(
                inventory,
                Arrays.stream(itemStacks)
                        .map(itemStack -> {
                            if (itemStack == null || itemStack.getType() == Material.AIR || itemStack.getAmount() <= 0) {
                                return null;
                            }
                            return SerializableItemStack.fromItemStack(itemStack, BigInteger.valueOf(itemStack.getAmount()));
                        })
                        .filter(Objects::nonNull)
                        .toArray(SerializableItemStack[]::new)
        ).stream()
                .map(SerializableItemStack::getItemStack)
                .toList();
    }

    /**
     * Adds an array of {@link SerializableItemStack} items to the specified inventory.
     * This method handles partial stacks and ensures items are added efficiently.
     * <p>
     *  TODO: some optimization
     *      *  - Create a 'firstPartial' with a 'fromIndex'
     *      *  - Record the lastPartial per Material
     *      *  - Cache firstEmpty result
     *
     * @param inventory  The inventory to which the items will be added.
     * @param itemStacks The array of {@link SerializableItemStack} items to add.
     * @return A {@link HashObjectMap} containing the leftover items that could not be added.
     */
    private static @NotNull HashObjectMap<Integer, SerializableItemStack> addItemsToInventory0(
            Inventory inventory,
            SerializableItemStack @NotNull ... itemStacks
    ) {
        //noinspection ConstantValue
        Preconditions.checkArgument(itemStacks != null, "items cannot be null");
        HashObjectMap<Integer, SerializableItemStack> leftover = new HashObjectMap<>();

        for (int i = 0; i < itemStacks.length; i++) {
            SerializableItemStack item = itemStacks[i];
            Preconditions.checkArgument(item != null, "ItemStack cannot be null");
            while (true) {
                // Do we already have a stack of it?
                int firstPartial = firstPartial(inventory, item.getItemStack());

                // Drat! no partial stack
                if (firstPartial == -1) {
                    // Find a free spot!
                    int firstFree = firstEmpty(inventory);
                    if (firstFree == -1) {
                        // No space at all!
                        leftover.put(i, item);
                        break;
                    } else {
                        // More than a single stack!
                        if (item.getAmount().compareTo(BigInteger.valueOf(item.getItemStack().getType().getMaxStackSize())) > 0) {
                            ItemStack stack = item.getItemStack().clone();
                            stack.setAmount(item.getItemStack().getType().getMaxStackSize());
                            inventory.setItem(firstFree, stack);
                            item.setAmount(item.getAmount().subtract(BigInteger.valueOf(item.getItemStack().getType().getMaxStackSize())));
                        } else {
                            // Store it
                            ItemStack stack = item.getItemStack().clone();
                            stack.setAmount(item.getAmount().intValue());
                            inventory.setItem(firstFree, stack);
                            break;
                        }
                    }
                } else {
                    // So, apparently it might only partially fit, well let's do just that
                    ItemStack partialItem = inventory.getItem(firstPartial);
                    if (partialItem == null) {
                        VoidChestAPI.getInstance().plugin().getLogger()
                                .log(Level.SEVERE, "Something went wrong, partialItem is null");
                        leftover.put(i, item);
                        break;
                    }

                    BigInteger amount = item.getAmount();
                    BigInteger partialAmount = BigInteger.valueOf(partialItem.getAmount());
                    BigInteger maxAmount = BigInteger.valueOf(partialItem.getType().getMaxStackSize());

                    // Check if it fully fits
                    if (amount.add(partialAmount).compareTo(maxAmount) <= 0) {
                        partialItem.setAmount(amount.add(partialAmount).intValue());
                        // To make sure the packet is sent to the client
                        inventory.setItem(firstPartial, partialItem);
                        break;
                    }

                    // It fits partially
                    partialItem.setAmount(maxAmount.intValue());
                    // To make sure the packet is sent to the client
                    inventory.setItem(firstPartial, partialItem);
                    item.setAmount(amount.add(partialAmount).subtract(maxAmount));
                }
            }
        }
        return leftover;
    }

    /**
     * Splits an array of {@link SerializableItemStack} into smaller {@link ItemStack} objects
     * that fit within the maximum stack size of the item type.
     *
     * @param itemArray The array of {@link SerializableItemStack} to split.
     * @return An array of {@link ItemStack} containing the smaller stacks.
     */
    @NotNull
    public static ItemStack @NotNull [] splitItemStacks(SerializableItemStack @NotNull [] itemArray) {
        List<ItemStack> smallerItemStacks = new ArrayList<>();
        for (SerializableItemStack item : itemArray) {
            if (item == null) continue;
            if (item.getItemStack() == null) continue;
            if (item.getItemStack().getType() == Material.AIR) continue;
            if (item.getAmount().compareTo(BigInteger.ZERO) <= 0) continue;

            ItemStack itemStack = item.getItemStack();
            BigInteger itemAmount = item.getAmount();
            while (itemAmount.compareTo(BigInteger.valueOf(itemStack.getType().getMaxStackSize())) > 0) {
                ItemStack singleItem = itemStack.clone();
                singleItem.setAmount(itemStack.getType().getMaxStackSize());
                smallerItemStacks.add(singleItem);
                itemAmount = itemAmount.subtract(BigInteger.valueOf(itemStack.getType().getMaxStackSize()));
            }
            if (itemAmount.compareTo(BigInteger.ZERO) > 0) {
                ItemStack remainingItem = itemStack.clone();
                remainingItem.setAmount(itemAmount.intValueExact());
                smallerItemStacks.add(remainingItem);
            }
        }
        ItemStack[] smallerItemStacksArray = new ItemStack[smallerItemStacks.size()];
        return smallerItemStacks.toArray(smallerItemStacksArray);
    }

    /**
     * Adds an array of {@link ItemStack} items to the specified inventory.
     * This method converts the {@link ItemStack} objects to {@link SerializableItemStack}
     * and delegates to the {@link #addItemsAndReturnLeftovers(Inventory, SerializableItemStack...)} method.
     *
     * @param inventory The inventory to which the items will be added.
     * @param items     The array of {@link ItemStack} items to add.
     * @return A {@link HashMap} containing the leftover items that could not be added.
     */
    public static @NotNull HashMap<Integer, ItemStack> addItem(
            Inventory inventory,
            ItemStack... items
    ) {
        return addItemsToInventory(inventory,
                Arrays.stream(items).map(
                        itemStack -> new SerializableItemStack(
                                itemStack,
                                BigInteger.valueOf(itemStack.getAmount())
                        )
                ).toArray(SerializableItemStack[]::new))
                .entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getItemStack().clone(), (a, b) -> b, HashMap::new
                ));
    }

    /**
     * Retrieves an unmodifiable view of the items in the specified inventory as a list of {@link SerializableItemStack} objects.
     * Each item in the inventory is converted to a {@link SerializableItemStack}, preserving the item's type and amount.
     * If an item slot in the inventory is empty, it is represented as {@code null} in the returned list.
     * The returned list is unmodifiable, ensuring that the original inventory's contents cannot be altered through this view.
     *
     * @param inventory The inventory from which to retrieve the items. Must not be {@code null}.
     * @return An unmodifiable list of {@link SerializableItemStack} objects representing the items in the inventory.
     * The list maintains the same order as the inventory's slots, with {@code null} entries for empty slots.
     */
    @Contract("_ -> new")
    public static @NotNull @UnmodifiableView UnmodifiableNullableArrayList<SerializableItemStack> getItems(@NotNull Inventory inventory) {
        NullableArrayList<SerializableItemStack> itemStacks = new NullableArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null) {
                itemStacks.set(i, null);
            } else {
                itemStacks.set(i, new SerializableItemStack(itemStack, BigInteger.valueOf(itemStack.getAmount())));
            }
        }
        return UnmodifiableNullableArrayList.unmodifiableList(itemStacks);
    }

    /**
     * Finds the first partial stack of the specified item in the inventory.
     * A partial stack is a stack that is not full and matches the given item.
     *
     * @param inventory The inventory to search.
     * @param item      The item to search for.
     * @return The index of the first partial stack, or -1 if no partial stack is found.
     */
    public static int firstPartial(@NotNull Inventory inventory, @NotNull ItemStack item) {
        ItemStack filteredItem = item.clone();
        ItemStack[] storageContents = inventory.getStorageContents();
        //noinspection ConstantValue
        if (item == null) {
            return -1;
        }
        for (int i = 0; i < storageContents.length; i++) {
            ItemStack cItem = storageContents[i];
            if (cItem != null && cItem.getAmount() < cItem.getType().getMaxStackSize() && cItem.isSimilar(filteredItem)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the first empty slot in the specified inventory.
     *
     * @param inventory The inventory to search.
     * @return The index of the first empty slot, or -1 if no empty slot is found.
     */
    public static int firstEmpty(@NotNull Inventory inventory) {
        ItemStack[] contents = inventory.getStorageContents();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == null) {
                return i;
            }
            if (contents[i].getType().equals(Material.AIR)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Converts a {@link ItemStack} to a {@link SerializableItemStack}.
     * If the item is null or of type {@link Material#AIR}, this method returns null.
     *
     * @param itemStack The {@link ItemStack} to convert.
     * @return The corresponding {@link SerializableItemStack}, or null if the item is invalid.
     */
    @Nullable
    public static SerializableItemStack getSerializableItemStack(ItemStack itemStack) {
        SerializableItemStack voidInventoryItemStack;

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            voidInventoryItemStack = null;
        } else {
            voidInventoryItemStack = new SerializableItemStack(itemStack, BigInteger.valueOf(itemStack.getAmount()));
        }
        return voidInventoryItemStack;
    }

}