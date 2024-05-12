package com.example.APBook.presentation.fragments.projects;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.APBook.R;
import com.example.APBook.data.retrofit.RetrofitInstance;
import com.example.APBook.data.retrofit.interfaces.ProjectApi;
import com.example.APBook.data.retrofit.interfaces.UsersApi;
import com.example.APBook.domain.models.projects.SubscriberModel;
import com.example.APBook.presentation.adapters.ChatsAdapter;
import com.example.APBook.presentation.adapters.SubscribersAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribersFragment extends Fragment {


    int projectId;
    List<SubscriberModel> subscriberModels = new ArrayList<>();

    public SubscribersFragment(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Call<List<SubscriberModel>> call = RetrofitInstance.projectApi.getSubscribers(projectId);
        call.enqueue(new Callback<List<SubscriberModel>>() {
            @Override
            public void onResponse(Call<List<SubscriberModel>> call, Response<List<SubscriberModel>> response) {
                if (!response.body().isEmpty()) {
                    subscriberModels = response.body();
                    SubscribersAdapter adapter = new SubscribersAdapter(getContext(), R.layout.item_subscriber, subscriberModels, getLayoutInflater(), projectId);
                    ListView listView = getView().findViewById(R.id.chats_list);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<SubscriberModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscribers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}