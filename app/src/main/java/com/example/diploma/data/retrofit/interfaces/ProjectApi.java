package com.example.diploma.data.retrofit.interfaces;

import com.example.diploma.domain.models.NewsModelResponse;
import com.example.diploma.domain.models.PhotoModel;
import com.example.diploma.domain.models.ProjectItemModel;
import com.example.diploma.domain.models.ProjectModel;
import com.example.diploma.domain.models.ProjectModelAdd;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("/api/project/subscriptions")
    Call<List<ProjectModel>> getMySubscriptions(@Query("id") int id);

    @POST("/api/project/subscribe")
    Call<String> subscribe(@Query("userId") int userId, @Query("projectId") int projectId);

    @DELETE("/api/project/unsubscribe")
    Call<String> unsubscribe(@Query("userId") int userId, @Query("projectId") int projectId);

    @POST("/api/project/{id}/photo")
    Call<PhotoModel> addPhoto(@Path("id") int id, @Body PhotoModel photo);

}
