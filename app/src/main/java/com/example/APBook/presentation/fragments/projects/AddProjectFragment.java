package com.example.APBook.presentation.fragments.projects;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.APBook.R;
import com.example.APBook.data.retrofit.ImageBBInstance;
import com.example.APBook.data.retrofit.repositories.ProjectsRepository;
import com.example.APBook.domain.models.CategoryModel;
import com.example.APBook.domain.models.projects.ProjectModel;
import com.example.APBook.domain.models.projects.ProjectModelAdd;
import com.example.APBook.domain.models.UploadResponse;
import com.example.APBook.Global;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectFragment extends Fragment {

    final Calendar myCalendar = Calendar.getInstance();
    String myFormat = "YYYY-MM-dd";
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
    TextInputLayout uploadLogoEditText;
    int categotyId = 0;
    boolean isOnline = false;

    public AddProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_project, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout nameEditText = getView().findViewById(R.id.name_edit);
        TextInputLayout secnameEditText = getView().findViewById(R.id.description_edit);
        TextInputLayout dateStartEditText = getView().findViewById(R.id.date_start_edit);
        TextInputLayout dateEndEditText = getView().findViewById(R.id.date_end_edit);
        TextInputLayout adressEditText = getView().findViewById(R.id.adress_edit);
        uploadLogoEditText = getView().findViewById(R.id.upload_post_photo);
        Button button = getView().findViewById(R.id.create_post_button);
        TextInputLayout onlineSpinnerLayout = getView().findViewById(R.id.online_spinner_layout);
        AutoCompleteTextView onlineSpinner = onlineSpinnerLayout.findViewById(R.id.online_spinner);
        TextInputLayout categorySpinnerLayout = getView().findViewById(R.id.category_spinner_layout);
        AutoCompleteTextView categorySpinner = categorySpinnerLayout.findViewById(R.id.category_spinner);

        DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                dateStartEditText.getEditText().setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        dateStartEditText.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), dateStart, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                dateEndEditText.getEditText().setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        dateEndEditText.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), dateEnd, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        List<String> optionsOnline = new ArrayList<>();
        optionsOnline.add("Да");
        optionsOnline.add("Нет");
        ArrayAdapter<String> onlinerAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, optionsOnline);
        onlineSpinner.setAdapter(onlinerAdapter);

        onlineSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isOnline = (position == 0);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, getCategories());
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categotyId = position + 1;
                Log.d("MyLog", "selectedCategory");
            }
        });

        uploadLogoEditText.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getEditText().getText().toString();
                String description = secnameEditText.getEditText().getText().toString();
                String startDate = dateStartEditText.getEditText().getText().toString();
                String endDate = dateEndEditText.getEditText().getText().toString();
                String address = adressEditText.getEditText().getText().toString();
                String logo = uploadLogoEditText.getEditText().getText().toString();
                ProjectModelAdd project = new ProjectModelAdd(name, 0, description, startDate, endDate,
                        isOnline, address, logo, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new CategoryModel(categotyId, "string"), Global.userId);
                Call<ProjectModel> call = new ProjectsRepository().addProject(project);
                call.enqueue(new Callback<ProjectModel>() {
                    @Override
                    public void onResponse(Call<ProjectModel> call, Response<ProjectModel> response) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flFragment, new MyProjectsFragment())
                                .commit();
                    }

                    @Override
                    public void onFailure(Call<ProjectModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
                    }
                });
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
                String encodeImage = encodeImageTo(selectedImageBitmap);
                Call<UploadResponse> call = ImageBBInstance.getApiService().uploadImage(ImageBBInstance.API_KEY, encodeImage);
                call.enqueue(new Callback<UploadResponse>() {
                    @Override
                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                        UploadResponse uploadResponse = response.body();
                        Log.d("MyLog", uploadResponse.getData().getUrl());
                        uploadLogoEditText.getEditText().setText(uploadResponse.getData().getUrl());
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

    private void updateLabel() {

    }

    private List<String> getCategories() {
        List<String> optionsCategories = new ArrayList<>();
        optionsCategories.add("Здравоохранение и ЗОЖ");
        optionsCategories.add("ЧС");
        optionsCategories.add("Дети и молодежь");
        optionsCategories.add("Ветераны и Историческая память");
        optionsCategories.add("Спорт и события");
        optionsCategories.add("Животные");
        optionsCategories.add("Старшее поколение");
        optionsCategories.add("Люди с ОВЗ");
        optionsCategories.add("Экология");
        optionsCategories.add("Культура и искусство");
        optionsCategories.add("Поиск пропавших");
        optionsCategories.add("Образование");
        optionsCategories.add("Интеллектуальная помощь");
        optionsCategories.add("Другое");
        return optionsCategories;
    }
}