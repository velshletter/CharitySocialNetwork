package com.example.APBook.data.retrofit.repositories;

import com.example.APBook.data.retrofit.RetrofitInstance;
import com.example.APBook.domain.models.CategoryModel;

import java.util.List;

import retrofit2.Call;

public class CategoriesRepository {

    public Call<List<CategoryModel>> getCategories(){
        return RetrofitInstance.categoriesApi.getCategories();
    }
}
