package com.lil.insta.entity;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class CommentInfo {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private UUID commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostInfo post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @Column(columnDefinition = "text")
    private String content;

    @Column(name = "toxicity_score")
    private Double toxicityScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status")
    private ModerationStatus moderationStatus = ModerationStatus.PENDING;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public UUID getCommentId() {
        return commentId;
    }

    public void setCommentId(UUID commentId) {
        this.commentId = commentId;
    }

    public PostInfo getPost() {
        return post;
    }

    public void setPost(PostInfo post) {
        this.post = post;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getToxicityScore() {
        return toxicityScore;
    }

    public void setToxicityScore(Double toxicityScore) {
        this.toxicityScore = toxicityScore;
    }

    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
