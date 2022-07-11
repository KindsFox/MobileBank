package com.demo.courseworkbank.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.demo.courseworkbank.model.models.Operation;

import java.util.List;

@Dao
public interface OperationDao {

    @Query("SELECT * FROM Operation WHERE cardId =:cardId")
    LiveData<List<Operation>> getOperationsLiveDataByCardId(long cardId);

    @Query("SELECT * FROM Operation WHERE cardId=:cardId")
    List<Operation> getOperationsByCardId(long cardId);

    @Query("SELECT * FROM operation where id=:id")
    Operation get(long id);

    @Insert
    long insert(Operation operation);

    @Update
    void update(Operation operation);

    @Delete
    void delete(Operation operation);
}
