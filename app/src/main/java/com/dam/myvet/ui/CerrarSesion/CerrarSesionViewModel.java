package com.dam.myvet.ui.CerrarSesion;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dam.myvet.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class CerrarSesionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CerrarSesionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Somos unos mataos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}