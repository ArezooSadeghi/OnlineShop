package com.example.onlineshop.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.onlineshop.model.Customer;

@Dao
public interface CustomerDao {

    @Insert
    void insert(Customer customer);
}
