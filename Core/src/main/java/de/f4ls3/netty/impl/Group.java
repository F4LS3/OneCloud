package de.f4ls3.netty.impl;

import de.f4ls3.netty.impl.enums.GroupType;

import java.util.UUID;

public class Group {

    private String groupName;
    private UUID groupUUID;
    private Session groupSession;
    private GroupType groupType;
    private int groupId;

    public Group(String groupName, UUID groupUUID, Session groupSession, GroupType groupType, int groupId) {
        this.groupName = groupName;
        this.groupUUID = groupUUID;
        this.groupSession = groupSession;
        this.groupType = groupType;
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public UUID getGroupUUID() {
        return groupUUID;
    }

    public Session getGroupSession() {
        return groupSession;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public int getGroupId() {
        return groupId;
    }
}
