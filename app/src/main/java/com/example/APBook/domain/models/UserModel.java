package com.example.APBook.domain.models;

import com.example.APBook.domain.models.projects.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private int id = 0;
    private String firebaseToken;
    private String email;
    private String password;
    private String firstName;
    private String secondName;
    private String photo = "";
    private int age;
    private int stars = 0;
    private List<ProjectModel> projects = new ArrayList<>();
    private List<Integer> selectedCategories = new ArrayList<>();
    private List<Integer> subscriptions = new ArrayList<>();
    private List<Integer> likedPosts = new ArrayList<>();

    public List<Integer> getLikedPosts() {
        return likedPosts;
    }


    public UserModel(int id, String firebaseToken, String email, String password, String firstName, String secondName, String photo, int age, List<ProjectModel> projects, List<Integer> selectedCategories, List<Integer> subscriptions, List<Integer> likedPosts) {
        this.id = id;
        this.firebaseToken = firebaseToken;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.photo = photo;
        this.age = age;
        this.projects = projects;
        this.selectedCategories = selectedCategories;
        this.subscriptions = subscriptions;
        this.likedPosts = likedPosts;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setLikedPosts(List<Integer> likedPosts) {
        this.likedPosts = likedPosts;
    }


    public List<Integer> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<Integer> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setProjects(List<ProjectModel> projects) {
        this.projects = projects;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setSubscriptions(List<Integer> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoto() {
        return photo;
    }

    public List<ProjectModel> getProjects() {
        return projects;
    }

    public String getSecondName() {
        return secondName;
    }

    public List<Integer> getSubscriptions() {
        return subscriptions;
    }

    public UserModel() {
        this.id = 0;
        this.email = "string";
        this.password = "string";
        this.firstName = "string";
        this.secondName = "string";
        this.photo = "string";
        this.age = 0;
    }

    public UserModel(int age, String firebaseToken, String email, String password, String firstName, String secName, String photo) {
        this.age = age;
        this.firebaseToken = firebaseToken;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secName;
        this.password = password;
        this.photo = photo;
    }

    public UserModel(int id, int age, String email, String password, String firstName, String secName, String photo) {
        this.id = id;
        this.age = age;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secName;
        this.password = password;
        this.photo = photo;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
