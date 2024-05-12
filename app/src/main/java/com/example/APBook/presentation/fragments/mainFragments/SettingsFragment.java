package com.example.APBook.presentation.fragments.mainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.UsersRepository;
import com.example.APBook.domain.models.UserModel;
import com.example.APBook.Global;
import com.example.APBook.presentation.fragments.auth.CategoryChooseFragment;

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
                        getParentFragmentManager()
                                .beginTransaction()
                                .addToBackStack("CategoryFragment")
                                .replace(R.id.flFragment, categoryChooseFragment, "CategoryFragment")
                                .commit();
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}