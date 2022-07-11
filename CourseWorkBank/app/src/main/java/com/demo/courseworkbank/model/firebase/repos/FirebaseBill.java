package com.demo.courseworkbank.model.firebase.repos;

import static com.demo.courseworkbank.utils.Constants.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.model.models.Bill;
import com.demo.courseworkbank.model.database.BillDao;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class FirebaseBill {

    private final DatabaseReference reference;
    private final FirebaseUser user;
    private final BillDao billDao;

    public FirebaseBill(DatabaseReference reference, FirebaseUser user) {
        this.reference = reference;
        this.user = user;
        billDao = App.getInstance().getDatabase().billDao();
        subscribe();
    }

    public void sendBill(Bill bill) {
        reference.child(CHILD).child(user.getUid()).child(BILL).child(String.valueOf(bill.getId())).setValue(bill);
    }

    public void deleteBill(Bill bill) {
        reference.child(CHILD).child(user.getUid()).child(BILL).child(String.valueOf(bill.getId())).removeValue();
    }

    private void subscribe() {
        reference.child(CHILD).child(user.getUid()).child(BILL).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getValue(Bill.class) != null) {
                    if (billDao.getBillById(snapshot.getValue(Bill.class).getId()) == null) {
                        billDao.insert(snapshot.getValue(Bill.class));
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                billDao.update(snapshot.getValue(Bill.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                billDao.delete(snapshot.getValue(Bill.class));
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
