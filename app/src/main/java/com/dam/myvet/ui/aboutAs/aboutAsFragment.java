package com.dam.myvet.ui.aboutAs;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.dam.myvet.R;

public class aboutAsFragment extends Fragment {

    private aboutAsViewModel aboutAsViewModel;
    private Button btdatos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_aboutas, container, false);
        btdatos = (Button) root.findViewById(R.id.btprivdatos);
        btdatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext())
                        .setTitle("Información de privacidad")
                        .setMessage("MyVet no recopila ningún tipo de información de usuario ni de uso." + "\n" +
                                "\nSolamente se almacenan en Firebase los datos de autenticación e información relevante para el correcto funcionamiento de la app")

                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alert.show();

            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}