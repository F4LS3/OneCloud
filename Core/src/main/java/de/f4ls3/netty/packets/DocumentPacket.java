package de.f4ls3.netty.packets;

import de.f4ls3.netty.interfaces.Packet;
import de.f4ls3.netty.utils.Document;

public class DocumentPacket implements Packet {

    private Document document;

    public DocumentPacket(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }
}
