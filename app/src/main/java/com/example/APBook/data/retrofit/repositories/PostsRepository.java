package com.example.APBook.data.retrofit.repositories;

import com.example.APBook.data.retrofit.RetrofitInstance;
import com.example.APBook.domain.models.Comment;
import com.example.APBook.domain.models.PostModelRequest;
import com.example.APBook.domain.models.PostResponseModel;
import com.example.APBook.Global;

import java.util.List;

import retrofit2.Call;

public class PostsRepository {

    public Call<List<PostResponseModel>> getSubscrPosts() {
        return RetrofitInstance.postApi.getSubscrPosts(Global.userId);
    }

    public Call<PostResponseModel> addPost(int projectId, PostModelRequest post) {
        return RetrofitInstance.postApi.addPost(projectId, post);
    }

    public Call<String> deletePost(int id){
        return RetrofitInstance.postApi.deletePost(id);
    }

    public Call<Comment> addComment(String text,  int postId){
        return RetrofitInstance.postApi.addComment(text, postId, Global.userId);
    }

    public Call<Void> likePost(int postId) {
        return RetrofitInstance.postApi.likePost(postId, Global.userId);
    }

    public Call<Void> unlikePost(int postId) {
        return RetrofitInstance.postApi.unlikePost(postId, Global.userId);
    }

}
