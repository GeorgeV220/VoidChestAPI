package com.georgev22.voidchest.api.menu.item.builder;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class BannerBuilder extends AbstractItemBuilder<BannerBuilder> {

    private List<Pattern> patterns = new ArrayList<>();

    public BannerBuilder(@NonNull Material material) {
        super(material);
    }

    public BannerBuilder(@NonNull Material material, int amount) {
        super(material, amount);
    }

    public BannerBuilder(@NonNull ItemStack base) {
        super(base);
    }

    @Contract("_ -> this")
    public @NonNull BannerBuilder addPattern(@NonNull Pattern pattern) {
        patterns.add(pattern);
        return this;
    }

    @Contract("_, _ -> this")
    public @NonNull BannerBuilder addPattern(@NonNull DyeColor color, @NonNull PatternType type) {
        patterns.add(new Pattern(color, type));
        return this;
    }

    @Contract("_ -> this")
    public @NonNull BannerBuilder setPatterns(@NonNull List<@NonNull Pattern> patterns) {
        this.patterns = patterns;
        return this;
    }

    @Contract("-> this")
    public @NonNull BannerBuilder clearPatterns() {
        patterns.clear();
        return this;
    }

    @Override
    public @NonNull ItemStack get() {
        ItemStack item = super.get();
        BannerMeta meta = (BannerMeta) item.getItemMeta();

        meta.setPatterns(patterns);

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public @NonNull BannerBuilder clone() {
        BannerBuilder builder = (BannerBuilder) super.clone();
        builder.patterns = new ArrayList<>(patterns);
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
    public @NonNull BannerBuilder shallowCopy() {
        BannerBuilder builder;
        if (this.base == null) builder = new BannerBuilder(this.material, this.amount);
        else builder = new BannerBuilder(this.base);
        builder.patterns = this.patterns;
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
    public @NonNull BannerBuilder deepCopy() {
        BannerBuilder builder;
        if (this.base == null) builder = new BannerBuilder(this.material, this.amount);
        else builder = new BannerBuilder(this.base.clone());
        builder.patterns = new ArrayList<>(patterns);
        return builder;
    }
}