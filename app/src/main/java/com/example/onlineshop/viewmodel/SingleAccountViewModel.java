package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlineshop.singleliveevent.SingleLiveEvent;

public class SingleAccountViewModel extends AndroidViewModel {
    private SingleLiveEvent<Boolean> mSettingClickedSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> mReviewClickedSingleLiveEvent = new SingleLiveEvent<>();

    public SingleAccountViewModel(@NonNull Application application) {
        super(application);
    }

    public SingleLiveEvent<Boolean> getSettingClickedSingleLiveEvent() {
        return mSettingClickedSingleLiveEvent;
    }

    public void setSettingClickedSingleLiveEvent() {
        mSettingClickedSingleLiveEvent.setValue(true);
    }

    public SingleLiveEvent<Boolean> getReviewClickedSingleLiveEvent() {
        return mReviewClickedSingleLiveEvent;
    }

    public void setReviewClickedSingleLiveEvent() {
        mReviewClickedSingleLiveEvent.setValue(true);
    }
}
