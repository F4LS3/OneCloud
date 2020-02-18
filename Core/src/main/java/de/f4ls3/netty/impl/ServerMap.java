package de.f4ls3.netty.impl;

import de.f4ls3.netty.utils.Logger;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class ServerMap<S, P> extends HashMap {

    @Override
    public Object put(Object key, Object value) {
        Logger.log("Added " + key.toString().toUpperCase() + " to request queue");
        return super.put(key, value);
    }
}
