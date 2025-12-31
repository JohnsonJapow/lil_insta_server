package com.lil.insta.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserProfile {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "profile_pic_key")
    private String profilePicKey;

    public UserProfile() {
    }

    public UserProfile(String userId, String fullname, String profilePicKey) {
        this.userId = userId;
        this.fullname = fullname;
        this.profilePicKey = profilePicKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
