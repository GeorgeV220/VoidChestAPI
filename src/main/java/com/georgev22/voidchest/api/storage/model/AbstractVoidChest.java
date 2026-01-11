package com.georgev22.voidchest.api.storage.model;

import com.georgev22.voidchest.api.link.ILink;
import com.georgev22.voidchest.api.link.ILinkManager;
import com.georgev22.voidchest.api.datastructures.maps.UnmodifiableObjectMap;
import com.georgev22.voidchest.api.storage.model.voidchest.Abilities;
import com.georgev22.voidchest.api.storage.model.voidchest.Charge;
import com.georgev22.voidchest.api.storage.model.voidchest.Stats;
import com.georgev22.voidchest.api.utilities.BoundingBox;
import com.georgev22.voidchest.api.utilities.SerializableBlock;
import com.georgev22.voidchest.api.utilities.SerializableLocation;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnknownNullability;
import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * The AbstractVoidChest abstract class provides methods for managing a VoidChest.
 */
public abstract class AbstractVoidChest extends Entity {

    public AbstractVoidChest(UUID uuid) {
        super(uuid);
    }

    /**
     * Checks if the VoidChest is a container.
     *
     * @return {@code true} if the VoidChest is a container, {@code false} otherwise.
     */
    public abstract boolean isContainer();

    /**
     * Sets the container status of the VoidChest.
     *
     * @param isContainer {@code true} to set the VoidChest as a container, {@code false} otherwise.
     */
    public abstract void isContainer(boolean isContainer);

    /**
     * Retrieves the block of the VoidChest as a SerializableBlock.
     *
     * @return The block of the VoidChest.
     */
    public abstract @NonNull SerializableBlock block();

    /**
     * Sets the block of the VoidChest.
     *
     * @param block The block to set.
     */
    public abstract void block(SerializableBlock block);

    /**
     * Retrieves the inventory of the block associated with the VoidChest.
     * <p>
     * This method may return {@code null} if the inventory could not be retrieved.
     * Additionally, calling this method outside the main thread or the region thread in Folia
     * will result in an exception. Ensure the appropriate scheduler is used when invoking this method.
     *
     * @return The inventory of the block associated with the VoidChest, or {@code null} if unavailable.
     * @throws IllegalStateException if called outside the main thread or region thread in Folia.
     */
    public abstract @Nullable Inventory blockInventory();

    /**
     * Sets the inventory of the block associated with the VoidChest.
     * <p><strong>Note</strong>
     * This is only available for VoidChests that are not containers.
     *
     * @param inventory The inventory to set.
     */
    public abstract void blockInventory(Inventory inventory);

    /**
     * Retrieves the location of the block associated with the VoidChest as a SerializableLocation.
     *
     * @return The location of the block associated with the VoidChest.
     */
    public abstract @NonNull SerializableLocation blockLocation();

    /**
     * Sets the location of the block associated with the VoidChest.
     *
     * @param blockLocation The location to set.
     */
    public abstract void blockLocation(SerializableLocation blockLocation);

    /**
     * Retrieves the type(config) name of the VoidChest.
     *
     * @return The type(config) name of the VoidChest.
     */
    @UnknownNullability
    public abstract String type();

    /**
     * Sets the type(config) name of the VoidChest.
     *
     * @param type The type(config) name to set.
     */
    public abstract void type(String type);

    /**
     * Retrieves the name of the VoidChest.
     * Players can change the name of the VoidChest
     *
     * @return The name of the VoidChest.
     */
    public abstract String name();

    /**
     * Sets the name of the VoidChest.
     *
     * @param name The name to set.
     */
    public abstract void name(String name);

    /**
     * Retrieves the bounding box that defines the area from which the VoidChest will collect items.
     * If the bounding box is not explicitly set, the area will default to the chunk where the VoidChest is placed.
     *
     * @return The bounding box defining the collection area.
     */
    @Nullable
    public BoundingBox boundingBox() throws IllegalStateException {
        return blockLocation().getBoundingBox();
    }

    /**
     * Retrieves the upgrades of the VoidChest.
     *
     * @return The upgrades of the VoidChest.
     */
    public abstract UnmodifiableObjectMap<NamespacedKey, Integer> upgrades();

    /**
     * Adds an upgrade to the VoidChest.
     *
     * @param upgrade The upgrade to add.
     * @param level   The level of the upgrade.
     */
    public abstract void addUpgrade(NamespacedKey upgrade, int level);

    /**
     * Removes an upgrade from the VoidChest.
     *
     * @param upgrade The upgrade to remove.
     */
    public abstract void removeUpgrade(NamespacedKey upgrade);

    /**
     * Retrieves the booster value of the VoidChest.
     *
     * @return The booster value of the VoidChest.
     */
    public abstract BigDecimal booster();

    /**
     * Sets the booster value of the VoidChest.
     *
     * @param booster The booster value to set.
     */
    public abstract void booster(Double booster);

    /**
     * Retrieves the charge state of the VoidChest.
     *
     * @return The charge state of the VoidChest.
     */
    public abstract Charge charge();

    /**
     * Sets the charge state of the VoidChest.
     *
     * @param charge The charge state to set.
     */
    public abstract void charge(Charge charge);

    /**
     * Retrieves the abilities of the VoidChest.
     *
     * @return The abilities of the VoidChest.
     */
    public abstract Abilities abilities();

    /**
     * Sets the abilities of the VoidChest.
     *
     * @param abilities The abilities to set.
     */
    public abstract void abilities(Abilities abilities);

    /**
     * Retrieves the statistics of the VoidChest.
     *
     * @return The statistics of the VoidChest.
     */
    public abstract Stats stats();

    /**
     * Sets the statistics of the VoidChest.
     *
     * @param stats The statistics to set.
     */
    public abstract void stats(Stats stats);

    /**
     * Retrieves the owner UUID of the VoidChest.
     *
     * @return The owner UUID of the VoidChest.
     */
    public abstract UUID ownerUUID();

    /**
     * Sets the owner UUID of the VoidChest.
     *
     * @param uuid The owner UUID to set.
     */
    public abstract void ownerUUID(UUID uuid);

    /**
     * Retrieves the owner name of the VoidChest.
     *
     * @return The owner name of the VoidChest.
     */
    public abstract String ownerName();

    /**
     * Sets the owner name of the VoidChest.
     *
     * @param name The owner name to set.
     */
    public abstract void ownerName(String name);

    /**
     * Retrieves the unmodifiable list of links associated with the VoidChest.
     *
     * <p>
     * <strong>Note</strong> This is an internal API, Please use {@link ILinkManager} instead.
     *
     * @return The unmodifiable list of links associated with the VoidChest.
     */
    @UnmodifiableView
    @ApiStatus.Internal
    public abstract List<ILink> links();

    /**
     * Sets the list of links associated with the VoidChest.
     *
     * <p>
     * <strong>Note</strong> This is an internal API, Please use {@link ILinkManager} instead.
     *
     * @param links The list of links to set.
     */
    @ApiStatus.Internal
    public abstract void links(List<ILink> links);

    /**
     * Reloads the void chest.
     */
    public abstract void reloadVoidChest();

    /**
     * Retrieves the maximum number of links that can be added to the VoidChest.
     *
     * @return The maximum number of links that can be added to the VoidChest.
     */
    public abstract int maxLinks();

    /**
     * Retrieves the list of disabled worlds.
     *
     * @return The list of disabled worlds.
     */
    public abstract List<String> getDisabledWorlds();

    /**
     * Adds a disabled world to the list of disabled worlds.
     *
     * @param world The name of the disabled world.
     */
    public abstract void addDisabledWorld(String world);

    /**
     * Removes a disabled world from the list of disabled worlds.
     *
     * @param world The name of the disabled world.
     */
    public abstract void removeDisabledWorld(String world);

    /**
     * Retrieves the void chest type configuration.
     *
     * @return The void chest type configuration.
     */
    public abstract FileConfiguration voidChestTypeConfig();

}
