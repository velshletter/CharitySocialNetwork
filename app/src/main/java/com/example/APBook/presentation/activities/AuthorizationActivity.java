package com.example.APBook.presentation.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.APBook.R;
import com.example.APBook.Global;
import com.example.APBook.presentation.fragments.auth.LoginFragment;
import com.example.APBook.presentation.fragments.auth.RegistrationFragment;
import com.example.APBook.presentation.fragments.auth.WelcomeFragment;

public class AuthorizationActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);
        if(Global.userId!=0){
            onLoginViewClick(this.getCurrentFocus());
        }
        else {
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.auth_fragment, welcomeFragment)
                    .commit();
        }
    }

    public void onRegistrationView(View view) {
        RegistrationFragment registrationFragment = new RegistrationFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.auth_fragment, registrationFragment)
                .commit();
    }

    public void onLoginViewClick(View view) {
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.auth_fragment, loginFragment)
                .commit();
    }
}