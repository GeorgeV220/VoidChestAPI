package com.georgev22.voidchest.api.tests.events;

import com.georgev22.library.maps.ObjectMap;
import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.tests.objects.SimpleObject;
import org.jetbrains.annotations.NotNull;

public class SimpleObjectModifyEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final SimpleObject simpleObject;
    private final ObjectMap<String, Object> oldData;
    private final ObjectMap<String, Object> newData;

    public SimpleObjectModifyEvent(SimpleObject simpleObject, ObjectMap<String, Object> oldData, ObjectMap<String, Object> newData) {
        super(true);
        this.simpleObject = simpleObject;
        this.oldData = oldData;
        this.newData = newData;
    }

    public SimpleObjectModifyEvent(SimpleObject simpleObject, ObjectMap<String, Object> oldData, ObjectMap<String, Object> newData, boolean async) {
        super(async);
        this.simpleObject = simpleObject;
        this.oldData = oldData;
        this.newData = newData;
    }

    public SimpleObject getSimpleObject() {
        return simpleObject;
    }

    public ObjectMap<String, Object> getNewData() {
        return newData;
    }

    public ObjectMap<String, Object> getOldData() {
        return oldData;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
