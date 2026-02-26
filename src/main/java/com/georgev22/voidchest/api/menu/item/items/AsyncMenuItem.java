package com.georgev22.voidchest.api.menu.item.items;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.menu.item.ItemProvider;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public class AsyncMenuItem extends MenuItem {

    private volatile ItemProvider itemProvider;

    public AsyncMenuItem(@Nullable ItemProvider itemProvider, @NonNull Supplier<? extends ItemProvider> providerSupplier) {
        super(itemProvider == null ? ItemProvider.EMPTY : itemProvider);

        this.itemProvider = itemProvider;

        VoidChestAPI.getInstance().minecraftScheduler().runAsyncTask(() -> {
            this.itemProvider = providerSupplier.get();
            VoidChestAPI.getInstance().minecraftScheduler().runTask(this::notifyContext);
        });
    }

    public AsyncMenuItem(@NonNull Supplier<? extends ItemProvider> providerSupplier) {
        this(null, providerSupplier);
    }

    @Override
    public @NonNull ItemProvider getItemProvider() {
        return this.itemProvider;
    }
}