package com.example.diploma.domain.models;

import java.util.List;

public class NewsModelRequest {

    private int id = 0;
    private String text;
    private List<PhotoModel> photos;


    public NewsModelRequest( String text, List<PhotoModel> photos) {
        this.text = text;
        this.photos = photos;
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
}
