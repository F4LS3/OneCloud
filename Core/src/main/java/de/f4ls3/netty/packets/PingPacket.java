package de.f4ls3.netty.packets;

import de.f4ls3.netty.interfaces.Packet;

public class PingPacket implements Packet {

    private long ping;

    public PingPacket(long ping) {
        this.ping = ping;
    }

    public long getPing() {
        return ping;
    }
}
