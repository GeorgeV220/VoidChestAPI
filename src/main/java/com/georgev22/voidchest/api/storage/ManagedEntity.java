package com.georgev22.voidchest.api.storage;

import com.georgev22.voidchest.api.storage.model.Entity;
import org.jspecify.annotations.NonNull;

/**
 * Represents a managed storage entity definition.
 * <p>
 * A {@code ManagedEntity} binds together:
 * <ul>
 *     <li>a unique string key used for identification (e.g. file paths, configuration sections, or SQL tables),</li>
 *     <li>the entity's concrete class type and,</li>
 *     <li>a factory used to create new entity instances.</li>
 * </ul>
 *
 * <p>This record is intended to serve as a registry entry for storage systems,
 * allowing consistent handling of multiple entity types (such as player data
 * and void chests) across different storage backends.
 *
 *
 * @param key unique identifier for this managed entity (for example,
 *            {@code "playerData"} or {@code "voidChests"})
 * @param type the concrete {@link Entity} class handled by this entry
 * @param factory factory responsible for creating new entity instances
 * @param <T> the entity type
 */
public record ManagedEntity<T extends Entity>(
        @NonNull String key,
        @NonNull Class<T> type,
        @NonNull EntityFactory<T> factory
) {
}
