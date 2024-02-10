package com.georgev22.voidchest.api.inventory.extras;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A record class that represents a navigation button in an inventory.
 * A navigation button has a page number, a slot number, and a click handler that performs an action when the button is clicked by a player.
 */
public record NavigationButton(int page, int slot, @NotNull Consumer<Player> clickHandler) {

    /**
     * Constructs a new navigation button with the given page number, slot number, and click handler.
     *
     * @param page         the page number of the navigation button
     * @param slot         the slot number of the navigation button
     * @param clickHandler the click handler of the navigation button
     */
    public NavigationButton {
    }

    /**
     * Returns a string representation of this navigation button.
     *
     * @return a string in the form of "NavigationButton{page=..., slot=...}"
     */
    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "NavigationButton{" +
                "page=" + page +
                ", slot=" + slot +
                '}';
    }
}