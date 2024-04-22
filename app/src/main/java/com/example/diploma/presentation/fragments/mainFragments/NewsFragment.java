package com.example.diploma.presentation.fragments.mainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.diploma.R;
import com.example.diploma.data.retrofit.repositories.PostsRepository;
import com.example.diploma.domain.models.NewsModelResponse;
import com.example.diploma.presentation.adapters.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    private NewsAdapter newsAdapter;
    private List<NewsModelResponse> newsList = new ArrayList<>();
    ListView newsListView;
    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPosts();
    }

    public void getPosts(){
        Call<List<NewsModelResponse>> call = new PostsRepository().getSubscrPosts();
        call.enqueue(new Callback<List<NewsModelResponse>>() {
            @Override
            public void onResponse(Call<List<NewsModelResponse>> call, Response<List<NewsModelResponse>> response) {
                if(response.body() == null){
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    newsList = response.body();
                    newsAdapter = new NewsAdapter(getContext(), R.layout.news_item_layout, newsList, getLayoutInflater());
                    newsListView.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<NewsModelResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsListView = getView().findViewById(R.id.news_list);
        newsAdapter = new NewsAdapter(getContext(), R.layout.news_item_layout, newsList, getLayoutInflater());
        newsListView.setAdapter(newsAdapter);
        SwipeRefreshLayout swipeRefreshLayout = getView().findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}