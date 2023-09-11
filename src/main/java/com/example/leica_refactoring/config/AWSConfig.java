package com.example.leica_refactoring.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AWSConfig {

    @Value("${iamAccessKey}")
    private String iamAccessKey;

    @Value("${iamSecretKey}")
    private String iamSecretKey;

    private String region = "ap-northeast-2";

    @Bean
    @Primary
    public AmazonS3 amazonS3Client(){
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(iamAccessKey,iamSecretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}

