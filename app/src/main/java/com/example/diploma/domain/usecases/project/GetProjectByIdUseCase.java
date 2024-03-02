package com.example.diploma.domain.usecases.project;

import com.example.diploma.data.retrofit.repositories.ProjectsRepository;
import com.example.diploma.domain.models.ProjectModel;

import java.io.IOException;

public class GetProjectByIdUseCase {

    private ProjectsRepository projectsRepository;
    private ProjectModel project = new ProjectModel();


    public GetProjectByIdUseCase(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    public ProjectModel invoke(int id){
        try {
            project = projectsRepository.getProjectById(id).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return project;
    }
}
