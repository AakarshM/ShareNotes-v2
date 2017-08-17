package com.sharenotes.spring.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharenotes.spring.controllers.api.services.AWSHandler;
import com.sharenotes.spring.controllers.api.services.DBConnector;
import com.sharenotes.spring.controllers.api.services.ObjectWrapper;
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
    private ObjectWrapper wrapper;

    //Injecting S3Handler, dbConnector dependencies.
    @Autowired
    private FileRoutes(FileHandler fHandler, ObjectWrapper wrapper){
        this.fHandler = fHandler;
        this.wrapper = wrapper;
    }
    private FileRoutes(){}

    @RequestMapping("/")
    public String ret(){
        return "Banamaya Charodia";
    }

    @RequestMapping(value = "/api/getfile", method = RequestMethod.POST) //specific note
    public FileInfo returnFile(@RequestBody String body) throws IOException{
        FileQuery fQuery = null;
        fQuery = wrapper.getWrapper().readValue(body, FileQuery.class);
        return fHandler.getFile(fQuery.getID());
    }

    @RequestMapping(value = "/api/getallfiles", method = RequestMethod.GET)
    public NoteCatalog returnCatalog(){
        return new NoteCatalog(fHandler.getFiles());
    }

    @RequestMapping(value = "/api/uploader", method = RequestMethod.POST)
    public String saveNotes(@RequestBody String body) throws IOException{
        Note note = null;
        note = wrapper.getWrapper().readValue(body, Note.class);
        fHandler.saveFile(note);
        return note.toString();
    }
}
