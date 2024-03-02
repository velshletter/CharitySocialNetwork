package com.example.diploma.data.retrofit.interfaces;

import com.example.diploma.domain.models.UpdateCategoryRequest;
import com.example.diploma.domain.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsersApi {

    @POST("/api/users")
    Call<UserModel> postRegister(@Body UserModel userModel);

    @GET("/api/users/auth/{email}")
    Call<UserModel> login(@Path("email") String email);

    @GET("/api/users/{id}")
    Call<UserModel> getUserById(@Path("id") int id);

    @PUT("/api/users/{id}")
    Call<UserModel> updateUserData(@Path("id") int id, @Body UserModel userModel);

    @PUT("/api/users/category/{id}")
    Call<UserModel> updateCategories(@Path("id") int id, @Body List<UpdateCategoryRequest> request);
}
