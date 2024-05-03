package com.example.APBook.data.retrofit.interfaces;

import com.example.APBook.domain.models.firebase.SendMessageDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FcmApi {

    @POST("/send")
    Call<Void> sendMessage(@Body SendMessageDto body);

    @POST("/broadcast")
    Call<Void> broadcast(@Body SendMessageDto body);

}
