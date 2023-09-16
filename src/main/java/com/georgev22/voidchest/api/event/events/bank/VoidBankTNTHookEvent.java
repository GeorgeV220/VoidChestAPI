package com.georgev22.voidchest.api.event.events.bank;

import com.georgev22.voidchest.api.banktnt.IVoidChestBankTNT;
import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * The VoidBankTNTHookEvent class is an event that is fired when a bank TNT plugin is hooked into VoidChest.
 * It extends the Event class.
 */
public class VoidBankTNTHookEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private IVoidChestBankTNT tntBank;

    /**
     * Constructs a new VoidBankTNTHookEvent.
     */
    public VoidBankTNTHookEvent() {
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
     * Retrieves the bank TNT plugin that is being hooked into VoidChest.
     *
     * @return The bank TNT plugin being hooked into VoidChest.
     */
    public IVoidChestBankTNT getBank() {
        return tntBank;
    }

    /**
     * Sets the bank TNT plugin that is being hooked into VoidChest.
     *
     * @param javaPlugin The JavaPlugin instance of the bank TNT plugin.
     * @param bank       The bank TNT plugin being hooked into VoidChest.
     * @throws NullPointerException if the bank TNT instance or bank TNT name is null.
     */
    public void setBank(JavaPlugin javaPlugin, IVoidChestBankTNT bank) {
        if (bank == null) {
            throw new NullPointerException("The TNT bank instance can't be null.");
        }

        if (bank.getName() == null) {
            throw new NullPointerException("The TNT bank name can't be null.");
        }

        javaPlugin.getLogger().info("Bank TNT: " + bank.getName() + " have been registered!");
        this.tntBank = bank;
    }

}