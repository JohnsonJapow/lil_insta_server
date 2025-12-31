package com.lil.insta.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lil.insta.entity.ModerationStatus;
import com.lil.insta.entity.PostInfo;
import com.lil.insta.entity.UserInfo;

@Repository
public interface PostRepository extends JpaRepository<PostInfo, UUID> {

    // Get a single approved post by user + post
    Optional<PostInfo> findByUserAndPostIdAndModerationStatus(
        UserInfo user,
        UUID postId,
        ModerationStatus status
    );

    // Pagination of all approved posts
    @EntityGraph(attributePaths = {"user"})
    Page<PostInfo> findAllByModerationStatusOrderByCreatedAtDesc(
        ModerationStatus status,
        Pageable pageable
    );

    // Pagination by user
    Page<PostInfo> findByUserAndModerationStatusOrderByCreatedAtDesc(
        UserInfo user,
        ModerationStatus status,
        Pageable pageable
    );

    // Count approved posts
    int countByModerationStatus(ModerationStatus status);
    int countByUserAndModerationStatus(UserInfo user, ModerationStatus status);

    
}