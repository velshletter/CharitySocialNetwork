package com.example.APBook.domain.models;

import java.util.List;

public class Comment {
    private int id = 0;
    private String text;
    private String creationTime;
    private int author;

    public Comment(int id, String text, String creationTime, int author) {
        this.id = id;
        this.text = text;
        this.creationTime = creationTime;
        this.author = author;
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

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String toFormattedString() {
        return "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"text\": \"" + text + "\",\n" +
                "  \"creationTime\": \"" + creationTime + "\",\n" +
                "  \"author\": " + author + "\n" +
                "}";
    }
}
