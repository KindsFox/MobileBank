package com.demo.courseworkbank.model.firebase;

import static com.demo.courseworkbank.utils.Constants.*;

import androidx.lifecycle.MutableLiveData;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.model.models.User;
import com.demo.courseworkbank.model.database.UserDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthRepo {

    private final FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private final UserDao userDao;

    private final MutableLiveData<String> authResultLiveData;

    public FirebaseAuthRepo() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        authResultLiveData = new MutableLiveData<>();
        userDao = App.getInstance().getDatabase().userDao();
        if (user != null) {
            authResultLiveData.postValue(LOGIN_SUCCESS);
        }
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                authResultLiveData.postValue(LOGIN_SUCCESS);
                App.getInstance().getFirebaseDatabase().init();
            } else {
                authResultLiveData.postValue(LOGIN_ERROR);
            }
            user = firebaseAuth.getCurrentUser();
        });
    }

    public void register(String email, String password, String FIO) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                authResultLiveData.postValue(REGISTER_SUCCESS);
                userDao.insert(new User(
                        firebaseAuth.getCurrentUser().getUid(),
                        email,
                        FIO,
                        password));
            } else {
                authResultLiveData.postValue(REGISTER_ERROR);
            }
        });
    }

    public void logOut() {
        firebaseAuth.signOut();
        user = firebaseAuth.getCurrentUser();
        authResultLiveData.postValue(LOGGED_OUT);
    }


    public FirebaseUser getUser() {
        return user;
    }

    public MutableLiveData<String> getAuthResultLiveData() {
        return authResultLiveData;
    }
}
