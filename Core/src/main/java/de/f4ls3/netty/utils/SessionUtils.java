package de.f4ls3.netty.utils;

import de.f4ls3.netty.impl.Session;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SessionUtils {

    private List<Session> sessions = new ArrayList<>();

    public Session getSessionByContext(ChannelHandlerContext context) {
        for (Session session : sessions) {
            if(session.getSessionContext().equals(context)) {
                return session;
            }
        }
        return null;
    }

    public Session getSessionByUUID(UUID uuid) {
        for (Session session : sessions) {
            if(session.getSessionUUID().toString().equals(uuid.toString())) {
                return session;
            }
        }
        return null;
    }

    public Session getSessionByName(String name) {
        for (Session session : sessions) {
            if(session.getSessionName().equals(name)) {
                return session;
            }
        }
        return null;
    }

    public Session getSessionById(String id) {
        for (Session session : sessions) {
            if(session.getSessionId().equals(id)) {
                return session;
            }
        }
        return null;
    }

    public Session newSession(String sessionName, String sessionId, UUID sessionUUID, ChannelHandlerContext sessionContext, boolean isAuthorized) {
        return new Session(sessionName, sessionId, sessionUUID, sessionContext, isAuthorized);
    }

    public void disconnectAllSessions() {
        for (Session session : sessions) {
            session.getSessionContext().channel().closeFuture();
            sessions.remove(session);
        }
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
