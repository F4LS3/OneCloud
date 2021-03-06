package de.f4ls3.netty.impl;

import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class Session {

    private String sessionName;
    private String sessionId;
    private UUID sessionUUID;
    private ChannelHandlerContext sessionContext;
    private boolean isAuthorized;

    public Session(String sessionName, String sessionId, UUID sessionUUID, ChannelHandlerContext sessionContext, boolean isAuthorized) {
        this.sessionName = sessionName;
        this.sessionId = sessionId;
        this.sessionUUID = sessionUUID;
        this.sessionContext = sessionContext;
        this.isAuthorized = isAuthorized;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public UUID getSessionUUID() {
        return sessionUUID;
    }

    public ChannelHandlerContext getSessionContext() {
        return sessionContext;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }
}
