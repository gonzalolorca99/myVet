package com.dam.myvet.ui.Clientes;

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
import androidx.recyclerview.widget.RecyclerView;

import com.dam.myvet.LoginActivity;
import com.dam.myvet.MenuClienteActivity;
import com.dam.myvet.ModificaActivity;
import com.dam.myvet.MuestraClienteActivity;
import com.dam.myvet.R;
import com.dam.myvet.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ClientesFragment extends Fragment {

    ListView listView;
    ArrayList<String> listaClientes;
    View root;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_clientes, container, false);

        listView = (ListView) root.findViewById(R.id.listaClientes);
        listaClientes = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.fila_cliente,
                R.id.filanombre
        );

        listView.setAdapter(adapter);

        db.collection("clientes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listaClientes.add(document.getData().get("email").toString());
                                adapter.add(document.getData().get("nombre").toString()+ " "+
                                        document.getData().get("apellidos").toString());
                            }
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db.collection("clientes")
                        .whereEqualTo("id",listaClientes.get(position))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Intent perfil = new Intent(getActivity(), MuestraClienteActivity.class);
                                    perfil.putExtra("email", listaClientes.get(position));
                                    startActivity(perfil);
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