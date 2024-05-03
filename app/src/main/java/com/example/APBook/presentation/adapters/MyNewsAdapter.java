package com.example.APBook.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.APBook.FillScreenImageActivity;
import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.PostsRepository;
import com.example.APBook.domain.models.PostResponseModel;
import com.example.APBook.presentation.Global;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNewsAdapter extends ArrayAdapter<PostResponseModel> {
    private LayoutInflater inflater;
    private List<PostResponseModel> listItemAd = new ArrayList<>();
    private Context context;
    ImageButton likeButton, commentsButton;

    public MyNewsAdapter(@NonNull Context context, int resource, List<PostResponseModel> listItemAd, LayoutInflater inflater) {
        super(context, resource, listItemAd);
        this.inflater = inflater;
        this.listItemAd = listItemAd;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        MyNewsAdapter.ViewHolder viewHolder;
        PostResponseModel listItemUser = listItemAd.get(position);
        convertView = inflater.inflate(R.layout.news_item_layout, null, false);
        viewHolder = new MyNewsAdapter.ViewHolder();
        viewHolder.projectName = convertView.findViewById(R.id.project_name_textview);
        viewHolder.description = convertView.findViewById(R.id.description);
        viewHolder.projectName.setText(listItemUser.getProject());
        viewHolder.description.setText(String.valueOf(listItemUser.getText()));
        viewHolder.project_logo = convertView.findViewById(R.id.projectImage);
        viewHolder.postImage = convertView.findViewById(R.id.post_image);
        if (listItemUser.getProjectLogo() != "string") {
            Glide.with(context)
                    .load(listItemUser.getProjectLogo())
                    .into(viewHolder.project_logo);
        }
        if (!listItemUser.getPhotos().get(0).link.equals("")) {
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

        likeButton = convertView.findViewById(R.id.like_image_button);
        commentsButton = convertView.findViewById(R.id.comments_image_button);

        if (listItemUser.getLikedUsers().contains(Global.userId)) {
            likeButton.setImageResource(R.drawable.like_filled);
        } else {
            likeButton.setImageResource(R.drawable.like_outlined);
        }
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listItemUser.getLikedUsers().contains(Global.userId)) {
                    likeButton.setImageResource(R.drawable.like_outlined);
                    listItemUser.getLikedUsers().remove(position);
                    notifyDataSetChanged();
                    Call<Void> call = new PostsRepository().unlikePost(listItemUser.getId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("MyLog", "unliked");

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("MyLog", t.toString());
                        }
                    });
                } else {
                    likeButton.setImageResource(R.drawable.like_filled);
                    Call<Void> call = new PostsRepository().likePost(listItemUser.getId());
                    listItemUser.getLikedUsers().add(Global.userId);
                    notifyDataSetChanged();
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("MyLog", "liked");

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("MyLog", t.toString());
                        }
                    });
                }

            }
        });


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
                            if (response.body() != null) {
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

    private class ViewHolder {
        TextView projectName, description;
        ImageView project_logo, postImage;

    }
}
