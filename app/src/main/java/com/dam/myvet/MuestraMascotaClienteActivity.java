package com.dam.myvet;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MuestraMascotaClienteActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_mascotac);

        final TextView nombre = findViewById(R.id.nombrePerfil);
        final TextView raza = findViewById(R.id.razaPerfil);
        final TextView edad = findViewById(R.id.edadPerfil);
        final TextView historial = findViewById(R.id.historialc);
        Bundle bundle = getIntent().getExtras();
        String nombrem = bundle.getString("nombre");
        String razam = bundle.getString("raza");
        String edadm = bundle.getString("edad");
        String histm = bundle.getString("hist");

        nombre.setText(nombrem);
        raza.setText(razam);
        if(!histm.equals("")){
            historial.setText(histm);
        }
        edad.setText(edadm);

    }
}
