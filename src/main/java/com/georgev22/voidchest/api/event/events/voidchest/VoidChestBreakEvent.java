package com.georgev22.voidchest.api.event.events.voidchest;

import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.event.events.VoidEvent;
import com.georgev22.voidchest.api.storage.data.IPlayerData;
import com.georgev22.voidchest.api.storage.data.IVoidStorage;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The VoidChestBreakEvent class is an event that is fired before a VoidChest is fully removed by being broken by an Entity.
 * The Entity can be a player or something else, such as an explosion.
 * It extends the VoidEvent class.
 */
public class VoidChestBreakEvent extends VoidEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Entity entity;
    private final Block block;
    private final IPlayerData playerData;

    /**
     * Constructs a new VoidChestBreakEvent with the specified Entity, VoidStorage, Block, and PlayerData.
     *
     * @param entity       The Entity responsible for breaking the VoidChest.
     * @param voidStorage  The VoidStorage associated with the VoidChest.
     * @param block        The Block of the VoidChest being broken.
     * @param playerData   The PlayerData associated with the VoidChest.
     */
    public VoidChestBreakEvent(final Entity entity, final IVoidStorage voidStorage, final Block block, final IPlayerData playerData) {
        super(voidStorage);
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
}