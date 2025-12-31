package com.lil.insta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lil.insta.aws.config.AwsConfig;
import com.lil.insta.entity.PostDataRequest;
import com.lil.insta.entity.dto.PostDto;
import com.lil.insta.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<Map<String, Object>> uploadPost(@RequestBody PostDataRequest postDataRequest) {
        Map<String, Object> result = postService.createPost(postDataRequest, AwsConfig.PRODUCT_POST_BUCKET);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/edit/{postId}")
    public ResponseEntity<Void> editPost(@RequestBody PostDataRequest postDataRequest, @PathVariable String postId) {
        boolean result = postService.editPost(postDataRequest, postId);
        if (!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(@RequestBody PostDataRequest postDataRequest, @PathVariable String postId) {
        boolean result = postService.deletePost(postDataRequest, postId);
        if (!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{postId}")
    public ResponseEntity<PostDto> getSinglePost(@RequestParam String userId, @PathVariable String postId) {
        PostDto postDto = postService.getSinglePost(userId, postId);
        if (postDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/get/all")
    public ResponseEntity<Map<String, Object>> getAllPosts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> results = postService.getPaginatedPosts(page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/get/all/{userId}")
    public ResponseEntity<Map<String, Object>> getPostsByUserId(@PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> results = postService.getPostsByUserId(userId, page, size);
        return ResponseEntity.ok(results);
    }
}
