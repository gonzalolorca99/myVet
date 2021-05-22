package com.dam.myvet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class ListaCitasCActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> listaCitas;
    private FirebaseFirestore db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_citas);

        listView = (ListView) findViewById(R.id.listaCitas);
        listaCitas = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.fila_cita,
                R.id.filacita
        );
        listView.setAdapter(adapter);
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        db.collection("citas")
                .whereEqualTo("cliente", email)
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
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
                                    Intent cita = new Intent(ListaCitasCActivity.this, MuestraCitaCActivity.class);
                                    cita.putExtra("fecha", listaCitas.get(position));
                                    startActivity(cita);
                                }
                            }
                        });
            }
        });
    }
}
