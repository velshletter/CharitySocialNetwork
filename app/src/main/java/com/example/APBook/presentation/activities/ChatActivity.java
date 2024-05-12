package com.example.APBook.presentation.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.APBook.R;
import com.example.APBook.domain.models.firebase.Message;
import com.example.APBook.presentation.adapters.MessagesAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    String chatId;
    String photo;
    String fio;


    TextInputLayout messageInput;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView otherUsername;
    ImageView imageView;
    RecyclerView recyclerView;

    Map<String, Object> message;
    FirebaseFirestore db;
    CollectionReference messagesRef;
    String firebaseUid;
    MessagesAdapter adapter;
    List<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        sendMessageBtn = findViewById(R.id.message_send_btn);
        messageInput = findViewById(R.id.chat_message_input);
        recyclerView = findViewById(R.id.chat_recycler_view);


        firebaseUid = FirebaseAuth.getInstance().getUid();
        chatId = getIntent().getStringExtra("chatId");
        photo = getIntent().getStringExtra("user_photo");
        fio = getIntent().getStringExtra("user_name");

        imageView = findViewById(R.id.profile_pic_image_view);
        otherUsername = findViewById(R.id.other_username);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        otherUsername.setText(fio);
        Glide.with(getApplicationContext())
                .load(photo)
                .placeholder(R.drawable.baseline_person_24)
                .into(imageView);

        db = FirebaseFirestore.getInstance();
        DocumentReference chatRef = db.collection("chats").document(chatId);
        messagesRef = chatRef.collection("messages");

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageInput.getEditText().getText().toString().trim();
                if (!messageText.isEmpty()) {
                    message = new HashMap<>();
                    message.put("text", messageText);
                    message.put("timestamp", FieldValue.serverTimestamp());
                    message.put("senderId", FirebaseAuth.getInstance().getUid());
                    DocumentReference chatMessagesRef = messagesRef.document();

                    chatMessagesRef.set(message)
                            .addOnSuccessListener(aVoid -> {
                                messageInput.getEditText().setText("");
                                adapter.notifyDataSetChanged();
                                Log.d("MyLog", "message sent");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(ChatActivity.this, "Ошибка отправки сообщения", Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });


        adapter = new MessagesAdapter(ChatActivity.this, messageList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        messagesRef.orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        return;
                    }

                    for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                DocumentSnapshot document = dc.getDocument();
                                String messageId = document.getId();
                                String text = document.getString("text");
                                String sender = document.getString("senderId");
                                Timestamp timestamp = document.getTimestamp("timestamp");

                                Message message1 = new Message(text, timestamp, sender, messageId);
                                messageList.add(message1);
                                Log.d("MyLog", message1.getSenderId());
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });

    }
}