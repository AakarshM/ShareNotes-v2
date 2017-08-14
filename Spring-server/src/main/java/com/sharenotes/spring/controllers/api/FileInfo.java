package com.sharenotes.spring.controllers.api;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * Created by Aakarsh on 8/14/17.
 */


public class FileInfo {
    private final String link;
    private final String name;

    FileInfo(String link, String name){
        this.link = link;
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }
}
