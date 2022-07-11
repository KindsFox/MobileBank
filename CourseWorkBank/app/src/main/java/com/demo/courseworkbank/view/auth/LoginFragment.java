package com.demo.courseworkbank.view.auth;

import static com.demo.courseworkbank.utils.Constants.LOGIN_SUCCESS;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.courseworkbank.R;
import com.demo.courseworkbank.utils.Navigator;
import com.demo.courseworkbank.view.card.CardsFragment;
import com.demo.courseworkbank.viewmodel.AuthViewModel;

public class LoginFragment extends Fragment {

    private EditText etEmail;
    private EditText etPassword;
    private TextView tvSignIn;
    private TextView tvRegister;


    private Navigator navigator;
    private AuthViewModel authViewModel;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = (Navigator) context;
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEmail = view.findViewById(R.id.et_login_email);
        etPassword = view.findViewById(R.id.et_login_password);
        tvSignIn = view.findViewById(R.id.tv_sign_in);
        tvRegister = view.findViewById(R.id.tv_login_register);

        authViewModel.getAuthResultLiveData().observe(this.getViewLifecycleOwner(), observer -> {
            String result = authViewModel.getAuthResultLiveData().getValue();
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
            if (result.equals(LOGIN_SUCCESS)) {
                navigator.navigateTo(CardsFragment.newInstance());
            }
        });

        tvSignIn.setOnClickListener(v ->
                authViewModel.login(
                        etEmail.getText().toString(),
                        etPassword.getText().toString()
                ));
        tvRegister.setOnClickListener(v ->
                navigator.navigateTo(RegistrationFragment.newInstance()));
    }
}