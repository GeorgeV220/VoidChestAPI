package com.georgev22.voidchest.api.link;

import com.georgev22.voidchest.api.datastructures.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.model.AbstractVoidChest;
import com.georgev22.voidchest.api.utilities.ContainerWrapper;
import com.georgev22.voidchest.api.utilities.SerializableContainer;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The {@code ILinkManager} interface provides methods to manage links between {@link AbstractVoidChest} objects and {@link SerializableContainer} objects.
 * It allows adding, removing, and retrieving links, as well as checking if links exist.
 */
public interface ILinkManager {

    /**
     * Retrieves a map of pending links between players and {@link AbstractVoidChest} objects.
     * The map stores the players who are in the process of linking their storage.
     *
     * @return A map of {@link Player} to {@link AbstractVoidChest} representing pending links.
     */
    @NonNull
    ObjectMap<Player, AbstractVoidChest> getPendingLinks();

    /**
     * Adds a link between a specified {@link AbstractVoidChest} and a {@link SerializableContainer}.
     * If the link already exists, the existing link is returned.
     *
     * @param voidChest The {@link AbstractVoidChest} to be linked.
     * @param container The {@link SerializableContainer} to be linked.
     * @return The {@link ILink} that was added, or the existing link if it already exists.
     */
    @NonNull
    ILink addLink(@NonNull AbstractVoidChest voidChest, @NonNull SerializableContainer container);

    /**
     * Removes a link between a specified {@link AbstractVoidChest} and a {@link SerializableContainer}.
     * If the link does not exist, an empty {@link Optional} is returned.
     *
     * @param voidChest The {@link AbstractVoidChest} to be unlinked.
     * @param container The {@link SerializableContainer} to be unlinked.
     * @return An {@link Optional} containing the {@link ILink} that was removed, or empty if no link exists.
     */
    @NonNull
    Optional<ILink> removeLink(@NonNull AbstractVoidChest voidChest, @NonNull SerializableContainer container);

    /**
     * Adds a link based on an {@link ILink} object.
     * If the link already exists, the existing link is returned.
     *
     * @param link The {@link ILink} that represents the link to be added.
     * @return The {@link ILink} that was added, or the existing link if it already exists.
     */
    @NonNull
    ILink addLink(@NonNull ILink link);

    /**
     * Removes a link based on an {@link ILink} object.
     * If the link does not exist, an empty {@link Optional} is returned.
     *
     * @param link The {@link ILink} that represents the link to be removed.
     * @return An {@link Optional} containing the {@link ILink} that was removed, or empty if no link exists.
     */
    @NonNull
    Optional<ILink> removeLink(@NonNull ILink link);

    /**
     * Retrieves a link between a specified {@link AbstractVoidChest} and a {@link SerializableContainer}.
     *
     * @param voidChest The {@link AbstractVoidChest} to find the link for.
     * @param container The {@link SerializableContainer} to find the link for.
     * @return An {@link Optional} containing the found {@link ILink}, or empty if no link exists.
     */
    @NonNull CompletableFuture<Optional<ILink>> getLink(@NonNull AbstractVoidChest voidChest, @NonNull SerializableContainer container);

    /**
     * Checks if a specified {@link AbstractVoidChest} is linked to a {@link SerializableContainer}.
     *
     * @param voidChest The {@link AbstractVoidChest} to check.
     * @param container The {@link SerializableContainer} to check.
     * @return {@code true} if the {@link AbstractVoidChest} is linked to the {@link SerializableContainer}, {@code false} otherwise.
     */
    CompletableFuture<Boolean> isLinked(@NonNull AbstractVoidChest voidChest, @NonNull SerializableContainer container);

    /**
     * Checks if a specified {@link AbstractVoidChest} has any links to any {@link SerializableContainer}.
     *
     * @param voidChest The {@link AbstractVoidChest} to check.
     * @return {@code true} if the {@link AbstractVoidChest} has any links, {@code false} otherwise.
     */
    boolean hasLinks(@NonNull AbstractVoidChest voidChest);

    /**
     * Retrieves all valid links associated with a specified {@link AbstractVoidChest}.
     *
     * @param voidChest The {@link AbstractVoidChest} for which to retrieve the links.
     * @return A list of {@link ILink} objects that represent valid connections
     * between the specified {@link AbstractVoidChest} and its {@link SerializableContainer}.
     * The list may be empty if no valid links are found.
     */
    @NonNull
    List<ILink> getLinks(@NonNull AbstractVoidChest voidChest);

    /**
     * Checks if a specified {@link ContainerWrapper} has any links to any {@link AbstractVoidChest}.
     *
     * @param container The {@link ContainerWrapper} to check.
     * @return {@code true} if the {@link ContainerWrapper} has any links, {@code false} otherwise.
     */
    boolean isLinked(@NonNull ContainerWrapper container);

    /**
     * Retrieves the {@link AbstractVoidChest} associated with a specified {@link ContainerWrapper}.
     *
     * @param container The {@link ContainerWrapper} for which to retrieve the {@link AbstractVoidChest}.
     * @return The {@link AbstractVoidChest} associated with the specified {@link ContainerWrapper}, or {@code Optional.empty()} if no link exists.
     */
    Optional<AbstractVoidChest> getVoidChest(@NonNull ContainerWrapper container);
}
