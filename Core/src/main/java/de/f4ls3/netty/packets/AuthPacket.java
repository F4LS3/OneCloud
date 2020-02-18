package de.f4ls3.netty.packets;

import de.f4ls3.netty.interfaces.Packet;

public class AuthPacket implements Packet {

    private String authKey;

    public AuthPacket() {}

    public AuthPacket(String authKey) {
        this.authKey = authKey;
    }

    public String getAuthKey() {
        return authKey;
    }
}
