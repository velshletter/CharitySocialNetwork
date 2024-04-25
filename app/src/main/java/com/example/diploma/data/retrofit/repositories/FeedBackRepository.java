package com.example.diploma.data.retrofit.repositories;

import com.example.diploma.data.retrofit.RetrofitInstance;
import com.example.diploma.domain.models.FeedBackModel;

import retrofit2.Call;

public class FeedBackRepository {

    public Call<FeedBackModel> sendProblemMessage(String feedback, int authorId){
        return RetrofitInstance.feedBackApi.sendProblemMessage(feedback, authorId);
    }

}
