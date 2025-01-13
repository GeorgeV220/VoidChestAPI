package com.georgev22.voidchest.api.event.events.economy;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.voideconomy.ProfitCalculator;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * This event is only fired when a custom ProfitCalculator is set for a VoidChest,
 * allowing plugins to integrate their own economic calculations.
 */
public class ProfitCalculatorHookEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private ProfitCalculator profitCalculator;

    /**
     * Retrieves the HandlerList for the event.
     *
     * @return The HandlerList for the event, allowing handlers to be registered.
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Retrieves the HandlerList for the event.
     *
     * @return The HandlerList for the event, which is used to manage event handlers.
     */
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets the ProfitCalculator associated with this event.
     *
     * @return The ProfitCalculator instance that has been registered for this event.
     */
    public ProfitCalculator getProfitCalculator() {
        return this.profitCalculator;
    }

    /**
     * Sets the ProfitCalculator for the event and logs the registration.
     * <p>
     * This method should be called when a custom ProfitCalculator is being registered.
     *
     * @param plugin           The plugin registering the ProfitCalculator.
     * @param voidChestName    The name of the VoidChest for which the calculator is being registered.
     * @param profitCalculator The ProfitCalculator instance to be registered.
     */
    public void setProfitCalculator(@NotNull Plugin plugin, String voidChestName, @NotNull ProfitCalculator profitCalculator) {
        plugin.getLogger().info(
                "[VoidChest]: Plugin " + plugin.getName() + " has registered their own economy "
                        + profitCalculator.getSimpleName() + " for VoidChest " + voidChestName);
        this.profitCalculator = profitCalculator;
    }
}
