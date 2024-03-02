package com.example.diploma.presentation.fragments.mainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diploma.R;
import com.example.diploma.data.retrofit.repositories.UsersRepository;
import com.example.diploma.domain.models.UserModel;
import com.example.diploma.presentation.Global;
import com.example.diploma.presentation.fragments.auth.CategoryChooseFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConstraintLayout button_categories = getView().findViewById(R.id.button_categories);
        button_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersRepository usersRepository = new UsersRepository();
                Call<UserModel> call = usersRepository.getUserById(Global.userId);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        List<Integer> selectedCategories = new ArrayList<>();
                        if(response.body() != null){
                            selectedCategories = response.body().getSelectedCategories();
                        }
                        CategoryChooseFragment categoryChooseFragment = new CategoryChooseFragment(selectedCategories);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flFragment, categoryChooseFragment)
                                .commit();
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {

                    }
                });
            }
        });
    }
}