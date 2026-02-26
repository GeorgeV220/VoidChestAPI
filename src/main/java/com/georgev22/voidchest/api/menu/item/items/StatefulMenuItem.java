package com.georgev22.voidchest.api.menu.item.items;

import com.georgev22.voidchest.api.datastructures.Pair;
import com.georgev22.voidchest.api.menu.actions.Action;
import com.georgev22.voidchest.api.menu.item.ItemProvider;
import com.georgev22.voidchest.api.menu.state.StateCondition;
import com.georgev22.voidchest.api.menu.viewer.ViewerContext;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A {@link MenuItem} that can change its {@link ItemProvider} dynamically
 * based on a numeric state. States can be set manually or determined
 * automatically using a {@link StateCondition}.
 * <p>
 * Each state has its own {@link ItemProvider}, with state {@code 0}
 * always reserved for the default provider.
 */
public class StatefulMenuItem extends MenuItem {

    private @Nullable StateCondition stateCondition;
    private Map<Integer, Pair<ItemProvider, Action>> stateProviders = new HashMap<>();
    private int currentState = 0;

    /**
     * Constructs a {@link StatefulMenuItem} with the specified default item provider.
     * The default provider is associated with state {@code 0} and serves as the base
     * state for this stateful menu item.
     *
     * @param defaultProvider the non-null item provider used for the default state (state {@code 0})
     */
    public StatefulMenuItem(@NonNull ItemProvider defaultProvider) {
        super(defaultProvider);
        this.stateProviders.put(0, Pair.create(defaultProvider, null));
    }

    /**
     * Constructs a new {@link StatefulMenuItem} with the specified default item provider
     * and optional action. The default provider is associated with state {@code 0} in the
     * internal state providers map.
     *
     * @param defaultProvider the primary item provider for the menu item; must not be null
     * @param action          the optional action associated with this menu item
     */
    public StatefulMenuItem(@NonNull ItemProvider defaultProvider, @Nullable Action action) {
        super(defaultProvider, action);
        this.stateProviders.put(0, Pair.create(defaultProvider, action));
    }

    /**
     * Sets the {@link ItemProvider} for a specific state.
     *
     * @param state        the numeric state identifier
     * @param itemProvider the provider to use when this menu item is in the given state
     */
    public void setStateProvider(int state, @NonNull ItemProvider itemProvider, @Nullable Action action) {
        stateProviders.put(state, Pair.create(itemProvider, action));
        // Optionally update the displayed item if this is the current state
        if (state == currentState) {
            setItemStack(itemProvider);
            setAction(action);
            notifyContext();
        }
    }

    /**
     * Removes a state and its associated {@link ItemProvider} from this menu item.
     * If the removed state is the current state, the menu item reverts to state {@code 0}.
     *
     * @param state the state to remove
     */
    public void removeState(int state) {
        stateProviders.remove(state);
        if (state == currentState) {
            if (stateProviders.isEmpty()) {
                setItemStack(ItemProvider.EMPTY);
                setAction(null);
                notifyContext();
            } else {
                setState(0);
            }
        }
    }

    /**
     * Changes the current state of this menu item and updates its displayed
     * {@link ItemProvider} accordingly.
     *
     * @param state the state to switch to
     */
    public void setState(int state) {
        this.currentState = state;
        Pair<ItemProvider, Action> provider = stateProviders.getOrDefault(state, Pair.create(getItemProvider(), getAction()));
        setItemStack(provider.first());
        setAction(provider.second());
        notifyContext();
    }

    /**
     * Gets the current state of this menu item.
     *
     * @return the current state identifier
     */
    public int getCurrentState() {
        return currentState;
    }

    /**
     * Retrieves the {@link ItemProvider} associated with a specific state.
     *
     * @param state the state identifier
     * @return the item provider for that state, or {@code null} if no provider was set
     */
    @Nullable
    public ItemProvider getStateProvider(int state) {
        Pair<ItemProvider, Action> provider = stateProviders.get(state);
        return provider != null ? provider.first() : null;
    }

    /**
     * Retrieves the {@link Action} associated with a specific state.
     *
     * @param state the state identifier
     * @return the action for that state, or {@code null} if no action was set
     */
    @Nullable
    public Action getStateAction(int state) {
        Pair<ItemProvider, Action> provider = stateProviders.get(state);
        return provider != null ? provider.second() : null;
    }

    /**
     * Sets the {@link StateCondition} used to determine this item's state dynamically
     * when viewed in a {@link ViewerContext}.
     *
     * @param stateCondition the condition evaluator, or {@code null} to disable automatic state updates
     */
    public void setStateCondition(@Nullable StateCondition stateCondition) {
        this.stateCondition = stateCondition;
    }

    /**
     * Gets the {@link StateCondition} used to determine this item's state dynamically
     * when viewed in a {@link ViewerContext}.
     *
     * @return the condition evaluator, or {@code null} if no condition is set
     */
    @Nullable
    public StateCondition getStateCondition() {
        return stateCondition;
    }

    /**
     * Updates the current state of this menu item based on the assigned
     * {@link StateCondition}, if one is present. If no condition is set,
     * this method does nothing.
     *
     * @param context the viewer context used for condition evaluation
     */
    public void updateState(@NonNull ViewerContext context) {
        if (stateCondition != null) {
            int state = stateCondition.evaluate(context, this);
            if (state == getCurrentState()) return;
            setState(state);
        }
    }

    /**
     * Creates and returns a copy of this {@link StatefulMenuItem}.
     * <p>
     * The clone includes:
     * <ul>
     *   <li>the current state and its associated {@link ItemProvider}</li>
     *   <li>all registered state providers</li>
     *   <li>the assigned {@link StateCondition}, if any</li>
     *   <li>all properties inherited from {@link MenuItem} (e.g., action, animation, colors)</li>
     * </ul>
     *
     * @return a cloned {@link StatefulMenuItem} with the same state, providers, and properties
     */
    @Override
    public StatefulMenuItem clone() {
        StatefulMenuItem clone = (StatefulMenuItem) super.clone();
        clone.stateProviders = stateProviders.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> Pair.create(entry.getValue().first().clone(), entry.getValue().second()), (a, b) -> b));
        clone.stateCondition = this.stateCondition;
        clone.currentState = this.currentState;

        Pair<ItemProvider, Action> provider = clone.stateProviders.getOrDefault(currentState, Pair.create(getItemProvider(), getAction()));
        clone.setItemStack(provider.first().clone());
        clone.setAction(provider.second());

        return clone;
    }

    @Override
    public @NonNull StatefulMenuItem deepCopy() {
        return this.clone();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
