package com.example.APBook.data.retrofit.interfaces;

import com.example.APBook.domain.models.FeedBackModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FeedBackApi {

    @POST("/feedback")
    Call<FeedBackModel> sendProblemMessage(
            @Body String feedback,
            @Query("authorId") int authorId
    );
}
