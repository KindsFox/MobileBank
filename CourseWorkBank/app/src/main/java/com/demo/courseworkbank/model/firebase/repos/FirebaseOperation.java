package com.demo.courseworkbank.model.firebase.repos;

import static com.demo.courseworkbank.utils.Constants.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.model.models.Card;
import com.demo.courseworkbank.model.models.Operation;
import com.demo.courseworkbank.model.database.OperationDao;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class FirebaseOperation {

    private final DatabaseReference reference;
    private final FirebaseUser user;
    private final OperationDao operationDao;

    public FirebaseOperation(DatabaseReference reference, FirebaseUser user) {
        this.reference = reference;
        this.user = user;
        operationDao = App.getInstance().getDatabase().operationDao();
        subscribe();
    }

    public void sendOperation(Operation operation) {
        reference.child(CHILD).child(user.getUid()).child(OPERATION).child(String.valueOf(operation.getId())).setValue(operation);
    }

    public void deleteOperation(Operation operation) {
        reference.child(CHILD).child(user.getUid()).child(OPERATION).child(String.valueOf(operation.getId())).removeValue();
    }

    private void subscribe() {
        reference.child(CHILD).child(user.getUid()).child(OPERATION).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getValue(Card.class) != null) {
                    if (operationDao.get(snapshot.getValue(Operation.class).getId()) == null) {
                        operationDao.insert(snapshot.getValue(Operation.class));
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                operationDao.update(snapshot.getValue(Operation.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                operationDao.delete(snapshot.getValue(Operation.class));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
