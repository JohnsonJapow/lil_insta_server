package com.lil.insta.entity;

public class PostDataRequest {
    private String userId;
    private String caption;
    private FileInfo fileInfo;
    private String moderationStatus;

    public String getUserId() {
        return userId;
    }

    public String getCaption() {
        return caption;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public ModerationStatus getModerationStatus(){
        return moderationStatus == null ? null : ModerationStatus.valueOf(moderationStatus);
    }
}
