package com.example.APBook.data.retrofit.repositories;

import com.example.APBook.data.retrofit.RetrofitInstance;
import com.example.APBook.domain.models.FeedBackModel;

import retrofit2.Call;

public class FeedBackRepository {

    public Call<FeedBackModel> sendProblemMessage(String feedback, int authorId){
        return RetrofitInstance.feedBackApi.sendProblemMessage(feedback, authorId);
    }

}
