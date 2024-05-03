package com.example.APBook.presentation.fragments.projects;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.APBook.R;
import com.example.APBook.data.retrofit.ImageBBInstance;
import com.example.APBook.data.retrofit.repositories.ProjectsRepository;
import com.example.APBook.domain.models.CategoryModel;
import com.example.APBook.domain.models.projects.ProjectModel;
import com.example.APBook.domain.models.projects.ProjectModelAdd;
import com.example.APBook.domain.models.UploadResponse;
import com.example.APBook.presentation.Global;
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

public class UpdateProjectInfoFragment extends Fragment {

    final Calendar myCalendar = Calendar.getInstance();
    String myFormat = "YYYY-MM-dd";
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
    TextInputLayout uploadLogoEditText, nameEditText, descriptionEditText, dateStartEditText, dateEndEditText, adressEditText, categorySpinnerLayout;
    AutoCompleteTextView onlineSpinner, categorySpinner;

    ProjectModel projectModel;
    boolean isOnline;
    int categotyId = 0;

    int id;
    List<String> optionsOnline = new ArrayList<>();

    public UpdateProjectInfoFragment(int id) {
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Call<ProjectModel> call = new ProjectsRepository().getProjectById(id);
        call.enqueue(new Callback<ProjectModel>() {
            @Override
            public void onResponse(Call<ProjectModel> call, Response<ProjectModel> response) {
                if (response.body() != null) {
                    projectModel = response.body();
                    loadView();
                }
            }

            @Override
            public void onFailure(Call<ProjectModel> call, Throwable t) {

            }
        });
    }

    public void loadView() {
        nameEditText = getView().findViewById(R.id.name_edit);
        descriptionEditText = getView().findViewById(R.id.description_edit);
        dateStartEditText = getView().findViewById(R.id.date_start_edit);
        dateEndEditText = getView().findViewById(R.id.date_end_edit);
        adressEditText = getView().findViewById(R.id.adress_edit);
        uploadLogoEditText = getView().findViewById(R.id.upload_post_photo);

        nameEditText.getEditText().setText(projectModel.name);
        descriptionEditText.getEditText().setText(projectModel.description);
        dateStartEditText.getEditText().setText(projectModel.startDate);
        dateEndEditText.getEditText().setText(projectModel.endDate);
        adressEditText.getEditText().setText(projectModel.address);
        adressEditText.getEditText().setText(projectModel.address);
        uploadLogoEditText.getEditText().setText(projectModel.logo);

        categorySpinnerLayout = getView().findViewById(R.id.category_spinner_layout);
        categorySpinner = categorySpinnerLayout.findViewById(R.id.category_spinner);
        if (projectModel.isOnline) {
            optionsOnline.add("Да");
            optionsOnline.add("Нет");
        } else {
            optionsOnline.add("Нет");
            optionsOnline.add("Да");
        }
        onlineSpinner = getView().findViewById(R.id.online_spinner);
        ArrayAdapter<String> onlinerAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, optionsOnline);
        onlineSpinner.setAdapter(onlinerAdapter);
        onlineSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isOnline = (position == 0);
            }
        });
        DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                dateStartEditText.getEditText().setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                dateEndEditText.getEditText().setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        dateStartEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), dateStart, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Button button = getView().findViewById(R.id.create_post_button);
        ImageButton delButton = getView().findViewById(R.id.delete_project);
        categorySpinner = getView().findViewById(R.id.category_spinner);

        categorySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categotyId = position + 1;
                Log.d("MyLog", "selectedCategory");
            }
        });
        dateEndEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), dateEnd, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, getCategories());
        categorySpinner.setAdapter(adapter);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<String> call = new ProjectsRepository().deleteProject(projectModel.id);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.flFragment, new MyProjectsFragment()).commit();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.flFragment, new MyProjectsFragment()).commit();
                    }
                });
            }
        });

        uploadLogoEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getEditText().getText().toString();
                String description = descriptionEditText.getEditText().getText().toString();
                String startDate = dateStartEditText.getEditText().getText().toString();
                String endDate = dateEndEditText.getEditText().getText().toString();
                String address = adressEditText.getEditText().getText().toString();
                String logo = uploadLogoEditText.getEditText().getText().toString();
                ProjectModelAdd projectModelAdd = new ProjectModelAdd(name, projectModel.id, description, startDate, endDate,
                        isOnline, address, logo, new ArrayList<>(), new ArrayList<>(),
                        new ArrayList<>(), new CategoryModel(categotyId, "string"), Global.userId);
                Call<ProjectModel> call = new ProjectsRepository().updateProject(projectModel.id, projectModelAdd);
                call.enqueue(new Callback<ProjectModel>() {
                    @Override
                    public void onResponse(Call<ProjectModel> call, Response<ProjectModel> response) {
                        if (response.body() != null) {
                            projectModel.isOnline = projectModelAdd.isOnline;
                            projectModel.name = projectModelAdd.name;
                            projectModel.address = projectModelAdd.address;
                            projectModel.description = projectModelAdd.description;
                            projectModel.logo = projectModelAdd.logo;
                            projectModel.startDate = projectModelAdd.startDate;
                            projectModel.endDate = projectModelAdd.endDate;
                            projectModel.category = projectModelAdd.category;
                            MyProjectInfoFragment myProjectInfoFragment = new MyProjectInfoFragment(projectModel);
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.flFragment, myProjectInfoFragment)
                                    .commit();
                        } else {
                            Toast.makeText(getContext(), "Проверьте введенные данные", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProjectModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_project_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT);
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