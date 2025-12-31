package com.lil.insta.entity.mapper;

import com.lil.insta.entity.CommentInfo;
import com.lil.insta.entity.dto.CommentDto;

public class CommentMapper {
        public static CommentDto toDto(CommentInfo entity) {
        CommentDto dto = new CommentDto();
        dto.setCommentId(entity.getCommentId());
        dto.setUserId(entity.getUser().getUserId().toString());
        dto.setPostId(entity.getPost().getPostId().toString());
        dto.setContent(entity.getContent());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setFullName(entity.getUser().getFullname());
        dto.setProfilePicKey(entity.getUser().getProfilePicKey());
        return dto;
    }
}