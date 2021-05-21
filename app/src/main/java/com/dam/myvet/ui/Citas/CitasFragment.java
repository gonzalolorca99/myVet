package com.dam.myvet.ui.Citas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dam.myvet.ListaCitasActivity;
import com.dam.myvet.R;

import java.util.Calendar;

public class CitasFragment extends Fragment {
    EditText textoFecha;
    Button botonFecha;
    Button botonCitas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_citas, container, false);

        textoFecha = root.findViewById(R.id.Fecha);
        botonFecha = root.findViewById(R.id.btcalendario);
        botonCitas = root.findViewById(R.id.btcitas);

        botonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFecha();
            }
        });

        botonCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FECHAORIGEN",textoFecha.getText().toString());
                Intent listaCitas = new Intent(getActivity(), ListaCitasActivity.class);
                listaCitas.putExtra("fecha", textoFecha.getText().toString());
                startActivity(listaCitas);
            }
        });

        return root;
    }

    private void selectFecha(){
        Calendar calendar = Calendar.getInstance();

        int año = 2021;
        int mes = 4;
        int dia = 1;

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateString;
                if (dayOfMonth < 10){
                    dateString = "0"+dayOfMonth+"/"+(month+1)+"/"+year;
                }else if (month < 10){
                    dateString = dayOfMonth+"/"+"0"+(month+1)+"/"+year;
                }else if (dayOfMonth < 10 && month < 10){
                    dateString = "0"+dayOfMonth+"/"+"0"+(month+1)+"/"+year;
                }else{
                    dateString = dayOfMonth+"/"+(month+1)+"/"+year;
                }

                textoFecha.setText(dateString);
            }
        }, año, mes, dia);

        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}