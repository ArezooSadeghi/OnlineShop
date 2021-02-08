package com.example.onlineshop.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAlertDialogBinding;
import com.example.onlineshop.viewmodel.SingleSharedDetailViewModel;
import com.google.android.material.snackbar.Snackbar;

public class AlertDialogFragment extends DialogFragment {
    private FragmentAlertDialogBinding mBinding;
    private SingleSharedDetailViewModel mViewModel;
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
        mViewModel = new ViewModelProvider(requireActivity()).get(SingleSharedDetailViewModel.class);
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

        setListener();

        return new AlertDialog.Builder(getContext())
                .setView(mBinding.getRoot())
                .create();
    }


    private void setListener() {
        mBinding.fabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.txtDiscountCode.getText().toString().isEmpty()) {
                    Snackbar.make(view, R.string.empty_discount_code, Snackbar.LENGTH_LONG).show();
                } else {
                    if (mBinding.txtDiscountCode.getText().toString().equals("code10")) {
                        Double finalPrice = Double.valueOf(mTotalPrice) - (Double.valueOf(mTotalPrice) * 0.1);
                        mBinding.setAmountPaidWithDiscount(getString(R.string.amount_paid_with_discount) + " " + finalPrice);
                    } else {
                        Snackbar.make(view, R.string.wrong_discount_code, Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });


        mBinding.btnFinalConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.getOkClickedSingleLiveEvent().setValue(true);
                dismiss();
            }
        });
    }
}