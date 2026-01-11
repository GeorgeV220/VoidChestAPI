package com.georgev22.voidchest.api.link;

import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import com.georgev22.voidchest.api.utilities.SerializableContainer;

import java.util.Optional;

/**
 * ILink represents a link between a {@link AbstractVoidChest} and a {@link SerializableContainer}.
 */
public interface ILink {

    /**
     * Get the {@link AbstractVoidChest} that this link is linked to.
     *
     * @return The {@link AbstractVoidChest} that this link is linked to.
     */
    AbstractVoidChest getVoidChest();

    /**
     * Retrieves the {@link SerializableContainer} that this link is associated with.
     * The container is lazily loaded if not already cached.
     * <p>
     * This method may block if it needs to perform an I/O operation to
     * load the container.
     *
     * @return An {@link Optional} containing the {@link SerializableContainer} if available,
     * otherwise an empty {@link Optional} if the container cannot be determined.
     */
    Optional<SerializableContainer> getContainer();

}
