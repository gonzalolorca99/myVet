package com.dam.myvet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MuestraClienteActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_cliente);

        final TextView nombre = findViewById(R.id.nombrePerfil);
        final TextView dni = findViewById(R.id.dniPerfil);
        final TextView email = findViewById(R.id.emailPerfil);
        final TextView telefono = findViewById(R.id.infotelefono);
        final TextView domicilio = findViewById(R.id.domicilioPerfil);
        Bundle bundle = getIntent().getExtras();
        String emailCliente = bundle.getString("email");
        db = FirebaseFirestore.getInstance();

        db.collection("clientes")
                .whereEqualTo("email", emailCliente)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nombre.setText(document.getData().get("nombre").toString()+" "+
                                        document.getData().get("apellidos").toString());
                                dni.setText(document.getData().get("dni").toString());
                                email.setText(emailCliente);
                                telefono.setText(document.getData().get("telefono").toString());
                                domicilio.setText(document.getData().get("domicilio").toString());
                            }

                        }
                    }
                });
    }
}