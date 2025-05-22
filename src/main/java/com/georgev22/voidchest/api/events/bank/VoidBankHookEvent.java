package com.georgev22.voidchest.api.events.bank;

import com.georgev22.voidchest.api.events.VoidChestBaseEvent;
import com.georgev22.voidchest.api.bank.IVoidChestBank;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidBankHookEvent class is an event that is fired when a bank plugin is hooked into VoidChest.
 * It extends the Event class.
 */
public class VoidBankHookEvent extends VoidChestBaseEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private IVoidChestBank economy;

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
     * Retrieves the bank plugin that is being hooked into VoidChest.
     *
     * @return The bank plugin being hooked into VoidChest.
     */
    public IVoidChestBank getBank() {
        return economy;
    }

    /**
     * Sets the bank plugin that is being hooked into VoidChest.
     *
     * @param javaPlugin The JavaPlugin instance of the bank plugin.
     * @param bank       The bank plugin being hooked into VoidChest.
     * @throws NullPointerException if the bank instance or bank name is null.
     */
    public void setBank(JavaPlugin javaPlugin, IVoidChestBank bank) {
        if (bank == null) {
            throw new NullPointerException("The bank instance can't be null.");
        }

        if (bank.getName() == null) {
            throw new NullPointerException("The bank name can't be null.");
        }

        javaPlugin.getLogger().info("Bank: " + bank.getName() + " have been registered!");
        this.economy = bank;
    }

}