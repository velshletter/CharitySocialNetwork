package com.example.diploma.presentation.adapters;

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
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.diploma.R;
import com.example.diploma.domain.models.CategoryModel;
import com.example.diploma.domain.models.ProjectModel;
import com.example.diploma.domain.models.ProjectModelAdd;
import com.example.diploma.presentation.fragments.ProjectInfoFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends ArrayAdapter<ProjectModel> {
    private LayoutInflater inflater;
    private List<ProjectModel> listItemAd;
    private Context context;
    ProjectModel listItemUser;
    FragmentManager fragmentManager;

    public ProjectsAdapter(@NonNull Context context, int resource, List<ProjectModel> listItemAd, LayoutInflater inflater, FragmentManager fragmentManager) {
        super(context, resource, listItemAd);
        this.inflater = inflater;
        this.listItemAd = listItemAd;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        ViewHolder viewHolder;
        listItemUser = listItemAd.get(position);
        convertView = inflater.inflate(R.layout.project_item, null, false);
        viewHolder = new ViewHolder();
        viewHolder.logo = convertView.findViewById(R.id.post_image);
        Glide.with(context)
                .load(listItemUser.logo)
                .placeholder(R.drawable.baseline_work_24)
                .into(viewHolder.logo);
        viewHolder.projectName = convertView.findViewById(R.id.project_name_textview);
        viewHolder.description = convertView.findViewById(R.id.description);
        viewHolder.projectName.setText(listItemUser.name);
        viewHolder.description.setText(listItemUser.description);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectModel project = listItemAd.get(position);
                ProjectInfoFragment projectInfoFragment = new ProjectInfoFragment(project);
                fragmentManager.beginTransaction()
                        .replace(R.id.flFragment, projectInfoFragment)
                        .commit();
            }

        });

        return convertView;
    }

    private class ViewHolder {
        TextView projectName, description;
        ImageView logo;
    }
}
