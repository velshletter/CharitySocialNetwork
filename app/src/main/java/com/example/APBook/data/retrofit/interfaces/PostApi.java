package com.example.APBook.data.retrofit.interfaces;

import com.example.APBook.domain.models.Comment;
import com.example.APBook.domain.models.PostModelRequest;
import com.example.APBook.domain.models.PostResponseModel;

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
    Call<List<PostResponseModel>> getSubscrPosts(@Query("user") int id);

    @POST("/api/project/{id}/post")
    Call<PostResponseModel> addPost(@Path("id") int id, @Body PostModelRequest post);

    @DELETE("/api/post/{id}")
    Call<String> deletePost(@Path("id") int id);

    @POST("/api/post/{postId}/comment")
    Call<Comment> addComment(@Body String text, @Path("postId") int postId, @Query("authorId") int authorId);

    @POST("/api/post/{postId}/like")
    Call<Void> likePost(@Path("postId") int postId, @Query("authorId") int authorId);

    @DELETE("/api/post/{postId}/unlike")
    Call<Void> unlikePost(@Path("postId") int postId, @Query("authorId") int authorId);
}
