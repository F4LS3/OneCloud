package de.f4ls3.netty.packets;

import de.f4ls3.netty.impl.enums.ConfirmationType;
import de.f4ls3.netty.interfaces.Packet;

public class ConfirmationPacket implements Packet {

    private ConfirmationType confirmationType;

    public ConfirmationPacket(ConfirmationType confirmationType) {
        this.confirmationType = confirmationType;
    }

    public ConfirmationType getConfirmationType() {
        return confirmationType;
    }
}
