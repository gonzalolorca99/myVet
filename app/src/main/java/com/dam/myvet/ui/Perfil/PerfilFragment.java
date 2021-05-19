package com.dam.myvet.ui.Perfil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dam.myvet.MenuClienteActivity;
import com.dam.myvet.ModificaActivity;
import com.dam.myvet.R;
import com.dam.myvet.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PerfilFragment extends Fragment {

    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        final TextView nombre = root.findViewById(R.id.nombrePerfil);
        final TextView dni = root.findViewById(R.id.dniPerfil);
        final TextView email = root.findViewById(R.id.emailPerfil);
        final TextView telefono = root.findViewById(R.id.infotelefono);
        final TextView domicilio = root.findViewById(R.id.domicilioPerfil);
        final Button modificaBoton = root.findViewById(R.id.btModificaPerfil);
        SharedPreferences prefs = this.getActivity().getSharedPreferences(
                getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String emailSesion = prefs.getString("email", null);
        db = FirebaseFirestore.getInstance();

        db.collection("clientes")
                .whereEqualTo("email", emailSesion)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nombre.setText(document.getData().get("nombre").toString()+" "+
                                        document.getData().get("apellidos").toString());
                                dni.setText(document.getData().get("dni").toString());
                                email.setText(emailSesion);
                                telefono.setText(document.getData().get("telefono").toString());
                                domicilio.setText(document.getData().get("domicilio").toString());
                            }

                        }
                    }
                });

        modificaBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ModificaActivity.class);
                intent.putExtra("correo", emailSesion);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}