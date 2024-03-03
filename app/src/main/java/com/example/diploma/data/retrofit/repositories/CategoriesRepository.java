package com.example.diploma.data.retrofit.repositories;

import com.example.diploma.data.retrofit.RetrofitInstance;
import com.example.diploma.domain.models.CategoryModel;

import java.util.List;

import retrofit2.Call;

public class CategoriesRepository {

    public Call<List<CategoryModel>> getCategories(){
        return RetrofitInstance.categoriesApi.getCategories();
    }
}
