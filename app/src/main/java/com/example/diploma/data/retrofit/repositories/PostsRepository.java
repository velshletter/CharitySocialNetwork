package com.example.diploma.data.retrofit.repositories;

import com.example.diploma.data.retrofit.RetrofitInstance;
import com.example.diploma.domain.models.NewsModelRequest;
import com.example.diploma.domain.models.NewsModelResponse;
import com.example.diploma.domain.models.PhotoModel;
import com.example.diploma.presentation.Global;

import java.util.List;

import retrofit2.Call;

public class PostsRepository {

    public Call<List<NewsModelResponse>> getSubscrPosts() {
        return RetrofitInstance.postApi.getSubscrPosts(Global.userId);
    }

    public Call<NewsModelResponse> addPost(int projectId, NewsModelRequest post) {
        return RetrofitInstance.postApi.addPost(projectId, post);
    }

}
