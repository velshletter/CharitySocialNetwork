package com.example.diploma.data.retrofit;

import com.example.diploma.data.retrofit.interfaces.UploadImageApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageBBInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.imgbb.com/1/";
    public static final String API_KEY = "7f7d1bd490ec2720b89ced83a81ef160";

    public static UploadImageApi getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(UploadImageApi.class);
    }
}
