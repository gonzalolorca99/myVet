package com.dam.myvet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MenuVetActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    SharedPreferences.Editor prefs;
    Bundle bundle;
    private String email;
    private TextView nombre;
    private TextView correo;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_vet);

        bundle = getIntent().getExtras();
        email = bundle.getString("email");

        // Guardado de datos
        prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
        prefs.putString("email",email);
        prefs.apply();

        Toolbar toolbar = findViewById(R.id.toolbar_vet);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.menu_vet_layout);
        NavigationView navigationView = findViewById(R.id.nav_vet_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_alertas, R.id.nav_turnos,
                R.id.nav_mascotas, R.id.nav_clientes, R.id.nav_cerrarsesion)
                .setDrawerLayout(drawer)
                .build();

        View headerView = navigationView.getHeaderView(0);
        nombre = (TextView) headerView.findViewById(R.id.nav_header_nombre_vet);
        correo = (TextView) headerView.findViewById(R.id.correoVet);

        db = FirebaseFirestore.getInstance();
        db.collection("veterinarios")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nombre.setText(document.getData().get("nombre").toString()+" "+document.getData().get("apellidos").toString());
                                correo.setText(email);
                            }
                        }
                    }
                });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_vet);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_vet);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onGroupItemClick(MenuItem item) {
        //Cierre de cesión
        prefs.clear();
        prefs.apply();
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}