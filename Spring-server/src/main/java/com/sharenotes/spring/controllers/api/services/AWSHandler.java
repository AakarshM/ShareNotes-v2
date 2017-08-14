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
import java.util.Properties;

/**
 * Created by Aakarsh on 8/13/17.
 */

@Component
@Scope("singleton")
public class AWSHandler {
    AmazonS3 s3Client;

    /*
    public AWSHandler(){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            System.out.println("GETTING-INPUT");

            input = new FileInputStream("aws.properties");
           // input = new FileInputStream("META-INF/spring/aws.properties");

            System.out.println("LOADED-INPUT");

            // load a properties file
            prop.load(input);

            System.out.println("LOADED-PROP");

            System.out.println("ACCESSKEY:" + prop.getProperty("aws.accessKey"));

            // get the property value and print it out
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(prop.getProperty("aws.accessKey"),
                    prop.getProperty("aws.secretKey"));

            System.out.println("ACCESSKEY2:" + prop.getProperty("aws.accessKey"));
            s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();

            System.out.println("SUCCESS CONNECTING AWS S3");

        } catch (IOException ex) {
            System.out.println("AWSHANDLER-ERROR");
            ex.printStackTrace();
        }
    } */

    private AWSHandler(){
        String accessKey="AKIAIGGWMCTPGKSIN5PQ";
        String secretKey="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";
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
