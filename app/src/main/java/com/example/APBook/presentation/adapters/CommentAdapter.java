package com.example.APBook.presentation.adapters;

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
import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.UsersRepository;
import com.example.APBook.domain.models.Comment;
import com.example.APBook.domain.models.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private LayoutInflater inflater;
    private List<Comment> comments = new ArrayList<>();
    private Context context;


    public CommentAdapter(@NonNull Context context, int resource, List<Comment> comments, LayoutInflater inflater) {
        super(context, resource, comments);
        this.inflater = inflater;
        this.comments = comments;
        this.context = context;
    }

    private class ViewHolder {
        TextView authorName, commentTextView;
        ImageView authorImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        Comment comment = comments.get(position);
        convertView = inflater.inflate(R.layout.comment_item, null, false);

        viewHolder.authorImage = convertView.findViewById(R.id.author_image);
        viewHolder.authorName = convertView.findViewById(R.id.author_name);
        viewHolder.commentTextView = convertView.findViewById(R.id.comment_text);
        viewHolder.commentTextView.setText(comment.getText());

        Call<UserModel> call = new UsersRepository().getUserById(comment.getAuthor());
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body() != null) {
                    String s = response.body().getFirstName() + " " + response.body().getSecondName();
                    viewHolder.authorName.setText(s);
                    Glide.with(context)
                            .load(response.body().getPhoto())
                            .placeholder(R.drawable.baseline_person_24)
                            .into(viewHolder.authorImage);
                } else viewHolder.authorName.setText("Неудалось загрузить имя");
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                viewHolder.authorName.setText("Неудалось загрузить имя");
            }
        });

//        if (position == comments.size() - 1) {
//            notifyDataLoaded();
//        }
        return convertView;
    }

    private OnDataLoadedListener onDataLoadedListener;

    public void setOnDataLoadedListener(OnDataLoadedListener listener) {
        this.onDataLoadedListener = listener;
    }

    private void notifyDataLoaded() {
        if (onDataLoadedListener != null) {
            onDataLoadedListener.onDataLoaded();
        }
    }

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }

}
