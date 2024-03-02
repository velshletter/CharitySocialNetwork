package com.example.diploma.data.retrofit.interfaces;

import com.example.diploma.domain.models.UploadResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UploadImageApi {

    @FormUrlEncoded
    @POST("upload")
    Call<UploadResponse> uploadImage(
            @Field("key") String apiKey,
            @Field("image") String imageData
    );
}
