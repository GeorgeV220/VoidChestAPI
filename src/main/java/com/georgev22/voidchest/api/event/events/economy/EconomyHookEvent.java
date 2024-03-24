package com.georgev22.voidchest.api.event.events.economy;

import com.georgev22.voidchest.api.economy.AEconomy;
import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * The EconomyHookEvent class is an event that is fired in order for 3rd party plugins to hook into VoidChest economy system.
 * It extends the Event class.
 * <p>
 *
 * @deprecated Use {@link com.georgev22.voidchest.api.economy.IEconomyManager#hook(AEconomy)} instead.
 */
@Deprecated(forRemoval = true, since = "2.4.0")
public class EconomyHookEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private AEconomy economy;

    /**
     * Constructs a new EconomyHookEvent.
     */
    public EconomyHookEvent() {
    }

    /**
     * Retrieves the HandlerList for the event.
     *
     * @return The HandlerList for the event.
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Retrieves the HandlerList for the event.
     *
     * @return The HandlerList for the event.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Retrieves the economy plugin that is being hooked into VoidChest.
     *
     * @return The economy plugin being hooked into VoidChest.
     */
    public AEconomy getEconomy() {
        return economy;
    }

    /**
     * Sets the economy plugin that is being hooked into VoidChest.
     *
     * @param economy The economy plugin being hooked into VoidChest.
     */
    public void setEconomy(@NotNull AEconomy economy) {
        Bukkit.getLogger().info("[VoidChest]: A plugin has registered their own economy: " + economy.getName());
        this.economy = economy;
    }

}