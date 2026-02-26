package com.georgev22.voidchest.api.menu.item.builder;

import com.georgev22.voidchest.api.datastructures.Pair;
import com.georgev22.voidchest.api.menu.item.ItemProvider;
import com.georgev22.voidchest.api.utilities.BukkitMinecraftUtils.MinecraftVersion;
import com.georgev22.voidchest.api.utilities.MessageBuilder;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstract base class for item builders.
 *
 * @param <S> The type of the builder for chaining.
 */
@SuppressWarnings({"UnusedReturnValue", "unchecked"})
public abstract class AbstractItemBuilder<S> implements ItemProvider {

    /**
     * The cached {@link ItemStack}.
     */
    private ItemStack cachedItem;
    /**
     * Whether the cache is dirty and needs to be updated.
     */
    private boolean cacheDirty = true;

    /**
     * The {@link ItemStack} to use as a base.
     */
    protected ItemStack base;
    /**
     * The {@link Material} of the {@link ItemStack}.
     */
    protected Material material;
    /**
     * The amount of the {@link ItemStack}.
     */
    protected int amount = 1;
    /**
     * The damage value of the {@link ItemStack}
     */
    protected int damage;
    /**
     * The custom model data value of the {@link ItemStack}.
     */
    protected int customModelData;
    /**
     * The unbreakable state of the {@link ItemStack}.
     */
    protected Boolean unbreakable;
    /**
     * The glow state of the {@link ItemStack}.
     */
    protected Boolean glow;
    /**
     * The display name of the {@link ItemStack}.
     */
    protected Component displayName;
    /**
     * The lore of the {@link ItemStack}.
     */
    protected List<Component> lore;
    /**
     * The selected {@link ItemFlag ItemFlags} of the {@link ItemStack}.
     */
    protected List<ItemFlag> itemFlags;
    /**
     * The enchantments of the {@link ItemStack}.
     */
    protected HashMap<Enchantment, Pair<Integer, Boolean>> enchantments;
    /**
     * Additional modifier functions to be run after building the {@link ItemStack}.
     */
    protected List<Function<ItemStack, ItemStack>> modifiers;

    /**
     * Additional NBT data to be added to the {@link ItemStack}.
     * This is used to add Minecraft NBT data to the {@link ItemStack}.
     */
    protected List<Consumer<ReadWriteNBT>> minecraftInternalNbtConsumers;

    /**
     * Additional NBT data to be added to the {@link ItemStack}.
     * This is used to add plugin specific NBT data to the {@link ItemStack}.
     */
    protected List<Consumer<ReadWriteNBT>> pluginsNbtConsumers;

    /**
     * Constructs a new {@link AbstractItemBuilder} based on the given {@link Material}.
     *
     * @param material The {@link Material}
     */
    public AbstractItemBuilder(@NonNull Material material) {
        this.material = material;
    }

    /**
     * Constructs a new {@link AbstractItemBuilder} based on the given {@link Material} and amount.
     *
     * @param material The {@link Material}
     * @param amount   The amount
     */
    public AbstractItemBuilder(@NonNull Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    /**
     * Constructs a new {@link AbstractItemBuilder} based on the give {@link ItemStack}.
     * This will keep the {@link ItemStack} and uses it's {@link ItemMeta}
     *
     * @param base The {@link ItemStack to use as a base}
     */
    public AbstractItemBuilder(@NonNull ItemStack base) {
        this.base = base.clone();
        this.amount = base.getAmount();
    }

    /**
     * Builds the {@link ItemStack}
     *
     * @return The {@link ItemStack}
     */
    @SuppressWarnings("deprecation")
    @Override
    public @NonNull ItemStack get() {
        if (!cacheDirty && cachedItem != null) {
            return cachedItem.clone();
        }

        ItemStack itemStack = (base != null ? base.clone() : new ItemStack(material, amount));
        itemStack.setAmount(amount);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            // display name
            if (displayName != null) {
                itemMeta.displayName(MessageBuilder.builder().append(displayName).build());
            }

            // lore
            if (lore != null) {
                itemMeta.lore(lore.stream().map(component -> MessageBuilder.builder().append(component)
                        .build()).collect(Collectors.toList()));
            }

            // damage
            if (MinecraftVersion.getCurrentVersion().isAboveOrEqual(MinecraftVersion.V1_13_R1)) {
                if (itemMeta instanceof org.bukkit.inventory.meta.Damageable)
                    ((org.bukkit.inventory.meta.Damageable) itemMeta).setDamage(damage);
            } else {
                //noinspection deprecation
                itemStack.setDurability((short) damage);
            }

            // custom model data
            if (MinecraftVersion.getCurrentVersion().isAboveOrEqual(MinecraftVersion.V1_14_R1)) {
                if (customModelData != 0)
                    itemMeta.setCustomModelData(customModelData);
            }

            // unbreakable
            if (MinecraftVersion.getCurrentVersion().isAboveOrEqual(MinecraftVersion.V1_11_R1)) {
                if (unbreakable != null)
                    itemMeta.setUnbreakable(unbreakable);
            }

            // enchantments
            if (enchantments != null) {
                if (base != null)
                    itemMeta.getEnchants().forEach((enchantment, level) -> itemMeta.removeEnchant(enchantment));

                enchantments.forEach((enchantment, pair) -> itemMeta.addEnchant(enchantment, pair.first(), pair.secondOptional().orElse(false)));
            }

            // item flags
            if (itemFlags != null) {
                if (base != null)
                    itemMeta.removeItemFlags(itemMeta.getItemFlags().toArray(new ItemFlag[0]));

                itemMeta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
            }

            // apply to the item stack
            itemStack.setItemMeta(itemMeta);
        }

        // run modifiers
        if (modifiers != null) {
            for (Function<ItemStack, ItemStack> modifier : modifiers)
                itemStack = modifier.apply(itemStack);
        }

        // NBT
        if ((minecraftInternalNbtConsumers != null && !minecraftInternalNbtConsumers.isEmpty())
                || (pluginsNbtConsumers != null && !pluginsNbtConsumers.isEmpty())
                || glow != null) {

            if (MinecraftVersion.getCurrentVersion().isAboveOrEqual(MinecraftVersion.V1_20_R4)) {
                // Components system (1.20.5+)
                NBT.modifyComponents(itemStack, nbtCompound -> {
                    if (glow != null) {
                        nbtCompound.setBoolean("minecraft:enchantment_glint_override", glow);
                    }
                    if (minecraftInternalNbtConsumers != null) {
                        minecraftInternalNbtConsumers.forEach(consumer -> consumer.accept(nbtCompound));
                    }
                });

                NBT.modify(itemStack, nbtCompound -> {
                    if (pluginsNbtConsumers != null) {
                        pluginsNbtConsumers.forEach(consumer -> consumer.accept(nbtCompound));
                    }
                });
            } else {
                // Classic NBT (pre-1.20.5)
                NBT.modify(itemStack, nbtCompound -> {
                    if (glow != null) {
                        nbtCompound.setBoolean("Glowing", glow);
                    }
                    if (minecraftInternalNbtConsumers != null) {
                        minecraftInternalNbtConsumers.forEach(consumer -> consumer.accept(nbtCompound));
                    }
                    if (pluginsNbtConsumers != null) {
                        pluginsNbtConsumers.forEach(consumer -> consumer.accept(nbtCompound));
                    }
                });
            }
        }

        cachedItem = itemStack;
        cacheDirty = false;
        return cachedItem;
    }

    /**
     * Removes a lore line at the given index.
     *
     * @param index The index of the lore line to remove
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S removeLoreLine(int index) {
        if (lore != null) {
            lore.remove(index);
            cacheDirty = true;
        }
        return (S) this;
    }

    /**
     * Clears the lore.
     *
     * @return The builder instance
     */
    @Contract("-> this")
    public @NonNull S clearLore() {
        if (lore != null) {
            lore.clear();
            cacheDirty = true;
        }
        return (S) this;
    }

    /**
     * Gets the base {@link ItemStack} of this builder.
     *
     * @return The base {@link ItemStack}
     */
    public @Nullable ItemStack getBase() {
        return base;
    }

    /**
     * Gets the {@link Material} of this builder.
     *
     * @return The {@link Material}
     */
    public @Nullable Material getMaterial() {
        return material;
    }

    /**
     * Sets the {@link Material} of this builder.
     *
     * @param material The {@link Material}
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setMaterial(@NonNull Material material) {
        this.material = material;
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Gets the amount.
     *
     * @return The amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     *
     * @param amount The amount
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setAmount(int amount) {
        this.amount = amount;
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Gets the damage value.
     *
     * @return The damage value
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the damage value.
     *
     * @param damage The damage value
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setDamage(int damage) {
        this.damage = damage;
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Gets the custom model data value.
     *
     * @return The custom model data value
     */
    public int getCustomModelData() {
        return customModelData;
    }

    /**
     * Sets the custom model data value.
     *
     * @param customModelData The custom model data value
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Gets the unbreakable state, null for default.
     *
     * @return The unbreakable state
     */
    public @Nullable Boolean isUnbreakable() {
        return unbreakable;
    }

    /**
     * Sets the unbreakable state.
     *
     * @param unbreakable The unbreakable state
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Gets the glow state.
     *
     * @return The glow state
     */
    public boolean isGlowing() {
        return glow;
    }

    /**
     * Sets the glow state.
     *
     * @param glow The glow state
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setGlow(boolean glow) {
        this.glow = glow;
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Gets the display name.
     *
     * @return The display name
     */
    public @Nullable Component getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name.
     *
     * @param displayName The display name
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setDisplayName(String displayName) {
        this.displayName = MessageBuilder.builder().appendMiniMessage(displayName).build();
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Sets the display name.
     *
     * @param component The display name
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setDisplayName(Component component) {
        this.displayName = component;
        cacheDirty = true;
        return (S) this;
    }

    //<editor-fold desc="lore">

    /**
     * Gets the lore.
     *
     * @return The lore
     */
    public @Nullable List<Component> getLore() {
        return lore;
    }

    /**
     * Sets the lore.
     *
     * @param lore The lore
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setLore(@NonNull List<@NonNull Component> lore) {
        this.lore = lore;
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Sets the lore using the legacy text format.
     *
     * @param lore The lore
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setLegacyLore(@NonNull List<@NonNull String> lore) {
        this.lore = lore.stream()
                .map(line -> MessageBuilder.builder().appendMiniMessage(line).build())
                .collect(Collectors.toList());
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Adds lore lines using the legacy text format.
     *
     * @param lines The lore lines
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S addLoreLines(@NonNull String... lines) {
        if (lore == null) lore = new ArrayList<>();

        for (String line : lines)
            lore.add(MessageBuilder.builder().appendMiniMessage(line).build());
        cacheDirty = true;

        return (S) this;
    }

    /**
     * Adds lore lines.
     *
     * @param lines The lore lines
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S addLoreLines(@NonNull Component... lines) {
        if (lore == null) lore = new ArrayList<>();

        Collections.addAll(lore, lines);
        cacheDirty = true;

        return (S) this;
    }

    /**
     * Adds lore lines.
     *
     * @param lines The lore lines
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S addLoreLines(@NonNull List<@NonNull Component> lines) {
        if (lore == null) lore = new ArrayList<>();

        lore.addAll(lines);
        cacheDirty = true;

        return (S) this;
    }

    /**
     * Adds lore lines using the legacy text format.
     *
     * @param lines The lore lines
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S addLegacyLoreLines(@NonNull List<@NonNull String> lines) {
        if (lore == null) lore = new ArrayList<>();

        for (String line : lines)
            lore.add(MessageBuilder.builder().appendMiniMessage(line).build());
        cacheDirty = true;

        return (S) this;
    }
    //</editor-fold>

    //<editor-fold desc="item flags">

    /**
     * Gets the configured {@link ItemFlag ItemFlags}.
     *
     * @return The {@link ItemFlag ItemFlags}
     */
    public @Nullable List<ItemFlag> getItemFlags() {
        return itemFlags;
    }

    /**
     * Sets the {@link ItemFlag ItemFlags}.
     *
     * @param itemFlags The {@link ItemFlag ItemFlags}
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setItemFlags(@NonNull List<ItemFlag> itemFlags) {
        this.itemFlags = itemFlags;
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Adds {@link ItemFlag ItemFlags}.
     *
     * @param itemFlags The {@link ItemFlag ItemFlags}
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S addItemFlags(@NonNull ItemFlag... itemFlags) {
        if (this.itemFlags == null) this.itemFlags = new ArrayList<>();
        this.itemFlags.addAll(Arrays.asList(itemFlags));
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Adds all existing {@link ItemFlag ItemFlags}.
     *
     * @return The builder instance
     */
    @Contract("-> this")
    public @NonNull S addAllItemFlags() {
        this.itemFlags = new ArrayList<>(Arrays.asList(ItemFlag.values()));
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Removes the specified {@link ItemFlag ItemFlags}.
     *
     * @param itemFlags The {@link ItemFlag ItemFlags} to remove
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S removeItemFlags(@NonNull ItemFlag... itemFlags) {
        if (this.itemFlags != null) {
            this.itemFlags.removeAll(Arrays.asList(itemFlags));
            cacheDirty = true;
        }
        return (S) this;
    }

    /**
     * Removes all {@link ItemFlag ItemFlags}.
     *
     * @return The builder instance
     */
    @Contract("-> this")
    public @NonNull S clearItemFlags() {
        if (itemFlags != null) {
            itemFlags.clear();
            cacheDirty = true;
        }
        return (S) this;
    }
    //</editor-fold>

    //<editor-fold desc="enchantments">

    /**
     * Gets the enchantments.
     *
     * @return The enchantments
     */
    public @Nullable HashMap<Enchantment, Pair<Integer, Boolean>> getEnchantments() {
        return enchantments;
    }

    /**
     * Sets the enchantments.
     *
     * @param enchantments The enchantments
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S setEnchantments(@NonNull HashMap<Enchantment, Pair<Integer, Boolean>> enchantments) {
        this.enchantments = enchantments;
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Adds an enchantment.
     *
     * @param enchantment            The enchantment
     * @param level                  The level
     * @param ignoreLevelRestriction Whether to ignore the level restriction
     * @return The builder instance
     */
    @Contract("_, _, _ -> this")
    public @NonNull S addEnchantment(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        if (enchantments == null) enchantments = new HashMap<>();
        enchantments.put(enchantment, new Pair<>(level, ignoreLevelRestriction));
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Adds an enchantment.
     *
     * @param enchantment The enchantment
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S removeEnchantment(Enchantment enchantment) {
        if (enchantments != null) {
            enchantments.remove(enchantment);
            cacheDirty = true;
        }
        return (S) this;
    }

    /**
     * Removes all enchantments.
     *
     * @return The builder instance
     */
    @Contract("-> this")
    public @NonNull S clearEnchantments() {
        if (enchantments != null) {
            enchantments.clear();
            cacheDirty = true;
        }
        return (S) this;
    }
    //</editor-fold>

    //<editor-fold desc="modifiers">

    /**
     * Gets the configured modifier functions.
     *
     * @return The modifier functions
     */
    public @Nullable List<Function<ItemStack, ItemStack>> getModifiers() {
        return modifiers;
    }

    /**
     * Adds a modifier function, which will be run after building the {@link ItemStack}.
     *
     * @param modifier The modifier function
     * @return The builder instance
     */
    @Contract("_ -> this")
    public @NonNull S addModifier(Function<ItemStack, ItemStack> modifier) {
        if (modifiers == null) modifiers = new ArrayList<>();
        modifiers.add(modifier);
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Removes all modifier functions.
     *
     * @return The builder instance
     */
    @Contract("-> this")
    public @NonNull S clearModifiers() {
        if (modifiers != null) modifiers.clear();
        cacheDirty = true;
        return (S) this;
    }
    //</editor-fold>

    //<editor-fold desc="NBT">

    /**
     * Sets (replaces) all plugin NBT consumers.
     * <p>
     * Use this for plugin-specific or custom NBT keys. These will be stored
     * using the classic NBT system across all versions.
     *
     * @param nbt the NBT consumer
     * @return this builder instance
     */
    public @NonNull S setPluginNBT(Consumer<ReadWriteNBT> nbt) {
        this.pluginsNbtConsumers = new ArrayList<>();
        this.pluginsNbtConsumers.add(nbt);
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Adds a new plugin NBT consumer.
     * <p>
     * Use this for plugin-specific or custom NBT keys. These will be stored
     * using the classic NBT system across all versions.
     *
     * @param nbt the NBT consumer
     * @return this builder instance
     */
    public @NonNull S addPluginNBT(Consumer<ReadWriteNBT> nbt) {
        if (this.pluginsNbtConsumers == null) {
            this.pluginsNbtConsumers = new ArrayList<>();
        }
        this.pluginsNbtConsumers.add(nbt);
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Removes a plugin NBT consumer.
     *
     * @param nbt the NBT consumer to remove
     * @return this builder instance
     */
    public @NonNull S removePluginNBT(Consumer<ReadWriteNBT> nbt) {
        if (pluginsNbtConsumers != null) {
            pluginsNbtConsumers.remove(nbt);
        }
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Clears all plugin NBT consumers.
     *
     * @return this builder instance
     */
    public @NonNull S clearPluginNBT() {
        if (pluginsNbtConsumers != null) {
            pluginsNbtConsumers.clear();
        }
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Gets all plugin NBT consumers.
     *
     * @return list of plugin NBT consumers or null if none
     */
    public @Nullable List<Consumer<ReadWriteNBT>> getPluginNbtConsumers() {
        return pluginsNbtConsumers;
    }

    /* ============================================================ */

    /**
     * Sets (replaces) all Minecraft internal NBT/Component consumers.
     * <p>
     * Use this for vanilla keys (e.g. {@code minecraft:custom_name}).
     * On 1.20.5+ these are stored via the components API.
     *
     * @param nbt the NBT consumer
     * @return this builder instance
     */
    public @NonNull S setComponentNBT(Consumer<ReadWriteNBT> nbt) {
        this.minecraftInternalNbtConsumers = new ArrayList<>();
        this.minecraftInternalNbtConsumers.add(nbt);
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Adds a new Minecraft internal NBT/Component consumer.
     * <p>
     * Use this for vanilla keys (e.g. {@code minecraft:custom_name}).
     * On 1.20.5+ these are stored via the components API.
     *
     * @param nbt the NBT consumer
     * @return this builder instance
     */
    public @NonNull S addComponentNBT(Consumer<ReadWriteNBT> nbt) {
        if (this.minecraftInternalNbtConsumers == null) {
            this.minecraftInternalNbtConsumers = new ArrayList<>();
        }
        this.minecraftInternalNbtConsumers.add(nbt);
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Removes a Minecraft internal NBT/Component consumer.
     *
     * @param nbt the NBT consumer to remove
     * @return this builder instance
     */
    public @NonNull S removeComponentNBT(Consumer<ReadWriteNBT> nbt) {
        if (minecraftInternalNbtConsumers != null) {
            minecraftInternalNbtConsumers.remove(nbt);
        }
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Clears all Minecraft internal NBT/Component consumers.
     *
     * @return this builder instance
     */
    public @NonNull S clearComponentNBT() {
        if (minecraftInternalNbtConsumers != null) {
            minecraftInternalNbtConsumers.clear();
        }
        cacheDirty = true;
        return (S) this;
    }

    /**
     * Gets all plugin NBT consumers.
     *
     * @return list of plugin NBT consumers or null if none
     */
    public @Nullable List<Consumer<ReadWriteNBT>> getPluginsNbtConsumers() {
        return pluginsNbtConsumers;
    }

    /**
     * Gets all Minecraft internal NBT/Component consumers.
     *
     * @return list of internal NBT consumers or null if none
     */
    public @Nullable List<Consumer<ReadWriteNBT>> getComponentNbtConsumers() {
        return minecraftInternalNbtConsumers;
    }

    //</editor-fold>

    /**
     * Clones this builder.
     *
     * @return The cloned builder
     */
    @Contract(value = "-> new", pure = true)
    @Override
    public @NonNull AbstractItemBuilder<S> clone() {
        try {
            AbstractItemBuilder<S> clone = (AbstractItemBuilder<S>) super.clone();
            if (base != null) clone.base = base.clone();
            if (lore != null) clone.lore = new ArrayList<>(lore);
            if (itemFlags != null) clone.itemFlags = new ArrayList<>(itemFlags);
            if (enchantments != null) clone.enchantments = new HashMap<>(enchantments);
            if (modifiers != null) clone.modifiers = new ArrayList<>(modifiers);
            if (minecraftInternalNbtConsumers != null)
                clone.minecraftInternalNbtConsumers = new ArrayList<>(minecraftInternalNbtConsumers);
            if (pluginsNbtConsumers != null) clone.pluginsNbtConsumers = new ArrayList<>(pluginsNbtConsumers);

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractItemBuilder<?> that)) return false;
        return cacheDirty == that.cacheDirty
                && getAmount() == that.getAmount()
                && getDamage() == that.getDamage()
                && getCustomModelData() == that.getCustomModelData()
                && Objects.equals(cachedItem, that.cachedItem)
                && Objects.equals(getBase(), that.getBase())
                && getMaterial() == that.getMaterial()
                && Objects.equals(unbreakable, that.unbreakable)
                && Objects.equals(glow, that.glow)
                && Objects.equals(getDisplayName(), that.getDisplayName())
                && Objects.equals(getLore(), that.getLore())
                && Objects.equals(getItemFlags(), that.getItemFlags())
                && Objects.equals(getEnchantments(), that.getEnchantments())
                && Objects.equals(getModifiers(), that.getModifiers())
                && Objects.equals(getPluginNbtConsumers(), that.getPluginNbtConsumers())
                && Objects.equals(getComponentNbtConsumers(), that.getComponentNbtConsumers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(cachedItem, cacheDirty, getBase(), getMaterial(), getAmount(), getDamage(), getCustomModelData(), unbreakable, glow, getDisplayName(), getLore(), getItemFlags(), getEnchantments(), getModifiers(), getPluginNbtConsumers(), getComponentNbtConsumers());
    }
}