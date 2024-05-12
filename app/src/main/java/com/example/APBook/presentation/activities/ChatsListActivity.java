package com.example.APBook.presentation.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.APBook.R;
import com.example.APBook.domain.models.firebase.ChatModel;
import com.example.APBook.presentation.adapters.ChatsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatsListActivity extends AppCompatActivity {


    FirebaseFirestore db;
    String userId;
    List<ChatModel> chatsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_list);

        userId = FirebaseAuth.getInstance().getUid();
        db = FirebaseFirestore.getInstance();

        ChatsAdapter adapter = new ChatsAdapter(getApplicationContext(), R.layout.item_chat, chatsList, getLayoutInflater(), this);
        ListView chatListView = findViewById(R.id.chats_list);
        chatListView.setAdapter(adapter);

        Query query1 = db.collection("chats").whereEqualTo("user1", userId);
        Query query2 = db.collection("chats").whereEqualTo("user2", userId);

        Task<QuerySnapshot> task1 = query1.get();
        Task<QuerySnapshot> task2 = query2.get();

        Tasks.whenAllSuccess(task1, task2).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> list) {

                for (Object obj : list) {
                    if (obj instanceof QuerySnapshot) {
                        QuerySnapshot snapshot = (QuerySnapshot) obj;
                        for (QueryDocumentSnapshot document : snapshot) {
                            String chatId = document.getId();
                            String userId1 = document.getString("user1");
                            String userId2 = document.getString("user2");
                            Timestamp createdAt = document.getTimestamp("created_at");
                            ChatModel chat = new ChatModel(chatId, userId1, userId2, createdAt);
                            chatsList.add(chat);

                            Log.d("MyLog", "userId1: " + userId1 + ", userId2: " + userId2 + ", created_at: " + String.valueOf(createdAt));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MyLog", "Error getting documents: ", e);
            }
        });
        FloatingActionButton addChatButton = findViewById(R.id.floatingActionButton);
        addChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyLog", "clickedbut");
            }
        });


//        Map<String, Object> chatData = new HashMap<>();
//        chatData.put("user1", " ");
//        chatData.put("user2", "userId2");
//        chatData.put("created_at", FieldValue.serverTimestamp());
//
//// Добавление документа чата в коллекцию "chats" с автоматически сгенерированным ID
//        db.collection("chats")
//                .add(chatData)
//                .addOnSuccessListener(documentReference -> {
//                    String chatId = documentReference.getId();
//                    // Обработка успешного добавления чата
//                })
//                .addOnFailureListener(e -> {
//                    // Обработка ошибки при добавлении чата
//                });
    }

}