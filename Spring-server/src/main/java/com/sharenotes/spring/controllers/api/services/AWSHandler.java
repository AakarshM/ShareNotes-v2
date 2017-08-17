package com.sharenotes.spring.controllers.api.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Created by Aakarsh on 8/13/17.
 */

@Component
@Scope("singleton")
public class AWSHandler {
    AmazonS3 s3Client;

    private AWSHandler() throws URISyntaxException {
        URI AWSAccessKeyURI = new URI(System.getenv("AWS_ACCESS"));
        URI AWSSecretKeyURI = new URI(System.getenv("AWS_SECRET"));
        String accessKey= AWSAccessKeyURI.toString();
        String secretKey= AWSSecretKeyURI.toString();
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey,
                secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        System.out.println("SUCCESS CONNECTING AWS S3");

    }

    public AmazonS3 getS3Client(){
        return this.s3Client;
    }
}
