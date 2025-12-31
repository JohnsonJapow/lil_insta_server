package com.lil.insta.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class PostInfo {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private UUID postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @Column(name = "file_key", nullable = true, unique = true)
    private String fileKey;

    @Column(name = "caption_text")
    private String captionText;

    @Column(name = "transcription_confidence")
    private Double transcriptionConfidence;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status")
    private ModerationStatus moderationStatus = ModerationStatus.PENDING;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentInfo> comments;

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getCaptionText() {
        return captionText;
    }

    public void setCaptionText(String captionText) {
        this.captionText = captionText;
    }

    public Double getTranscriptionConfidence() {
        return transcriptionConfidence;
    }

    public void setTranscriptionConfidence(Double transcriptionConfidence) {
        this.transcriptionConfidence = transcriptionConfidence;
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CommentInfo> getComments() {
        return comments;
    }

    public void setComments(List<CommentInfo> comments) {
        this.comments = comments;
    }

}
