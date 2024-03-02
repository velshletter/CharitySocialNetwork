package com.example.diploma.domain.usecases.project;

import android.util.Log;

import com.example.diploma.data.retrofit.repositories.ProjectsRepository;
import com.example.diploma.domain.models.ProjectModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllProjectsUseCase {
    private ProjectsRepository projectsRepository;
    private List<ProjectModel> projects = new ArrayList<>();
    private Call<List<ProjectModel>> call;

    public GetAllProjectsUseCase(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    public List<ProjectModel> invoke() {
        //            projects = projectsRepository.getAllProjects().execute().body();
        call = projectsRepository.getAllProjects();
        call.enqueue(new Callback<List<ProjectModel>>() {
            @Override
            public void onResponse(Call<List<ProjectModel>> call, Response<List<ProjectModel>> response) {
                projects = response.body();
                Log.d("MyLog", "sffas");
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
        return projects;
    }
}
