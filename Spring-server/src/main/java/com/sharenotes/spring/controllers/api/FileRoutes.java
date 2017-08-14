package com.sharenotes.spring.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharenotes.spring.controllers.api.services.AWSHandler;
import com.sharenotes.spring.controllers.api.services.DBConnector;
import com.sun.org.apache.regexp.internal.RE;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Component
@RestController
public class FileRoutes {
    private FileHandler fHandler;

    //Injecting S3Handler, dbConnector dependencies.
    @Autowired
    private FileRoutes(FileHandler fHandler){
        this.fHandler = fHandler;
    }
    private FileRoutes(){}

    @RequestMapping("/")
    public String ret(){
        return "Banamaya Charodia";
    }

    @RequestMapping(value = "/api/getfile", method = RequestMethod.POST) //specific note
    public FileInfo returnFile(@RequestBody String body){
        ObjectMapper mapper = new ObjectMapper();
        FileQuery fQuery = null;
        try {
            fQuery = mapper.readValue(body, FileQuery.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fHandler.getFile(fQuery.getID());
    }

    @RequestMapping(value = "/api/getallfiles", method = RequestMethod.GET)
    public NoteCatalog returnCatalog(){
        return new NoteCatalog(fHandler.getFiles());
    }

    @RequestMapping(value = "/api/uploader", method = RequestMethod.POST)
    public boolean saveNotes(@RequestBody String body){
        ObjectMapper mapper = new ObjectMapper();
        Note note;
        try{
            note = mapper.readValue(body, Note.class);
            return fHandler.saveFile(note);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
