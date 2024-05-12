package com.example.APBook.presentation.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.APBook.R;
import com.example.APBook.domain.models.firebase.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private List<Message> messages;
    private Context context;
    private String currentUserId;

    public MessagesAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
        currentUserId = FirebaseAuth.getInstance().getUid();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MessageType.SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder,  @SuppressLint("RecyclerView") int position) {
        Message message = messages.get(position);
        holder.messageTextView.setText(message.getText());

//        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
//        holder.timestampTextView.setText(sdf.format(message.getTimestamp()));

//        if (message.getSenderId().equals(currentUserId)) {
//            holder.senderTextView.setVisibility(View.GONE);
//        } else {
//            holder.senderTextView.setVisibility(View.VISIBLE);
//            holder.senderTextView.setText(message.getSenderId());
//        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        return message.getSenderId().equals(currentUserId) ? MessageType.SENT : MessageType.RECEIVED;
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
//        TextView senderTextView;
        TextView messageTextView;
        TextView timestampTextView;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
//            senderTextView = itemView.findViewById(R.id.senderTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
//            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }

    private static class MessageType {
        static final int SENT = 1;
        static final int RECEIVED = 2;
    }
}
