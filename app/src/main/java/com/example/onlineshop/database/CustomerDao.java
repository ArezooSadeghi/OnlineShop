package com.example.onlineshop.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.onlineshop.model.Customer;

import java.util.List;

@Dao
public interface CustomerDao {

    @Insert
    void insert(Customer customer);

    @Query("select * from customer_table")
    List<Customer> getCustomers();

    @Query("select * from customer_table where email=:email")
    Customer getCustomer(String email);

    @Update
    void updateCustomer(Customer customer);
}
