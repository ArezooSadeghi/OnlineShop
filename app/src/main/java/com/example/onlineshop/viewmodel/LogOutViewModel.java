package com.example.onlineshop.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.onlineshop.singleliveevent.SingleLiveEvent;

public class LogOutViewModel extends ViewModel {
    private SingleLiveEvent<Boolean> mLogOutClickedSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> mCommonClickedSingleLiveEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> getLogOutClickedSingleLiveEvent() {
        return mLogOutClickedSingleLiveEvent;
    }

    public void setLogOutClickedSingleLiveEvent() {
        mLogOutClickedSingleLiveEvent.setValue(true);
    }

    public SingleLiveEvent<Boolean> getCommonClickedSingleLiveEvent() {
        return mCommonClickedSingleLiveEvent;
    }

    public void setCommonClickedSingleLiveEvent() {
        mCommonClickedSingleLiveEvent.setValue(true);
    }
}
