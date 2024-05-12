package com.example.APBook.data.retrofit.repositories;

import com.example.APBook.data.retrofit.RetrofitInstance;
import com.example.APBook.domain.models.firebase.SendMessageDto;

import retrofit2.Call;

public class FcmRepository {

    public Call<Void> broadcast(SendMessageDto dto) {
        return RetrofitInstance.fcmApi.broadcast(dto);
    }

    public Call<Void> sendMessage(SendMessageDto dto) {
        return RetrofitInstance.fcmApi.sendMessage(dto);
    }
}
