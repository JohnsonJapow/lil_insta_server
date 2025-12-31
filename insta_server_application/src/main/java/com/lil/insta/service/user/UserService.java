package com.lil.insta.service.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lil.insta.aws.config.AwsConfig;
import com.lil.insta.aws.service.S3Service;
import com.lil.insta.entity.FileInfo;
import com.lil.insta.entity.UserDataRequest;
import com.lil.insta.entity.UserInfo;
import com.lil.insta.repository.UserRepository;
import com.lil.insta.service.user.UserService;

@Service
public class UserService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final S3Service s3Service;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder, S3Service s3Service) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.s3Service = s3Service;
    }

    public boolean addNewUser(UserDataRequest userDataRequest) {
        String username = userDataRequest.getUsername();
        String fullname = userDataRequest.getFullname();
        String password = userDataRequest.getPassword();
        if (username == null || username.length() == 0) {
            log.error("Invalid username :{}", username);
            return false;
        }
        if (fullname == null || fullname.length() == 0) {
            log.error("Invalid fullname :{}", fullname);
            return false;
        }
        if (password == null || password.length() == 0) {
            log.error("Invalid password :{}", password);
            return false;
        }
        boolean isExisted = userRepository.existsByUsername(username);
        if (!isExisted) {
            password = encoder.encode(userDataRequest.getPassword());
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setFullname(fullname);
            userInfo.setPassword(password);
            userRepository.save(userInfo);
            return true;
        }
        log.info("User already exists :{}" + username);
        return false;
    }

    public Map<String, Object> updateExistingUser(UserDataRequest userDataRequest) {
        String userId = userDataRequest.getUserId();
        String username = userDataRequest.getUsername();
        String fullname = userDataRequest.getFullname();
        String password = userDataRequest.getPassword();
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);

        Optional<UserInfo> userInfoOptional = userRepository.findById(UUID.fromString(userId));
        if (userInfoOptional.isPresent()) {
            UserInfo userInfo = userInfoOptional.get();
            if (!password.isEmpty() && password != null) {
                password = encoder.encode(userDataRequest.getPassword());
                userInfo.setPassword(password);
            }
            if (!username.isEmpty() && username != null) {
                userInfo.setUsername(username);
            }
            if (!fullname.isEmpty() && fullname != null) {
                userInfo.setFullname(fullname);
            }

            if (userDataRequest.getFileInfo().isPresent()) {
                FileInfo fileInfo = userDataRequest.getFileInfo().get();
                String fileName = fileInfo.getFileName();
                String key = String.format(
                        "users/%s/%s",
                        userInfo.getUserId(),
                        fileName);
                userInfo.setProfilePicKey(key);
                String presignedPutUrl = s3Service.createPresignedPutUrl(key, AwsConfig.PRODUCT_USER_BUCKET);
                result.put("profilePicKey", key);
                result.put("presignedPutUrl", presignedPutUrl);
            }
            userInfo = userRepository.save(userInfo);
            result.put("fullname", userInfo.getFullname());
            result.put("success", true);
            return result;
        }
        log.debug("Could not found :{}" + (userInfoOptional.isPresent() ? userInfoOptional.get().getUsername() : ""));
        return result;
    }

    public synchronized void updateLastLogin(String userId) {
        if (!userId.isEmpty() && userId != null)
            userRepository.updateLastLogin(UUID.fromString(userId));
    }
}
