package com.dam.myvet;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistroCitaActivity extends AppCompatActivity {
    private EditText asunto;
    private Spinner mascotas;
    private Button botonFecha;
    private Button botonCitas;
    private TextView fec;
    private TextView hora;
    private int hor, min;
    ArrayList<String> listaMascotas;
    private String masceleg;
    private Date inputdate;
    private String dateString;
    private String formatfecha;
    private String formhora;
    private String formcita;
    private String fdia;
    private int cont;
    private String hist;

    private FirebaseFirestore db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_citac);

        asunto = (EditText) findViewById(R.id.Asunto);
        mascotas = (Spinner) findViewById(R.id.spinmasc);
        botonFecha = (Button) findViewById(R.id.btcalendarioc);
        botonCitas = (Button) findViewById(R.id.btpedir);
        fec = (TextView) findViewById(R.id.fechaeleg);
        hora = (TextView) findViewById(R.id.horaeleg);
        listaMascotas = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                listaMascotas
        );

        botonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFecha();
            }
        });



        mascotas.setAdapter(adapter);
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        db.collection("mascotas")
                .whereEqualTo("dueño", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                adapter.add(document.getData().get("nombre").toString());
                            }
                        }
                    }
                });

        mascotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                masceleg = (String) mascotas.getAdapter().getItem(position);
                Log.d("Masceleg", masceleg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botonCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!asunto.getText().toString().isEmpty() && !formatfecha.isEmpty() && !masceleg.isEmpty() && !email.isEmpty()){
                    Cita cita = new Cita(asunto.getText().toString(), email, masceleg, formatfecha);
                    Log.d("Cita creada:", formatfecha);
                    db.collection("citas")
                            .whereEqualTo("fecha", formatfecha)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    cont = 0;
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            cont++;
                                        }
                                        if(cont == 0){
                                            db.collection("citas").document(formcita).set(cita);
                                            db.collection("mascotas")
                                                    .whereEqualTo("nombre", masceleg)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                    Log.d("CompruebaMasc", document.getId() + " => " + document.getData());
                                                                    hist = document.get("historial").toString();
                                                                    if(hist.equals("")){
                                                                        db.collection("mascotas").document("Masc "+ masceleg+" ("+email+")")
                                                                                .update("historial", asunto.getText().toString());
                                                                    }
                                                                    else{
                                                                        db.collection("mascotas").document("Masc "+ masceleg+" ("+email+")")
                                                                                .update("historial", hist+", "+asunto.getText().toString());
                                                                    }
                                                                }

                                                            }
                                                        }
                                                    });

                                            Toast.makeText(RegistroCitaActivity.this, "Cita creada con éxito" , Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            alertaFecha();
                                        }
                                    }
                                }
                            });
                }
            }
        });




    }
    private void selectFecha(){
        Calendar calendar = Calendar.getInstance();

        int año = 2021;
        int mes = 4;
        int dia = 1;

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                if (dayOfMonth < 10){
                    dateString = "0"+dayOfMonth+"/"+(month+1)+"/"+year;
                    fdia = dayOfMonth+"-"+(month+1)+"-"+year;
                }else if (month < 10){
                    dateString = dayOfMonth+"/"+"0"+(month+1)+"/"+year;
                    fdia = dayOfMonth+"-"+(month+1)+"-"+year;
                }else if (dayOfMonth < 10 && month < 10){
                    dateString = "0"+dayOfMonth+"/"+"0"+(month+1)+"/"+year;
                    fdia = dayOfMonth+"-"+(month+1)+"-"+year;
                }else{
                    dateString = dayOfMonth+"/"+(month+1)+"/"+year;
                    fdia = dayOfMonth+"-"+(month+1)+"-"+year;
                }

                try {
                    inputdate = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                calendar.setTime(inputdate);
                if(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase().equals("SUNDAY")){
                    alertaFecha();
                }
                else {
                    fec.setText("Día elegido: " + dateString);
                    selectHora();
                }
            }


        }, año, mes, dia);

        datePickerDialog.show();
    }

    private void selectHora(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hor = hourOfDay;
                min = minute;

                if((hor>=10 && hor<=14) || (hor>=17 && hor<=21)){
                    if(min % 15 == 0){
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.set(0,0,0,hor,min);
                        hora.setText("Hora elegida: "+DateFormat.format("hh:mm aa", calendar2));
                        if(min == 0){
                           formhora = hor +":"+"00";
                        }
                        else{
                            formhora = hor +":"+min;
                        }

                        formatfecha = dateString + " " + formhora;
                        formcita = fdia + " " + formhora;

                        db.collection("citas")
                                .whereEqualTo("fecha", formatfecha)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        cont = 0;
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                cont++;
                                            }
                                            if(cont !=0){
                                                alertaFecha();
                                            }
                                        }
                                    }
                                });


                    }
                    else{
                        alertaHora();
                    }

                }
                else{
                    alertaHora();
                }




            }
        }, 12, 0, false);

        timePickerDialog.updateTime(hor, min);

        timePickerDialog.show();
    }

    public void alertaFecha() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error de fecha");
        builder.setMessage("La fecha seleccionada no está disponible");
        builder.setPositiveButton("Aceptar", null);
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void alertaHora() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error de hora");
        builder.setMessage("La hora seleccionada no está disponible");
        builder.setPositiveButton("Aceptar", null);
        Dialog dialog = builder.create();
        dialog.show();
    }
}
