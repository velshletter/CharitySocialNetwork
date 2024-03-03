package com.example.diploma.data.retrofit.repositories;

import com.example.diploma.data.retrofit.RetrofitInstance;
import com.example.diploma.domain.models.NewsModelResponse;
import com.example.diploma.domain.models.PhotoModel;
import com.example.diploma.domain.models.ProjectItemModel;
import com.example.diploma.domain.models.ProjectModel;
import com.example.diploma.domain.models.ProjectModelAdd;
import com.example.diploma.presentation.Global;

import java.util.List;

import retrofit2.Call;

public class ProjectsRepository {

    public Call<List<ProjectModel>> getAllProjects(){
        return RetrofitInstance.projectApi.getAllProjects();
    }

    public Call<List<ProjectItemModel>> getProjectList(){
        return RetrofitInstance.projectApi.getProjectsByCategories(Global.userId);
    }

    public Call<ProjectModel> getProjectById(int id){
        return RetrofitInstance.projectApi.getProjectById(id);
    }

    public Call<ProjectModel> addProject(ProjectModelAdd project){
        return RetrofitInstance.projectApi.addProject(project, Global.userId);
    }

    public Call<ProjectModel> updateProject(int id, ProjectModelAdd projectModel){
        return RetrofitInstance.projectApi.updateProject(id, projectModel);
    }

    public Call<List<ProjectModel>> getMySubscriptions(){
        return RetrofitInstance.projectApi.getMySubscriptions(Global.userId);
    }

    public Call<String> subscribe(int projectId){
        return RetrofitInstance.projectApi.subscribe(Global.userId, projectId);
    }

    public Call<String> unsubscribe(int projectId){
        return RetrofitInstance.projectApi.unsubscribe(Global.userId, projectId);
    }

    public Call<String> deleteProject(int projectId){
        return RetrofitInstance.projectApi.deleteProject(projectId);
    }

    public Call<PhotoModel> addPhoto(int projectId, PhotoModel photoModel) {
        return RetrofitInstance.projectApi.addPhoto(projectId, photoModel);
    }
}
