package com.example.APBook.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.APBook.R;
import com.example.APBook.domain.models.CategoryModel;

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
        ImageView imageView = convertView.findViewById(R.id.categoryCheckBox);
        textView.setText(listItemUser.getName());
        if (listItemUser.getChecked()) {
//            textView.setBackgroundColor(androidx.appcompat.R.attr.colorPrimary);
            imageView.setImageResource(R.drawable.outline_check_box_24);
        } else {
//            textView.setBackgroundColor();
            imageView.setImageResource(R.drawable.baseline_check_box_outline_blank_24);
        }

        return convertView;
    }
}
