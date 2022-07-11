package com.demo.courseworkbank.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.demo.courseworkbank.model.models.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM Card WHERE userUid =:userUid")
    LiveData<List<Card>> getCardsByUserId(String userUid);

    @Query("SELECT * FROM card where id=:id")
    Card get(long id);

    @Query("DELETE FROM Bill where cardId=:cardId")
    void deleteBillByCardId(long cardId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Card card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);
}
