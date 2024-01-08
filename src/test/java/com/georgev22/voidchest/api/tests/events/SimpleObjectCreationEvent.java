package com.georgev22.voidchest.api.tests.events;

import com.georgev22.voidchest.api.event.Event;
import com.georgev22.voidchest.api.event.HandlerList;
import com.georgev22.voidchest.api.tests.objects.SimpleObject;
import org.jetbrains.annotations.NotNull;

public class SimpleObjectCreationEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final SimpleObject simpleObject;

    public SimpleObjectCreationEvent(SimpleObject simpleObject) {
        super(true);
        this.simpleObject = simpleObject;
    }

    public SimpleObjectCreationEvent(SimpleObject simpleObject, boolean async) {
        super(async);
        this.simpleObject = simpleObject;
    }

    public SimpleObject getSimpleObject() {
        return simpleObject;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
