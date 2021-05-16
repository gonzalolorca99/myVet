package com.dam.myvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegistroActivity extends AppCompatActivity {
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

                if (!nombreEditText.getText().toString().isEmpty() && !apellidosEditText.getText().toString().isEmpty() &&
                        !telefonoEditText.getText().toString().isEmpty() && !domicilioEditText.getText().toString().isEmpty()) {
                    Cliente cliente = new Cliente(nombreEditText.getText().toString(),
                            apellidosEditText.getText().toString(),telefonoEditText.getText().toString(),
                            domicilioEditText.getText().toString(), email,
                            dniEditText.getText().toString());
                    db.collection("clientes").document(email).set(cliente);
                    Intent intent = new Intent(RegistroActivity.this, MenuClienteActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });
    }
}