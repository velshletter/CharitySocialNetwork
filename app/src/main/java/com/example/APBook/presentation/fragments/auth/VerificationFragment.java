package com.example.APBook.presentation.fragments.auth;

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
import com.example.APBook.data.retrofit.repositories.UsersRepository;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationFragment extends Fragment {

    String email = "", password = "", code = "";

    public VerificationFragment(String email, String passw) {
        this.email = email;
        this.password = passw;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout emailEditText = getView().findViewById(R.id.code_textview);
        Button button = getView().findViewById(R.id.confirm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = String.valueOf(emailEditText.getEditText().getText());
                if (!code.isEmpty()) {
                    EnterDataFragment enterDataFragment = new EnterDataFragment(email, password, code);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.auth_fragment, enterDataFragment)
                            .commit();
                }
            }
        });


    }
}