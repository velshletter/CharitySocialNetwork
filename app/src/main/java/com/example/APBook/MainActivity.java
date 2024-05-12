package com.example.APBook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.APBook.data.retrofit.repositories.ProjectsRepository;
import com.example.APBook.presentation.activities.AuthorizationActivity;
import com.example.APBook.presentation.activities.ChatActivity;
import com.example.APBook.presentation.fragments.FeedBackFragment;
import com.example.APBook.presentation.fragments.projects.AddProjectFragment;
import com.example.APBook.presentation.fragments.ChangePasswordFragment;
import com.example.APBook.presentation.fragments.ChangeUserDataFragment;
import com.example.APBook.presentation.fragments.projects.MyProjectsFragment;
import com.example.APBook.presentation.fragments.projects.MySubscriptionsFragment;
import com.example.APBook.presentation.fragments.mainFragments.NewsFragment;
import com.example.APBook.presentation.fragments.mainFragments.ProfileFragment;
import com.example.APBook.presentation.fragments.mainFragments.ProjectsFragment;
import com.example.APBook.presentation.fragments.mainFragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    private ProjectsRepository projectsRepository = new ProjectsRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseMessaging.getInstance().subscribeToTopic("chat").addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Log.d("MyLog", "successfb");
//                } else {
//                    Log.d("MyLog", "errorfb");
//                }
//            }
//        });



        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.projects);

    }

    NewsFragment newsFragment = new NewsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    ProjectsFragment projectsFragment = new ProjectsFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            getSupportFragmentManager()
                    .beginTransaction()
//                    .addToBackStack("NewsFragment")
                    .replace(R.id.flFragment, newsFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.person) {
            getSupportFragmentManager()
                    .beginTransaction()
//                    .addToBackStack("ProfileFragment")
                    .replace(R.id.flFragment, profileFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.settings) {
            getSupportFragmentManager()
                    .beginTransaction()
//                    .addToBackStack("SettingsFragment")
                    .replace(R.id.flFragment, settingsFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.projects) {
            getSupportFragmentManager()
                    .beginTransaction()
//                    .addToBackStack("ProjectsFragment")
                    .replace(R.id.flFragment, projectsFragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void onMySubscriptionsClick(View view) {
        MySubscriptionsFragment mySubscriptionsFragment = new MySubscriptionsFragment();
        getSupportFragmentManager().beginTransaction()
//                .addToBackStack("MySubscriptionsFragment")
                .replace(R.id.flFragment, mySubscriptionsFragment, "MySubscriptionsFragment")
                .commit();
    }

    public void onMyProjectsClick(View view) {
        MyProjectsFragment myProjectsFragment = new MyProjectsFragment();
        getSupportFragmentManager().beginTransaction()
//                .addToBackStack("MyProjectsFragment")
                .replace(R.id.flFragment, myProjectsFragment, "MyProjectsFragment")
                .commit();
    }

    public void onAddProjectClick(View view) {
        AddProjectFragment addProjectFragment = new AddProjectFragment();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("AddProjectFragment")
                .replace(R.id.flFragment, addProjectFragment, "AddProjectFragment")
                .commit();
    }

    public void onClickLogout(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("authorized", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear().apply();
        Global.is_logined = false;
        Intent intent = new Intent(MainActivity.this, AuthorizationActivity.class);
        finish();
        startActivity(intent);
    }

    public void onChangeUserData(View view) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("ChangeUserDataFragment")
                .replace(R.id.flFragment, new ChangeUserDataFragment(), "ChangeUserDataFragment")
                .commit();
    }

    public void onChangePassword(View view) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("ChangePasswordFragment")
                .replace(R.id.flFragment, new ChangePasswordFragment())
                .commit();
    }

    public void onClickSupport(View view) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("SupportFragment")
                .replace(R.id.flFragment, new FeedBackFragment())
                .commit();
    }

    public void onCalendarClick(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        this.finish();
        startActivity(intent);
    }
}