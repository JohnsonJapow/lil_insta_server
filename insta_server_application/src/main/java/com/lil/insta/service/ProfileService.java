package com.lil.insta.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lil.insta.entity.UserProfile;
import com.lil.insta.repository.ProfileRepository;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Map<String, Object> searchProfiles(String keyword) {
        List<UserProfile> profiles = profileRepository.searchByFullname(keyword);
        Map<String, Object> response = new HashMap<>();
        response.put("profiles", profiles);
        return response;
    }    
}
