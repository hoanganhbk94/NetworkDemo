package com.example.hoanganh.networkdemo.entity;

/**
 * Created by HoangAnh on 4/1/2016.
 */
public class Project {
    private String name;
    private String role;

    public Project() {
    }

    public Project(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
