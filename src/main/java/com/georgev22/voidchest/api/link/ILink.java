package com.georgev22.voidchest.api.link;

import com.georgev22.voidchest.api.storage.data.IVoidChest;
import org.bukkit.block.Container;

import java.util.Optional;

/**
 * ILink represents a link between a {@link IVoidChest} and a {@link Container}.
 */
public interface ILink {

    /**
     * Get the {@link IVoidChest} that this link is linked to.
     *
     * @return The {@link IVoidChest} that this link is linked to.
     */
    IVoidChest getVoidChest();

    /**
     * Retrieves the {@link Container} that this link is associated with.
     * The container is lazily loaded if not already cached.
     * <p>
     * This method may block if it needs to perform an I/O operation to
     * load the container.
     *
     * @return An {@link Optional} containing the {@link Container} if available,
     * otherwise an empty {@link Optional} if the container cannot be determined.
     */
    Optional<Container> getContainer();

}
