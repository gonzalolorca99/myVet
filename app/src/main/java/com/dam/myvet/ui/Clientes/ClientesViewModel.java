package com.dam.myvet.ui.Clientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClientesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClientesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aquí no le ofrecemos servicio a los tiesos, váyase señor Cuesta.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}