package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.NotificationSettingDialogBinding;
import com.example.onlineshop.service.PollService;
import com.example.onlineshop.utilities.Preferences;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NotificationSettingDialogFragment extends BottomSheetDialogFragment {
    private NotificationSettingDialogBinding mBinding;

    public static NotificationSettingDialogFragment newInstance() {
        NotificationSettingDialogFragment fragment = new NotificationSettingDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.notification_setting_dialog,
                container,
                false);

        mBinding.setIsOnNotification(Preferences.getIsOnNotification(getContext()));

        mBinding.btnSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.setIsOnNotification(getContext(), mBinding.switchOnNotification.isChecked());
                boolean isOn = PollService.isAlarmSet(getContext());
                if (mBinding.switchOnNotification.isChecked()) {
                    if (!isOn) {
                        PollService.scheduleAlarm(getContext(), !isOn);
                    }
                } else {
                    if (isOn) {
                        PollService.scheduleAlarm(getContext(), !isOn);
                    }
                }
            }
        });

        return mBinding.getRoot();
    }
}