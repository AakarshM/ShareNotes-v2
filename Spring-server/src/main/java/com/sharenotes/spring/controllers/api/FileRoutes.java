package com.sharenotes.spring.controllers.api;

import com.sharenotes.spring.controllers.api.services.ObjectWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


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
    public Map<String, String> ret(){
        HashMap<String, String> map = new HashMap<>();
        map.put("Jason Williams", "Saihiljit Dogan");
        map.put("Well, well, well", "GARBAGE");
        map.put("William", "William");
        map.put("Kinkakyu", "Japanese");
        map.put("Mr", "Panitos");
        return map;
    }

    @RequestMapping(value = "/api/getfile", method = RequestMethod.POST) //specific note
    public Future<FileInfo> returnFile(@RequestBody String body) throws IOException{
        CompletableFuture<FileInfo> fileFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() ->{
            try{
                FileQuery fQuery = wrapper.getWrapper().readValue(body, FileQuery.class);
                fileFuture.complete(fHandler.getFile(fQuery.getID()));
            } catch (IOException e){

            }
        });
        return fileFuture;
    }

    @RequestMapping(value = "/api/getallfiles", method = RequestMethod.GET)
    public NoteCatalog returnCatalog(){
        return new NoteCatalog(fHandler.getFiles());
    }

    @RequestMapping(value = "/api/uploader", method = RequestMethod.POST)
    public String saveNotes(@RequestBody String body) throws IOException{
        Note note = wrapper.getWrapper().readValue(body, Note.class);
        fHandler.saveFile(note);
        return note.toString();
    }
}
