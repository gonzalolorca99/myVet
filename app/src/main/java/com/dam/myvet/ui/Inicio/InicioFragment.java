package com.dam.myvet.ui.Inicio;

import android.os.Bundle;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dam.myvet.R;

public class InicioFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        Button btalertas = (Button) root.findViewById(R.id.btalertas);
        Button btturnos = (Button) root.findViewById(R.id.btturnos);
        Button btmascvet = (Button) root.findViewById(R.id.btmascotasvet);
        Button btclientes =(Button) root.findViewById(R.id.btclientes);
        btalertas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(root);
                nav.navigate(R.id.nav_alertas);
            }
        });

        btturnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navtur = Navigation.findNavController(root);
                navtur.navigate((R.id.nav_turnos));
            }
        });

        btmascvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navmascvet = Navigation.findNavController(root);
                navmascvet.navigate(R.id.nav_mascotas);
            }
        });

        btclientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navclientes = Navigation.findNavController(root);
                navclientes.navigate(R.id.nav_clientes);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}