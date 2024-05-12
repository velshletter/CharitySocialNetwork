package com.example.APBook.presentation.fragments.mainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.ProjectsRepository;
import com.example.APBook.domain.models.projects.ProjectItemModel;
import com.example.APBook.presentation.adapters.ProjectListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProjectsFragment extends Fragment {

    private ProjectListAdapter projectsAdapter;
    private List<ProjectItemModel> projectsList = new ArrayList<>();
    private ProjectsRepository projectsRepository = new ProjectsRepository();
    private Call<List<ProjectItemModel>> call;
    ListView projectsListView;
    public ProjectsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        call = projectsRepository.getProjectList();
        call.enqueue(new Callback<List<ProjectItemModel>>() {
            @Override
            public void onResponse(Call<List<ProjectItemModel>> call, Response<List<ProjectItemModel>> response) {
                projectsList = response.body();
                projectsAdapter = new ProjectListAdapter(getContext(), R.layout.project_item, projectsList, getLayoutInflater(), getParentFragmentManager());
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
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        projectsListView = getView().findViewById(R.id.my_projects_list);
        projectsAdapter = new ProjectListAdapter(getContext(), R.layout.project_item, projectsList, getLayoutInflater(),getFragmentManager());
        projectsListView.setAdapter(projectsAdapter);
    }


}