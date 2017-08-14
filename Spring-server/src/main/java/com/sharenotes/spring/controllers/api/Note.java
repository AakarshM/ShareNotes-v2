package com.sharenotes.spring.controllers.api;

import javax.persistence.*;



@Table(name="notes")
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="course")
    private String course;

    @Column(name="university")
    private String university;

    @Column(name="date")
    private String date;

    @Column(name="professor")
    private String professor;

    @Column(name="semester")
    private String semester;

    @Column(name="url")
    private String url;

    public Note(){

    }

    public Note(String name, String course, String university, String date, String professor, String semester) {
        this.name = name;
        this.course = course;
        this.university = university;
        this.date = date;
        this.professor = professor;
        this.semester = semester;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
