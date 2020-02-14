package de.f4ls3.netty.packets;

import de.f4ls3.netty.impl.Packet;
import de.f4ls3.netty.impl.RequestType;

public class RequestPacket implements Packet {

    private RequestType requestType;

    public RequestPacket(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
