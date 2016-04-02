package com.example.hoanganh.networkdemo.entity;

/**
 * Created by HoangAnh on 4/1/2016.
 */
public class Person{
    private String name;
    private int gender;
    private Project project;

    public Person() {
    }

    public Person(String name, int gender, Project project) {
        this.name = name;
        this.gender = gender;
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
