package com.example.diploma.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.diploma.R;
import com.example.diploma.domain.models.CategoryModel;

import java.util.List;

public class CategoriesAdapter extends ArrayAdapter<CategoryModel> {
    private LayoutInflater inflater;
    private List<CategoryModel> listItemAd;
    private Context context;
    CategoryModel listItemUser;

    public CategoriesAdapter(@NonNull Context context, int resource, List<CategoryModel> listItemAd, LayoutInflater inflater) {
        super(context, resource, listItemAd);
        this.inflater = inflater;
        this.listItemAd = listItemAd;
        this.context = context;
    }

    @SuppressLint({"ViewHolder", "ResourceAsColor"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        listItemUser = listItemAd.get(position);
        convertView = inflater.inflate(R.layout.category_item, null, false);
        TextView textView = convertView.findViewById(R.id.txtName);
        textView.setText(listItemUser.getName());
        if(listItemUser.getChecked()){
            textView.setBackgroundColor(R.color.purple);
        }
        else textView.setBackgroundColor(R.color.transparent);

        return convertView;
    }
}
