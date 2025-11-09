package com.georgev22.voidchest.api.registry;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.storage.EntityManager;
import com.georgev22.voidchest.api.storage.data.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.logging.Level;

public final class EntityManagerRegistry
        extends AbstractRegistry<Class<? extends Entity>, EntityManager<? extends Entity>> {

    private static final EntityManagerRegistry INSTANCE = new EntityManagerRegistry();

    private EntityManagerRegistry() {
    }

    public static EntityManagerRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(@NotNull EntityManager<? extends Entity> value) throws IllegalArgumentException {
        super.register(value.getEntityClass(), value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean replaceOrRegister(@NotNull EntityManager<? extends Entity> value) {
        return super.replaceOrRegister(value.getEntityClass(), value);
    }

    /**
     * Retrieves a typed {@link EntityManager} for the given entity class from the registry.
     *
     * <p>This method provides type-safe access to {@link EntityManager} instances stored in this registry.
     * It will immediately throw a {@link ClassCastException} if the stored manager is not compatible with the requested type.</p>
     *
     * @param <T>   the type of {@link Entity} for which the {@link EntityManager} is requested
     * @param clazz the {@link Class} object of the entity type
     * @return an {@link Optional} containing the typed {@link EntityManager} if present or an empty {@link Optional}
     */
    @SuppressWarnings("unchecked")
    public <T extends Entity> @NotNull Optional<EntityManager<T>> getTyped(Class<T> clazz) {
        Optional<? extends EntityManager<? extends Entity>> optionalRaw = super.get(clazz);

        if (optionalRaw.isEmpty()) {
            return Optional.empty();
        }

        EntityManager<? extends Entity> rawManager = optionalRaw.get();

        if (!clazz.isAssignableFrom(rawManager.getEntityClass())) {
            if (VoidChestAPI.debug()) {
                VoidChestAPI.getInstance().plugin().getLogger().log(Level.SEVERE, "Failed to get typed manager",
                        new ClassCastException("EntityManager registered for "
                                + rawManager.getEntityClass().getName()
                                + " cannot be cast to EntityManager<" + clazz.getName() + ">"));
            }
            return Optional.empty();
        }

        return Optional.of((EntityManager<T>) rawManager);
    }

}
