package com.dam.myvet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("correo");
        Log.d("Estamos en registro:", email);
    }
}