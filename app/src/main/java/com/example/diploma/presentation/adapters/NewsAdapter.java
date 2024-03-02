package com.example.diploma.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.diploma.R;
import com.example.diploma.domain.models.NewsModelRequest;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsModelRequest> {


    private LayoutInflater inflater;
    private List<NewsModelRequest> listItemAd = new ArrayList<>();
    private Context context;

    public NewsAdapter(@NonNull Context context, int resource, List<NewsModelRequest> listItemAd, LayoutInflater inflater) {
        super(context, resource, listItemAd);
        this.inflater = inflater;
        this.listItemAd = listItemAd;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NewsAdapter.ViewHolder viewHolder;
        NewsModelRequest listItemUser = listItemAd.get(position);
        convertView = inflater.inflate(R.layout.news_item_layout, null, false);
        viewHolder = new NewsAdapter.ViewHolder();
        viewHolder.projectName = convertView.findViewById(R.id.project_name_textview);
        viewHolder.description = convertView.findViewById(R.id.description);
        viewHolder.projectName.setText("");
        viewHolder.description.setText(listItemUser.getText());
        return convertView;
    }

    private class ViewHolder {
        TextView projectName, description;
    }
}
