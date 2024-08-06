package com.mycompany.myapp.service.dto;

public class GroupChatDTO {
    private Long groupId;
    private String groupName;

    public GroupChatDTO(Long groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
