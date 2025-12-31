package com.lil.insta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lil.insta.entity.CommentRequest;
import com.lil.insta.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/get/{postId}")
    public ResponseEntity<Map<String, Object>> getCommentListOfSinglePost(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @PathVariable String postId) {
        Map<String, Object> results = commentService.getPagenatedCommentListOfSinglePost(page, size, postId);
        return ResponseEntity.ok(results);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Map<String, Object>> addCommentOfSinglePost(@RequestBody CommentRequest req) {
        commentService.addCommentAndPublish(req.getPostId(), req.getUserId(), req.getContent());
        return ResponseEntity.ok(Map.of("success", true));

    }
}
