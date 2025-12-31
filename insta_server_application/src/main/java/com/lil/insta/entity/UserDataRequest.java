package com.lil.insta.entity;

import java.util.Optional;

public class UserDataRequest {
    private String userId;
    private String username;
    private String fullname;
    private String password;
    private Optional<FileInfo> fileInfo;

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public Optional<FileInfo> getFileInfo() {
        return fileInfo;
    }
    
}
