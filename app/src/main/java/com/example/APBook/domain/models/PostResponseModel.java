package com.example.APBook.domain.models;

import java.util.List;

public class PostResponseModel {

    private int id;
    private String text;
    private List<PhotoModel> photos;
    private String project;
    private String projectLogo;
    private String creationDate;
    private List<Integer> likedUsers;
    private List<Comment> comments;

    public PostResponseModel(int id, String text, List<PhotoModel> photos, String project, String projectLogo, String creationDate, List<Integer> likedUsers, List<Comment> comments) {
        this.id = id;
        this.text = text;
        this.photos = photos;
        this.project = project;
        this.projectLogo = projectLogo;
        this.creationDate = creationDate;
        this.likedUsers = likedUsers;
        this.comments = comments;
    }


    public List<Integer> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<Integer> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getProjectLogo() {
        return projectLogo;
    }

    public void setProjectLogo(String projectLogo) {
        this.projectLogo = projectLogo;
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
