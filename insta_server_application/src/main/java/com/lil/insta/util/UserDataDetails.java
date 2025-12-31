package com.lil.insta.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lil.insta.entity.UserInfo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDataDetails implements UserDetails {

    private String username;
    private String fullname;
    private String password;
    private String userId;
    private String profilePicKey;

    private List<GrantedAuthority> authorities;

    public UserDataDetails(UserInfo userInfo) {
        this.username = userInfo.getUsername(); // Use email as username
        this.password = userInfo.getPassword();
        this.fullname = userInfo.getFullname();
        this.userId = userInfo.getUserId().toString();
        this.profilePicKey = userInfo.getProfilePicKey();
        this.authorities = List.of(userInfo.getRole().toString().split(","))
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    public String getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public String getProfilePicKey() {
        return profilePicKey;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}