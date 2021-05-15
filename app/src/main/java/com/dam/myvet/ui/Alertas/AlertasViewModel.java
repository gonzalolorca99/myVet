package com.dam.myvet.ui.Alertas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlertasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AlertasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aquí no le ofrecemos servicio a los tiesos, váyase señor Cuesta.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}