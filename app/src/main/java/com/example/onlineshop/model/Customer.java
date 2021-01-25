package com.example.onlineshop.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "customer_table")
public class Customer {

    @NotNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "email")
    private String mEmail;

    @ColumnInfo(name = "address")
    private List<String> mAddress;

    public Customer(String email) {
        mEmail = email;
        mAddress = new ArrayList<>();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public List<String> getAddress() {
        return mAddress;
    }

    public void setAddress(List<String> address) {
        mAddress = address;
    }
}
