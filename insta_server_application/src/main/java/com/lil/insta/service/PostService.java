package com.lil.insta.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lil.insta.aws.service.S3Service;
import com.lil.insta.entity.FileInfo;
import com.lil.insta.entity.ModerationStatus;
import com.lil.insta.entity.PostDataRequest;
import com.lil.insta.entity.PostInfo;
import com.lil.insta.entity.UserInfo;
import com.lil.insta.entity.dto.PostDto;
import com.lil.insta.entity.mapper.PostMapper;
import com.lil.insta.repository.PostRepository;
import com.lil.insta.repository.UserRepository;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final S3Service s3Service;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper, S3Service s3Service) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
        this.s3Service = s3Service;
    }

    public Map<String, Object> createPost(PostDataRequest postDataRequest, String bucketName) {
        UserInfo user = userRepository.findById(UUID.fromString(postDataRequest.getUserId()))
            .orElseThrow(() -> new RuntimeException("User not found"));

        String caption = postDataRequest.getCaption();
        PostInfo postInfo = new PostInfo();
        postInfo.setUser(user);
        postInfo.setCaptionText(caption);
        postInfo.setModerationStatus(ModerationStatus.UPLOADING);
        postInfo = postRepository.save(postInfo);
        FileInfo fileInfo = postDataRequest.getFileInfo();
        String fileKey = String.format(
            "posts/%s/%s/%s",
            user.getUserId(),
            postInfo.getPostId(),
            fileInfo.getFileName()
        );
        postInfo.setFileKey(fileKey);
        postRepository.save(postInfo);

        String presignedPutUrl = s3Service.createPresignedPutUrl(
            fileKey,
            bucketName
        );

        Map<String, Object> response = new HashMap<>();
        response.put("postId", postInfo.getPostId());
        response.put("presignedPutUrl", presignedPutUrl);

        return response;
    }

    public boolean editPost(PostDataRequest postDataRequest, String postId) {
        Optional<PostInfo> postInfo = postRepository.findById(UUID.fromString(postId));
        if (postInfo.isEmpty())
            return false;
        PostInfo editedPost = postInfo.get();

        if (postDataRequest.getCaption() != null) {
            editedPost.setCaptionText(postDataRequest.getCaption());
        }

        if (postDataRequest.getModerationStatus() != null) {
            editedPost.setModerationStatus(postDataRequest.getModerationStatus());
        }

        postRepository.save(editedPost);
        return true;
    }

    public boolean deletePost(PostDataRequest postDataRequest, String postId) {
        Optional<PostInfo> post = postRepository.findById(UUID.fromString(postId));
        if (post.isEmpty())
            return false;
        if (!post.get().getUser().getUserId().toString().equals(postDataRequest.getUserId()))
            return false;
        postRepository.deleteById(UUID.fromString(postId));
        return true;
    }

    public PostDto getSinglePost(String userId, String postId) {
        PostInfo post = postRepository.findById(UUID.fromString(postId))
                .orElse(null);

        return post != null ? postMapper.toDto(post) : null;
    }

    public Map<String, Object> getPaginatedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<PostInfo> pagedPostInfo =
            postRepository.findAllByModerationStatusOrderByCreatedAtDesc(
                ModerationStatus.APPROVED, pageable);

        List<PostDto> postDtos = pagedPostInfo.getContent()
            .stream()
            .map(postMapper::toDto)
            .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("posts", postDtos);
        response.put("currentPage", page);
        response.put("totalPages", pagedPostInfo.getTotalPages());
        response.put("totalPosts", pagedPostInfo.getTotalElements());

        return response;
    }

    public Map<String, Object> getPostsByUserId(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        UserInfo user = userRepository.findById(UUID.fromString(userId))
            .orElseThrow(() -> new RuntimeException("User not found"));

        Page<PostInfo> pagedPostInfo =
            postRepository.findByUserAndModerationStatusOrderByCreatedAtDesc(
                user, ModerationStatus.APPROVED, pageable);

        List<PostDto> postDtos = pagedPostInfo.getContent()
            .stream()
            .map(postMapper::toDto)
            .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("posts", postDtos);
        response.put("currentPage", page);
        response.put("totalPages", pagedPostInfo.getTotalPages());
        response.put("totalPosts", pagedPostInfo.getTotalElements());
        response.put("profilePicKey", user.getProfilePicKey());
        response.put("fullname", user.getFullname());
        response.put("userId", userId);

        return response;
    }

    public int getCountAllPosts() {
        return postRepository.countByModerationStatus(ModerationStatus.APPROVED);
    }

    public int countByUserIdAndModerationStatus(String userId) {
        Optional<UserInfo> user = userRepository.findById(UUID.fromString(userId));
        return postRepository.countByUserAndModerationStatus(user.get(), ModerationStatus.APPROVED);
    }
}
