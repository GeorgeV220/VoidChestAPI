package com.georgev22.voidchest.api.menu.utilities;

import com.georgev22.voidchest.api.datastructures.maps.HashObjectMap;
import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.menu.Menu;
import com.georgev22.voidchest.api.menu.actions.Action;
import com.georgev22.voidchest.api.menu.item.ItemProvider;
import com.georgev22.voidchest.api.menu.item.items.MenuItem;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class to simplify the creation and customization of menus.
 * Provides methods to add items, set menu properties, and manage pagination.
 */
@SuppressWarnings("UnusedReturnValue")
public class MenuBuilder {

    private final String title;
    private int rows;
    private boolean isAnimated;
    private final List<MenuItem> items;
    private final ObjectMap<Integer, MenuItem> reservedItems;
    private Action defaultAction;
    private Action bottomInventoryAction;
    private ObjectMap<String, Object> customData = new HashObjectMap<>();
    private final ObjectMap<Character, MenuItem> charMappings = new HashObjectMap<>();
    private final ObjectMap<Integer, Character> structureMap = new HashObjectMap<>();

    /**
     * Constructs a MenuBuilder with a specified title and default 6 rows.
     *
     * @param title The title of the menu.
     */
    public MenuBuilder(String title) {
        this.title = title;
        this.rows = 6;
        this.items = new ArrayList<>();
        this.reservedItems = new HashObjectMap<>();
    }

    /**
     * Sets the number of rows for the menu.
     *
     * @param rows The number of rows for the menu.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder setRows(int rows) {
        this.rows = rows;
        return this;
    }

    /**
     * Adds a new item to the menu in the first free (non-reserved) slot.
     *
     * @param item The MenuItem to be added to the menu.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder addItem(MenuItem item) {
        items.add(item);
        return this;
    }

    /**
     * Adds a new item to the menu in the first free (non-reserved) slot.
     *
     * @param itemProvider The ItemProvider representing the item.
     * @param action       The action to perform when the item is clicked.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder addItem(ItemProvider itemProvider, Action action) {
        items.add(new MenuItem(itemProvider, action));
        return this;
    }

    /**
     * Adds a reserved item to a specified slot.
     * Reserved items cannot be replaced by other items in the menu.
     *
     * @param slot     The slot where the item should be placed.
     * @param menuItem The MenuItem to be placed in the reserved slot.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder setReservedItem(int slot, MenuItem menuItem) {
        reservedItems.put(slot, menuItem);
        return this;
    }

    /**
     * Adds a reserved item to a specified slot.
     * Reserved items cannot be replaced by other items in the menu.
     *
     * @param slot         The slot where the item should be placed.
     * @param itemProvider The ItemProvider to be placed in the reserved slot.
     * @param action       The action to perform when the reserved item is clicked.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder setReservedItem(int slot, ItemProvider itemProvider, @Nullable Action action) {
        MenuItem reservedItem = new MenuItem(itemProvider, action);
        reservedItems.put(slot, reservedItem);
        return this;
    }

    /**
     * Sets whether the menu should have animations
     *
     * @param animated true if the menu should be animated, false otherwise
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder setAnimated(boolean animated) {
        this.isAnimated = animated;
        return this;
    }

    /**
     * Sets the default action for the menu.
     *
     * @param action The default action to be set.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder setDefaultAction(@Nullable Action action) {
        this.defaultAction = action;
        return this;
    }

    /**
     * Sets the action to be performed for the bottom inventory of the menu.
     *
     * @param action The action to be set for the bottom inventory, or null to clear the action.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder setBottomInventoryAction(@Nullable Action action) {
        this.bottomInventoryAction = action;
        return this;
    }

    /**
     * Sets custom data for the menu builder.
     *
     * @param customData An ObjectMap containing key-value pairs of custom data to associate with the menu.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder setCustomData(ObjectMap<String, Object> customData) {
        this.customData = customData;
        return this;
    }

    /**
     * Adds a custom key-value pair to the menu's custom data map.
     * This method allows you to associate arbitrary data with the menu.
     *
     * @param key   The key under which the data will be stored. Must not be null.
     * @param value The value to be associated with the given key. Can be any object.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder addCustomData(String key, Object value) {
        customData.put(key, value);
        return this;
    }

    /**
     * Defines the structure of the menu using a pattern of characters.
     * Each string represents a row in the menu, and each character represents a slot.
     * <p>
     * {@code Example:
     * setStructure(
     * "# # # # # # # # #",
     * "# x x x x x x x #",
     * "# x x x x x x x #",
     * "# # # < # > # # #"
     * )
     * }
     *
     * @param structure Lines of characters representing the menu structure.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder setStructure(@NonNull Structure structure) {
        this.rows = structure.getRows();
        int slot = 0;
        for (String line : structure.getStructure()) {
            for (char c : line.toCharArray()) {
                structureMap.put(slot, c);
                slot++;
            }
        }
        return this;
    }

    /**
     * Maps a character in the structure to a reserved MenuItem.
     *
     * @param character The character to map.
     * @param menuItem  The MenuItem associated with the character.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder mapChar(char character, MenuItem menuItem) {
        charMappings.put(character, menuItem);
        return this;
    }

    /**
     * Maps a character in the structure to a reserved ItemProvider + action.
     *
     * @param character    The character to map.
     * @param itemProvider The ItemProvider associated with the character.
     * @param action       The action associated with the character.
     * @return The MenuBuilder instance for method chaining.
     */
    public MenuBuilder mapChar(char character, ItemProvider itemProvider, @Nullable Action action) {
        charMappings.put(character, new MenuItem(itemProvider, action));
        return this;
    }

    /**
     * Builds the Menu instance with the provided settings and items.
     *
     * @return A new Menu instance.
     */
    public Menu build() {
        Menu menu = new Menu(title, rows, reservedItems.keySet().stream().toList());

        structureMap.forEach((slot, character) -> {
            MenuItem mapped = charMappings.get(character);
            if (mapped != null) {
                MenuItem menuItem = mapped.clone();
                menuItem.setSlot(slot);
                reservedItems.put(slot, menuItem);
            }
        });

        items.forEach(menuItem -> menu.addItem(menuItem.clone()));

        reservedItems.forEach(menu::setReservedItemSlot);

        menu.setAnimated(isAnimated);
        menu.setDefaultAction(defaultAction);
        menu.setBottomInventoryAction(bottomInventoryAction);
        menu.getCustomData().set(customData);

        return menu;
    }
}
