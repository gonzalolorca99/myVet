package com.dam.myvet.ui.Mascotas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dam.myvet.MuestraClienteActivity;
import com.dam.myvet.MuestraMascotaActivity;
import com.dam.myvet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MascotasFragment extends Fragment {

    ListView listView;
    ArrayList<String> listaMascotas;
    ArrayList<String> listaDueños;
    ArrayList<String> listaNombreDueños;
    View root;
    private FirebaseFirestore db;int i; int j;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_mascotas, container, false);

        listView = (ListView) root.findViewById(R.id.listaMascotas);
        listaMascotas = new ArrayList<String>();
        listaDueños = new ArrayList<String>();
        listaNombreDueños = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.fila_mascota,
                R.id.filamascota
        );

        listView.setAdapter(adapter);

        db.collection("mascotas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            j = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listaMascotas.add(document.getData().get("nombre").toString());
                                listaDueños.add(document.getData().get("dueño").toString());
                                adapter.add(document.getData().get("nombre").toString());
                                j++;
                                db.collection("clientes")
                                        .whereEqualTo("email",document.getData().get("dueño").toString())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Log.d("LISTADUEÑOS", listaDueños.get(i));
                                                        listaNombreDueños.add(document.getData().get("nombre").toString()+ " "+
                                                                document.getData().get("apellidos").toString()+" ("+
                                                                document.getData().get("dni").toString()+")");
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });

        /*for (i = 0; i <= j-1;i++){
            db.collection("clientes")
                    .whereEqualTo("email",listaDueños.get(i))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("LISTADUEÑOS", listaDueños.get(i));
                                    listaNombreDueños.add(document.getData().get("nombre").toString()+ " "+
                                            document.getData().get("apellidos").toString()+" ("+
                                            document.getData().get("dni").toString()+")");
                                }
                            }
                        }
                    });
        }*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db.collection("mascotas")
                        .whereEqualTo("nombre",listaMascotas.get(position))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Intent infoMascota = new Intent(getActivity(), MuestraMascotaActivity.class);
                                    infoMascota.putExtra("email", listaDueños.get(position));
                                    infoMascota.putExtra("nombre",listaMascotas.get(position));
                                    infoMascota.putExtra("nombreDueño",listaNombreDueños.get(position));
                                    startActivity(infoMascota);
                                }
                            }
                        });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}