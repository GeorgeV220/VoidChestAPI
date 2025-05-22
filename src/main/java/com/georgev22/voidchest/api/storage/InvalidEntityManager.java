package com.georgev22.voidchest.api.storage;

import com.georgev22.voidchest.api.storage.data.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class InvalidEntityManager<E extends Entity> implements EntityManager<E> {

    /**
     * Saves the specified entity.
     *
     * @param entity the entity to save
     */
    @Override
    public void save(@NotNull E entity) {

    }

    /**
     * Finds an entity by its unique identifier.
     *
     * @param id the unique identifier
     * @return the entity, or {@code null} if not found
     */
    @Override
    public E findById(@NotNull String id) {
        return null;
    }

    /**
     * Deletes the specified entity.
     *
     * @param entity the entity to delete
     */
    @Override
    public void delete(@NotNull E entity) {

    }

    /**
     * Loads an entity by its unique identifier.
     *
     * @param id the unique identifier
     * @return the entity, or {@code null} if not found
     */
    @Override
    public @Nullable E load(@NotNull String id) {
        return null;
    }

    /**
     * Loads all entities
     */
    @Override
    public void loadAll() {

    }

    /**
     * Saves all entities
     */
    @Override
    public void saveAll() {

    }

    @Override
    public void saveAll(Consumer<E> consumer) {

    }

    /**
     * Returns all entities managed by this manager.
     *
     * @return a list of all entities
     */
    @Override
    public List<E> getAll() {
        return List.of();
    }

    /**
     * Checks if an entity with the specified identifier exists.
     *
     * @param id the unique identifier
     * @return {@code true} if the entity exists, {@code false} otherwise
     */
    @Override
    public boolean exists(@NotNull String id) {
        return false;
    }

    /**
     * Retrieves an entity by its unique identifier. Optionally, creates a new entity
     * if it does not already exist.
     *
     * @param id           the unique identifier
     * @param loadIfExists whether to load the entity if it exists
     * @return the entity, or {@code null} if not found and loadIfExists is false
     */
    @Override
    public E getEntity(@NotNull String id, boolean loadIfExists) {
        return null;
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
    @Override
    public E getEntity(@NotNull String id, boolean loadIfExists, Consumer<E> createConsumerIfDoesNotExist) {
        return null;
    }

    @Override
    public String getName() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public String getSimpleName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void shutdown(Consumer<E> consumer) {

    }
}
