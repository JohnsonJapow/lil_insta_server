package com.lil.insta.aws.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lil.insta.aws.service.S3Service;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    private S3Service s3Service;

    @Autowired
    public S3Controller(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @GetMapping(value="/getUrl")
    public Map<String, String> getPresignedGetUrl(@RequestParam String key, @RequestParam String bucketName){
        String url = s3Service.createPresignedGetUrl(key, bucketName);
         return Map.of("url", url);
    }
}
