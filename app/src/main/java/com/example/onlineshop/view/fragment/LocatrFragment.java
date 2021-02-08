package com.example.onlineshop.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentLocatrBinding;
import com.example.onlineshop.viewmodel.LocatrViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocatrFragment extends Fragment {
    private FragmentLocatrBinding mBinding;
    private LocatrViewModel mViewModel;
    private GoogleMap mGoogleMap;
    private LatLng mLatLng;

    private static final int REQUEST_CODE_PERMISSION_LOCATION = 0;
    private static final String TAG = AddressBottomSheetDialogFragment.class.getSimpleName();


    public static LocatrFragment newInstance() {
        LocatrFragment fragment = new LocatrFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mViewModel = new ViewModelProvider(requireActivity()).get(LocatrViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_locatr,
                container,
                false);

        mBinding.setLocatrViewModel(mViewModel);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                updateUI();
                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mLatLng = latLng;
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + ":" + latLng.longitude);
                        mGoogleMap.clear();
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        mGoogleMap.addMarker(markerOptions);
                    }
                });
            }
        });

        initToolbar();

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults == null || grantResults.length == 0) {
                    return;
                }

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
                return;
        }
    }


    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.locatrToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }


    private void setObserver() {
        mViewModel.getLocationLiveData().observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                updateUI();
            }
        });


        mViewModel.getMyLocationClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isMyLocationClicked) {
                if (hasLocationAccess()) {
                    getCurrentLocation();
                } else {
                    requestLocationAccessPermission();
                }
            }
        });


        mViewModel.getConfirmationClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConfirmationClicked) {
                if (mLatLng != null) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            Geocoder geocoder;
                            List<Address> addresses;
                            geocoder = new Geocoder(getContext(), Locale.getDefault());
                            try {
                                addresses = geocoder.getFromLocation(mLatLng.latitude, mLatLng.longitude, 1);
                                String address = addresses.get(0).getAddressLine(0);
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String approximateAddress = address + city + state + country + " Lat: " + mLatLng.latitude + " Lng: " + mLatLng.longitude;
                                mViewModel.getAddressMutableLiveData().postValue(approximateAddress);
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage(), e);
                            }
                        }
                    };
                    thread.start();
                }
                AddressBottomSheetDialogFragment fragment = AddressBottomSheetDialogFragment.newInstance();
                fragment.show(getParentFragmentManager(), TAG);
            }
        });
    }


    private boolean hasLocationAccess() {
        return ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    private void requestLocationAccessPermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermissions(permissions, REQUEST_CODE_PERMISSION_LOCATION);
    }


    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        if (!hasLocationAccess()) {
            return;
        }
        mViewModel.getCurrentLocation();
    }

    private void updateUI() {
        Location location = mViewModel.getLocationLiveData().getValue();
        if (location == null || mGoogleMap == null)
            return;

        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions myMarkerOptions = new MarkerOptions()
                .position(myLatLng);

        mGoogleMap.clear();
        mGoogleMap.addMarker(myMarkerOptions);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(myLatLng));
    }
}