package com.dam.myvet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.myvet.ui.MascotasC.MascotasCFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RegistroMascotaActivity extends AppCompatActivity {

    private EditText nombreMasc;
    private EditText razaMasc;
    private EditText edadMasc;
    private Button registerButton;
    private FirebaseFirestore db;
    private int contadorMasc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_mascota);

        db = FirebaseFirestore.getInstance();
        nombreMasc = (EditText) findViewById(R.id.NombreMasc);
        razaMasc = (EditText) findViewById(R.id.razaMasc);
        edadMasc = (EditText) findViewById(R.id.edadMasc);
        registerButton = (Button) findViewById(R.id.btregistromasc);
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("correo");
        Log.d("Registro mascota:", email);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contadorMasc = 0;
                if (!nombreMasc.getText().toString().isEmpty() && !razaMasc.getText().toString().isEmpty() &&
                        !edadMasc.getText().toString().isEmpty()){
                    Mascota mascota = new Mascota(nombreMasc.getText().toString(), razaMasc.getText().toString(), edadMasc.getText().toString(), email, "");
                    Log.d("Mascota", "Nueva Mascota" + nombreMasc.getText().toString());
                    db.collection("mascotas")
                            .whereEqualTo("nombre", nombreMasc.getText().toString())
                            .whereEqualTo("raza", razaMasc.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("DESPUESTASK","MIMUERTO");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("CompruebaMasc", document.getId() + " => " + document.getData());
                                            contadorMasc++;
                                        }
                                        if (contadorMasc == 0){
                                            Log.d("REGISTRADA","Mascota registrada con éxito");
                                            db.collection("mascotas").document("Masc "+nombreMasc.getText().toString()+" ("+email+")").set(mascota);
                                            Intent intent = new Intent(RegistroMascotaActivity.this, MenuClienteActivity.class);
                                            intent.putExtra("email", email);
                                            startActivity(intent);
                                        }
                                        else{
                                            alertaRegistroMasc();
                                        }
                                    }
                                }
                            });

                }
            }
        });
    }

    public void alertaRegistroMasc() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error registrando la mascota");
        builder.setPositiveButton("Aceptar", null);
        Dialog dialog = builder.create();
        dialog.show();
    }
}
