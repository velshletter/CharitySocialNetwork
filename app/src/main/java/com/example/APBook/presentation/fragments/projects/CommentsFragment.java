package com.example.APBook.presentation.fragments.projects;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.APBook.presentation.activities.FillScreenImageActivity;
import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.PostsRepository;
import com.example.APBook.domain.models.Comment;
import com.example.APBook.domain.models.PostResponseModel;
import com.example.APBook.Global;
import com.example.APBook.presentation.adapters.CommentAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsFragment extends Fragment {

    private List<Comment> commentList = new ArrayList<>();
    ListView commentListView;
    private CommentAdapter commentAdapter;
    private PostResponseModel post;

    TextView projectName, description;
    ImageView project_logo, postImage;
    ImageButton likeButton;

    public CommentsFragment(PostResponseModel post) {
        this.post = post;
        commentList = post.getComments();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        projectName = getView().findViewById(R.id.project_name_textview);
        description = getView().findViewById(R.id.description);
        projectName.setText(post.getProject());
        description.setText(post.getText());
        project_logo = getView().findViewById(R.id.projectImage);
        postImage = getView().findViewById(R.id.post_image);
        likeButton = getView().findViewById(R.id.like_image_button);

        if (post.getLikedUsers().contains(Global.userId)) {
            likeButton.setImageResource(R.drawable.like_filled);
        } else {
            likeButton.setImageResource(R.drawable.like_outlined);
        }

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post.getLikedUsers().contains(Global.userId)) {
                    likeButton.setImageResource(R.drawable.like_outlined);
                    post.getLikedUsers().remove(Global.userId);
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
                    likeButton.setImageResource(R.drawable.like_filled);
                    Call<Void> call = new PostsRepository().likePost(post.getId());
                    post.getLikedUsers().add(Global.userId);
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

        if (!post.getProjectLogo().equals("string")) {
            Glide.with(getContext())
                    .load(post.getProjectLogo())
                    .into(project_logo);
        }
        if (!post.getPhotos().isEmpty()) {
            if (!post.getPhotos().get(0).link.equals("")) {
                postImage.setMinimumHeight(750);
                Glide.with(getContext())
                        .load(post.getPhotos().get(0).link)
                        .into(postImage);
                postImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), FillScreenImageActivity.class);
                        intent.putExtra("url", post.getPhotos().get(0).link);
                        getContext().startActivity(intent);
                    }

                });
            }
        }

        ProgressBar progressBar = getView().findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);

        commentListView = getView().findViewById(R.id.comments_listview);
        commentAdapter = new CommentAdapter(getContext(), R.layout.item_comment, commentList, getLayoutInflater());
        commentListView.setAdapter(commentAdapter);
//        commentListView.setVisibility(View.INVISIBLE);
//        commentAdapter.setOnDataLoadedListener(new CommentAdapter.OnDataLoadedListener() {
//            @Override
//            public void onDataLoaded() {
//                progressBar.setVisibility(View.GONE);
//                commentListView.setVisibility(View.VISIBLE);
//            }
//        });

        ImageButton addComment = getView().findViewById(R.id.sendComment);
        TextInputLayout addCommentTextField = getView().findViewById(R.id.addComment);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = addCommentTextField.getEditText().getText().toString().trim();
                if (!commentText.isEmpty()) {
                    Call<Comment> call = new PostsRepository().addComment(commentText, post.getId());
                    call.enqueue(new Callback<Comment>() {
                        @Override
                        public void onResponse(Call<Comment> call, Response<Comment> response) {
                            if (response.isSuccessful()) {
                                Log.d("MyLog", response.body().toFormattedString());
                                Comment comment = response.body();
                                addCommentTextField.getEditText().setText("");
                                commentList.add(response.body());
                                commentAdapter.notifyDataSetChanged();
                                Log.d("MyLog", "comment add");
                            }
                        }
                        @Override
                        public void onFailure(Call<Comment> call, Throwable t) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                            Log.d("MyLog", "comment fail");
                        }
                    });
                }
            }
        });
    }
}