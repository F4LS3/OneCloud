package de.f4ls3.netty.impl;

import java.io.File;
import java.util.UUID;

public class ServerGroup extends Group {

    private int onlineServers;

    public ServerGroup(String groupName, UUID groupUUID, Session groupSession, GroupType groupType, int groupId) {
        super(groupName, groupUUID, groupSession, groupType, groupId);
        this.onlineServers = 0;
    }

    public void startServer(int amount) {
        // TODO: Server start
        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File("./"));
    }
}
