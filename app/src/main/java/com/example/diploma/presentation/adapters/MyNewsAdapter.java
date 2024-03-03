package com.example.diploma.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.diploma.FillScreenImageActivity;
import com.example.diploma.R;
import com.example.diploma.data.retrofit.repositories.PostsRepository;
import com.example.diploma.domain.models.NewsModelResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNewsAdapter extends ArrayAdapter<NewsModelResponse> {
    private LayoutInflater inflater;
    private List<NewsModelResponse> listItemAd = new ArrayList<>();
    private Context context;

    public MyNewsAdapter(@NonNull Context context, int resource, List<NewsModelResponse> listItemAd, LayoutInflater inflater) {
        super(context, resource, listItemAd);
        this.inflater = inflater;
        this.listItemAd = listItemAd;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        MyNewsAdapter.ViewHolder viewHolder;
        NewsModelResponse listItemUser = listItemAd.get(position);
        convertView = inflater.inflate(R.layout.news_item_layout, null, false);
        viewHolder = new MyNewsAdapter.ViewHolder();
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
        if(!listItemUser.getPhotos().get(0).link.equals("")) {
            viewHolder.postImage.setMinimumHeight(750);
            Glide.with(context)
                    .load(listItemUser.getPhotos().get(0).link)
                    .into(viewHolder.postImage);
            viewHolder.postImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), FillScreenImageActivity.class);
                    intent.putExtra("url", listItemUser.getPhotos().get(0).link); // Здесь передайте ID вашего изображения
                    getContext().startActivity(intent);
                }

            });
        }

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showContextMenu(v, position);
                return true;
            }
        });
        return convertView;
    }
    private void showContextMenu(View v, int position) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Удалить");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 1) {
                    Call<String> call = new PostsRepository().deletePost(listItemAd.get(position).getId());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body()!=null){
                                Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
                                listItemAd.remove(position);
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("MyLog", t.toString());
                            listItemAd.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();

                        }
                    });
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }

    public void updateData(List<NewsModelResponse> newData) {
        listItemAd = newData;
        notifyDataSetChanged();
    }
    private class ViewHolder {
        TextView projectName, description;
        ImageView project_logo, postImage;

    }
}
