package com.georgev22.voidchest.api.menu.actions;

import com.georgev22.voidchest.api.menu.item.items.MenuItem;
import com.georgev22.voidchest.api.menu.viewer.ViewerContext;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jspecify.annotations.NonNull;

import java.util.EnumMap;
import java.util.Map;

/**
 * Represents an action that can be executed when a {@link MenuItem}
 * is clicked in a menu. Each {@link Action} can define handlers for
 * specific {@link ClickType}s, allowing different behaviors depending
 * on how the item was clicked.
 *
 * <p>Subclasses must implement {@link #registerHandlers()} to bind
 * click types to their corresponding behavior.</p>
 *
 * <p>Example:</p>
 * <pre>{@code
 * public class ExampleAction extends Action {
 *     public ExampleAction(NamespacedKey key) {
 *         super(key);
 *     }
 *
 *     @Override
 *     protected void registerHandlers() {
 *         on(ClickType.LEFT, this::handleLeftClick);
 *         on(ClickType.RIGHT, this::handleRightClick);
 *     }
 *
 *     private void handleLeftClick(ViewerContext ctx, MenuItem item, InventoryClickEvent e) {
 *         ctx.getViewer().sendMessage("Left click!");
 *     }
 *
 *     private void handleRightClick(ViewerContext ctx, MenuItem item, InventoryClickEvent e) {
 *         ctx.getViewer().sendMessage("Right click!");
 *     }
 * }
 * }</pre>
 */
public abstract class Action implements Keyed {

    private final NamespacedKey key;
    private final Map<ClickType, ActionHandler> handlers = new EnumMap<>(ClickType.class);

    /**
     * Creates a new {@link Action} with the given key.
     * Subclasses will automatically register their click handlers
     * via {@link #registerHandlers()}.
     *
     * @param key The unique key that identifies this action.
     */
    public Action(NamespacedKey key) {
        this.key = key;
        registerHandlers();
    }

    /**
     * Subclasses must override this method to register handlers
     * for the {@link ClickType}s they want to support.
     * <p>
     * Example:
     * <pre>{@code
     * @Override
     * protected void registerHandlers() {
     *     on(ClickType.LEFT, this::onLeftClick);
     *     on(ClickType.RIGHT, this::onRightClick);
     * }
     * }</pre>
     */
    protected abstract void registerHandlers();

    /**
     * Registers a handler for a given {@link ClickType}.
     * When the player clicks with this click type, the associated
     * handler will be executed.
     *
     * @param clickType The type of click to bind to.
     * @param handler   The handler that executes for the given click type.
     */
    protected void on(ClickType clickType, ActionHandler handler) {
        handlers.put(clickType, handler);
    }

    /**
     * Registers a handler for multiple {@link ClickType}s.
     * When the player clicks with any of these click types, the associated
     * handler will be executed.
     *
     * @param clickTypes The types of clicks to bind to.
     * @param handler    The handler that executes for the given click types.
     */
    protected void on(ClickType @NonNull [] clickTypes, ActionHandler handler) {
        for (ClickType clickType : clickTypes) {
            handlers.put(clickType, handler);
        }
    }

    /**
     * Executes this action for the given event. If a handler
     * exists for the event's {@link ClickType}, it is executed.
     * Otherwise, nothing happens.
     *
     * @param context  The viewer context of the player who clicked.
     * @param menuItem The item that was clicked in the menu.
     * @param event    The raw inventory click event.
     */
    public void execute(@NonNull ViewerContext context,
                        @NonNull MenuItem menuItem,
                        @NonNull InventoryClickEvent event) {
        ActionHandler handler = handlers.get(event.getClick());
        if (handler != null) {
            handler.handle(context, menuItem, event);
        }
    }

    /**
     * Gets the unique key of this action.
     *
     * @return The action key.
     */
    public @NonNull NamespacedKey getKey() {
        return key;
    }

    /**
     * Functional interface representing a click handler
     * for a specific {@link ClickType}.
     */
    @FunctionalInterface
    public interface ActionHandler {
        /**
         * Handles a click action.
         *
         * @param context  The viewer context of the player who clicked.
         * @param menuItem The menu item that was clicked.
         * @param event    The raw inventory click event.
         */
        void handle(@NonNull ViewerContext context,
                    @NonNull MenuItem menuItem,
                    @NonNull InventoryClickEvent event);
    }
}
