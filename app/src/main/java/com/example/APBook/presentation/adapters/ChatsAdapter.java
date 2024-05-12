package com.example.APBook.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.APBook.presentation.activities.ChatsListActivity;
import com.example.APBook.R;
import com.example.APBook.domain.models.firebase.ChatModel;
import com.example.APBook.presentation.activities.ChatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ChatsAdapter extends ArrayAdapter<ChatModel> {
    private LayoutInflater inflater;
    private List<ChatModel> chatModelList;
    private Context context;
    ChatsListActivity activity;
    ChatModel chat;
    String fio;
    String photo;


    public ChatsAdapter(@NonNull Context context, int resource, List<ChatModel> chatModelList, LayoutInflater inflater, ChatsListActivity activity) {
        super(context, resource, chatModelList);
        this.inflater = inflater;
        this.chatModelList = chatModelList;
        this.context = context;
        this.activity = activity;
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ChatsAdapter.ViewHolder viewHolder;
        chat = chatModelList.get(position);

        String secondUserId = FirebaseAuth.getInstance().getUid().equals(chat.getUid1()) ? chat.getUid2() : chat.getUid1();
        FirebaseFirestore.getInstance().collection("users")
                .document(secondUserId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot != null) {
                            photo = (String) documentSnapshot.get("photo");
                            fio = documentSnapshot.get("first_name") + " " + documentSnapshot.get("second_name");
                            notifyDataSetChanged();
                        }
                    }
                });
        convertView = inflater.inflate(R.layout.item_chat, null, false);
        viewHolder = new ChatsAdapter.ViewHolder();
        viewHolder.logo = convertView.findViewById(R.id.chat_user_photo);
        Glide.with(context)
                .load(photo)
                .placeholder(R.drawable.baseline_person_24)
                .into(viewHolder.logo);
        viewHolder.username = convertView.findViewById(R.id.user_chat_textview);

        viewHolder.username.setText(fio);
//        viewHolder.description.setText(listItemUser.description);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtra("chatId", chat.getChatId());
                intent.putExtra("user_photo", photo);
                intent.putExtra("user_name", fio);
                activity.startActivity(intent);
            }

        });

        return convertView;
    }

    private class ViewHolder {
        TextView username;
        FloatingActionButton addChatButton;
        ImageView logo;
    }
}
