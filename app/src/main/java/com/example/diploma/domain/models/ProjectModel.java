package com.example.diploma.domain.models;

import java.util.List;

public class ProjectModel {

    public int id;
    public String name;
    public String description;
    public String startDate;
    public String endDate;
    public Boolean isOnline;
    public String address;
    public String logo;
    public int author;
    public List<NewsModelResponse> posts;
    public List<Integer> subscribers;
    public List<PhotoModel> photos;
    public CategoryModel category;

    public ProjectModel() {
    }

    public ProjectModel(String projectName, String projectImage, String description) {
        this.name = projectName;
        this.logo = projectImage;
        this.description = description;
    }

    public ProjectModel(String name, int id, String description, String startDate, String endDate,
                        Boolean isOnline, String address, String logo, List<NewsModelResponse> posts,
                        List<Integer> subscribers, List<PhotoModel> photos, CategoryModel category, int author) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOnline = isOnline;
        this.address = address;
        this.logo = logo;
        this.posts = posts;
        this.subscribers = subscribers;
        this.photos = photos;
        this.category = category;
        this.author = author;
    }
}
