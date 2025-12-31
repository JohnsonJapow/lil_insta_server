package com.lil.insta.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lil.insta.entity.UserDataRequest;
import com.lil.insta.service.user.JwtService;
import com.lil.insta.service.user.UserService;
import com.lil.insta.util.UserDataDetails;

@RestController
@RequestMapping("/auth")
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody UserDataRequest userDataRequest) {
        boolean result = userService.addNewUser(userDataRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("success", result);
        if (result) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(400).body(response);
    }

    // This is for updating the username and password only
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserDataRequest userDataRequest) {
        Map<String, Object> result = userService.updateExistingUser(userDataRequest);
        if ((Boolean)result.get("success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(400).body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateAndGetToken(@RequestBody UserDataRequest userDataRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDataRequest.getUsername(),
                            userDataRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                UserDataDetails userDetails = (UserDataDetails) authentication.getPrincipal();
                String token = jwtService.generateToken(userDetails.getUsername());
                response.put("success", true);
                response.put("token", token);
                response.put("userId", userDetails.getUserId());
                response.put("fullname", userDetails.getFullname());
                response.put("profilePicKey", userDetails.getProfilePicKey());
                userService.updateLastLogin(userDetails.getUserId());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(
                    Map.of("success", false, "message", "Invalid credentials"));
        }
    }
}
