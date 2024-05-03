package com.example.APBook.presentation.fragments.mainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.UsersRepository;
import com.example.APBook.domain.models.UserModel;
import com.example.APBook.presentation.Global;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    UserModel user;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Call<UserModel> call = new UsersRepository().getUserById(Global.userId);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.body() == null){
                    Toast.makeText(getContext(), "Не удалось получить данные", Toast.LENGTH_SHORT).show();
                }else {
                    user = response.body();
                    load();
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void load(){
        TextView name_surnameTv = getView().findViewById(R.id.name_user_tw);
        TextView ageTv = getView().findViewById(R.id.age_profile);

        name_surnameTv.setText(user.getFirstName() + " " + user.getSecondName());
        ageTv.setText(String.valueOf(user.getAge()));
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.getResult().isEmpty()) {
                    Log.d("MyLog", task.getResult());
                }
            }
        });
        ImageView userImage = getView().findViewById(R.id.photo_profile_image_view);
        Glide.with(getContext())
                .load(user.getPhoto())
                .centerCrop()
                .into(userImage);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}