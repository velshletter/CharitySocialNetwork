package com.example.diploma.presentation.fragments.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diploma.R;
import com.example.diploma.data.retrofit.repositories.UsersRepository;

public class RegistrationFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.registration_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText email = getView().findViewById(R.id.email_edit);
        EditText password = getView().findViewById(R.id.editTextTextPassword);
        EditText repeatPassword = getView().findViewById(R.id.editTextTextPassword2);
        Button button =  getView().findViewById(R.id.registration_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(password.getText()).equals(String.valueOf(repeatPassword.getText()))){
                    EnterDataFragment enterDataFragment = new EnterDataFragment(email.getText().toString(), password.getText().toString());
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.auth_fragment, enterDataFragment)
                            .commit();
                }
            }
        });


    }
}
