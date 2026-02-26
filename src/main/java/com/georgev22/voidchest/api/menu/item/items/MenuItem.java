package com.georgev22.voidchest.api.menu.item.items;

import com.georgev22.voidchest.api.animation.engine.AnimationInstance;
import com.georgev22.voidchest.api.animation.engine.AnimationRequest;
import com.georgev22.voidchest.api.animation.engine.AnimationRequestOptions;
import com.georgev22.voidchest.api.animation.frame.FrameGenerator;
import com.georgev22.voidchest.api.animation.render.ComponentRenderer;
import com.georgev22.voidchest.api.animation.type.AnimationType;
import com.georgev22.voidchest.api.menu.actions.Action;
import com.georgev22.voidchest.api.menu.item.ItemProvider;
import com.georgev22.voidchest.api.menu.viewer.ViewerContext;
import com.georgev22.voidchest.api.utilities.BukkitMinecraftUtils;
import com.georgev22.voidchest.api.utilities.Copyable;
import com.georgev22.voidchest.api.utilities.CustomData;
import com.georgev22.voidchest.api.utilities.Utils;
import com.georgev22.voidchest.api.utilities.color.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents an item in a menu. Each item has an associated ItemProvider and an action
 * that is triggered when the item is interacted with by a player.
 */
public class MenuItem implements Cloneable, Copyable<MenuItem> {

    @ApiStatus.Internal
    private UUID id;
    private CustomData customData;
    private ItemProvider itemProvider;
    private @Nullable Action action;
    private @Nullable List<Color> colors;
    private @Nullable List<MenuFrameItem> frames;
    private @Nullable AnimationType animationType;
    private @Nullable AnimationRequestOptions animationRequestOptions;
    private transient AnimationInstance<Component> animationInstance;
    private int slot = -1;
    private final Set<ViewerContext> contexts = new HashSet<>();

    public MenuItem(@NonNull ItemProvider itemProvider) {
        this(itemProvider, null);
    }

    /**
     * Constructs a MenuItem with an ItemProvider and an action.
     *
     * @param itemProvider The ItemProvider representing the item in the menu.
     * @param action       The action to perform when the item is clicked by a player.
     */
    public MenuItem(@NonNull ItemProvider itemProvider, @Nullable Action action) {
        this(itemProvider, action, null);
    }

    /**
     * Constructs a MenuItem with an ItemProvider, an action, and a list of colors.
     *
     * @param itemProvider The ItemProvider representing the item in the menu.
     * @param action       The action to perform when the item is clicked by a player.
     * @param colors       The list of colors to apply to the item.
     */
    public MenuItem(@NonNull ItemProvider itemProvider,
                    @Nullable Action action,
                    @Nullable List<Color> colors
    ) {
        this(itemProvider, action, colors, null);
    }

    /**
     * Constructs a MenuItem with an ItemProvider, an action, a list of colors, and a list of frames.
     *
     * @param itemProvider The ItemProvider representing the item in the menu.
     * @param action       The action to perform when the item is clicked by a player.
     * @param colors       The list of colors to apply to the item.
     * @param frames       The list of frames to apply to the item.
     */
    public MenuItem(@NonNull ItemProvider itemProvider,
                    @Nullable Action action,
                    @Nullable List<Color> colors,
                    @Nullable List<MenuFrameItem> frames
    ) {
        this(itemProvider, action, colors, frames, null, null);
    }

    /**
     * Constructs a MenuItem with an ItemProvider, an action, a list of colors, a list of frames, and an animation.
     *
     * @param itemProvider            The ItemProvider representing the item in the menu.
     * @param action                  The action to perform when the item is clicked by a player.
     * @param colors                  The list of colors to apply to the item.
     * @param frames                  The list of frames to apply to the item.
     * @param animationType           The animation type to apply to the item.
     * @param animationRequestOptions The options to apply to the animation.
     */
    public MenuItem(@NonNull ItemProvider itemProvider,
                    @Nullable Action action,
                    @Nullable List<Color> colors,
                    @Nullable List<MenuFrameItem> frames,
                    @Nullable AnimationType animationType,
                    @Nullable AnimationRequestOptions animationRequestOptions
    ) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        String seed = System.nanoTime()
                + ThreadLocalRandom.current().nextLong()
                + Base64.getEncoder().encodeToString(randomBytes)
                + UUID.randomUUID();

        this.id = Utils.generateUUID(seed);
        this.customData = new CustomData();

        this.itemProvider = itemProvider;
        this.action = action;
        this.colors = colors;
        this.frames = frames;
        this.animationType = animationType;
        this.animationRequestOptions = animationRequestOptions;
    }

    /**
     * Gets the ItemProvider of this menu item.
     *
     * @return The ItemProvider representing the menu item.
     */
    @NonNull
    public ItemProvider getItemProvider() {
        return itemProvider;
    }

    /**
     * Gets the action associated with this menu item.
     *
     * @return The Consumer<Player> action to be executed when the item is clicked.
     */
    @Nullable
    public Action getAction() {
        return action;
    }

    /**
     * Sets the ItemProvider for this menu item.
     *
     * @param itemProvider The new ItemProvider to set for this menu item.
     */
    public void setItemStack(@NonNull ItemProvider itemProvider) {
        this.itemProvider = itemProvider;
    }

    /**
     * Sets the action for this menu item.
     *
     * @param action The new action to be executed when the item is clicked.
     */
    public void setAction(@Nullable Action action) {
        this.action = action;
    }

    /**
     * Gets the list of colors associated with this menu item.
     *
     * @return The list of colors.
     */
    public @Nullable List<Color> getColors() {
        return colors;
    }

    /**
     * Sets the list of colors associated with this menu item.
     *
     * @param colors The new list of colors.
     */
    public void setColors(@Nullable List<Color> colors) {
        this.colors = colors;
    }

    /**
     * Checks if this menu item has colors associated with it.
     *
     * @return True if the menu item has colors, false otherwise.
     */
    public boolean hasColors() {
        return colors != null && !colors.isEmpty();
    }

    /**
     * Gets the list of frames associated with this menu item.
     *
     * @return The list of frames.
     */
    public @Nullable List<MenuFrameItem> getFrames() {
        return frames;
    }

    /**
     * Sets the list of frames associated with this menu item.
     *
     * @param frames The new list of frames.
     */
    public void setFrames(@Nullable List<MenuFrameItem> frames) {
        this.frames = frames;
    }

    /**
     * Checks if this menu item has frames associated with it.
     *
     * @return True if the menu item has frames, false otherwise.
     */
    public boolean hasFrames() {
        return frames != null && !frames.isEmpty();
    }

    /**
     * Sets the slot of this menu item.
     *
     * @param slot The new slot to set for this menu item.
     */
    @ApiStatus.Internal
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Gets the slot of this menu item.
     *
     * @return The slot of this menu item.
     */
    @ApiStatus.Internal
    public int getSlot() {
        return slot;
    }

    /**
     * Sets the animation type for this menu item.
     *
     * @param animationType The new animation type to set for this menu item.
     */
    public void setAnimationType(@Nullable AnimationType animationType) {
        this.animationType = animationType;
    }

    /**
     * Sets the animation request options for this menu item.
     *
     * @param animationRequestOptions The new animation request options to set for this menu item.
     */
    public void setAnimationRequestOptions(@Nullable AnimationRequestOptions animationRequestOptions) {
        this.animationRequestOptions = animationRequestOptions;
    }

    /**
     * Checks if this menu item has an animation.
     *
     * @return True if the menu item has an animation, false otherwise.
     */
    public boolean hasAnimation() {
        return animationType != null &&
                !animationType.getName().equalsIgnoreCase("none") &&
                !animationType.getName().equalsIgnoreCase("unknown");
    }

    /**
     * Retrieves the custom data associated with the menu item.
     *
     * @return the custom data, never null.
     */
    public CustomData getCustomData() {
        return customData;
    }

    /**
     * Adds the specified ViewerContext to the collection if it is not already present.
     * If the context already exists in the collection, this method does nothing.
     *
     * @param viewerContext the ViewerContext instance to be added to the collection
     **/
    public void addViewerContext(ViewerContext viewerContext) {
        if (this.contexts.contains(viewerContext)) return;
        this.contexts.add(viewerContext);
    }

    /**
     * Removes the specified ViewerContext from the collection if it is present.
     * If the context is not present in the collection, this method does nothing.
     *
     * @param viewerContext the ViewerContext instance to be removed from the collection
     **/
    public void removeViewerContext(ViewerContext viewerContext) {
        if (!this.contexts.contains(viewerContext)) return;
        this.contexts.remove(viewerContext);
    }

    /**
     * Returns an unmodifiable view of the collection of ViewerContext instances associated with this MenuItem.
     *
     * @return an unmodifiable view of the collection of ViewerContext instances
     */
    @UnmodifiableView
    public Set<ViewerContext> getViewerContexts() {
        return Collections.unmodifiableSet(contexts);
    }

    /**
     * Notifies all ViewerContext instances associated with this MenuItem that the context has changed.
     */
    public void notifyContext() {
        for (ViewerContext viewerContext : contexts) {
            viewerContext.notifyContext();
        }
    }

    public void initializeAnimation(Component baseName) {
        if (animationType == null) return;
        if (!hasAnimation() || !hasColors()) return;

        String plain = LegacyComponentSerializer.legacySection().serialize(baseName);
        plain = BukkitMinecraftUtils.stripColor(plain);

        if (animationInstance != null) {
            if (animationInstance.getRequest().text().equals(plain)) {
                return;
            }
            animationInstance = null;
        }

        if (animationRequestOptions == null) {
            animationRequestOptions = new AnimationRequestOptions(false, colors, 10);
        }

        AnimationRequest animationRequest = new AnimationRequest(
                plain,
                animationRequestOptions
        );

        FrameGenerator generator = animationType.create(animationRequest);

        animationInstance = new AnimationInstance<>(
                animationRequest,
                generator,
                new ComponentRenderer()
        );
    }

    public AnimationInstance<Component> getAnimationInstance() {
        return animationInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public MenuItem clone() {
        try {
            final MenuItem clone = (MenuItem) super.clone();

            // deep-copy mutable fields
            clone.itemProvider = this.itemProvider != null ? this.itemProvider.clone() : null;
            clone.colors = this.colors != null ? new ArrayList<>(this.colors) : null;
            clone.frames = this.frames != null ? new ArrayList<>(this.frames) : null;
            clone.animationType = this.animationType;
            clone.animationInstance = null;
            clone.animationRequestOptions = this.animationRequestOptions;
            clone.slot = this.slot;

            // copy custom data
            clone.customData = this.customData.clone();

            // fresh context set — don’t copy references
            clone.contexts.clear();

            // generate new ID for the clone
            byte[] randomBytes = new byte[32];
            new SecureRandom().nextBytes(randomBytes);
            String seed = System.nanoTime()
                    + ThreadLocalRandom.current().nextLong()
                    + Base64.getEncoder().encodeToString(randomBytes)
                    + UUID.randomUUID();
            clone.id = Utils.generateUUID(seed);

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Something went wrong while cloning MenuItem", e);
        }
    }

    /**
     * Creates a shallow copy of the current object.
     * <p>
     * Only the top-level fields are copied; nested objects are referenced directly.
     *
     * @return a shallow copy of type {@code T}
     */
    @Override
    public @NonNull MenuItem shallowCopy() {
        try {
            return (MenuItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Something went wrong while shallow copying MenuItem", e);
        }
    }

    /**
     * Creates a deep copy of the current object.
     * <p>
     * Both top-level fields and nested objects are fully copied.
     *
     * @return a deep copy of type {@code T}
     */
    @Override
    public @NonNull MenuItem deepCopy() {
        return this.clone();
    }
}
