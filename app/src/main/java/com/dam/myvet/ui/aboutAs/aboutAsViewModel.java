package com.dam.myvet.ui.aboutAs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class aboutAsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public aboutAsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Somos unos mataos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}