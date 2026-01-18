package com.georgev22.voidchest.api.storage;

import com.georgev22.voidchest.api.storage.model.Entity;

/**
 * Represents a managed storage entity definition.
 * <p>
 * A {@code ManagedEntity} binds together:
 * <ul>
 *     <li>a unique string key used for identification (e.g. file paths, config sections, SQL tables),</li>
 *     <li>the entity's base class type,</li>
 *     <li>and a factory used to create new entity instances.</li>
 * </ul>
 *
 * <p>This record is intended to be used as a registry entry for storage systems,
 * allowing consistent handling of multiple entity types (such as player data
 * and void chests) across different storage backends.
 *
 * @param key     unique identifier for this managed entity (e.g. {@code "playerData"}, {@code "voidChests"})
 * @param type    the concrete {@link Entity} class handled by this entry
 * @param factory factory responsible for constructing new entity instances
 * @param <T>     the entity type
 */
public record ManagedEntity<T extends Entity>(
        String key,
        Class<T> type,
        EntityFactory<T> factory
) {
}
