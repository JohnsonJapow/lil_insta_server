package com.lil.insta.repository;

import com.lil.insta.entity.CommentInfo;
import com.lil.insta.entity.ModerationStatus;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentInfo, String> {

    @Query("""
        SELECT c FROM CommentInfo c
        WHERE c.post.postId = :postId
        AND c.moderationStatus = com.lil.insta.entity.ModerationStatus.APPROVED
        ORDER BY c.createdAt DESC
        """)
    @EntityGraph(attributePaths = {"user"})
    List<CommentInfo> findApprovedCommentsByPostId(
        @Param("postId") UUID postId,
        Pageable pageable
    );

    @Modifying
    @Query("""
        UPDATE CommentInfo c
        SET c.toxicityScore = :toxicityScore,
            c.moderationStatus = :moderationStatus
        WHERE c.commentId = :commentId
        """)
    int updateCommentModeration(
        @Param("commentId") UUID commentId,
        @Param("toxicityScore") double toxicityScore,
        @Param("moderationStatus") ModerationStatus moderationStatus
    );

    @Query("""
        SELECT COUNT(c) FROM CommentInfo c
        WHERE c.post.postId = :postId
        AND c.moderationStatus = com.lil.insta.entity.ModerationStatus.APPROVED
        """)
    int countApprovedComments(@Param("postId") UUID postId);
}