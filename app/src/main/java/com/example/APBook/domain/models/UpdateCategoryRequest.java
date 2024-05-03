package com.example.APBook.domain.models;

public class UpdateCategoryRequest {

    private int id;
    private String name;

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

    public UpdateCategoryRequest(int id, String text) {
        this.id = id;
        this.name = text;
    }
}
