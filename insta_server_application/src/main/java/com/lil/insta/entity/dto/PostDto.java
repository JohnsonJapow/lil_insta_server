package com.lil.insta.entity.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class PostDto {

    private UUID postId;
    private String fileKey;
    private String captionText;
    private String userId;
    private Timestamp createdAt;
    private String fullName;
    private String profilePicKey;

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getCaptionText() {
        return captionText;
    }

    public void setCaptionText(String captionText) {
        System.out.println(captionText);
        this.captionText = captionText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePicKey() {
        return profilePicKey;
    }

    public void setProfilePicKey(String profilePicKey) {
        this.profilePicKey = profilePicKey;
    }
    
    
}
