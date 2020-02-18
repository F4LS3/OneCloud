package de.f4ls3.netty.interfaces;

import java.io.Serializable;

public interface Packet extends Serializable {

    default String getName() {
        return getClass().getSimpleName();
    }
}
