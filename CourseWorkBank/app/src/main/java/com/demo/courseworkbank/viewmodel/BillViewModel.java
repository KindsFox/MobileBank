package com.demo.courseworkbank.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.model.models.Bill;
import com.demo.courseworkbank.model.database.BillDao;
import com.demo.courseworkbank.model.firebase.repos.FirebaseBill;

import java.util.List;

public class BillViewModel extends ViewModel {

    private final BillDao billDao;
    private final FirebaseBill firebaseBill;

    public BillViewModel() {
        billDao = App.getInstance().getDatabase().billDao();
        firebaseBill = App.getInstance().getFirebaseDatabase().firebaseBill;
    }

    public LiveData<List<Bill>> getBillsLiveDataByCardId(long cardId) {
        return billDao.getBillsLiveDataByCardId(cardId);
    }

    public Bill getBillByBillNumber(String billNumber) {
        return billDao.getBillByBillNumber(billNumber);
    }

    public long addBill(Bill bill) {
        bill.setId(billDao.insert(bill));
        firebaseBill.sendBill(bill);
        return bill.getId();
    }

    public void editBill(Bill bill) {
        billDao.update(bill);
        firebaseBill.sendBill(bill);
    }

    public void deleteBill(Bill bill) {
        billDao.delete(bill);
        firebaseBill.deleteBill(bill);
    }
}
