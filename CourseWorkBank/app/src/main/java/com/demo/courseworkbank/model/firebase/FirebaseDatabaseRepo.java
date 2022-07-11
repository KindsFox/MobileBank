package com.demo.courseworkbank.model.firebase;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.model.firebase.repos.FirebaseBill;
import com.demo.courseworkbank.model.firebase.repos.FirebaseCard;
import com.demo.courseworkbank.model.firebase.repos.FirebaseOperation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseRepo {

    private final FirebaseDatabase database;
    private FirebaseUser user;

    public FirebaseBill firebaseBill;
    public FirebaseCard firebaseCard;
    public FirebaseOperation firebaseOperation;

    public FirebaseDatabaseRepo() {
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            init();
        }
    }

    public void init() {
        DatabaseReference reference = database.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseBill = new FirebaseBill(reference, user);
        firebaseCard = new FirebaseCard(reference, user);
        firebaseOperation = new FirebaseOperation(reference, user);
    }
}
