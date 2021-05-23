package com.dam.myvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RegistroActivity extends AppCompatActivity {
    private EditText nombreEditText;
    private EditText apellidosEditText;
    private EditText dniEditText;
    private EditText telefonoEditText;
    private EditText domicilioEditText;
    private Button registerButton;
    private FirebaseFirestore db;
    private int contadorDNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = FirebaseFirestore.getInstance();
        nombreEditText = (EditText) findViewById(R.id.Nombre);
        apellidosEditText = (EditText) findViewById(R.id.apellidos);
        dniEditText = (EditText) findViewById(R.id.dni);
        telefonoEditText = (EditText) findViewById(R.id.telefono);
        domicilioEditText = (EditText) findViewById(R.id.domicilio);
        registerButton = (Button) findViewById(R.id.btregistro);
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("correo");
        Log.d("Estamos en registro:", email);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                contadorDNI = 0;

                if (!nombreEditText.getText().toString().isEmpty() && !apellidosEditText.getText().toString().isEmpty() &&
                        !telefonoEditText.getText().toString().isEmpty() && !domicilioEditText.getText().toString().isEmpty()
                        && !dniEditText.getText().toString().isEmpty()) {
                    Cliente cliente = new Cliente(nombreEditText.getText().toString(),
                            apellidosEditText.getText().toString(), telefonoEditText.getText().toString(),
                            domicilioEditText.getText().toString(), email,
                            dniEditText.getText().toString());
                    db.collection("clientes")
                            .whereEqualTo("dni", dniEditText.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("CompruebaRegistro", document.getId() + " => " + document.getData());
                                            if (document.getData().get("dni").toString().equals(dniEditText.getText().toString())) {
                                                contadorDNI++;
                                            }
                                        }
                                        if (contadorDNI == 0){
                                            Log.d("INICIOSESION","Usuario registrado y paso a menú");
                                            db.collection("clientes").document(email).set(cliente);
                                            Intent intent = new Intent(RegistroActivity.this, MenuClienteActivity.class);
                                            intent.putExtra("email", email);
                                            startActivity(intent);
                                        }
                                        else{
                                            alertaRegistro();
                                        }
                                    }
                                }
                            });
                }
                else{
                    alertaRegistro();
                }
            }
        });
    }

    public void alertaRegistro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error registrando al usuario");
        builder.setPositiveButton("Aceptar", null);
        Dialog dialog = builder.create();
        dialog.show();
    }
}