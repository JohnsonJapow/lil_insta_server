package com.lil.insta.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lil.insta.util.UserDataDetails;
import com.lil.insta.entity.UserInfo;
import com.lil.insta.repository.UserRepository;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database by username
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);
        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        UserInfo user = userInfo.get();
        return new UserDataDetails(user);
    }
}