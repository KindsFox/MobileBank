package com.demo.courseworkbank.model.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.demo.courseworkbank.model.models.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE email =:email")
    User get(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
