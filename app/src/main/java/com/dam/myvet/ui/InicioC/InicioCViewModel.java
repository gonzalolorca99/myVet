package com.dam.myvet.ui.InicioC;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InicioCViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InicioCViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}