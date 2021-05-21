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

public class MuestraCitaActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String correo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_cita);

        final TextView asunto = findViewById(R.id.asuntoCita);
        final TextView cliente = findViewById(R.id.clienteCita);
        final TextView mascota = findViewById(R.id.mascotaCita);
        Bundle bundle = getIntent().getExtras();
        String fecha = bundle.getString("fecha");
        db = FirebaseFirestore.getInstance();

        db.collection("citas")
                .whereEqualTo("fecha", fecha)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                correo = document.getData().get("cliente").toString();
                                db.collection("clientes")
                                        .whereEqualTo("email", correo)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document2 : task.getResult()) {
                                                        cliente.setText(document2.getData().get("nombre").toString()+" "+
                                                                document2.getData().get("apellidos").toString() +
                                                                " ("+correo+")");
                                                    }

                                                }
                                            }
                                        });
                                asunto.setText(document.getData().get("asunto").toString());
                                mascota.setText(document.getData().get("mascota").toString());
                            }

                        }
                    }
                });
    }
}
