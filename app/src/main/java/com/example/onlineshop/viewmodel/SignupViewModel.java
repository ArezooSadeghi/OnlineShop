package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.model.Customer;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.singleliveevent.SingleLiveEvent;

public class SignupViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<Integer> mStatusCodePostCustomerLiveData;
    private SingleLiveEvent<Boolean> mRegisterClickedSingleLiveEvent = new SingleLiveEvent<>();


    public SignupViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance(getApplication());
        mStatusCodePostCustomerLiveData = mRepository.getStatusCodePostCustomerMutableLiveData();
    }

    public LiveData<Integer> getStatusCodePostCustomerLiveData() {
        return mStatusCodePostCustomerLiveData;
    }

    public SingleLiveEvent<Boolean> getRegisterClickedSingleLiveEvent() {
        return mRegisterClickedSingleLiveEvent;
    }

    public void setRegisterClickedSingleLiveEvent() {
        mRegisterClickedSingleLiveEvent.setValue(true);
    }

    public void postCustomer(String email) {
        mRepository.postCustomer(email);
    }

    public void insert(Customer customer) {
        mRepository.insert(customer);
    }
}
