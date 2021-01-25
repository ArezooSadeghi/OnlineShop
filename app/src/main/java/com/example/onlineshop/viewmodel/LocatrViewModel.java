package com.example.onlineshop.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.repository.ProductRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class LocatrViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private MutableLiveData<Location> mLocationMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mAddressMutableLiveData = new MutableLiveData<>();
    private LiveData<Integer> mStatusCodePostOrderLiveData;
    private MutableLiveData<List<String>> mAddressesMutableLiveData = new MutableLiveData<>();
    private List<String> mAddresses = new ArrayList<>();

    public LocatrViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mStatusCodePostOrderLiveData = mRepository.getStatusCodePostOrderMutableLiveData();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplication());
    }

    public LiveData<Location> getLocationLiveData() {
        return mLocationMutableLiveData;
    }

    public MutableLiveData<String> getAddressMutableLiveData() {
        return mAddressMutableLiveData;
    }

    public LiveData<Integer> getStatusCodePostOrderLiveData() {
        return mStatusCodePostOrderLiveData;
    }

    public MutableLiveData<List<String>> getAddressesMutableLiveData() {
        return mAddressesMutableLiveData;
    }

    public List<String> getAddresses() {
        return mAddresses;
    }

    public void setAddresses(List<String> addresses) {
        mAddresses = addresses;
    }

    public void postOrder(String email) {
        mRepository.postOrder(email);
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(0);
        locationRequest.setNumUpdates(1);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                mLocationMutableLiveData.setValue(locationResult.getLocations().get(0));
            }
        };

        mFusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}

