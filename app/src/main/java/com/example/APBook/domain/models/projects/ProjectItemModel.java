package com.example.APBook.domain.models.projects;

public class ProjectItemModel {
    public int id;
    public String name;
    public String description;
    public String logo;

    public ProjectItemModel(int id, String name, String description, String logo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logo = logo;
    }
}
