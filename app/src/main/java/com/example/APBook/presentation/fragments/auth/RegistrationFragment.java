package com.example.APBook.presentation.fragments.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.APBook.R;
import com.example.APBook.data.retrofit.repositories.UsersRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        TextInputLayout email = getView().findViewById(R.id.email_edit);
        TextInputLayout password = getView().findViewById(R.id.editTextTextPassword);
        TextInputLayout repeatPassword = getView().findViewById(R.id.editTextTextPassword2);
        Button button = getView().findViewById(R.id.registration_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(password.getEditText().getText()).equals(
                        String.valueOf(repeatPassword.getEditText().getText()))
                        && !String.valueOf(email.getEditText().getText()).equals("")) {

                    Call<Void> call = new UsersRepository().sendVerification(String.valueOf(email.getEditText().getText()));
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.body() != null) {
                                Log.d("MyLog", response.message());
                            } else Log.d("MyLog","nullasasasas");

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("MyLog", Objects.requireNonNull(t.getMessage()));
//                            Toast.makeText(getContext(), "Проверьте введенные данные", Toast.LENGTH_SHORT).show();
                        }
                    });
                    VerificationFragment verificationFragment = new VerificationFragment(email.getEditText().getText().toString(),
                            password.getEditText().getText().toString());
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.auth_fragment, verificationFragment)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Проверьте введенные данные", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
