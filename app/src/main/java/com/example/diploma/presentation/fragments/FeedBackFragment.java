package com.example.diploma.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diploma.R;
import com.example.diploma.data.retrofit.repositories.FeedBackRepository;
import com.example.diploma.domain.models.FeedBackModel;
import com.example.diploma.presentation.Global;
import com.example.diploma.presentation.fragments.mainFragments.SettingsFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackFragment extends Fragment {

    EditText editProblemMessageTextField;

    public FeedBackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_support, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editProblemMessageTextField = getView().findViewById(R.id.supportMessageTextField);
        Button sendButton = getView().findViewById(R.id.sendFeedbackButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = String.valueOf(editProblemMessageTextField.getText());
                if (!message.equals("")) {
                    Call<FeedBackModel> call = new FeedBackRepository().sendProblemMessage(message, Global.userId);
                    call.enqueue(new Callback<FeedBackModel>() {
                        @Override
                        public void onResponse(Call<FeedBackModel> call, Response<FeedBackModel> response) {
                            SettingsFragment settingsFragment = new SettingsFragment();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction().replace(R.id.flFragment, settingsFragment).commit();
                        }

                        @Override
                        public void onFailure(Call<FeedBackModel> call, Throwable t) {
                            Log.d("MyLog", t.getMessage());
                            Toast.makeText(getContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}