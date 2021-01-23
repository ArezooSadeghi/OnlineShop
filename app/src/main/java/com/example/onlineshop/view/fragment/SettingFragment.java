package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private FragmentSettingBinding mBinding;

    private static final String TAG = SettingFragment.class.getSimpleName();


    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_setting,
                container,
                false);

        mBinding.btnSettingNotification.setOnClickListener(this);

        return mBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        NotificationSettingDialogFragment fragment = NotificationSettingDialogFragment.newInstance();
        fragment.show(getParentFragmentManager(), TAG);
    }
}