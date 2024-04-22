package com.example.diploma.presentation.fragments.projects;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.diploma.R;
import com.example.diploma.data.retrofit.repositories.ProjectsRepository;
import com.example.diploma.data.retrofit.repositories.UsersRepository;
import com.example.diploma.domain.models.NewsModelResponse;
import com.example.diploma.domain.models.ProjectModel;
import com.example.diploma.domain.models.UserModel;
import com.example.diploma.presentation.Global;
import com.example.diploma.presentation.adapters.NewsAdapter;
import com.example.diploma.presentation.adapters.ProjectPhotoAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectInfoFragment extends Fragment {

    private ProjectModel project;
    private ProjectsRepository projectsRepository = new ProjectsRepository();
    Boolean isFollowing = false;
    UserModel user;
    ProjectModel loadedProject;
    ProjectPhotoAdapter adapter;
    RecyclerView photosView;

    public ProjectInfoFragment(ProjectModel project) {
        this.project = project;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadProjectData();
    }

    private void loadProjectData() {
        Call<ProjectModel> call = projectsRepository.getProjectById(project.id);
        call.enqueue(new Callback<ProjectModel>() {
            @Override
            public void onResponse(Call<ProjectModel> call, Response<ProjectModel> response) {
                loadedProject = response.body();

                Call<UserModel> callMe = new UsersRepository().getUserById(Global.userId);
                callMe.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.body() != null) {
                            if (response.body().getSubscriptions().contains(loadedProject.id)) {
                                isFollowing = true;
                            }
                            Call<UserModel> callUser = new UsersRepository().getUserById(loadedProject.author);
                            callUser.enqueue(new Callback<UserModel>() {
                                @Override
                                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                    if (response.body() != null) {
                                        user = response.body();
                                        displayData();

                                    }
                                }

                                @Override
                                public void onFailure(Call<UserModel> call, Throwable t) {
                                    Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<ProjectModel> call, Throwable t) {
                Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayData() {
        View view = getView();
        if (view != null) {
            TextView nameTV = getView().findViewById(R.id.project_name_edit);
            TextView startTV = getView().findViewById(R.id.startTime);
            TextView endTV = getView().findViewById(R.id.endTime);
            TextView adressTV = getView().findViewById(R.id.adressTextView);
            TextView authorTV = getView().findViewById(R.id.authorTextView);
            TextView subscrTV = getView().findViewById(R.id.subscribers);
            TextView description = getView().findViewById(R.id.descriprion_profile);

            ImageView imageView = getView().findViewById(R.id.imageView2);
            Glide.with(getContext())
                    .load(loadedProject.logo)
                    .placeholder(R.drawable.baseline_work_24)
                    .into(imageView);
            nameTV.setText(loadedProject.name);
            startTV.setText(loadedProject.startDate);
            endTV.setText(loadedProject.endDate);
            adressTV.setText(loadedProject.address);
            authorTV.setText(user.getFirstName());
            subscrTV.setText(String.valueOf(loadedProject.subscribers.size()));
            description.setText(loadedProject.description);

            List<NewsModelResponse> newsList = loadedProject.posts;
            ListView newsListView = getView().findViewById(R.id.news_project_list);
            NewsAdapter newsAdapter = new NewsAdapter(getContext(), R.layout.news_item_layout, newsList, getLayoutInflater());
            newsListView.setAdapter(newsAdapter);

            photosView = getView().findViewById(R.id.images);
            adapter = new ProjectPhotoAdapter(getContext(), loadedProject.photos);
            photosView.setAdapter(adapter);
            photosView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            Button followButton = getView().findViewById(R.id.follow_button);
            if (isFollowing) {
                followButton.setText("Отписаться");
            } else {
                followButton.setText("Подписаться");
            }
            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFollowing) {
                        Call<String> call = projectsRepository.unsubscribe(project.id);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                followButton.setText("Подписаться");
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                followButton.setText("Подписаться");
                            }
                        });

                    } else {
                        Call<String> call = projectsRepository.subscribe(project.id);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                followButton.setText("Отписаться");
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                followButton.setText("Отписаться");
                            }
                        });


                    }
                    isFollowing = !isFollowing;
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}