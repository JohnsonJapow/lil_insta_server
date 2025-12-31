package com.lil.insta.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserInfo {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 50)
    private String fullname;

    @Column(name = "profile_pic_key")
    private String profilePicKey;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.user;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfilePicKey() {
        return profilePicKey;
    }

    public void setProfilePicKey(String profilePicKey) {
        this.profilePicKey = profilePicKey;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}