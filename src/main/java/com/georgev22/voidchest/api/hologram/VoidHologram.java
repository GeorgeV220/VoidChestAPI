package com.georgev22.voidchest.api.hologram;

import com.georgev22.voidchest.api.datastructures.maps.UnmodifiableObjectMap;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * Represents a hologram bound to a specific {@link IVoidChest}.
 * <p>
 * Implementations are responsible for creating, updating, positioning,
 * and removing holograms using a specific hologram provider
 * (e.g. FancyHolograms, DecentHolograms, etc.).
 * </p>
 *
 * <p>
 * Each hologram implementation should uniquely identify itself via
 * {@link #getKey()} and {@link #getName()}.
 * </p>
 */
public interface VoidHologram extends Keyed {

    /**
     * Checks whether the hologram associated with the given {@link IVoidChest}
     * has been deleted or is no longer valid.
     *
     * @param voidChest the void chest whose hologram state is being checked
     * @return {@code true} if the hologram has been deleted or does not exist,
     * {@code false} if it is still present
     */
    boolean isDeleted(@NonNull IVoidChest voidChest);

    /**
     * Removes (despawns) the hologram associated with the given {@link IVoidChest}.
     * <p>
     * Implementations should ensure this operation is safe to call multiple times
     * and does not throw errors if the hologram is already removed.
     * </p>
     *
     * @param voidChest the void chest whose hologram should be removed
     */
    void remove(@NonNull IVoidChest voidChest);

    /**
     * Removes (despawns) all holograms.
     * <p>
     * Implementations should ensure this operation is safe to call multiple times
     * and does not throw errors if the hologram is already removed.
     * </p>
     */
    void removeAll();

    /**
     * Updates the hologram associated with the given {@link IVoidChest}.
     * <p>
     * This may include updating displayed text, lines, visibility,
     * or any provider-specific properties.
     * </p>
     *
     * @param voidChest the void chest whose hologram should be updated
     */
    void update(@NonNull IVoidChest voidChest);

    /**
     * Calculates and applies the correct world location for the hologram
     * associated with the given {@link IVoidChest}.
     * <p>
     * This method should account for chest location changes, offsets,
     * and provider-specific positioning rules.
     * </p>
     *
     * @param voidChest the void chest used to calculate the hologram location
     * @return The calculated location or {@code null} if the location cannot be calculated
     */
    @NonNull Location calculateLocation(@NonNull IVoidChest voidChest);

    /**
     * Returns the human-readable name of the hologram implementation.
     * <p>
     * Example values: {@code FancyHolograms}, {@code DecentHolograms}.
     * </p>
     *
     * @return the name of the hologram implementation
     */
    @NonNull String getName();

    Optional<AbstractHologram<?>> getHologram(@NonNull IVoidChest voidChest);

    /**
     * Returns the holograms map.
     *
     * @return the holograms map
     */
    UnmodifiableObjectMap<IVoidChest, AbstractHologram<?>> getHolograms();
}
