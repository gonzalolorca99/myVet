package com.dam.myvet.ui.CitasC;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dam.myvet.ListaCitasActivity;
import com.dam.myvet.ListaCitasCActivity;
import com.dam.myvet.ListaOcupActivity;
import com.dam.myvet.R;
import com.dam.myvet.RegistroCitaActivity;

public class CitasCFragment extends Fragment {

    Button botonver;
    Button botonpedir;
    Button botonhora;
    Button botonocup;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_citasc, container, false);

        botonver = (Button) root.findViewById(R.id.btvercitas);
        botonpedir = (Button) root.findViewById(R.id.btpedircita);
        botonhora = (Button) root.findViewById(R.id.bthorarios);
        botonocup = (Button) root.findViewById(R.id.btcitasoc);

        botonhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext())
                        .setTitle("Horarios de los veterinarios")
                        .setMessage("\nPablo Estepa Navas: " + "Lunes-Sábado " + "10:00-14:00"+ "\n"+
                                "\nGonzalo Estrada Castro: " + "Lunes-Sábado " + "17:00-21:00"+ "\n"+
                                "\nLas citas son cada 15min (00-15-30-45).\n")

                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alert.show();
            }
        });

        botonver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent listaCitas = new Intent(getActivity(), ListaCitasCActivity.class);
               startActivity(listaCitas);

            }
        });

        botonpedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pedirCita = new Intent(getActivity(), RegistroCitaActivity.class);
                startActivity(pedirCita);
            }
        });

        botonocup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaocup = new Intent(getActivity(), ListaOcupActivity.class);
                startActivity(listaocup);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}