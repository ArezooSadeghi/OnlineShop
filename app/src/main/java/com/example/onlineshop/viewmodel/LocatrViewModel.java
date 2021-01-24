package com.example.onlineshop.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocatrViewModel extends AndroidViewModel {
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private MutableLiveData<Location> mLocationMutableLiveData = new MutableLiveData<>();

    public LocatrViewModel(@NonNull Application application) {
        super(application);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplication());
    }

    public LiveData<Location> getLocationLiveData() {
        return mLocationMutableLiveData;
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

