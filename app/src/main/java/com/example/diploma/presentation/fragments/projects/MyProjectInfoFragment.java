package com.example.diploma.presentation.fragments.projects;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.example.diploma.data.retrofit.ImageBBInstance;
import com.example.diploma.data.retrofit.repositories.ProjectsRepository;
import com.example.diploma.domain.models.NewsModelResponse;
import com.example.diploma.domain.models.PhotoModel;
import com.example.diploma.domain.models.ProjectModel;
import com.example.diploma.domain.models.UploadResponse;
import com.example.diploma.presentation.adapters.NewsAdapter;
import com.example.diploma.presentation.adapters.ProjectPhotoAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProjectInfoFragment extends Fragment {


    private ProjectModel project;
    private ProjectsRepository projectsRepository = new ProjectsRepository();
    private ProjectModel loadedProject;
    ProjectPhotoAdapter adapter;
    RecyclerView photosView;
    public MyProjectInfoFragment(ProjectModel project) {
        this.project = project;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadProjectData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_project_info, container, false);
    }

    private void loadProjectData() {
        Call<ProjectModel> call = projectsRepository.getProjectById(project.id);
        call.enqueue(new Callback<ProjectModel>() {
            @Override
            public void onResponse(Call<ProjectModel> call, Response<ProjectModel> response) {
                if(response.body() != null) {
                    loadedProject = response.body();
                    displayData();
                }
                else{
                    Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProjectModel> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
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
            Button addPostButton = getView().findViewById(R.id.add_post_button);
            Button addPhotoButton = getView().findViewById(R.id.add_photo_button);
            Button updateProjectButton = getView().findViewById(R.id.update_project_info);
            ListView newsListView = getView().findViewById(R.id.news_project_list);

            updateProjectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flFragment, new UpdateProjectInfoFragment(project.id))
                            .commit();
                }
            });

            addPostButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddPostFragment addPostFragment = new AddPostFragment(loadedProject);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flFragment, addPostFragment)
                            .commit();
                }
            });

            addPhotoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageChooser();
                }
            });
            ImageView imageView = getView().findViewById(R.id.imageView2);
            Glide.with(getContext())
                    .load(loadedProject.logo)
                    .placeholder(R.drawable.baseline_work_24)
                    .into(imageView);
            nameTV.setText(loadedProject.name);
            startTV.setText(loadedProject.startDate);
            endTV.setText(loadedProject.endDate);
            adressTV.setText(loadedProject.address);
            authorTV.setText("Вы");
            subscrTV.setText(String.valueOf(loadedProject.subscribers.size()));
            description.setText(loadedProject.description);
            List<NewsModelResponse> newsList = loadedProject.posts;
            photosView = getView().findViewById(R.id.images);
            adapter = new ProjectPhotoAdapter(getContext(), loadedProject.photos);
            photosView.setAdapter(adapter);
            photosView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            NewsAdapter newsAdapter = new NewsAdapter(getContext(), R.layout.news_item_layout, newsList, getLayoutInflater());
            newsListView.setAdapter(newsAdapter);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                Bitmap selectedImageBitmap = null;
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String encodeImage = encodeImageTo(selectedImageBitmap);
                Call<UploadResponse> call = ImageBBInstance.getApiService().uploadImage(ImageBBInstance.API_KEY, encodeImage);
                call.enqueue(new Callback<UploadResponse>() {
                    @Override
                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                        if (response.body() != null) {
                            String uploadResponse = response.body().getData().getUrl();
                            Log.d("MyLog", uploadResponse);
                            uploadPhotoToDB(uploadResponse);
                            project.photos.add(new PhotoModel(uploadResponse));
                            adapter = new ProjectPhotoAdapter(getContext(), project.photos);
                            photosView.setAdapter(adapter);
                        }
                        else Toast.makeText(getContext(), "Попробуйте еще раз", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<UploadResponse> call, Throwable t) {
                        Log.d("MyLog", t.toString());
                        Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    });

    private String encodeImageTo(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void uploadPhotoToDB(String url){
        Call<PhotoModel> call = new ProjectsRepository().addPhoto(project.id, new PhotoModel(url));
        call.enqueue(new Callback<PhotoModel>() {
            @Override
            public void onResponse(Call<PhotoModel> call, Response<PhotoModel> response) {
                if(response.body() != null){
                    Toast.makeText(getContext(), "Фото добавлено", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PhotoModel> call, Throwable t) {
                Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
            }
        });
    }
}