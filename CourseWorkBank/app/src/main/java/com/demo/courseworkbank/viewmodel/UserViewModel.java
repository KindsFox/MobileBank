package com.demo.courseworkbank.viewmodel;

import androidx.lifecycle.ViewModel;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.model.models.User;
import com.demo.courseworkbank.model.database.UserDao;

public class UserViewModel extends ViewModel {

    private final UserDao userDao;

    public UserViewModel() {
        userDao = App.getInstance().getDatabase().userDao();
    }

    public User getUser(String email){
        return userDao.get(email);
    }
}
