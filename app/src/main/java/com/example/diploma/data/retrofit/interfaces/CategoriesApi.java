package com.example.diploma.data.retrofit.interfaces;

import com.example.diploma.domain.models.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface CategoriesApi {

    @POST("/api/category")
    Call<List<CategoryModel>> getCategories();
}
