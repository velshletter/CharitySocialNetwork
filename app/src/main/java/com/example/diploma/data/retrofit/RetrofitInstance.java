package com.example.diploma.data.retrofit;

import com.example.diploma.data.retrofit.interfaces.PostApi;
import com.example.diploma.data.retrofit.interfaces.ProjectApi;
import com.example.diploma.data.retrofit.interfaces.UsersApi;
import com.example.diploma.presentation.Global;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(1, TimeUnit.SECONDS)
            .cookieJar(new JavaNetCookieJar(new CookieManager(null, CookiePolicy.ACCEPT_ALL)))
            .build();

    private static Retrofit retrofit;
    public static UsersApi usersApi;
    public static ProjectApi projectApi ;
    public static PostApi postApi;

    public static void updateRetrofit(String url){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://"+ url + ":8080")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usersApi  = retrofit.create(UsersApi.class);
        projectApi = retrofit.create(ProjectApi.class);
        postApi = retrofit.create(PostApi.class);
    }
}

