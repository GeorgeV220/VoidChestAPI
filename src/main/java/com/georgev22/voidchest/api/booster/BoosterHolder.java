package com.georgev22.voidchest.api.booster;

import com.georgev22.voidchest.api.events.booster.BoosterCreateEvent;
import com.georgev22.voidchest.api.events.booster.BoosterExpireEvent;
import com.georgev22.voidchest.api.storage.model.Booster;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents an entity that can hold and manage boosters.
 * <p>
 * Implementations of this interface can be any entity that should support
 * boosters, such as:
 * <ul>
 *   <li>Player data</li>
 *   <li>VoidChest (SellChest) instances</li>
 *   <li>Island data (future)</li>
 *   <li>Server-wide contexts (future)</li>
 *   <li>Custom plugin entities</li>
 * </ul>
 * <p>
 * Example implementation:
 * <pre>{@code
 * public class PlayerData extends Entity implements BoosterHolder {
 *     private final List<Booster> boosters = new ArrayList<>();
 *
 *     @Override
 *     public Collection<Booster> getBoosters() {
 *         return Collections.unmodifiableCollection(boosters);
 *     }
 *
 *     @Override
 *     public void addBooster(Booster booster) {
 *         boosters.add(booster);
 *     }
 *
 *     // ... other methods
 * }
 * }</pre>
 *
 * @see Booster
 * @see BoosterManager
 */
public interface BoosterHolder {

    /**
     * Returns all boosters held by this holder.
     * <p>
     * The returned collection should be unmodifiable or a copy to prevent
     * external modification.
     *
     * @return all boosters (may include expired ones)
     */
    @NonNull Collection<Booster> getBoosters();

    /**
     * Returns all boosters of the specified type.
     * <p>
     * This may include expired boosters; use {@link #getActiveBoosters(BoosterType)}
     * to get only active ones.
     *
     * @param type the booster type to filter by
     * @return boosters of the specified type
     */
    @NonNull Collection<Booster> getBoosters(@NonNull BoosterType type);

    /**
     * Returns all currently active (non-expired) boosters.
     *
     * @return active boosters
     */
    @NonNull Collection<Booster> getActiveBoosters();

    /**
     * Returns all currently active boosters of the specified type.
     *
     * @param type the booster type to filter by
     * @return active boosters of the specified type
     */
    default @NonNull Collection<Booster> getActiveBoosters(@NonNull BoosterType type) {
        return getBoosters(type).stream()
                .filter(Booster::isActive)
                .collect(Collectors.toList());
    }

    /**
     * Adds a booster to this holder.
     * <p>
     * Implementations should fire {@link BoosterCreateEvent}
     * if event handling is supported.
     *
     * @param booster the booster to add
     * @return {@code true} if the booster was added, {@code false} if an error occurred
     * @throws NullPointerException if booster is null
     */
    boolean addBooster(@NonNull Booster booster);

    /**
     * Removes a booster from this holder.
     *
     * @param booster the booster to remove
     * @throws NullPointerException if booster is null
     */
    void removeBooster(@NonNull Booster booster);

    /**
     * Removes a booster by its unique identifier.
     *
     * @param boosterId the unique identifier of the booster to remove
     * @throws NullPointerException if boosterId is null
     */
    void removeBooster(@NonNull UUID boosterId);

    /**
     * Removes all expired boosters from this holder.
     * <p>
     * Implementations should fire {@link BoosterExpireEvent}
     * for each removed booster if event handling is supported.
     *
     * @return the number of boosters removed
     */
    int cleanExpiredBoosters();

    /**
     * Checks if this holder has any active booster of the specified type.
     *
     * @param type the booster type to check
     * @return {@code true} if an active booster of this type exists, {@code false} otherwise
     */
    default boolean hasActiveBooster(@NonNull BoosterType type) {
        return getBoosters(type).stream().anyMatch(Booster::isActive);
    }

    /**
     * Returns the unique identifier of this holder.
     * <p>
     * This is used to identify the holder in calculations and events.
     *
     * @return the unique identifier
     */
    @NonNull UUID getUniqueId();

    /**
     * Returns a human-readable name for this holder type.
     * <p>
     * Used for logging and debugging purposes.
     * Examples: "Player", "VoidChest", "Island", "Server"
     *
     * @return the holder type name
     */
    @NonNull String getHolderTypeName();
}