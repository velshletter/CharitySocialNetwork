package com.example.diploma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.diploma.data.retrofit.repositories.ProjectsRepository;
import com.example.diploma.presentation.Authorization;
import com.example.diploma.presentation.Global;
import com.example.diploma.presentation.fragments.projects.AddProjectFragment;
import com.example.diploma.presentation.fragments.ChangePasswordFragment;
import com.example.diploma.presentation.fragments.ChangeUserDataFragment;
import com.example.diploma.presentation.fragments.projects.MyProjectsFragment;
import com.example.diploma.presentation.fragments.projects.MySubscriptionsFragment;
import com.example.diploma.presentation.fragments.mainFragments.NewsFragment;
import com.example.diploma.presentation.fragments.mainFragments.ProfileFragment;
import com.example.diploma.presentation.fragments.mainFragments.ProjectsFragment;
import com.example.diploma.presentation.fragments.mainFragments.SettingsFragment;
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
        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.projects);

    }

    NewsFragment newsFragment = new NewsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    ProjectsFragment projectsFragment = new ProjectsFragment(projectsRepository);
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            getSupportFragmentManager()
                    .beginTransaction()
//                    .addToBackStack("NewsFragment")
                    .replace(R.id.flFragment, newsFragment )
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.person) {
            getSupportFragmentManager()
                    .beginTransaction()
//                    .addToBackStack("ProfileFragment")
                    .replace(R.id.flFragment, profileFragment )
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
                .replace(R.id.flFragment, mySubscriptionsFragment,"MySubscriptionsFragment")
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
        Intent intent = new Intent(MainActivity.this, Authorization.class);
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

//    public void onChangeCategoryViewClick(View view) {
//
//        UsersRepository usersRepository = new UsersRepository();
//        Call<UserModel> call = usersRepository.getUserById(Global.userId);
//        call.enqueue(new Callback<UserModel>() {
//            @Override
//            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//                List<Integer> selectedCategories = new ArrayList<>();
//                if(response.body() != null){
//                    selectedCategories = response.body().getSelectedCategories();
//                }
//                CategoryChooseFragment categoryChooseFragment = new CategoryChooseFragment(selectedCategories);
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.flFragment, categoryChooseFragment)
//                        .commit();
//            }
//
//            @Override
//            public void onFailure(Call<UserModel> call, Throwable t) {
//
//            }
//        });
//
//    }

}