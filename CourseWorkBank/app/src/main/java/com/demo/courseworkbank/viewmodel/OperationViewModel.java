package com.demo.courseworkbank.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.model.models.Operation;
import com.demo.courseworkbank.model.database.OperationDao;
import com.demo.courseworkbank.model.firebase.repos.FirebaseOperation;

import java.util.List;

public class OperationViewModel extends ViewModel {

    private final OperationDao operationDao;
    private final FirebaseOperation firebaseOperation;

    public OperationViewModel() {
        operationDao = App.getInstance().getDatabase().operationDao();
        firebaseOperation = App.getInstance().getFirebaseDatabase().firebaseOperation;
    }

    public LiveData<List<Operation>> getOperationsLiveDataByCardId(long cardId) {
        return operationDao.getOperationsLiveDataByCardId(cardId);
    }

    public void addOperation(Operation operation) {
        operation.setId(operationDao.insert(operation));
        firebaseOperation.sendOperation(operation);
    }
}
