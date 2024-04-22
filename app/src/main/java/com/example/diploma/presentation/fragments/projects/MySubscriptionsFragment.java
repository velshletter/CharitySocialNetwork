package com.example.diploma.presentation.fragments.projects;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diploma.R;
import com.example.diploma.data.retrofit.repositories.ProjectsRepository;
import com.example.diploma.presentation.adapters.ProjectsAdapter;
import com.example.diploma.domain.models.ProjectModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySubscriptionsFragment extends Fragment {


    private ProjectsAdapter projectsAdapter;
    private List<ProjectModel> subscribedProjectsList =new ArrayList<>();
    private ProjectsRepository projectsRepository = new ProjectsRepository();
    private Call<List<ProjectModel>> call;
    ListView projectsListView;

    public MySubscriptionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        call = projectsRepository.getMySubscriptions();
        call.enqueue(new Callback<List<ProjectModel>>() {
            @Override
            public void onResponse(Call<List<ProjectModel>> call, Response<List<ProjectModel>> response) {
                subscribedProjectsList = response.body();
                projectsAdapter = new ProjectsAdapter(getContext(), R.layout.project_item, subscribedProjectsList, getLayoutInflater(), getFragmentManager());
                projectsListView.setAdapter(projectsAdapter);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_subscriptions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        projectsListView = getView().findViewById(R.id.subscriptions_list);
        projectsAdapter = new ProjectsAdapter(getContext(), R.layout.project_item, subscribedProjectsList, getLayoutInflater(),getFragmentManager());
        projectsListView.setAdapter(projectsAdapter);


    }
}