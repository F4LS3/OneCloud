package de.f4ls3.netty.impl;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerGroup extends Group {

    private int onlineServers;
    private ServerMap<String, Process> serverProcesses;

    public ServerGroup(String groupName, UUID groupUUID, Session groupSession, GroupType groupType, int groupId) {
        super(groupName, groupUUID, groupSession, groupType, groupId);
        this.onlineServers = 0;
        this.serverProcesses = new ServerMap<>();
    }

    public void startServer(int amount) {
        try {
            for (int i = 0; i < amount; i++) {
                ProcessBuilder builder = new ProcessBuilder("java", "-jar", "spigot.jar")
                        .directory(new File("./templates/" + getGroupName() + "/"));
                builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                serverProcesses.put(getGroupName() + (serverProcesses.size() + 1), builder.start());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer(int amount) {
        try {
            int runs = 0;
            for (Object value : serverProcesses.values()) {
                if (value instanceof Process) {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(((Process) value).getOutputStream()));
                    if (runs != amount) {
                        writer.write("stop\n");
                        runs++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
