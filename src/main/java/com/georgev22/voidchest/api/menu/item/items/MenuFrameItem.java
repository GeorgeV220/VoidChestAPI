package com.georgev22.voidchest.api.menu.item.items;


import com.georgev22.voidchest.api.menu.item.ItemProvider;

public class MenuFrameItem {

    private final MenuItem parent;
    private final ItemProvider frameItemProvider;

    public MenuFrameItem(MenuItem parent, ItemProvider frameItemProvider) {
        this.parent = parent;
        this.frameItemProvider = frameItemProvider;
    }

    public MenuItem getParent() {
        return parent;
    }

    public ItemProvider getFrameItemProvider() {
        return frameItemProvider;
    }

}
