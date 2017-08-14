package com.sharenotes.spring.controllers.api;

import javax.persistence.Entity;

/**
 * Created by Aakarsh on 8/14/17.
 */

public class FileQuery {
    private String name;
    private String university;
    private String professor;
    private int ID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
