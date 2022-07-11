package com.demo.courseworkbank.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.demo.courseworkbank.model.models.Bill;
import com.demo.courseworkbank.model.models.Card;
import com.demo.courseworkbank.model.models.Operation;
import com.demo.courseworkbank.model.models.User;
import com.demo.courseworkbank.utils.Converters;

@Database(entities = {
        User.class,
        Card.class,
        Bill.class,
        Operation.class
}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract CardDao cardDao();

    public abstract BillDao billDao();

    public abstract OperationDao operationDao();

}
