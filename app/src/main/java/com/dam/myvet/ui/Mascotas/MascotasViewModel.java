package com.dam.myvet.ui.Mascotas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MascotasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MascotasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tú traes un perro, pero tú eres una sucia rata");
    }

    public LiveData<String> getText() {
        return mText;
    }
}