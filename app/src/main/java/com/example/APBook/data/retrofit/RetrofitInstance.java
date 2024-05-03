package com.example.APBook.data.retrofit;

import androidx.fragment.app.FragmentContainerView;

import com.example.APBook.data.retrofit.interfaces.CategoriesApi;
import com.example.APBook.data.retrofit.interfaces.FcmApi;
import com.example.APBook.data.retrofit.interfaces.FeedBackApi;
import com.example.APBook.data.retrofit.interfaces.PostApi;
import com.example.APBook.data.retrofit.interfaces.ProjectApi;
import com.example.APBook.data.retrofit.interfaces.UsersApi;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .cookieJar(new JavaNetCookieJar(new CookieManager(null, CookiePolicy.ACCEPT_ALL)))
            .build();

    private static Retrofit retrofit;
    public static UsersApi usersApi;
    public static ProjectApi projectApi ;
    public static PostApi postApi;
    public static CategoriesApi categoriesApi;
    public static FeedBackApi feedBackApi;
    public static FcmApi fcmApi;

    public static void updateRetrofit(String url){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://"+ url + ":8080")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usersApi  = retrofit.create(UsersApi.class);
        projectApi = retrofit.create(ProjectApi.class);
        postApi = retrofit.create(PostApi.class);
        categoriesApi = retrofit.create(CategoriesApi.class);
        feedBackApi = retrofit.create(FeedBackApi.class);
        fcmApi = retrofit.create(FcmApi.class);
    }
}

