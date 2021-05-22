package com.dam.myvet;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MuestraMascotaActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_mascota);

        final TextView nombre = findViewById(R.id.nombrePerfil);
        final TextView raza = findViewById(R.id.razaPerfil);
        final TextView edad = findViewById(R.id.edadPerfil);
        final TextView dueño = findViewById(R.id.dueñoPerfil);
        final TextView historial = findViewById(R.id.historial);
        Bundle bundle = getIntent().getExtras();
        String emailCliente = bundle.getString("email");
        String nombreMascota = bundle.getString("nombre");
        db = FirebaseFirestore.getInstance();

        db.collection("mascotas")
                .whereEqualTo("dueño", emailCliente)
                .whereEqualTo("nombre", nombreMascota)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nombre.setText(document.getData().get("nombre").toString());
                                raza.setText(document.getData().get("raza").toString());
                                dueño.setText(bundle.getString("nombreDueño"));
                                edad.setText(document.getData().get("edad").toString());
                                historial.setText(document.getData().get("historial").toString());
                            }

                        }
                    }
                });
    }
}
