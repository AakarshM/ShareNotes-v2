package com.sharenotes.spring.controllers.api;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.goebl.david.Webb;
import com.sharenotes.spring.controllers.api.services.AWSHandler;
import com.sharenotes.spring.controllers.api.services.DBConnector;
import org.apache.coyote.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.validator.constraints.URL;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aakarsh on 8/14/17.
 */


@Component
public class FileHandler {
    private AWSHandler S3Handler;
    private DBConnector dbConnector;

    //Injecting S3Handler, dbConnector dependencies.
    @Autowired
    private FileHandler(AWSHandler S3Handler, DBConnector dbConnector){
        this.S3Handler = S3Handler;
        this.dbConnector = dbConnector;
    }

    public FileInfo getFile(int id){
        Session dbSession = dbConnector.getDBSession();

        Transaction tx = null;
        List<Note> list = null;
        FileInfo fInfo = null;

        try {
            tx = dbSession.beginTransaction();
            Query query = dbSession.createQuery("select t from Note t where id = :idQ");
            query.setParameter("idQ", id);
            list = query.list();
            tx.commit();

            String path = list.get(0).getUrl();

            java.util.Date expiration = new java.util.Date();
            long msec = expiration.getTime();
            msec += 1000 * 60 * 60; // 1 hour.
            expiration.setTime(msec);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest("uw-note-share", path);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
            generatePresignedUrlRequest.setExpiration(expiration);

            java.net.URL url = S3Handler.getS3Client().generatePresignedUrl(generatePresignedUrlRequest);

           // String URLString = ShortenURL(url.toString());

            fInfo = new FileInfo(url.toString(), list.get(0).getName());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {

            if(dbSession.isOpen()) dbSession.close();
            return fInfo;

        }

    }

    public List<Note> getFiles(){
        Session dbSession = dbConnector.getDBSession();
        Transaction tx = null;
        List<Note> list = null;
        try{
            tx = dbSession.beginTransaction();
            Query query = dbSession.createQuery("select t from Note t");
            list = query.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if(dbSession.isOpen()) dbSession.close();
            return list;
        }
    }

    public boolean saveFile(Note newNote){
        Session dbSession = dbConnector.getDBSession();
        try {
            dbSession.beginTransaction();
            dbSession.save(newNote);
            dbSession.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String ShortenURL(String URLString){
        Webb webb = Webb.create();
        JSONObject googlObj =
                webb.post("https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyD0um7LZvR3oYMSMH089qL8_KRXr4EGUs4")
                .param("longUrl", URLString)
                .ensureSuccess()
                .asJsonObject()
                .getBody();

        return googlObj.get("id").toString();

    }
}
