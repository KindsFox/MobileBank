package com.demo.courseworkbank.model.firebase.repos;

import static com.demo.courseworkbank.utils.Constants.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.model.models.Bill;
import com.demo.courseworkbank.model.models.Card;
import com.demo.courseworkbank.model.database.CardDao;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class FirebaseCard {

    private final DatabaseReference reference;
    private final FirebaseUser user;
    private final CardDao cardDao;

    public FirebaseCard(DatabaseReference reference, FirebaseUser user) {
        this.reference = reference;
        this.user = user;
        cardDao = App.getInstance().getDatabase().cardDao();
        subscribe();
    }

    public void sendCard(Card card) {
        reference.child(CHILD).child(user.getUid()).child(CARD).child(String.valueOf(card.getId())).setValue(card);
    }

    public void deleteCard(Card card) {
        reference.child(CHILD).child(user.getUid()).child(CARD).child(String.valueOf(card.getId())).removeValue();
        deleteBillByCardId(card.getId());
    }

    private void deleteBillByCardId(long cardId) {
        reference.child(CHILD).child(user.getUid()).child(BILL).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                for (DataSnapshot data : task.getResult().getChildren()) {
                    if (data.getValue(Bill.class).cardId == cardId) {
                        reference.child(CHILD).child(user.getUid()).child(BILL).child(data.getKey()).removeValue();
                    }
                }
            }
        });
    }

    private void subscribe() {
        reference.child(CHILD).child(user.getUid()).child(CARD).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getValue(Card.class) != null) {
                    if (cardDao.get(snapshot.getValue(Card.class).getId()) == null) {
                        cardDao.insert(snapshot.getValue(Card.class));
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                cardDao.update(snapshot.getValue(Card.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                cardDao.delete(snapshot.getValue(Card.class));
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
