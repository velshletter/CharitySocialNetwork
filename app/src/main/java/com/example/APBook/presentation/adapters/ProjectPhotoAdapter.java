package com.example.APBook.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.APBook.presentation.activities.FillScreenImageActivity;
import com.example.APBook.R;
import com.example.APBook.domain.models.PhotoModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectPhotoAdapter extends RecyclerView.Adapter<ProjectPhotoAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<PhotoModel> listItemAd = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.project_imageview);
        }
    }

    public ProjectPhotoAdapter(Context context, List<PhotoModel> listItemAd) {
        this.listItemAd = listItemAd;
        this.context = context;
    }

    public void updateContext(Context newContext) {
        this.context = newContext;
    }

    @NonNull
    @Override
    public ProjectPhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.project_image_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectPhotoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //new DownloadImageTask(holder.imageView).execute(listItemAd.get(position).link);
        Glide.with(holder.imageView.getContext())
                .load(listItemAd.get(position).link)
                .placeholder(R.drawable.baseline_work_24)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FillScreenImageActivity.class);
                intent.putExtra("url", listItemAd.get(position).link);
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return listItemAd.size();
    }
}
