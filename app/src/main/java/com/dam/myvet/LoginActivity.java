package com.dam.myvet;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private Button googleButton;
    private FirebaseAuth mAuth;
    private LinearLayout loginLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.register);
        loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
        googleButton = (Button) findViewById(R.id.googleButton);
        mAuth = FirebaseAuth.getInstance();

        //Registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {
                    mAuth.createUserWithEmailAndPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent registro = new Intent(LoginActivity.this, RegistroActivity.class);
                                        registro.putExtra("correo", usernameEditText.getText().toString());
                                        startActivity(registro);
                                    } else {
                                        alertaLogin();
                                    }
                                }
                            });
                }
            }
        });

        //Inicia sesión
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {
                    mAuth.signInWithEmailAndPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        showMenu();
                                    } else {
                                        alertaLogin();
                                    }
                                }
                            });
                }
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient googleClient = GoogleSignIn.getClient(LoginActivity.this, gso);
                googleClient.signOut();
                startActivityForResult(googleClient.getSignInIntent(), RC_SIGN_IN);
            }
        });
    }


    public void onStart() {
        super.onStart();
        loginLayout.setVisibility(View.VISIBLE);
    }

    public void alertaLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error autenticando al usuario");
        builder.setPositiveButton("Aceptar",null);
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void showMenu(){
        Intent intent = new Intent(this, MenuClienteActivity.class);
        intent.putExtra("email", usernameEditText.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                if (account != null){
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                    mAuth.signInWithCredential(credential).addOnCompleteListener(LoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        SharedPreferences prefs = getSharedPreferences(
                                                getString(R.string.prefs_file), Context.MODE_PRIVATE);
                                        String email = prefs.getString("email", null);

                                        if (email != null){
                                            loginLayout.setVisibility(View.INVISIBLE);
                                            showMenu();
                                        }
                                    } else {
                                        alertaLogin();
                                    }
                                }
                            });
                }
            } catch (ApiException e) {
                alertaLogin();
            }
        }
    }


}