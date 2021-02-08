package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAddressBottomSheetDialogBinding;
import com.example.onlineshop.viewmodel.LocatrViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddressBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private FragmentAddressBottomSheetDialogBinding mBinding;
    private LocatrViewModel mViewModel;


    public static AddressBottomSheetDialogFragment newInstance() {
        AddressBottomSheetDialogFragment fragment = new AddressBottomSheetDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(LocatrViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_address_bottom_sheet_dialog,
                container,
                false);

        mBinding.setLocatrViewModel(mViewModel);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
    }


    private void setObserver() {
        mViewModel.getAddressMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String approximateAddress) {
                mBinding.setApproximateAddress(approximateAddress);
            }
        });


        mViewModel.getCancelClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isCancelClicked) {
                dismiss();
            }
        });


        mViewModel.getSaveClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSaveClicked) {
                mViewModel.getFinalAddressMutableLiveData().setValue(mBinding.txtAddress.getText().toString());
                dismiss();
            }
        });
    }
}