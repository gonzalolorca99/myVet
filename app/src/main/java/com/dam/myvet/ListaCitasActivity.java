package com.dam.myvet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListaCitasActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> listaCitas;
    private FirebaseFirestore db;
    int i;
    int j;
    String conversion;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_citas);

        listView = (ListView) findViewById(R.id.listaCitas);
        listaCitas = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        String fecha = bundle.getString("fecha");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.fila_cita,
                R.id.filacita
        );

        listView.setAdapter(adapter);
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        Log.d("EMAIL", email);
        Log.d("FECHA", fecha);

        if(email.equals("pabestnav@hotmail.com")){

            for ( i = 10; i<14; i++ ){
                for (j = 0; j < 60; j +=15){
                    conversion = String.valueOf(j);
                    if (j == 0){
                        conversion = "00";
                    }
                    Log.d("TURNOMANANA", fecha+" "+i+":"+conversion);
                    db.collection("citas")
                            .whereEqualTo("fecha", fecha+" "+i+":"+conversion)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            listaCitas.add(document.getData().get("fecha").toString());
                                            adapter.add(document.getData().get("fecha").toString());
                                        }
                                    }
                                }
                            });
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if(email.equals("gonestcas@gmail.com")){

            for ( i = 17; i<21; i++ ){
                for (j = 0; j < 60; j +=15){
                    conversion = String.valueOf(j);
                    if (j == 0){
                        conversion = "00";
                    }

                    Log.d("TURNOMANANA", fecha+" "+i+":"+conversion);
                    db.collection("citas")
                            .whereEqualTo("fecha", fecha+" "+i+":"+conversion)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("ONCOMPLETE", document.getData().get("fecha").toString());
                                            listaCitas.add(document.getData().get("fecha").toString());
                                            adapter.add(document.getData().get("fecha").toString());
                                        }
                                    }
                                }
                            });
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db.collection("citas")
                        .whereEqualTo("fecha",listaCitas.get(position))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Intent cita = new Intent(ListaCitasActivity.this, MuestraCitaActivity.class);
                                    cita.putExtra("fecha", listaCitas.get(position));
                                    startActivity(cita);
                                }
                            }
                        });
            }
        });
    }
}
