package com.demo.courseworkbank.viewmodel;

import static com.demo.courseworkbank.utils.Constants.*;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.model.firebase.FirebaseAuthRepo;
import com.demo.courseworkbank.utils.AuthValidator;

public class AuthViewModel extends ViewModel {

    private final FirebaseAuthRepo firebaseAuth;

    public AuthViewModel() {
        firebaseAuth = App.getInstance().getFirebaseAuth();
    }


    public MutableLiveData<String> getAuthResultLiveData() {
        return firebaseAuth.getAuthResultLiveData();
    }

    public void login(String email, String password) {
        if (loginResultText(email, password).equals(LOGIN_SUCCESS)) {
            firebaseAuth.login(email, password);
        } else {
            getAuthResultLiveData().postValue(loginResultText(email, password));
        }
    }

    public void register(String email, String FIO, String firstPassword, String secondPassword) {
        if (registerResultText(email, FIO, firstPassword, secondPassword).equals(REGISTER_SUCCESS)) {
            firebaseAuth.register(email, firstPassword, FIO);
        } else {
            getAuthResultLiveData().postValue(registerResultText(email, FIO, firstPassword, secondPassword));
        }
    }

    private String registerResultText(String email, String FIO, String firstPassword, String secondPassword) {
        if (AuthValidator.isEmailEmpty(email)) {
            return "Почта пустая";
        } else if (!AuthValidator.isValidEmail(email)) {
            return "Некорректная почта";
        } else if (AuthValidator.isFIOEmpty(FIO)) {
            return "Поле ФИО не может быть пустым";
        } else if (!AuthValidator.isValidPassword(firstPassword)
                || !AuthValidator.isValidPassword(secondPassword)) {
            return "Пароль не может быть короче 6 символов";
        } else if (AuthValidator.isPasswordEmpty(firstPassword)
                || AuthValidator.isPasswordEmpty(secondPassword)) {
            return "Пароль не может быть пустым";
        } else if (!AuthValidator.arePasswordsEqual(firstPassword, secondPassword)) {
            return "Пароли не совпадают";
        } else {
            return REGISTER_SUCCESS;
        }
    }

    private String loginResultText(String email, String password) {
        if (AuthValidator.isEmailEmpty(email)) {
            return "Введите почту";
        } else if (!AuthValidator.isValidEmail(email)) {
            return "Некорректная почта";
        } else if (AuthValidator.isPasswordEmpty(password)) {
            return "Введите пароль";
        } else if (!AuthValidator.isValidPassword(password)) {
            return "Пароль не может быть короче 6 символов";
        } else {
            return LOGIN_SUCCESS;
        }
    }
}
