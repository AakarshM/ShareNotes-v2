package com.sharenotes.spring.controllers.api;

import com.sharenotes.spring.controllers.api.services.AWSHandler;
import com.sharenotes.spring.controllers.api.services.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Component
@RestController
public class FileRoutes {
    private AWSHandler S3Handler;
    private DBConnector dbConnector;

    //Injecting S3Handler, dbConnector dependencies.
    @Autowired
    private FileRoutes(AWSHandler S3Handler, DBConnector dbConnector){
        this.S3Handler = S3Handler;
        this.dbConnector = dbConnector;
    }

    @RequestMapping("/")
    public String ret(){
        return "Garodia";
    }

    private FileRoutes(){}

}
