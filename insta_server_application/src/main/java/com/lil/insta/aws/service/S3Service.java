package com.lil.insta.aws.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class S3Service {
    // private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(S3Service.class);
    private final S3Presigner s3Presigner;
    @Autowired
    public S3Service(S3Presigner s3Presigner) {
        this.s3Presigner = s3Presigner;
    }

    public String createPresignedPutUrl(String key, String bucketName) {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(3))
            .putObjectRequest(putObjectRequest)
            .build();

        PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
        return presignedPutObjectRequest.url().toString();
    }

    public String createPresignedGetUrl(String key, String bucketName) {

        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(3))
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);
        return presignedGetObjectRequest.url().toString();
    }

}
