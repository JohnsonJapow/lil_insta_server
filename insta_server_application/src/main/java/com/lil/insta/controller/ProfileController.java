package com.lil.insta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lil.insta.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/user/search")
    public ResponseEntity<Map<String, Object>> searchProfiles(@RequestParam(defaultValue = "") String keyword) {
        Map<String, Object> results = profileService.searchProfiles(keyword);
        return ResponseEntity.ok(results);
    }
}
