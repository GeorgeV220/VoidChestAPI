package com.georgev22.voidchest.api.events.voidchest;

import com.georgev22.voidchest.api.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The VoidChestBreakEvent class is an event that is fired before a VoidChest is fully removed by being broken by an Entity.
 * The Entity can be a player or something else, such as an explosion.
 * It extends the VoidEvent class.
 */
public class VoidChestBreakEvent extends VoidEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final @Nullable Entity entity;
    private final Block block;
    private final IPlayerData playerData;
    private boolean cancel;

    /**
     * Constructs a new VoidChestBreakEvent with the specified Entity, VoidChest, Block, and PlayerData.
     *
     * @param entity     The Entity responsible for breaking the VoidChest.
     * @param voidChest  The VoidChest associated with the VoidChest.
     * @param block      The Block of the VoidChest being broken.
     * @param playerData The PlayerData associated with the VoidChest.
     */
    public VoidChestBreakEvent(final @Nullable Entity entity, final IVoidChest voidChest, final Block block, final IPlayerData playerData) {
        super(voidChest);
        this.entity = entity;
        this.block = block;
        this.playerData = playerData;
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
     * Retrieves the Entity responsible for breaking the VoidChest.
     *
     * @return The Entity responsible for breaking the VoidChest, or null if not applicable.
     */
    @Nullable
    public Entity getEntity() {
        return entity;
    }

    /**
     * Retrieves the Block of the VoidChest being broken.
     *
     * @return The Block of the VoidChest being broken, or null if not applicable.
     */
    @Nullable
    public Block getBlock() {
        return block;
    }

    /**
     * Retrieves the PlayerData associated with the VoidChest.
     *
     * @return The PlayerData associated with the VoidChest.
     */
    public IPlayerData getPlayerData() {
        return playerData;
    }

    /**
     * Checks if the event is cancelled.
     *
     * @return True if the event is cancelled, false otherwise.
     */
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    /**
     * Sets whether the event has been cancelled.
     *
     * @param cancelled {@code true} if the event should be cancelled, {@code false} otherwise
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancel = cancelled;
    }

    @Override
    protected <T extends Event> T callSynchronousEvent() {
        Location location = block.getLocation();
        //noinspection ConstantValue
        if (isFolia && location != null) {
            //noinspection unchecked
            return (T) voidChestAPI.minecraftScheduler().createTaskForLocation(
                    () -> super.callSynchronousEvent(),
                    location
            ).join();
        }
        return super.callSynchronousEvent();
    }
}
