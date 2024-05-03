package com.example.APBook.presentation.fragments.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.APBook.MainActivity;
import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.UsersRepository;
import com.example.APBook.domain.models.UserModel;
import com.example.APBook.presentation.Global;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    UserModel user;
    Call<UserModel> call;
    String token = "";

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.getResult().isEmpty()) {
                    token = task.getResult();
                } else token = "";
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout emailEditText = getView().findViewById(R.id.email_edit_login);
        TextInputLayout passwordEditText = getView().findViewById(R.id.password_edit);
        Button loginButton = getView().findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<UserModel> call = new UsersRepository().login(emailEditText.getEditText().getText().toString(), token);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.body() == null) {
                            Toast.makeText(getContext(), "Пользователь не найден", Toast.LENGTH_SHORT).show();
                        } else {
                            user = response.body();
                            String pasw = passwordEditText.getEditText().getText().toString();
                            if (pasw.equals(user.getPassword())) {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                getActivity().finish();
                                startActivity(intent);
                                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("authorized", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean("is_logined", true).apply();
                                editor.putInt("user_id", user.getId()).apply();
                                Global.userId = user.getId();
                            } else
                                Toast.makeText(getContext(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.d("MyLog", t.getMessage());
                        Log.d("MyLog", call.toString());
                        Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

