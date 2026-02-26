package com.georgev22.voidchest.api.menu.item.builder;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("removal")
public final class PotionBuilder extends AbstractItemBuilder<PotionBuilder> {

    private List<PotionEffect> effects = new ArrayList<>();
    private Color color;
    private PotionData basePotionData;

    public PotionBuilder(@NonNull PotionType type) {
        super(type.getMaterial());
    }

    public PotionBuilder(@NonNull ItemStack base) {
        super(base);
    }

    @Contract("_ -> this")
    public @NonNull PotionBuilder setColor(@NonNull Color color) {
        this.color = color;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull PotionBuilder setColor(java.awt.@NonNull Color color) {
        this.color = Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue());
        return this;
    }

    @Contract("_ -> this")
    public @NonNull PotionBuilder setBasePotionData(@NonNull PotionData basePotionData) {
        this.basePotionData = basePotionData;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull PotionBuilder addEffect(@NonNull PotionEffect effect) {
        effects.add(effect);
        return this;
    }

    @Override
    public @NonNull ItemStack get() {
        ItemStack item = super.get();
        PotionMeta meta = (PotionMeta) item.getItemMeta();

        meta.clearCustomEffects();
        if (color != null) meta.setColor(color);
        if (basePotionData != null) meta.setBasePotionData(basePotionData);
        effects.forEach(effect -> meta.addCustomEffect(effect, true));

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public @NonNull PotionBuilder clone() {
        PotionBuilder builder = (PotionBuilder) super.clone();
        builder.effects = new ArrayList<>(effects);
        builder.color = color;
        builder.basePotionData = basePotionData;
        return builder;
    }

    /**
     * Creates a shallow copy of the current object.
     * <p>
     * Only the top-level fields are copied; nested objects are referenced directly.
     *
     * @return a shallow copy of type {@code T}
     */
    @Override
    public @NonNull PotionBuilder shallowCopy() {
        PotionBuilder builder;
        if (this.base == null) builder = new PotionBuilder(PotionType.valueOf(material.name()));
        else builder = new PotionBuilder(this.base);
        builder.effects = effects;
        builder.color = color;
        builder.basePotionData = basePotionData;
        return builder;
    }

    /**
     * Creates a deep copy of the current object.
     * <p>
     * Both top-level fields and nested objects are fully copied.
     *
     * @return a deep copy of type {@code T}
     */
    @Override
    public @NonNull PotionBuilder deepCopy() {
        PotionBuilder builder;
        if (this.base == null) builder = new PotionBuilder(PotionType.valueOf(material.name()));
        else builder = new PotionBuilder(this.base.clone());
        builder.effects = new ArrayList<>(effects);
        builder.color = color;
        builder.basePotionData = basePotionData;
        return builder;
    }

    public enum PotionType {

        NORMAL(Material.POTION),
        SPLASH(Material.SPLASH_POTION),
        LINGERING(Material.LINGERING_POTION);

        private final @NonNull Material material;

        PotionType(@NonNull Material material) {
            this.material = material;
        }

        public @NonNull Material getMaterial() {
            return material;
        }

    }

}