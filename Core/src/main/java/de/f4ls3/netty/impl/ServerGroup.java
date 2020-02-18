package de.f4ls3.netty.impl;

import de.f4ls3.netty.impl.enums.GroupType;
import de.f4ls3.netty.utils.Logger;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                int serverId = (serverProcesses.size() + 1);
                Process p = builder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                List<String> serverOutput = new ArrayList<>();
                serverProcesses.put(getGroupName() + "-" + serverId, p);
                Logger.log("Started [" + getGroupName() + "-" + serverId + "/uuid=" + getGroupUUID() + "] server");
                while(reader.readLine() != null) serverOutput.add(reader.readLine());

                System.out.println(serverOutput);
                this.onlineServers++;
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
