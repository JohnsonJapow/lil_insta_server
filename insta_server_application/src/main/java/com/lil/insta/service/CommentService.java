package com.lil.insta.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lil.insta.entity.CommentInfo;
import com.lil.insta.entity.ModerationStatus;
import com.lil.insta.entity.PostInfo;
import com.lil.insta.entity.UserInfo;
import com.lil.insta.entity.dto.CommentDto;
import com.lil.insta.entity.mapper.CommentMapper;
import com.lil.insta.repository.CommentRepository;
import com.lil.insta.repository.PostRepository;
import com.lil.insta.repository.UserRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    // private Optional<KafkaTemplate<String, String>> kafkaTemplate;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository,
            UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Object> getPagenatedCommentListOfSinglePost(int page, int size, String postId) {
        Pageable pageable = PageRequest.of(page, size);
        List<CommentInfo> resultList = commentRepository.findApprovedCommentsByPostId(UUID.fromString(postId),
                pageable);
        List<CommentDto> results = resultList.stream().map(CommentMapper::toDto).toList();
        int totalComments = commentRepository.countApprovedComments(UUID.fromString(postId));
        int totalPages = (int) Math.ceil((double) totalComments / size);
        Map<String, Object> map = new HashMap<>();
        map.put("comments", results);
        map.put("currentPage", page);
        map.put("totalPages", totalPages);
        map.put("totalComments", totalComments);
        return map;
    }

    public void addCommentAndPublish(String postId, String userId, String content) {
        addCommentOfSinglePost(postId, userId, content);
        // publishComment(commentInfo.getCommentId().toString(), content);
    }

    private CommentInfo addCommentOfSinglePost(String postId, String userId, String content) {

        CommentInfo commentInfo = new CommentInfo();

        // Load Post entity
        PostInfo post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Load User entity
        UserInfo user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        commentInfo.setPost(post);
        commentInfo.setUser(user);
        commentInfo.setContent(content);
        commentInfo.setModerationStatus(ModerationStatus.APPROVED); // It is pending for future moderation function.

        return commentRepository.save(commentInfo);
    }

    // private void publishComment(String commentId, String content) {
    //     kafkaTemplate.ifPresent(template -> {
    //         String message =
    //             "{\"commentId\":\"" + commentId + "\",\"content\":\"" + content + "\"}";
    //         template.send(KafkaConfig.COMMENT_TOPIC, message);
    //     });
    // }
}
