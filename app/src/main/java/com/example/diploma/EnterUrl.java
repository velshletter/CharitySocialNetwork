package com.example.diploma;

import static com.example.diploma.presentation.Global.is_logined;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diploma.data.retrofit.RetrofitInstance;
import com.example.diploma.data.retrofit.repositories.UsersRepository;
import com.example.diploma.presentation.Authorization;
import com.example.diploma.presentation.Global;
import com.example.diploma.presentation.fragments.auth.LoginFragment;

public class EnterUrl extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_url_activity);
        EditText ipEditText = findViewById(R.id.ip_edit);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ipEditText.getText().toString();
                if (url.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Введите ваш локальный айпи", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sharedpreferences = getSharedPreferences("authorized", Context.MODE_PRIVATE);
                    is_logined = sharedpreferences.getBoolean("is_logined", false);
                    Global.userId = sharedpreferences.getInt("user_id", 0);
                    RetrofitInstance.updateRetrofit(url);
                    if (!is_logined) {
                        Intent intent = new Intent(EnterUrl.this, Authorization.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(EnterUrl.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
