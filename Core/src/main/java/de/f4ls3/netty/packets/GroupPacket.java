package de.f4ls3.netty.packets;

import de.f4ls3.netty.impl.Session;
import de.f4ls3.netty.interfaces.Packet;
import de.f4ls3.netty.utils.Document;

public class GroupPacket implements Packet {

    private Document document;
    private Session session;

    public GroupPacket(Document document, Session session) {
        this.document = document;
        this.session = session;
    }

    public Document getDocument() {
        return document;
    }

    public Session getSession() {
        return session;
    }
}
