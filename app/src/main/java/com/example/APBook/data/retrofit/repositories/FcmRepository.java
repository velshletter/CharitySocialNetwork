package com.example.APBook.data.retrofit.repositories;

import com.example.APBook.data.retrofit.RetrofitInstance;
import com.example.APBook.domain.models.PostResponseModel;
import com.example.APBook.domain.models.firebase.SendMessageDto;
import com.example.APBook.presentation.Global;

import java.util.List;

import retrofit2.Call;

public class FcmRepository {

    public Call<Void> broadcast(SendMessageDto dto) {
        return RetrofitInstance.fcmApi.broadcast(dto);
    }

    public Call<Void> sendMessage(SendMessageDto dto) {
        return RetrofitInstance.fcmApi.sendMessage(dto);
    }
}
