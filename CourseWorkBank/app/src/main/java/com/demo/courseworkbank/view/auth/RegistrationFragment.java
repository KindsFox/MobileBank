package com.demo.courseworkbank.view.auth;

import static com.demo.courseworkbank.utils.Constants.REGISTER_SUCCESS;

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
import com.demo.courseworkbank.viewmodel.AuthViewModel;


public class RegistrationFragment extends Fragment {

    private EditText etEmail;
    private EditText etFIO;
    private EditText etFirstPassword;
    private EditText etSecondPassword;
    private TextView tvRegister;

    private AuthViewModel viewModel;

    public RegistrationFragment() {
    }

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEmail = view.findViewById(R.id.et_email_register);
        etFirstPassword = view.findViewById(R.id.et_first_password_register);
        etSecondPassword = view.findViewById(R.id.et_second_password_register);
        tvRegister = view.findViewById(R.id.tv_register);
        etFIO = view.findViewById(R.id.et_fio_register);

        viewModel.getAuthResultLiveData().observe(this.getViewLifecycleOwner(), observer -> {
            String result = viewModel.getAuthResultLiveData().getValue();
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
            if (result.equals(REGISTER_SUCCESS)) {
                requireActivity().onBackPressed();
            }
        });

        tvRegister.setOnClickListener(v ->
                viewModel.register(etEmail.getText().toString(),
                        etFIO.getText().toString(),
                        etFirstPassword.getText().toString(),
                        etSecondPassword.getText().toString())
        );
    }
}