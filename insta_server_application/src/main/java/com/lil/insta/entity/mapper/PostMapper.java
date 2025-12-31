package com.lil.insta.entity.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lil.insta.aws.config.AwsConfig;
import com.lil.insta.aws.service.S3Service;
import com.lil.insta.entity.PostInfo;
import com.lil.insta.entity.dto.PostDto;

@Component
public class PostMapper {
    private S3Service s3Service;
    @Autowired
    public PostMapper(S3Service s3Service){
        this.s3Service = s3Service;
    }
    public PostDto toDto(PostInfo entity) {
        
        PostDto dto = new PostDto();
        dto.setPostId(entity.getPostId());
        dto.setFileKey(s3Service.createPresignedGetUrl(entity.getFileKey(), AwsConfig.PRODUCT_POST_BUCKET));
        dto.setCaptionText(entity.getCaptionText());
        dto.setUserId(entity.getUser().getUserId().toString());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setFullName(entity.getUser().getFullname());
        dto.setProfilePicKey(s3Service.createPresignedGetUrl(entity.getUser().getProfilePicKey(), AwsConfig.PRODUCT_USER_BUCKET));
        return dto;
    }
}
