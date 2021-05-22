package com.dam.myvet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MuestraCitaCActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private Button bteliminar;
    private String formatfec;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_citac);

        final TextView asunto = findViewById(R.id.asuntoCita);
        final TextView fechac = findViewById(R.id.fechaCita);
        final TextView mascota = findViewById(R.id.mascotaCita);
        bteliminar = findViewById(R.id.btEliminarCita);


        Bundle bundle = getIntent().getExtras();
        String fecha = bundle.getString("fecha");
        formatfec= fecha.split("")[0].split("/")[0]+"-"+fecha.split("")[0].split("/")[1]+"-"+fecha.split("")[0].split("/")[2] + " "+ fecha.split("")[1];
        db = FirebaseFirestore.getInstance();
        bteliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("citas").document(formatfec).delete();

            }
        });

        db.collection("citas")
                .whereEqualTo("fecha", fecha)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                asunto.setText(document.getData().get("asunto").toString());
                                mascota.setText(document.getData().get("mascota").toString());
                                fechac.setText(fecha);
                            }

                        }
                    }
                });
    }
}
