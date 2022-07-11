package com.demo.courseworkbank;

import android.app.Application;

import androidx.room.Room;

import com.demo.courseworkbank.model.database.AppDatabase;
import com.demo.courseworkbank.model.firebase.FirebaseAuthRepo;
import com.demo.courseworkbank.model.firebase.FirebaseDatabaseRepo;

public class App extends Application {

    private static App instance;
    private AppDatabase database;
    private FirebaseAuthRepo firebaseAuth;
    private FirebaseDatabaseRepo firebaseDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        database = Room.databaseBuilder(this, AppDatabase.class, "bank.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        firebaseDatabase = new FirebaseDatabaseRepo();
        firebaseAuth = new FirebaseAuthRepo();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public FirebaseAuthRepo getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseDatabaseRepo getFirebaseDatabase() {
        return firebaseDatabase;
    }
}
