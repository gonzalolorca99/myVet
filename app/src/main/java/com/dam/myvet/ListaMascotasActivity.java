package com.dam.myvet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListaMascotasActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> listaMascotas;
    ArrayList<String> listarazas;
    ArrayList<String> listaEdad;
    ArrayList<String> listaHist;
    private FirebaseFirestore db;
    String histvacio;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_mascotasc);


        listView = (ListView) findViewById(R.id.listaMascotasC);
        listaMascotas = new ArrayList<String>();
        listaEdad = new ArrayList<String>();
        listarazas = new ArrayList<String>();
        listaHist = new ArrayList<String>();

        db = FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        String dueño = bundle.getString("dueño");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.fila_mascota,
                R.id.filamascota
        );

        listView.setAdapter(adapter);

        db.collection("mascotas")
                .whereEqualTo("dueño", dueño)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listaMascotas.add(document.getData().get("nombre").toString());
                                listarazas.add(document.getData().get("raza").toString());
                                if (document.getData().get("historial") == null){
                                    histvacio = " ";
                                    listaHist.add(histvacio);
                                }
                                else{
                                    listaHist.add(document.getData().get("historial").toString());
                                }

                                listaEdad.add(document.getData().get("edad").toString());
                                adapter.add(document.getData().get("nombre").toString());

                            }
                        }
                    }
                });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent infoMascota = new Intent(ListaMascotasActivity.this, MuestraMascotaClienteActivity.class);
                infoMascota.putExtra("raza", listarazas.get(position));
                infoMascota.putExtra("nombre", listaMascotas.get(position));
                infoMascota.putExtra("edad", listaEdad.get(position));
                infoMascota.putExtra("hist", listaHist.get(position));
                startActivity(infoMascota);

            }
        });

    }
}
