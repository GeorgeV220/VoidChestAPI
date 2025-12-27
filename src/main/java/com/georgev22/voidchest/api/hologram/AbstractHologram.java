package com.georgev22.voidchest.api.hologram;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * Platform-independent hologram wrapper used by VoidChest.
 * <p>
 * This base class handles:
 * <ul>
 *     <li>Thread-safe line storage and mutation</li>
 *     <li>Location management</li>
 *     <li>Per-player visibility control</li>
 *     <li>Safe asynchronous line preparation</li>
 *     <li>Automatic update propagation</li>
 * </ul>
 * <p>
 * Only rendering logic must be implemented by the concrete engine adapter.
 *
 * @param <H> Native hologram object type
 */
public abstract class AbstractHologram<H> {

    private final AtomicLong updateCounter = new AtomicLong();
    protected final H hologram;
    protected final List<String> lines = new CopyOnWriteArrayList<>();
    protected final Set<Player> viewers = Collections.synchronizedSet(new HashSet<>());

    protected volatile Location location;
    protected volatile boolean deleted;

    public AbstractHologram(H hologram) {
        this.hologram = hologram;
    }

    /**
     * @return the native hologram object
     */
    public final H getHologram() {
        return hologram;
    }

    /**
     * @return {@code true} if this hologram has been removed
     */
    public final boolean isDeleted() {
        return deleted;
    }

    /**
     * @return immutable snapshot of current hologram lines
     */
    @Contract(pure = true)
    public final @NonNull @Unmodifiable List<String> getLines() {
        return List.copyOf(lines);
    }

    /**
     * Replaces all hologram lines.
     *
     * @param newLines new hologram lines
     */
    public final void setLines(Collection<String> newLines) {
        lines.clear();
        lines.addAll(newLines);
    }

    /**
     * Replaces all hologram lines.
     *
     * @param newLines new hologram lines
     */
    public final void setLines(String... newLines) {
        setLines(List.of(newLines));
    }

    /**
     * Appends new hologram lines.
     *
     * @param add lines to add
     */
    public final void addLines(Collection<String> add) {
        lines.addAll(add);
    }

    /**
     * Appends new hologram lines.
     *
     * @param add lines to add
     */
    public final void addLines(String... add) {
        addLines(List.of(add));
    }

    /**
     * Updates a specific hologram line.
     *
     * @param index 0-based line index
     * @param text  new line text
     */
    public final void updateLine(int index, String text) {
        lines.set(index, text);
    }

    /**
     * Removes a specific hologram line.
     *
     * @param index 0-based line index
     */
    public final void clearLine(int index) {
        lines.remove(index);
    }

    /**
     * Removes all hologram lines.
     */
    public final void clearLines() {
        lines.clear();
    }

    /**
     * Prepares hologram lines asynchronously and safely applies them on the main thread.
     * <p>
     * Intended for heavy placeholder parsing, database formatting, localization, etc.
     *
     * @param plugin  plugin instance used for scheduling
     * @param builder async hologram line builder
     */
    public final void prepareAsync(Plugin plugin, Consumer<List<String>> builder) {
        long gen = updateCounter.incrementAndGet();

        CompletableFuture
                .supplyAsync(() -> {
                    List<String> prepared = new ArrayList<>();
                    builder.accept(prepared);
                    return prepared;
                })
                .thenAccept(prepared ->
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            if (gen == updateCounter.get() && !deleted) {
                                setLines(prepared);
                                update();
                            }
                        })
                );
    }

    /**
     * @return current hologram location
     */
    public final Location getLocation() {
        return location;
    }

    /**
     * Teleports this hologram to a new location.
     *
     * @param location new hologram location
     */
    public final void setLocation(@NonNull Location location) {
        this.location = location.clone();
    }

    /**
     * Shows this hologram to a specific player.
     *
     * @param player target player
     */
    public final void show(Player player) {
        if (viewers.add(player)) {
            onShow(player);
        }
    }

    /**
     * Hides this hologram from a specific player.
     *
     * @param player target player
     */
    public final void hide(Player player) {
        if (viewers.remove(player)) {
            onHide(player);
        }
    }

    /**
     * @param player player to check
     * @return {@code true} if the player is currently viewing this hologram
     */
    public final boolean isViewing(Player player) {
        return viewers.contains(player);
    }

    /**
     * @return immutable snapshot of all current viewers
     */
    @Contract(pure = true)
    public final @NonNull @Unmodifiable Set<Player> getViewers() {
        return Set.copyOf(viewers);
    }

    /**
     * Permanently deletes this hologram.
     */
    public final void remove() {
        if (!deleted) {
            deleted = true;
            onDelete();
        }
    }

    /**
     * Called whenever hologram state changes.
     * Implementations must re-synchronize lines and location.
     */
    protected abstract void update();

    /**
     * Called when a player should start seeing the hologram.
     *
     * @param player target player
     */
    protected abstract void onShow(Player player);

    /**
     * Called when a player should stop seeing the hologram.
     *
     * @param player target player
     */
    protected abstract void onHide(Player player);

    /**
     * Called once when the hologram is deleted.
     */
    protected abstract void onDelete();
}
