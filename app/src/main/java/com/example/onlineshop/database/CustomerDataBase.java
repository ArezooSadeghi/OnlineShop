package com.example.onlineshop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.onlineshop.model.Customer;

@Database(entities = {Customer.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class CustomerDataBase extends RoomDatabase {
    private static CustomerDataBase sDataBase;
    private static final String DB_NAME = "customer.db";

    public synchronized static CustomerDataBase getInstance(Context context) {
        if (sDataBase == null) {
            sDataBase = Room.databaseBuilder(context.getApplicationContext(),
                    CustomerDataBase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sDataBase;
    }

    public abstract CustomerDao getCustomerDao();
}
