package com.lil.insta.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.lil.insta.entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo> findByUsername(String username);
    boolean existsByUsername(String username);
    // @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    // Optional<UserInfo> findByUsernameNative(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserInfo u SET u.lastLogin = CURRENT_TIMESTAMP WHERE u.userId = :userId")
    void updateLastLogin(UUID userId);

}
