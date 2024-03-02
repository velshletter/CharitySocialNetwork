package com.example.diploma.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diploma.R;
import com.example.diploma.data.retrofit.repositories.UsersRepository;
import com.example.diploma.domain.models.UserModel;
import com.example.diploma.presentation.Global;
import com.example.diploma.presentation.fragments.mainFragments.SettingsFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {


    UserModel user;
    public ChangePasswordFragment() {
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
                    user = response.body();
                } else {
                    Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText passwordEdit1 = getView().findViewById(R.id.editTextTextPassword);
        EditText passwordEdit2 = getView().findViewById(R.id.editTextTextPassword2);
        Button saveButton = getView().findViewById(R.id.change_password_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(passwordEdit1.getText().toString().equals(passwordEdit2.getText().toString()) && !passwordEdit1.getText().toString().trim().isEmpty()) {
                    UserModel userModel = new UserModel(Global.userId, user.getAge(),
                            user.getEmail(), passwordEdit1.getText().toString(),
                            user.getFirstName(), user.getSecondName(), user.getPhoto());
                    Call<UserModel> call = new UsersRepository().updateUserData(userModel);
                    call.enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                            if (response.body() != null) {
                                SettingsFragment settingsFragment = new SettingsFragment();
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction().replace(R.id.flFragment, settingsFragment).commit();
                            } else {
                                Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {

                        }
                    });
                }else {
                    Toast.makeText(getContext(), "Проверьте введенные данные", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}