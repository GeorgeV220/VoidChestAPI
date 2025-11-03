package com.georgev22.voidchest.api.events.economy;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.events.VoidChestBaseEvent;
import com.georgev22.voidchest.api.economy.player.AEconomy;
import com.georgev22.voidchest.api.economy.player.EconomyMode;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This event is fired when a custom AEconomy is set for a VoidChest,
 * allowing plugins to integrate their own economic calculations.
 *
 * @deprecated Use {@link com.georgev22.voidchest.api.economy.player.IEconomyManager#hook(AEconomy)}
 */
@Deprecated(forRemoval = true, since = "4.3.0") //Remove in 5.0.0 of the API
public class AEconomyHookEvent extends VoidChestBaseEvent {

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
        VoidChestAPI.getInstance().plugin().getLogger().info("A plugin has registered their own economy: " + economy.getName() + " for mode: " + economyMode);
        this.economy = economy;
    }

    /**
     * Sets the economy plugin that is being hooked into VoidChest.
     *
     * @param plugin  The plugin registering the economy.
     * @param economy The economy plugin being hooked into VoidChest.
     */
    public void setEconomy(@NotNull Plugin plugin, @NotNull AEconomy economy) {
        VoidChestAPI.getInstance().plugin().getLogger().info(plugin.getName() + " has registered their own economy: " + economy.getName() + " for mode:" + economyMode);
        this.economy = economy;
    }

}