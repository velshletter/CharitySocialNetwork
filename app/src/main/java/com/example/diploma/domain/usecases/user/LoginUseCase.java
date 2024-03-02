package com.example.diploma.domain.usecases.user;

import android.util.Log;

import com.example.diploma.data.retrofit.repositories.UsersRepository;
import com.example.diploma.domain.models.UserModel;

import java.io.IOException;

import retrofit2.Call;

public class LoginUseCase {
    private UsersRepository usersRepository;

    public LoginUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserModel invoke(String email) {
        if (!email.equals("")) {
            try {
                Call<UserModel> call = usersRepository.login(email);
//                Log.d("MyLog", String.valueOf(call.execute().body()));
                return call.execute().body();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
