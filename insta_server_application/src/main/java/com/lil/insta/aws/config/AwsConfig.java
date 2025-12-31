package com.lil.insta.aws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AwsConfig {
    public static final String PRODUCT_POST_BUCKET = "lil-insta-post-image";
    public static final String PRODUCT_USER_BUCKET = "lil-insta-avatar-image";

    @Bean
    public S3Presigner s3Presigner(){
        return S3Presigner.builder().region(Region.EU_WEST_2).build();
    }
}
