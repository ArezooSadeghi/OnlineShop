package com.example.onlineshop.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.onlineshop.singleliveevent.SingleLiveEvent;

public class SettingViewModel extends ViewModel {
    private SingleLiveEvent<Boolean> mSettingNotificationClickedSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> mLogOutClickedSingleLiveEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> getSettingNotificationClickedSingleLiveEvent() {
        return mSettingNotificationClickedSingleLiveEvent;
    }

    public void setSettingNotificationClickedSingleLiveEvent() {
        mSettingNotificationClickedSingleLiveEvent.setValue(true);
    }

    public SingleLiveEvent<Boolean> getLogOutClickedSingleLiveEvent() {
        return mLogOutClickedSingleLiveEvent;
    }

    public void setLogOutClickedSingleLiveEvent() {
        mLogOutClickedSingleLiveEvent.setValue(true);
    }
}
