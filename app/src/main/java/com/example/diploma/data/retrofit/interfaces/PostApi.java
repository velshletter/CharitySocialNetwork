package com.example.diploma.data.retrofit.interfaces;

import com.example.diploma.domain.models.NewsModelRequest;
import com.example.diploma.domain.models.NewsModelResponse;
import com.example.diploma.domain.models.PhotoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostApi {

    @GET("/api/post")
    Call<List<NewsModelResponse>> getSubscrPosts(@Query("user") int id);

    @POST("/api/project/{id}/post")
    Call<NewsModelResponse> addPost(@Path("id") int id, @Body NewsModelRequest post);

    @DELETE("/api/post/{id}")
    Call<String> deletePost(@Path("id") int id);


}
