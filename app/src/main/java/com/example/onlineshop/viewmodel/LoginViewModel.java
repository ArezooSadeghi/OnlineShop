package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlineshop.model.Customer;
import com.example.onlineshop.repository.ProductRepository;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    private ProductRepository mRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance(getApplication());
    }

    public List<Customer> getCustomers() {
        return mRepository.getCustomers();
    }

    public boolean isValidCustomer(String email, List<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
