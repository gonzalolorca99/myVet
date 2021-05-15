package com.dam.myvet.ui.MascotasC;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MascotasCViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MascotasCViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tú traes un perro, pero tú eres una sucia rata");
    }

    public LiveData<String> getText() {
        return mText;
    }
}