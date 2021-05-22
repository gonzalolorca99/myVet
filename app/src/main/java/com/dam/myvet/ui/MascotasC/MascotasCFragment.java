package com.dam.myvet.ui.MascotasC;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
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

import com.dam.myvet.ListaMascotasActivity;
import com.dam.myvet.MuestraMascotaClienteActivity;
import com.dam.myvet.R;
import com.dam.myvet.RegistroActivity;
import com.dam.myvet.RegistroMascotaActivity;

public class MascotasCFragment extends Fragment {

    private Button botonMostrar;
    private Button botonAnadir;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mascotasc, container, false);

        botonMostrar = (Button) root.findViewById(R.id.btlistarmasc);
        botonAnadir = (Button) root.findViewById(R.id.btanadirmasc);

        botonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent muestramasc = new Intent(getActivity(), ListaMascotasActivity.class);
                SharedPreferences prefs = getActivity().getSharedPreferences(
                        getString(R.string.prefs_file), Context.MODE_PRIVATE);
                String correo = prefs.getString("email", null);
                muestramasc.putExtra("dueño", correo);
                startActivity(muestramasc);
            }
        });

        botonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regmasc = new Intent(getActivity(), RegistroMascotaActivity.class);
                SharedPreferences prefs = getActivity().getSharedPreferences(
                        getString(R.string.prefs_file), Context.MODE_PRIVATE);
                String email = prefs.getString("email", null);
                regmasc.putExtra("correo", email);
                startActivity(regmasc);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}