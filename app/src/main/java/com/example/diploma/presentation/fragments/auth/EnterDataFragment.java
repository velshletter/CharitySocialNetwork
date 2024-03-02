package com.example.diploma.presentation.fragments.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diploma.R;
import com.example.diploma.data.retrofit.ImageBBInstance;
import com.example.diploma.data.retrofit.repositories.UsersRepository;
import com.example.diploma.domain.models.UploadResponse;
import com.example.diploma.domain.models.UserModel;
import com.example.diploma.domain.usecases.user.RegisterUseCase;
import com.example.diploma.presentation.Global;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterDataFragment extends Fragment {

    String email="", password="";
    UsersRepository usersRepository = new UsersRepository();

    ImageView imageView;
    EditText name, secName, ageEditText, photo;

    public EnterDataFragment() {

    }

    public EnterDataFragment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.enter_your_data_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = getView().findViewById(R.id.imageView1);
        name = getView().findViewById(R.id.editTextText4);
        secName = getView().findViewById(R.id.editTextText3);
        ageEditText = getView().findViewById(R.id.editTextText2);
        photo = getView().findViewById(R.id.editTextText);
        Button saveButton = getView().findViewById(R.id.save_button);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = String.valueOf(name.getText()).trim();
                String secNameStr = String.valueOf(secName.getText()).trim();
                String ageStr = String.valueOf(ageEditText.getText()).trim();
                String photoStr = String.valueOf(photo.getText()).trim();

                if (!nameStr.isEmpty() && !secNameStr.isEmpty() && !ageStr.isEmpty()) {
                    UserModel userModel = new UserModel(Integer.parseInt(ageStr), email, password, nameStr, secNameStr, photoStr);
                    Call<UserModel> call = usersRepository.register(userModel);
                    call.enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                            if(response.body() != null) {
                                CategoryChooseFragment categoryChooseFragment = new CategoryChooseFragment(new ArrayList<>());
                                int userId = response.body().getId();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.auth_fragment, categoryChooseFragment).commit();
                                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("authorized", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean("is_logined", true).apply();
                                editor.putInt("user_id", userId).apply();
                                Global.userId = userId;
                            }
                            else {
                                Toast.makeText(getContext(), "Email уже существует", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Log.d("MyLog", "Пользователь уже существует");
                            Toast.makeText(getContext(), "Пользователь уже существует", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Проверьте введенные данные", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                Bitmap selectedImageBitmap = null;
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(selectedImageBitmap);
                String encodeImage = encodeImageTo(selectedImageBitmap);
                Toast.makeText(getContext(), "Подождите пока появится ссылка", Toast.LENGTH_LONG).show();
                Call<UploadResponse> call = ImageBBInstance.getApiService().uploadImage(ImageBBInstance.API_KEY, encodeImage);
                call.enqueue(new Callback<UploadResponse>() {
                    @Override
                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                        if (response.body() != null) {
                            UploadResponse uploadResponse = response.body();
                            Log.d("MyLog", uploadResponse.getData().getUrl());
                            photo.setText(uploadResponse.getData().getUrl());
                        }
                        else Toast.makeText(getContext(), "Попробуйте еще раз", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<UploadResponse> call, Throwable t) {
                        Log.d("MyLog", t.toString());
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    });

    private String encodeImageTo(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    private String encodeImageFrom(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;

    }
}