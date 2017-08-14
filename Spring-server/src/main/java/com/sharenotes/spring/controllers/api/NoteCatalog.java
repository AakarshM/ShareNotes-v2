package com.sharenotes.spring.controllers.api;

import java.util.List;

/**
 * Created by Aakarsh on 8/14/17.
 */
public class NoteCatalog {
    private List<Note> notes;
    private String query = "all";

    public NoteCatalog(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
