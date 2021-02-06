package com.example.onlineshop.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityMainBinding;
import com.example.onlineshop.eventbus.event.AddEvent;
import com.example.onlineshop.eventbus.event.AddToCartEvent;
import com.example.onlineshop.eventbus.event.DeleteEvent;
import com.example.onlineshop.eventbus.event.RemoveEvent;
import com.google.android.material.badge.BadgeDrawable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {
    private ActivityMainBinding mBinding;
    private int mNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBinding.navView, navController);
        navController.addOnDestinationChangedListener(this::onDestinationChanged);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onDestinationChanged(@NonNull NavController controller,
                                     @NonNull NavDestination destination,
                                     @Nullable Bundle arguments) {
        if (destination.getId() == R.id.loginFragment || destination.getId() == R.id.signupFragment
                || destination.getId() == R.id.addressFragment || destination.getId() == R.id.locatrFragment
                || destination.getId() == R.id.reviewFragment) {
            mBinding.navView.setVisibility(View.GONE);
        } else {
            mBinding.navView.setVisibility(View.VISIBLE);
        }
    }


    @Subscribe
    public void getNumber(AddToCartEvent addToCartEvent) {
        setBadge(++mNumber);
    }


    @Subscribe
    public void getNumberWithAddEvent(AddEvent addEvent) {
        setBadge(++mNumber);
    }


    @Subscribe
    public void getNumberWithRemoveEvent(RemoveEvent removeEvent) {
        setBadge(--mNumber);
    }


    @Subscribe
    public void getNumberWithDeleteEvent(DeleteEvent deleteEvent) {
        --mNumber;
        BadgeDrawable badgeDrawable = mBinding.navView.getOrCreateBadge(R.id.navigation_cart);
        badgeDrawable.setVisible(false);
    }


    public void setBadge(int number) {
        BadgeDrawable badgeDrawable = mBinding.navView.getOrCreateBadge(R.id.navigation_cart);
        badgeDrawable.setNumber(number);
        badgeDrawable.setVisible(true);
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}