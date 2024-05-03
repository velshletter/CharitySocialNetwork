package com.example.APBook.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.APBook.FillScreenImageActivity;
import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.PostsRepository;
import com.example.APBook.domain.models.PostResponseModel;
import com.example.APBook.presentation.Global;
import com.example.APBook.presentation.fragments.projects.CommentsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsAdapter extends ArrayAdapter<PostResponseModel> {
    private LayoutInflater inflater;
    private List<PostResponseModel> listItemAd = new ArrayList<>();
    private Context context;
    FragmentManager fragmentManager;

    public NewsAdapter(@NonNull Context context, int resource, List<PostResponseModel> listItemAd, LayoutInflater inflater, FragmentManager fragmentManager) {
        super(context, resource, listItemAd);
        this.inflater = inflater;
        this.listItemAd = listItemAd;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        PostResponseModel post = listItemAd.get(position);
        convertView = inflater.inflate(R.layout.news_item_layout, null, false);
        viewHolder = new ViewHolder();
        viewHolder.projectName = convertView.findViewById(R.id.project_name_textview);
        viewHolder.description = convertView.findViewById(R.id.description);
        viewHolder.projectName.setText(post.getProject());
        viewHolder.description.setText(String.valueOf(post.getText()));
        viewHolder.project_logo = convertView.findViewById(R.id.projectImage);
        viewHolder.postImage = convertView.findViewById(R.id.post_image);
        viewHolder.likeButton = convertView.findViewById(R.id.like_image_button);
        viewHolder.commentsButton = convertView.findViewById(R.id.comments_image_button);

        if (post.getLikedUsers().contains(Global.userId)) {
            viewHolder.likeButton.setImageResource(R.drawable.like_filled);
        }
        else {
            viewHolder.likeButton.setImageResource(R.drawable.like_outlined);
        }

        viewHolder.commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentsFragment commentsFragment = new CommentsFragment(post);
                fragmentManager.beginTransaction()
                        .replace(R.id.flFragment, commentsFragment)
                        .commit();
            }
        });
        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post.getLikedUsers().contains(Global.userId)) {
                    viewHolder.likeButton.setImageResource(R.drawable.like_outlined);
                    post.getLikedUsers().remove(Integer.valueOf(Global.userId));
                    notifyDataSetChanged();
                    Call<Void> call = new PostsRepository().unlikePost(post.getId());
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
                    viewHolder.likeButton.setImageResource(R.drawable.like_filled);
                    Call<Void> call = new PostsRepository().likePost(post.getId());
                    post.getLikedUsers().add(Global.userId);
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


        if (post.getProjectLogo() != "string") {
            Glide.with(context)
                    .load(post.getProjectLogo())
                    .placeholder(R.mipmap.ic_launcher_foreground)
                    .into(viewHolder.project_logo);
        }
        if (!post.getPhotos().isEmpty()) {
            if (!post.getPhotos().get(0).link.equals("")) {
                viewHolder.postImage.setMinimumHeight(750);
                Glide.with(context)
                        .load(post.getPhotos().get(0).link)
                        .placeholder(R.drawable.layout_all_corners_background_color)
                        .into(viewHolder.postImage);
                viewHolder.postImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), FillScreenImageActivity.class);
                        intent.putExtra("url", post.getPhotos().get(0).link);
                        getContext().startActivity(intent);
                    }

                });
            }
        }
        return convertView;
    }

    private class ViewHolder {
        TextView projectName, description;
        ImageView project_logo, postImage;
        ImageButton likeButton, commentsButton;
    }
}
