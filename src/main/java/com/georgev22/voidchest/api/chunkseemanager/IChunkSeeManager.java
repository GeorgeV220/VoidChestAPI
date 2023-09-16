package com.georgev22.voidchest.api.chunkseemanager;

import org.bukkit.entity.Player;

import org.bukkit.entity.Player;

/**
 * The IChunkSeeManager interface provides methods for managing chunk visibility.
 */
public interface IChunkSeeManager {

    /**
     * Reloads the chunk visibility settings.
     */
    void reloadChunkSee();

    /**
     * Adds a player to the chunk visibility management.
     *
     * @param player The player to add.
     */
    void addPlayer(Player player);

    /**
     * Checks if a player is inside a managed chunk.
     *
     * @param player The player to check.
     * @return True if the player is inside a managed chunk, false otherwise.
     */
    boolean isInside(Player player);

    /**
     * Removes a player from the chunk visibility management.
     *
     * @param player The player to remove.
     */
    void removePlayer(Player player);
}
