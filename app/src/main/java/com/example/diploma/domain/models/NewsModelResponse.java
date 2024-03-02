package com.example.diploma.domain.models;

import java.util.List;

public class NewsModelResponse {

    private int id;
    private String text;
    private List<PhotoModel> photos;
    private String project;

    public NewsModelResponse(int id, String text, List<PhotoModel> photos, String project) {
        this.id = id;
        this.text = text;
        this.photos = photos;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<PhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoModel> photos) {
        this.photos = photos;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
