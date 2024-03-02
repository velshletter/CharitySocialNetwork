package com.example.diploma.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diploma.R;
import com.example.diploma.data.retrofit.repositories.ProjectsRepository;
import com.example.diploma.data.retrofit.repositories.UsersRepository;
import com.example.diploma.domain.models.UserModel;
import com.example.diploma.presentation.Global;
import com.example.diploma.presentation.adapters.MyProjectsAdapter;
import com.example.diploma.presentation.adapters.ProjectsAdapter;
import com.example.diploma.domain.models.ProjectModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProjectsFragment extends Fragment {


    private MyProjectsAdapter myProjectsAdapter;
    private List<ProjectModel> myProjectsList = new ArrayList<>();
    ListView listview;

    public MyProjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Call<UserModel> call = new UsersRepository().getUserById(Global.userId);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body() != null) {
                    myProjectsList = response.body().getProjects();
                    myProjectsAdapter = new MyProjectsAdapter(getContext(), R.layout.project_item, myProjectsList, getLayoutInflater(), getFragmentManager());
                    listview.setAdapter(myProjectsAdapter);
                }
                else {
                    Toast.makeText(getContext(), "Ошибка при загрузке ваших проектов", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getContext(), "NetworkError", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview = getView().findViewById(R.id.my_projects_list);
        myProjectsAdapter = new MyProjectsAdapter(getContext(), R.layout.project_item, myProjectsList, getLayoutInflater(), getFragmentManager());
        listview.setAdapter(myProjectsAdapter);
    }


}