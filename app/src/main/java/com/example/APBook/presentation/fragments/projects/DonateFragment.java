package com.example.APBook.presentation.fragments.projects;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.APBook.R;
import com.example.APBook.presentation.fragments.mainFragments.ProjectsFragment;
import com.example.APBook.presentation.fragments.mainFragments.SettingsFragment;

public class DonateFragment extends Fragment {

    public DonateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button sendButton = getView().findViewById(R.id.sendFeedbackButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.flFragment, new ProjectsFragment()).commit();
                Toast.makeText(getContext(), "Спасибо!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}