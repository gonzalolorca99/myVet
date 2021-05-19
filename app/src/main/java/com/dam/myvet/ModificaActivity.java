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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ModificaActivity extends AppCompatActivity {
    private EditText nombreEditText;
    private EditText apellidosEditText;
    private EditText dniEditText;
    private EditText telefonoEditText;
    private EditText domicilioEditText;
    private Button registerButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica);

        db = FirebaseFirestore.getInstance();
        nombreEditText = (EditText) findViewById(R.id.Nombre);
        apellidosEditText = (EditText) findViewById(R.id.apellidos);
        dniEditText = (EditText) findViewById(R.id.dni);
        telefonoEditText = (EditText) findViewById(R.id.telefono);
        domicilioEditText = (EditText) findViewById(R.id.domicilio);
        registerButton = (Button) findViewById(R.id.btregistro);
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("correo");

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!nombreEditText.getText().toString().isEmpty() && !apellidosEditText.getText().toString().isEmpty() &&
                        !telefonoEditText.getText().toString().isEmpty() && !domicilioEditText.getText().toString().isEmpty()
                        && !dniEditText.getText().toString().isEmpty()) {
                    Cliente cliente = new Cliente(nombreEditText.getText().toString(),
                            apellidosEditText.getText().toString(), telefonoEditText.getText().toString(),
                            domicilioEditText.getText().toString(), email,
                            dniEditText.getText().toString());
                    Log.d("COMPRUEBAESPACIO","MIMUERTO");
                    db.collection("clientes")
                            .whereEqualTo("email", email)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            db.collection("clientes").document(email).set(cliente);
                                            Intent intent = new Intent(ModificaActivity.this, MenuClienteActivity.class);
                                            intent.putExtra("email", email);
                                            startActivity(intent);
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