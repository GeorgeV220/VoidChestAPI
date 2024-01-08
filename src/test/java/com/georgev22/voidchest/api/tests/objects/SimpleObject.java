package com.georgev22.voidchest.api.tests.objects;

import com.georgev22.library.maps.ConcurrentObjectMap;
import com.georgev22.library.utilities.Entity;
import com.georgev22.voidchest.api.tests.Main;
import com.georgev22.voidchest.api.tests.events.SimpleObjectCreationEvent;
import com.georgev22.voidchest.api.tests.events.SimpleObjectModifyEvent;

import java.util.UUID;

public class SimpleObject implements Entity {

    private final ConcurrentObjectMap<String, Object> data = new ConcurrentObjectMap<>();

    private final UUID id;

    private final boolean async = true;

    public SimpleObject(UUID id) {
        SimpleObjectCreationEvent event = new SimpleObjectCreationEvent(this, async);
        Main.getInstance().getEventManager().callEvent(event);
        this.id = id;
    }

    @Override
    public Entity addCustomData(String key, Object value) {
        SimpleObjectModifyEvent event = new SimpleObjectModifyEvent(this, this.data, Entity.super.addCustomData(key, value).getCustomData(), async);
        Main.getInstance().getEventManager().callEvent(event);
        return event.getSimpleObject();
    }

    @Override
    public Entity addCustomDataIfNotExists(String key, Object value) {
        SimpleObjectModifyEvent event = new SimpleObjectModifyEvent(this, this.data, Entity.super.addCustomDataIfNotExists(key, value).getCustomData(), async);
        Main.getInstance().getEventManager().callEvent(event);
        return event.getSimpleObject();
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public ConcurrentObjectMap<String, Object> getCustomData() {
        return this.data;
    }
}
