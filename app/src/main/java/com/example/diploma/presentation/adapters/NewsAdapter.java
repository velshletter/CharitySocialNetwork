package com.example.diploma.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.diploma.R;
import com.example.diploma.domain.models.NewsModelResponse;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsModelResponse> {
    private LayoutInflater inflater;
    private List<NewsModelResponse> listItemAd = new ArrayList<>();
    private Context context;

    public NewsAdapter(@NonNull Context context, int resource, List<NewsModelResponse> listItemAd, LayoutInflater inflater) {
        super(context, resource, listItemAd);
        this.inflater = inflater;
        this.listItemAd = listItemAd;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NewsAdapter.ViewHolder viewHolder;
        NewsModelResponse listItemUser = listItemAd.get(position);
        convertView = inflater.inflate(R.layout.news_item_layout, null, false);
        viewHolder = new NewsAdapter.ViewHolder();
        viewHolder.projectName = convertView.findViewById(R.id.project_name_textview);
        viewHolder.description = convertView.findViewById(R.id.description);
        viewHolder.projectName.setText(listItemUser.getProject());
        viewHolder.description.setText(String.valueOf(listItemUser.getText()));
        viewHolder.project_logo = convertView.findViewById(R.id.projectImage);
        viewHolder.postImage = convertView.findViewById(R.id.post_image);
        if(listItemUser.getProjectLogo()!= "string") {
            Glide.with(context)
                    .load(listItemUser.getProjectLogo())
                    .placeholder(R.drawable.baseline_border_color_24)
                    .into(viewHolder.project_logo);
        }
        if(!listItemUser.getPhotos().isEmpty()) {
            Glide.with(context)
                    .load(listItemUser.getPhotos().get(0).link)
                    .placeholder(R.drawable.baseline_border_color_24)
                    .into(viewHolder.postImage);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView projectName, description;
        ImageView project_logo, postImage;

    }
}
