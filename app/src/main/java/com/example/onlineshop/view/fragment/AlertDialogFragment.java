package com.example.onlineshop.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAlertDialogBinding;

public class AlertDialogFragment extends DialogFragment {
    private FragmentAlertDialogBinding mBinding;
    private String mTotalPrice;
    private static final String ARGS_TOTAL_PRICE = "totalPrice";


    public static AlertDialogFragment newInstance(String totalPrice) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_TOTAL_PRICE, totalPrice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTotalPrice = getArguments().getString(ARGS_TOTAL_PRICE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.fragment_alert_dialog,
                null,
                false);

        mBinding.setTotalAmountPaid(getString(R.string.total_amount_paid) + mTotalPrice);

        return new AlertDialog.Builder(getContext())
                .setView(mBinding.getRoot())
                .create();
    }
}