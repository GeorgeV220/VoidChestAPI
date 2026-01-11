package com.georgev22.voidchest.api.storage;

import com.georgev22.voidchest.api.storage.model.Entity;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * A no-op implementation of {@link EntityManager} that always returns empty or default values.
 * Useful for disabling entity management without null checks or feature branches.
 *
 * @param <E> the type of entity to manage
 */
public class InvalidEntityManager<E extends Entity> implements EntityManager<E> {

    private final Class<E> entityClass;

    public InvalidEntityManager(@NonNull Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Class<E> getEntityClass() {
        return entityClass;
    }

    /**
     * No-op save implementation.
     *
     * @param entity the entity to save
     */
    @Override
    public void save(@NonNull E entity) {
        // No-op
    }

    /**
     * Always returns {@link Optional#empty()} as no entities are stored.
     *
     * @param id the unique identifier
     * @return an empty {@link Optional}
     */
    @Override
    public Optional<E> findById(@NonNull String id) {
        return Optional.empty();
    }

    /**
     * No-op delete implementation.
     *
     * @param entity the entity to delete
     */
    @Override
    public void delete(@NonNull E entity) {
        // No-op
    }

    /**
     * Always returns {@link Optional#empty()} as no entities are loaded.
     *
     * @param id the unique identifier
     * @return an empty {@link Optional}
     */
    @Override
    public CompletableFuture<Optional<E>> load(@NonNull String id) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    /**
     * No-op loadAll implementation.
     */
    @Override
    public void loadAll() {
        // No-op
    }

    /**
     * No-op saveAll implementation.
     */
    @Override
    public void saveAll() {
        // No-op
    }

    /**
     * No-op saveAll implementation with consumer.
     *
     * @param consumer consumer to call on each entity
     */
    @Override
    public void saveAll(Consumer<E> consumer) {
        // No-op
    }

    /**
     * Always returns an empty list.
     *
     * @return an empty {@link List}
     */
    @Override
    public List<E> getAll() {
        return List.of();
    }

    /**
     * Always returns {@code false} since no entities are stored.
     *
     * @param id the unique identifier
     * @return {@code false}
     */
    @Override
    public boolean exists(@NonNull String id) {
        return false;
    }

    @Override
    public Optional<E> create(@NonNull String id, @NonNull Consumer<E> consumer) {
        return Optional.empty();
    }

    /**
     * Returns the fully qualified class name.
     *
     * @return the full class name
     */
    @Override
    public String getName() {
        return this.getClass().getCanonicalName();
    }

    /**
     * Returns the simple class name.
     *
     * @return the simple class name
     */
    @Override
    public String getSimpleName() {
        return this.getClass().getSimpleName();
    }

    /**
     * No-op shutdown implementation.
     *
     * @param consumer consumer to apply to each entity
     */
    @Override
    public void shutdown(Consumer<E> consumer) {
        // No-op
    }
}
