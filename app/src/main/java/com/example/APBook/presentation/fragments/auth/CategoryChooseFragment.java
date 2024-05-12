package com.example.APBook.presentation.fragments.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.APBook.MainActivity;
import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.UsersRepository;
import com.example.APBook.domain.models.CategoryModel;
import com.example.APBook.domain.models.UpdateCategoryRequest;
import com.example.APBook.domain.models.UserModel;
import com.example.APBook.Global;
import com.example.APBook.presentation.adapters.CategoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryChooseFragment extends Fragment {

    private CategoriesAdapter categoriesAdapter;
    private List<CategoryModel> categoriesList = new ArrayList<>();
    private List<UpdateCategoryRequest> request = new ArrayList<>();
    private List<Integer> userCategories = new ArrayList<>();
    UsersRepository usersRepository = new UsersRepository();

    public CategoryChooseFragment(List<Integer> selectedCategories) {
        userCategories = selectedCategories;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoriesList.add(new CategoryModel(1, "Здравоохранение и ЗОЖ", false));
        categoriesList.add(new CategoryModel(2, "ЧС", false));
        categoriesList.add(new CategoryModel(3, "Дети и молодежь", false));
        categoriesList.add(new CategoryModel(4, "Ветераны и Историческая память", false));
        categoriesList.add(new CategoryModel(5, "Спорт и события", false));
        categoriesList.add(new CategoryModel(6, "Животные", false));
        categoriesList.add(new CategoryModel(7, "Старшое поколение ", false));
        categoriesList.add(new CategoryModel(8, "Люди с ОВЗ", false));
        categoriesList.add(new CategoryModel(9, "Экология", false));
        categoriesList.add(new CategoryModel(10, "Культура и искусство", false));
        categoriesList.add(new CategoryModel(11, "Поиск пропавших", false));
        categoriesList.add(new CategoryModel(12, "Образование", false));
        categoriesList.add(new CategoryModel(13, "Интеллектуальная помощь", false));
        categoriesList.add(new CategoryModel(14, "Другое", false));
        for (int i = 0; i < 14; i++) {
            CategoryModel category = categoriesList.get(i);
            if(userCategories.contains(category.getId())){
                category.setChecked(true);
                categoriesList.set(i, category);
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_choose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = getView().findViewById(R.id.save_category_button);

        ListView categoriesListView = getView().findViewById(R.id.categories_list);
        categoriesAdapter = new CategoriesAdapter(getContext(), R.layout.category_item, categoriesList, getLayoutInflater());
        categoriesListView.setAdapter(categoriesAdapter);
        categoriesListView.setClickable(true);
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MyLog", String.valueOf(position));
                CategoryModel categoryModel = categoriesList.get(position);
                categoryModel.setChecked(!categoryModel.getChecked());
                categoriesList.set(position, categoryModel);
                categoriesAdapter.notifyDataSetChanged();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 14; i++) {
                    if (categoriesList.get(i).getChecked()) {
                        UpdateCategoryRequest categoryRequest = new UpdateCategoryRequest(categoriesList.get(i).getId(), categoriesList.get(i).getName());
                        request.add(categoryRequest);
                    }
                }
                if (request.isEmpty()) {
                    Toast.makeText(getContext(), "Выберите категорию", Toast.LENGTH_SHORT).show();
//                    request.add(new UpdateCategoryRequest(14, "Другое"));
                }else {
                    Call<UserModel> call = usersRepository.updateCategories(Global.userId, request);
                    call.enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if(response.body() != null) {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                getActivity().finish();
                                startActivity(intent);
                                Log.d("MyLog", response.body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}