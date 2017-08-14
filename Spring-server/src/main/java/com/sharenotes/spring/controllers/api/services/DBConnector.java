package com.sharenotes.spring.controllers.api.services;

import com.sharenotes.spring.controllers.api.Note;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Aakarsh on 8/13/17.
 */

@Component
@Scope("singleton")
public class DBConnector {

    private SessionFactory dbFactory;

    private DBConnector(){
        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            Configuration cfg = new Configuration().configure("META-INF/spring/hibernate.cfg.xml");
            cfg.setProperty("hibernate.connection.url", dbUrl);
            cfg.setProperty("hibernate.connection.username", username);
            cfg.setProperty("hibernate.connection.password", password);

            SessionFactory factory = cfg.
                    addAnnotatedClass(Note.class).
                    buildSessionFactory();

            this.dbFactory = factory;

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Session getDBSession(){
        return dbFactory.getCurrentSession();
    }


    @PreDestroy
    public void destroy(){
        dbFactory = null;
    }
}
