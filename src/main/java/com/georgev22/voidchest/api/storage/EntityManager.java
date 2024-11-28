package com.georgev22.voidchest.api.storage;

import com.georgev22.voidchest.api.storage.data.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Interface for managing entities with CRUD operations and additional helper methods.
 *
 * @param <E> the type of entity to manage
 */
public interface EntityManager<E extends Entity> {

    /**
     * Saves the specified entity.
     *
     * @param entity the entity to save
     */
    void save(@NotNull E entity);

    /**
     * Finds an entity by its unique identifier.
     *
     * @param id the unique identifier
     * @return the entity, or {@code null} if not found
     */
    E findById(@NotNull String id);

    /**
     * Finds an entity by its unique identifier.
     *
     * @param uuid the unique identifier
     * @return the entity, or {@code null} if not found
     */
    default E findById(@NotNull UUID uuid) {
        return findById(uuid.toString());
    }

    /**
     * Deletes the specified entity.
     *
     * @param entity the entity to delete
     */
    void delete(@NotNull E entity);

    /**
     * Loads an entity by its unique identifier.
     *
     * @param id the unique identifier
     * @return the entity, or {@code null} if not found
     */
    @Nullable E load(@NotNull String id);

    /**
     * Loads an entity by its unique identifier.
     *
     * @param uuid the unique identifier
     * @return the entity, or {@code null} if not found
     */
    default @Nullable E load(@NotNull UUID uuid) {
        return load(uuid.toString());
    }

    /**
     * Loads all entities
     */
    void loadAll();

    /**
     * Saves all entities
     */
    void saveAll();

    /**
     * Returns all entities managed by this manager.
     *
     * @return a list of all entities
     */
    List<E> getAll();

    /**
     * Checks if an entity with the specified identifier exists.
     *
     * @param id the unique identifier
     * @return {@code true} if the entity exists, {@code false} otherwise
     */
    boolean exists(@NotNull String id);

    /**
     * Checks if an entity with the specified identifier exists.
     *
     * @param uuid the unique identifier
     * @return {@code true} if the entity exists, {@code false} otherwise
     */
    default boolean exists(@NotNull UUID uuid) {
        return exists(uuid.toString());
    }

    /**
     * Retrieves an entity by its unique identifier. Optionally, creates a new entity
     * if it does not already exist.
     *
     * @param id           the unique identifier
     * @param loadIfExists whether to load the entity if it exists
     * @return the entity, or {@code null} if not found and loadIfExists is false
     */
    E getEntity(@NotNull String id, boolean loadIfExists);

    /**
     * Retrieves an entity by its unique identifier. Optionally, creates a new entity
     * if it does not already exist.
     *
     * @param uuid         the unique identifier
     * @param loadIfExists whether to load the entity if it exists
     * @return the entity, or {@code null} if not found and loadIfExists is false
     */
    default E getEntity(@NotNull UUID uuid, boolean loadIfExists) {
        return getEntity(uuid.toString(), loadIfExists);
    }

    /**
     * Retrieves an entity by its unique identifier. If it does not exist and the
     * createConsumer is provided, creates a new entity.
     *
     * @param id                           the unique identifier
     * @param loadIfExists                 whether to load the entity if it exists
     * @param createConsumerIfDoesNotExist the consumer to handle entity creation if not found
     * @return the entity, or newly created entity if not found
     */
    E getEntity(@NotNull String id, boolean loadIfExists, Consumer<E> createConsumerIfDoesNotExist);

    /**
     * Retrieves an entity by its unique identifier. If it does not exist and the
     * createConsumer is provided, creates a new entity.
     *
     * @param uuid                         the unique identifier
     * @param loadIfExists                 whether to load the entity if it exists
     * @param createConsumerIfDoesNotExist the consumer to handle entity creation if not found
     * @return the entity, or newly created entity if not found
     */
    default E getEntity(@NotNull UUID uuid, boolean loadIfExists, Consumer<E> createConsumerIfDoesNotExist) {
        return getEntity(uuid.toString(), loadIfExists, createConsumerIfDoesNotExist);
    }

    String getName();

    String getSimpleName();

}
