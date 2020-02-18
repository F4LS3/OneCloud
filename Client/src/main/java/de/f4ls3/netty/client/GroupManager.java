package de.f4ls3.netty.client;

import de.f4ls3.netty.Core;
import de.f4ls3.netty.impl.Group;
import de.f4ls3.netty.utils.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupManager {

    private List<Group> groups = new ArrayList<>();

    public void loadGroups() {
        File[] groupDirs = new File("./groups/").listFiles();

        for (File dir : groupDirs) {
            if(!dir.isDirectory()) return;

            for (File file : dir.listFiles()) {
                if(file.getName().equalsIgnoreCase("config.json")) {
                    Document document = new Document(Core.getGson().fromJson(Core.getFileParser().parseFile(file), HashMap.class));

                }
            }
        }
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public List<Group> getGroups() {
        return groups;
    }
}
