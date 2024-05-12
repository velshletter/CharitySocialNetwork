package com.example.APBook.data.retrofit.interfaces;

import com.example.APBook.domain.models.UpdateCategoryRequest;
import com.example.APBook.domain.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsersApi {

    @POST("/api/users")
    Call<UserModel> register(@Body UserModel userModel, @Query("code") String code);

    @GET("/api/users/auth/{email}")
    Call<UserModel> login(@Path("email") String email, @Query("token") String token);

    @GET("/api/users/{id}")
    Call<UserModel> getUserById(@Path("id") int id);

    @PUT("/api/users/{id}")
    Call<UserModel> updateUserData(@Path("id") int id, @Body UserModel userModel);

    @PUT("/api/users/{id}/category")
    Call<UserModel> updateCategories(@Path("id") int id, @Body List<UpdateCategoryRequest> request);

    @GET("/api/email/send-verification")
    Call<Void> sendVerification(@Query("email") String email);
}
