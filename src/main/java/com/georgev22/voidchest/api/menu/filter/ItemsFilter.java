package com.georgev22.voidchest.api.menu.filter;

import com.georgev22.voidchest.api.menu.item.items.MenuItem;
import com.georgev22.voidchest.api.menu.viewer.ViewerContext;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * Represents a filter for menu items. Filters can be used to
 * customize the set of items shown to a viewer by modifying,
 * reordering, or removing items from the list.
 *
 * <p>Filters are applied before pagination, so they control which
 * items will be visible on each page of the menu.</p>
 */
@FunctionalInterface
public interface ItemsFilter {

    /**
     * Apply this filter to a list of menu items for a specific viewer.
     *
     * @param viewerContext The context of the viewer this filter is applied for.
     * @param items         The input list of items before filtering.
     * @return A new list of items after filtering. Never {@code null}.
     */
    @NonNull List<MenuItem> apply(@NonNull ViewerContext viewerContext,
                                  @NonNull List<MenuItem> items);

    /**
     * Combine multiple filters into one, applying them sequentially
     * in the order they appear in the list.
     *
     * @param filters The filters to chain together.
     * @return A single filter that applies each filter in sequence.
     */
    @Contract(pure = true)
    static @NonNull ItemsFilter chain(List<ItemsFilter> filters) {
        return (viewerContext, items) -> {
            List<MenuItem> result = items;
            for (ItemsFilter filter : filters) {
                result = filter.apply(viewerContext, result);
            }
            return result;
        };
    }

    /**
     * Combine two filters into one, applying {@code first} followed by {@code second}.
     *
     * @param first  The first filter to apply.
     * @param second The second filter to apply.
     * @return A filter that applies both sequentially.
     */
    @Contract(pure = true)
    static @NonNull ItemsFilter chain(ItemsFilter first, ItemsFilter second) {
        return (viewerContext, items) ->
                second.apply(viewerContext, first.apply(viewerContext, items));
    }
}
