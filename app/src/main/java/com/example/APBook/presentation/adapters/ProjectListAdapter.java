package com.example.APBook.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.ProjectsRepository;
import com.example.APBook.domain.models.projects.ProjectItemModel;
import com.example.APBook.domain.models.projects.ProjectModel;
import com.example.APBook.presentation.fragments.projects.ProjectInfoFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectListAdapter extends ArrayAdapter<ProjectItemModel> {
    private LayoutInflater inflater;
    private List<ProjectItemModel> listItemAd;
    private Context context;
    ProjectItemModel listItemUser;
    FragmentManager fragmentManager;

    public ProjectListAdapter(@NonNull Context context, int resource, List<ProjectItemModel> listItemAd, LayoutInflater inflater, FragmentManager fragmentManager) {
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



        ProjectListAdapter.ViewHolder viewHolder;
        listItemUser = listItemAd.get(position);
        convertView = inflater.inflate(R.layout.project_item, null, false);
        viewHolder = new ProjectListAdapter.ViewHolder();
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
                Call<ProjectModel> call = new ProjectsRepository().getProjectById(listItemAd.get(position).id);
                call.enqueue(new Callback<ProjectModel>() {
                    @Override
                    public void onResponse(Call<ProjectModel> call, Response<ProjectModel> response) {
                        if(response.body() != null){
                            ProjectInfoFragment projectInfoFragment = new ProjectInfoFragment(response.body());
                            fragmentManager.beginTransaction()
                                    .replace(R.id.flFragment, projectInfoFragment)
                                    .commit();
                        }
                        else Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ProjectModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

        return convertView;
    }

    private class ViewHolder {
        TextView projectName, description;
        ImageView logo;
    }
}
