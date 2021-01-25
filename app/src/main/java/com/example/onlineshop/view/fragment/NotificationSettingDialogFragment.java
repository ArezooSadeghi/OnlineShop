package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

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
    private int mLastCheckedId = -1;
    private int[] mButtonIds = new int[]{R.id.radio_btn_default, R.id.radio_btn_3_hours, R.id.radio_btn_5_hours, R.id.radio_btn_8_hours, R.id.radio_btn_12_hours};
    private int[] mHours = new int[]{1, 3, 5, 8, 12};

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
        if (Preferences.getSelectedButtonId(getContext()) == 0) {
            mBinding.radioGroup.check(R.id.radio_btn_default);
        } else {
            mBinding.radioGroup.check(Preferences.getSelectedButtonId(getContext()));
        }


        mBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                mLastCheckedId = checkedId;
            }
        });

        mBinding.btnSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.setIsOnNotification(getContext(), mBinding.switchOnNotification.isChecked());
                boolean isOn = PollService.isAlarmSet(getContext());
                if (mBinding.switchOnNotification.isChecked()) {
                    Preferences.setIsOnNotification(getContext(), true);
                    if (!isOn) {
                        for (int i = 0; i < mButtonIds.length; i++) {
                            if (mLastCheckedId == mButtonIds[i]) {
                                setNewAlarm(mButtonIds[i], !isOn, mHours[i]);
                                Preferences.setSelectedButtonId(getContext(), mButtonIds[i]);
                                break;
                            } else {
                                setNewAlarm(R.id.radio_btn_default, !isOn, 1);
                            }
                        }
                    } else {
                        if (mLastCheckedId == -1) {
                            return;
                        } else {
                            for (int i = 0; i < mButtonIds.length; i++) {
                                if (mLastCheckedId == mButtonIds[i]) {
                                    setNewAlarm(mButtonIds[i], isOn, mHours[i]);
                                    break;
                                }
                            }

                            for (int i = 0; i < mButtonIds.length; i++) {
                                if (Preferences.getSelectedButtonId(getContext()) == mButtonIds[i]) {
                                    cancelOldAlarm(mButtonIds[i], !isOn, mHours[i]);
                                    break;
                                }
                            }
                            Preferences.setSelectedButtonId(getContext(), mLastCheckedId);
                        }
                    }
                } else {
                    Preferences.setIsOnNotification(getContext(), false);
                    if (isOn) {
                        for (int i = 0; i < mButtonIds.length; i++) {
                            if (Preferences.getSelectedButtonId(getContext()) == mButtonIds[i]) {
                                cancelOldAlarm(mButtonIds[i], !isOn, mHours[i]);
                                Preferences.setSelectedButtonId(getContext(), R.id.radio_btn_default);
                                break;
                            }
                        }
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

    private void setNewAlarm(int buttonId, boolean isOn, int hour) {
        PollService.scheduleAlarmBaseUserSelectedTime(getContext(), isOn, hour);
    }

    private void cancelOldAlarm(int buttonId, boolean isOn, int hour) {
        PollService.scheduleAlarmBaseUserSelectedTime(getContext(), isOn, hour);
    }
}