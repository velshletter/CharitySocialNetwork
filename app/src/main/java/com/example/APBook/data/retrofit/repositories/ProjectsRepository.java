package com.example.APBook.data.retrofit.repositories;

import com.example.APBook.data.retrofit.RetrofitInstance;
import com.example.APBook.domain.models.PhotoModel;
import com.example.APBook.domain.models.projects.ProjectItemModel;
import com.example.APBook.domain.models.projects.ProjectModel;
import com.example.APBook.domain.models.projects.ProjectModelAdd;
import com.example.APBook.Global;

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
