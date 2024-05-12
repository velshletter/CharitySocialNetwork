package com.example.APBook.data.retrofit.interfaces;

import com.example.APBook.domain.models.PhotoModel;
import com.example.APBook.domain.models.UserModel;
import com.example.APBook.domain.models.projects.ProjectItemModel;
import com.example.APBook.domain.models.projects.ProjectModel;
import com.example.APBook.domain.models.projects.ProjectModelAdd;
import com.example.APBook.domain.models.projects.SubscriberModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectApi {

    @GET("/api/project")
    Call<List<ProjectModel>> getAllProjects();

    @GET("/api/project/list")
    Call<List<ProjectItemModel>> getProjectsByCategories(@Query("id") int id);

    @GET("/api/project/{id}")
    Call<ProjectModel> getProjectById(@Path("id") int id);

    @POST("/api/project")
    Call<ProjectModel> addProject(@Body ProjectModelAdd project, @Query("userId") int id);

    @PUT("/api/project/{id}")
    Call<ProjectModel> updateProject(@Path("id") int id, @Body ProjectModelAdd projectModel);

    @PUT("/api/project/{id}/subscribers/rate/{userId}")
    Call<Void> rateSub(@Path("id") int id, @Path("userId") int userId);

    @PUT("/api/project/{id}/subscribers/ignore/{userId}")
    Call<Void> ignoreSub(@Path("id") int id, @Path("userId") int userId);

    @GET("/api/project/subscriptions")
    Call<List<ProjectModel>> getMySubscriptions(@Query("id") int id);

    @GET("/api/project/{id}/subscribers")
    Call<List<SubscriberModel>> getSubscribers(@Path("id") int id);

    @POST("/api/project/subscribe")
    Call<String> subscribe(@Query("userId") int userId, @Query("projectId") int projectId);

    @DELETE("/api/project/unsubscribe")
    Call<String> unsubscribe(@Query("userId") int userId, @Query("projectId") int projectId);

    @DELETE("/api/project/{id}")
    Call<String> deleteProject(@Path("id") int projectId);

    @POST("/api/project/{id}/photo")
    Call<PhotoModel> addPhoto(@Path("id") int id, @Body PhotoModel photo);

}
