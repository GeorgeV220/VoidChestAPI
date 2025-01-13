package com.georgev22.voidchest.api.event.events.economy;

import com.georgev22.voidchest.api.economy.AEconomy;
import com.georgev22.voidchest.api.economy.EconomyMode;
import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AEconomyHookEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private AEconomy economy;
    private final EconomyMode economyMode;

    /**
     * Constructs a new AEconomyHookEvent.
     *
     * @param economyMode The {@link EconomyMode} that is being hooked into VoidChest.
     */
    public AEconomyHookEvent(EconomyMode economyMode) {
        this.economyMode = economyMode;
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
    public @Nullable AEconomy getEconomy() {
        return economy;
    }

    public @NotNull EconomyMode getEconomyMode() {
        return economyMode;
    }

    /**
     * Sets the economy plugin that is being hooked into VoidChest.
     *
     * @param economy The economy plugin being hooked into VoidChest.
     */
    public void setEconomy(@NotNull AEconomy economy) {
        Bukkit.getLogger().info("[VoidChest]: A plugin has registered their own economy: " + economy.getName() + " for mode: " + economyMode);
        this.economy = economy;
    }

}