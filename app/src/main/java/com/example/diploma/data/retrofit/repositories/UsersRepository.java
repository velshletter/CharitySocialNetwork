package com.example.diploma.data.retrofit.repositories;

import com.example.diploma.data.retrofit.RetrofitInstance;
import com.example.diploma.domain.models.UpdateCategoryRequest;
import com.example.diploma.domain.models.UserModel;
import com.example.diploma.presentation.Global;

import java.util.List;

import retrofit2.Call;

public class UsersRepository {
    public Call<UserModel> register(UserModel userModel) {
        return RetrofitInstance.usersApi.postRegister(userModel);
    }

    public Call<UserModel> getUserById(int id) {
        return RetrofitInstance.usersApi.getUserById(id);
    }

    public Call<UserModel> login(String email) {
        return RetrofitInstance.usersApi.login(email);
    }

    public Call<UserModel> updateUserData(UserModel userModel) {
        return RetrofitInstance.usersApi.updateUserData(Global.userId, userModel);
    }

    public Call<UserModel> updateCategories(int userId, List<UpdateCategoryRequest> request) {
        return RetrofitInstance.usersApi.updateCategories(userId, request);
    }

}
