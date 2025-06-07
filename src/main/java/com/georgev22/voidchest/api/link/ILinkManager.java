package com.georgev22.voidchest.api.link;

import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.storage.data.IVoidChest;
import com.georgev22.voidchest.api.utilities.ContainerWrapper;
import com.georgev22.voidchest.api.utilities.SerializableContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The {@code ILinkManager} interface provides methods to manage links between {@link IVoidChest} objects and {@link SerializableContainer} objects.
 * It allows adding, removing, and retrieving links, as well as checking if links exist.
 */
public interface ILinkManager {

    /**
     * Retrieves a map of pending links between players and {@link IVoidChest} objects.
     * The map stores the players who are in the process of linking their storage.
     *
     * @return A map of {@link Player} to {@link IVoidChest} representing pending links.
     */
    @NotNull
    ObjectMap<Player, IVoidChest> getPendingLinks();

    /**
     * Adds a link between a specified {@link IVoidChest} and a {@link SerializableContainer}.
     * If the link already exists, the existing link is returned.
     *
     * @param voidChest The {@link IVoidChest} to be linked.
     * @param container The {@link SerializableContainer} to be linked.
     * @return The {@link ILink} that was added, or the existing link if it already exists.
     */
    @NotNull
    ILink addLink(@NotNull IVoidChest voidChest, @NotNull SerializableContainer container);

    /**
     * Removes a link between a specified {@link IVoidChest} and a {@link SerializableContainer}.
     * If the link does not exist, an empty {@link Optional} is returned.
     *
     * @param voidChest The {@link IVoidChest} to be unlinked.
     * @param container The {@link SerializableContainer} to be unlinked.
     * @return An {@link Optional} containing the {@link ILink} that was removed, or empty if no link exists.
     */
    @NotNull
    Optional<ILink> removeLink(@NotNull IVoidChest voidChest, @NotNull SerializableContainer container);

    /**
     * Adds a link based on an {@link ILink} object.
     * If the link already exists, the existing link is returned.
     *
     * @param link The {@link ILink} that represents the link to be added.
     * @return The {@link ILink} that was added, or the existing link if it already exists.
     */
    @NotNull
    ILink addLink(@NotNull ILink link);

    /**
     * Removes a link based on an {@link ILink} object.
     * If the link does not exist, an empty {@link Optional} is returned.
     *
     * @param link The {@link ILink} that represents the link to be removed.
     * @return An {@link Optional} containing the {@link ILink} that was removed, or empty if no link exists.
     */
    @NotNull
    Optional<ILink> removeLink(@NotNull ILink link);

    /**
     * Retrieves a link between a specified {@link IVoidChest} and a {@link SerializableContainer}.
     *
     * @param voidChest The {@link IVoidChest} to find the link for.
     * @param container The {@link SerializableContainer} to find the link for.
     * @return An {@link Optional} containing the found {@link ILink}, or empty if no link exists.
     */
    @NotNull CompletableFuture<Optional<ILink>> getLink(@NotNull IVoidChest voidChest, @NotNull SerializableContainer container);

    /**
     * Checks if a specified {@link IVoidChest} is linked to a {@link SerializableContainer}.
     *
     * @param voidChest The {@link IVoidChest} to check.
     * @param container The {@link SerializableContainer} to check.
     * @return {@code true} if the {@link IVoidChest} is linked to the {@link SerializableContainer}, {@code false} otherwise.
     */
    CompletableFuture<Boolean> isLinked(@NotNull IVoidChest voidChest, @NotNull SerializableContainer container);

    /**
     * Checks if a specified {@link IVoidChest} has any links to any {@link SerializableContainer}.
     *
     * @param voidChest The {@link IVoidChest} to check.
     * @return {@code true} if the {@link IVoidChest} has any links, {@code false} otherwise.
     */
    boolean hasLinks(@NotNull IVoidChest voidChest);

    /**
     * Retrieves all valid links associated with a specified {@link IVoidChest}.
     *
     * @param voidChest The {@link IVoidChest} for which to retrieve the links.
     * @return A list of {@link ILink} objects that represent valid connections
     * between the specified {@link IVoidChest} and its {@link SerializableContainer}.
     * The list may be empty if no valid links are found.
     */
    @NotNull
    List<ILink> getLinks(@NotNull IVoidChest voidChest);

    /**
     * Checks if a specified {@link ContainerWrapper} has any links to any {@link IVoidChest}.
     *
     * @param container The {@link ContainerWrapper} to check.
     * @return {@code true} if the {@link ContainerWrapper} has any links, {@code false} otherwise.
     */
    boolean isLinked(@NotNull ContainerWrapper container);

    /**
     * Retrieves the {@link IVoidChest} associated with a specified {@link ContainerWrapper}.
     *
     * @param container The {@link ContainerWrapper} for which to retrieve the {@link IVoidChest}.
     * @return The {@link IVoidChest} associated with the specified {@link ContainerWrapper}, or {@code Optional.empty()} if no link exists.
     */
    Optional<IVoidChest> getVoidChest(@NotNull ContainerWrapper container);
}
