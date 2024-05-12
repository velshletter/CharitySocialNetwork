package com.example.APBook.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.example.APBook.R;
import com.example.APBook.data.retrofit.RetrofitInstance;
import com.example.APBook.domain.models.projects.SubscriberModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribersAdapter extends ArrayAdapter<SubscriberModel> {
    private LayoutInflater inflater;
    private List<SubscriberModel> subscriberModels;
    private Context context;
    SubscriberModel subscriberModel;
    String fio;
    String photo;
    int projectId;


    public SubscribersAdapter(@NonNull Context context, int resource, List<SubscriberModel> subscriberModels, LayoutInflater inflater, int projectId) {
        super(context, resource, subscriberModels);
        this.inflater = inflater;
        this.subscriberModels = subscriberModels;
        this.context = context;
        this.projectId = projectId;
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_subscriber, null, false);
        SubscribersAdapter.ViewHolder viewHolder;
        subscriberModel = subscriberModels.get(position);


        photo = subscriberModel.getPhoto();
        fio = subscriberModel.getFirstName() + " " + subscriberModel.getSecondName();
        notifyDataSetChanged();

        viewHolder = new SubscribersAdapter.ViewHolder();
        viewHolder.logo = convertView.findViewById(R.id.chat_user_photo);
        Glide.with(context)
                .load(photo)
                .placeholder(R.drawable.baseline_person_24)
                .into(viewHolder.logo);
        viewHolder.username = convertView.findViewById(R.id.user_chat_textview);

        viewHolder.username.setText(fio);
//        viewHolder.description.setText(listItemUser.description);

        viewHolder.imageButton = convertView.findViewById(R.id.star_button);
        if (subscriberModel.isStar()) {
            viewHolder.imageButton.setImageResource(R.drawable.baseline_star_24);
        } else {
            viewHolder.imageButton.setImageResource(R.drawable.baseline_star_border_24);
        }
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subscriberModel.isStar()) {
                    viewHolder.imageButton.setImageResource(R.drawable.baseline_star_border_24);
                    subscriberModel.setStar(false);
                    notifyDataSetChanged();
                    Call<Void> call = RetrofitInstance.projectApi.ignoreSub(projectId, subscriberModel.getId());
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
                    viewHolder.imageButton.setImageResource(R.drawable.baseline_star_24);
                    subscriberModel.setStar(true);
                    notifyDataSetChanged();
                    Call<Void> call = RetrofitInstance.projectApi.rateSub(projectId, subscriberModel.getId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("MyLog", "lijed");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("MyLog", t.toString());
                        }
                    });
                }
            }

        });

        return convertView;
    }

    private class ViewHolder {
        TextView username;
        ImageButton imageButton;
        ImageView logo;
    }
}

