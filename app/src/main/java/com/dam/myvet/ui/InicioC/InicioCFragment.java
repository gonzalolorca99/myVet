package com.dam.myvet.ui.InicioC;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dam.myvet.R;
import com.dam.myvet.ui.Perfil.PerfilFragment;

public class InicioCFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_inicioc, container, false);
        Button btperfil = (Button) root.findViewById(R.id.btperfil);
        Button btabous = (Button) root.findViewById(R.id.btabous);
        Button btmasc = (Button) root.findViewById(R.id.btmascotas);
        Button btcitas =(Button) root.findViewById(R.id.btcitas);
        btperfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(root);
                nav.navigate(R.id.nav_perfil);
            }
        });

        btabous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navab = Navigation.findNavController(root);
                navab.navigate((R.id.nav_aboutas));
            }
        });

        btmasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navmasc = Navigation.findNavController(root);
                navmasc.navigate(R.id.nav_mascotasC);
            }
        });

        btcitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navcitas = Navigation.findNavController(root);
                navcitas.navigate(R.id.nav_citasC);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}