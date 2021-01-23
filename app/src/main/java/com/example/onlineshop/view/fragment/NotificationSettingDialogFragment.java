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
        mBinding.numberPicker.setMinValue(1);
        mBinding.numberPicker.setMaxValue(24);

        mBinding.setIsOnNotification(Preferences.getIsOnNotification(getContext()));
        if (Preferences.getSelectedButtonId(getContext()) != 0) {
            mBinding.radioGroup.check(Preferences.getSelectedButtonId(getContext()));
        }

        mBinding.btnSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.setIsOnNotification(getContext(), mBinding.switchOnNotification.isChecked());
                boolean isOn = PollService.isAlarmSet(getContext());
                if (mBinding.switchOnNotification.isChecked()) {
                    if (!isOn) {
                        checkTimeSelected(isOn);
                    } else {
                        if (mBinding.radioGroup.getCheckedRadioButtonId() == R.id.radio_btn_3_hours) {
                            Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_3_hours);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), isOn, 3);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 5);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 8);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 12);
                            PollService.scheduleAlarm(getContext(), !isOn);

                        } else if (mBinding.radioGroup.getCheckedRadioButtonId() == R.id.radio_btn_5_hours) {
                            Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_5_hours);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), isOn, 5);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 3);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 8);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 12);
                            PollService.scheduleAlarm(getContext(), !isOn);

                        } else if (mBinding.radioGroup.getCheckedRadioButtonId() == R.id.radio_btn_8_hours) {
                            Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_8_hours);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), isOn, 8);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 3);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 5);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 12);
                            PollService.scheduleAlarm(getContext(), !isOn);

                        } else if (mBinding.radioGroup.getCheckedRadioButtonId() == R.id.radio_btn_12_hours) {
                            Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_12_hours);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), isOn, 12);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 3);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 5);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 8);
                            PollService.scheduleAlarm(getContext(), !isOn);

                        } else {
                            Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_default);
                            PollService.scheduleAlarm(getContext(), isOn);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 12);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 3);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 5);
                            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 8);
                        }
                    }
                } else {
                    if (isOn) {
                        checkTimeSelected(isOn);
                    }
                }
                dismiss();
            }
        });

        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return mBinding.getRoot();
    }

    private void checkTimeSelected(boolean isOn) {
        if (mBinding.radioGroup.getCheckedRadioButtonId() == R.id.radio_btn_3_hours) {
            Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_3_hours);
            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 3);
        } else if (mBinding.radioGroup.getCheckedRadioButtonId() == R.id.radio_btn_5_hours) {
            Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_5_hours);
            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 5);
        } else if (mBinding.radioGroup.getCheckedRadioButtonId() == R.id.radio_btn_8_hours) {
            Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_8_hours);
            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 8);
        } else if (mBinding.radioGroup.getCheckedRadioButtonId() == R.id.radio_btn_12_hours) {
            Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_12_hours);
            PollService.scheduleAlarmBaseUserSelectedTime(getContext(), !isOn, 12);
        } else {
            Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_default);
            PollService.scheduleAlarm(getContext(), !isOn);
        }
    }
}