package com.example.APBook.domain.models.projects;

public class SubscriberModel {

    private String firstName;
    private String secondName;
    private String photo = "";
    private int id;
    private boolean star;

    public SubscriberModel(String firstName, String secondName, String photo, int id, boolean star) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.photo = photo;
        this.id = id;
        this.star = star;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }
}
