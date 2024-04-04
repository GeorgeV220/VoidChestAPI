package com.georgev22.voidchest.api.tests.objects;

import com.georgev22.library.maps.ConcurrentObjectMap;
import com.georgev22.library.utilities.Entity;
import com.georgev22.voidchest.api.tests.Main;
import com.georgev22.voidchest.api.tests.events.SimpleObjectCreationEvent;
import com.georgev22.voidchest.api.tests.events.SimpleObjectModifyEvent;

public class SimpleObject extends Entity {

    private final ConcurrentObjectMap<String, Object> data = new ConcurrentObjectMap<>();

    private final boolean async;

    public SimpleObject(String id, boolean async) {
        super(id);
        SimpleObjectCreationEvent event = new SimpleObjectCreationEvent(this, async);
        Main.getInstance().getEventManager().callEvent(event);
        this.data.append("id", this._id());
        this.data.append("async", async);
        this.async = async;
    }

    public Entity addCustomData(String key, Object value) {
        SimpleObjectModifyEvent event = new SimpleObjectModifyEvent(this, this.data,
                this.data.append(key, value), async);
        Main.getInstance().getEventManager().callEvent(event);
        return event.getSimpleObject();
    }

    public Entity addCustomDataIfNotExists(String key, Object value) {
        SimpleObjectModifyEvent event = new SimpleObjectModifyEvent(this, this.data,
                this.data.append(key, value), async);
        Main.getInstance().getEventManager().callEvent(event);
        return event.getSimpleObject();
    }

    public ConcurrentObjectMap<String, Object> getCustomData() {
        return this.data;
    }
}
