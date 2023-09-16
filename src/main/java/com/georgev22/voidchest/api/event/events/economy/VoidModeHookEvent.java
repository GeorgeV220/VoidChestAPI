package com.georgev22.voidchest.api.event.events.economy;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.voideconomy.IVoidEconomy;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidModeHookEvent class is an event that is fired when a void mode plugin is hooked into VoidChest.
 * It extends the Event class.
 */
public class VoidModeHookEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private IVoidEconomy economy;

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
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Retrieves the void mode plugin that is being hooked into VoidChest.
     *
     * @return The void mode plugin being hooked into VoidChest.
     */
    public IVoidEconomy getEconomy() {
        return economy;
    }

    /**
     * Sets the void mode plugin that is being hooked into VoidChest.
     *
     * @param economy The void mode plugin being hooked into VoidChest.
     */
    public void setEconomy(IVoidEconomy economy) {
        Bukkit.getLogger().info("A plugin has registered their own void mode: " + economy.getName());
        this.economy = economy;
    }

}