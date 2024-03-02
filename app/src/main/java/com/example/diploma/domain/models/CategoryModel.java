package com.example.diploma.domain.models;

public class CategoryModel {
    private int id;
    private String name;
    private Boolean isChecked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryModel(int id, String text, Boolean isChecked) {
        this.id = id;
        this.name = text;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
