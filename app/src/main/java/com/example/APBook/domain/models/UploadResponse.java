package com.example.APBook.domain.models;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    @SerializedName("data")
    private Data data;

    @SerializedName("success")
    private boolean success;

    @SerializedName("status")
    private int status;

    public Data getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public class Data {
        @SerializedName("id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("url_viewer")
        private String urlViewer;

        @SerializedName("url")
        private String url;

        @SerializedName("display_url")
        private String displayUrl;

        @SerializedName("width")
        private int width;

        @SerializedName("height")
        private int height;

        @SerializedName("size")
        private String size;

        @SerializedName("time")
        private String time;

        @SerializedName("expiration")
        private String expiration;

        @SerializedName("image")
        private Image image;

        @SerializedName("thumb")
        private Thumbnail thumbnail;

        @SerializedName("medium")
        private Medium medium;

        @SerializedName("delete_url")
        private String deleteUrl;

        public String getUrl() {
            return url;
        }
// геттеры и сеттеры
    }

    public class Image {
        @SerializedName("filename")
        private String filename;

        @SerializedName("name")
        private String name;

        @SerializedName("mime")
        private String mime;

        @SerializedName("extension")
        private String extension;

        @SerializedName("url")
        private String url;

        // геттеры и сеттеры
    }

    public class Thumbnail {
        @SerializedName("filename")
        private String filename;

        @SerializedName("name")
        private String name;

        @SerializedName("mime")
        private String mime;

        @SerializedName("extension")
        private String extension;

        @SerializedName("url")
        private String url;

        // геттеры и сеттеры
    }

    public class Medium {
        @SerializedName("filename")
        private String filename;

        @SerializedName("name")
        private String name;

        @SerializedName("mime")
        private String mime;

        @SerializedName("extension")
        private String extension;

        @SerializedName("url")
        private String url;

        // геттеры и сеттеры
    }
}
