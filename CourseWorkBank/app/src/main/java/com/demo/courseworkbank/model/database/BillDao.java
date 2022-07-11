package com.demo.courseworkbank.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.demo.courseworkbank.model.models.Bill;

import java.util.List;

@Dao
public interface BillDao {

    @Query("SELECT * FROM Bill WHERE cardId =:cardId")
    LiveData<List<Bill>> getBillsLiveDataByCardId(long cardId);

    @Query("SELECT * FROM Bill WHERE id=:id")
    Bill getBillById(long id);

    @Query("SELECT * FROM bill where billNumber=:billNumber")
    Bill getBillByBillNumber(String billNumber);

    @Insert
    long insert(Bill bill);

    @Update
    void update(Bill bill);

    @Delete
    void delete(Bill bill);
}
