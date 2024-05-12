package com.example.APBook.presentation.fragments.projects;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.ProjectsRepository;
import com.example.APBook.data.retrofit.repositories.UsersRepository;
import com.example.APBook.domain.models.PostResponseModel;
import com.example.APBook.domain.models.UserModel;
import com.example.APBook.domain.models.firebase.ChatModel;
import com.example.APBook.domain.models.projects.ProjectModel;
import com.example.APBook.presentation.activities.ChatActivity;
import com.example.APBook.Global;
import com.example.APBook.presentation.adapters.NewsAdapter;
import com.example.APBook.presentation.adapters.ProjectPhotoAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    List<ChatModel> chatsList = new ArrayList<>();

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

            ImageView imageView = getView().findViewById(R.id.photo_profile_image_view);
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

            authorTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getChat();
                }
            });

            List<PostResponseModel> newsList = loadedProject.posts;
            ListView newsListView = getView().findViewById(R.id.news_project_list);
            NewsAdapter newsAdapter = new NewsAdapter(getContext(), R.layout.item_news, newsList, getLayoutInflater(), getFragmentManager());
            newsListView.setAdapter(newsAdapter);

            photosView = getView().findViewById(R.id.images);
            adapter = new ProjectPhotoAdapter(getContext(), loadedProject.photos);
            photosView.setAdapter(adapter);
            photosView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            Button donateButton = getView().findViewById(R.id.donate_button);
            donateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction().replace(R.id.flFragment, new DonateFragment())
                            .commit();
                }
            });

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

    public void getChat() {
        String yourId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("email", user.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            String secondUserId = documentSnapshot.getId();
                            if (yourId.equals(secondUserId)) {
                                return;
                            } else {
                                Query query1 = db.collection("chats").whereEqualTo("user1", yourId).whereEqualTo("user2", secondUserId);
                                Query query2 = db.collection("chats").whereEqualTo("user1", secondUserId).whereEqualTo("user2", yourId);

                                Task<QuerySnapshot> task1 = query1.get();
                                Task<QuerySnapshot> task2 = query2.get();

                                Task<List<Task<?>>> allTasks = Tasks.whenAllComplete(task1, task2);

                                allTasks.addOnSuccessListener(new OnSuccessListener<List<Task<?>>>() {
                                    @Override
                                    public void onSuccess(List<Task<?>> tasks) {
                                        boolean task1Success = task1.isSuccessful();
                                        boolean task2Success = task2.isSuccessful();
                                        if (task1Success && task1.getResult().getDocuments().size() != 0) {
                                            DocumentSnapshot document = task1.getResult().getDocuments().get(0);
                                            String chatId = document.getId();
                                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                                            intent.putExtra("chatId", chatId);
                                            intent.putExtra("user_photo", user.getPhoto());
                                            intent.putExtra("user_name", user.getFirstName() + " " + user.getSecondName());
                                            getActivity().startActivity(intent);
                                        } else {
                                            if (task2Success && task1.getResult().getDocuments().size() != 0) {
                                                DocumentSnapshot document = task2.getResult().getDocuments().get(0);
                                                String chatId = document.getId();
                                                Intent intent = new Intent(getActivity(), ChatActivity.class);
                                                intent.putExtra("chatId", chatId);
                                                intent.putExtra("user_photo", user.getPhoto());
                                                intent.putExtra("user_name", user.getFirstName() + " " + user.getSecondName());
                                                getActivity().startActivity(intent);
                                            } else {
                                                createChat(yourId, secondUserId);
                                            }
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d("TAG", "No documents found");
                        }
                    }
                });
    }


    public void createChat(String yourId, String secondUserId) {
        Map<String, Object> chatData = new HashMap<>();
        chatData.put("user1", yourId);
        chatData.put("user2", secondUserId);

        FirebaseFirestore.getInstance().collection("chats")
                .add(chatData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("chatId", documentReference.getId());
                        intent.putExtra("user_photo", user.getPhoto());
                        intent.putExtra("user_name", user.getFirstName() + " " + user.getSecondName());
                        getActivity().startActivity(intent);
                        Log.d("TAG", "New chat created with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Error creating chat", e);
                    }
                });
    }
}