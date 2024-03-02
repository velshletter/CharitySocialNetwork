package com.example.diploma.domain.usecases.user;

import com.example.diploma.domain.models.UserModel;
import com.example.diploma.data.retrofit.repositories.UsersRepository;

import java.io.IOException;

import retrofit2.Call;

public class RegisterUseCase {
    private UsersRepository usersRepository;

    public RegisterUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserModel invoke(UserModel user){
        try {
            Call<UserModel> call = usersRepository.register(user);
            return call.execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
