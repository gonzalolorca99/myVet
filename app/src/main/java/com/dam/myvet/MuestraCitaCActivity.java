package com.dam.myvet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private int mes;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_citac);

        final TextView asunto = findViewById(R.id.asuntoCita);
        final TextView fechac = findViewById(R.id.fechaCita);
        final TextView mascota = findViewById(R.id.mascotaCita);
        bteliminar = findViewById(R.id.btEliminarCita);


        Bundle bundle = getIntent().getExtras();
        String fecha = bundle.getString("fecha");
        if(Integer.parseInt(fecha.split(" ")[0].split("/")[1])< 10){
            mes = Integer.parseInt(fecha.split(" ")[0].split("/")[1]);
            formatfec= fecha.split(" ")[0].split("/")[0]+"-"+mes+"-"+fecha.split(" ")[0].split("/")[2] + " "+ fecha.split(" ")[1];

        }
        else{
            formatfec= fecha.split(" ")[0].split("/")[0]+"-"+fecha.split(" ")[0].split("/")[1]+"-"+fecha.split(" ")[0].split("/")[2] + " "+ fecha.split(" ")[1];
        }

        db = FirebaseFirestore.getInstance();
        bteliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("citas").document(formatfec).delete();
                Intent inicio = new Intent(MuestraCitaCActivity.this, MenuClienteActivity.class);
                SharedPreferences prefs = getSharedPreferences(
                        getString(R.string.prefs_file), Context.MODE_PRIVATE);
                String email = prefs.getString("email", null);
                inicio.putExtra("email", email);
                Toast.makeText(MuestraCitaCActivity.this, "Cita eliminada con éxito", Toast.LENGTH_SHORT).show();
                startActivity(inicio);

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
