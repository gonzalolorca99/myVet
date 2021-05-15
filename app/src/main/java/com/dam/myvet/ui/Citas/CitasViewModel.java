package com.dam.myvet.ui.Citas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CitasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CitasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aquí no le ofrecemos servicio a los tiesos, váyase señor Cuesta.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}