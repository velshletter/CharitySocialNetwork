package com.example.APBook.data.retrofit.repositories;

import com.example.APBook.data.retrofit.RetrofitInstance;
import com.example.APBook.domain.models.UpdateCategoryRequest;
import com.example.APBook.domain.models.UserModel;
import com.example.APBook.presentation.Global;

import java.util.List;

import retrofit2.Call;

public class UsersRepository {
    public Call<UserModel> register(UserModel userModel, String code) {
        return RetrofitInstance.usersApi.register(userModel, code);
    }

    public Call<UserModel> getUserById(int id) {
        return RetrofitInstance.usersApi.getUserById(id);
    }

    public Call<UserModel> login(String email, String token) {
        return RetrofitInstance.usersApi.login(email, token);
    }

    public Call<UserModel> updateUserData(UserModel userModel) {
        return RetrofitInstance.usersApi.updateUserData(Global.userId, userModel);
    }

    public Call<UserModel> updateCategories(int userId, List<UpdateCategoryRequest> request) {
        return RetrofitInstance.usersApi.updateCategories(userId, request);
    }

    public Call<Void> sendVerification(String email) {
        return RetrofitInstance.usersApi.sendVerification(email);
    }

}
