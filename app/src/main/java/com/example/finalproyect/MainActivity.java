package com.example.finalproyect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    AnimatedBottomBar bottom_bar;
    FragmentManager fragmentManager;
    DrawerLayout drawerLayout1;
    Toolbar toolbar1;
    NavigationView navigationView1;

    // Agrega una variable para almacenar el ID del usuario
    private int userId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*--------LLAMAR A LOS ELEMENTOS ---------*/
        setContentView(R.layout.activity_main);
        drawerLayout1 = findViewById(R.id.drawer1);
        toolbar1 = findViewById(R.id.toolbar1);
        navigationView1 = findViewById(R.id.nv1);
        toolbar1.setTitle("SIMA PARKING");
        setSupportActionBar(toolbar1);

        // Obtener el ID del usuario desde los extras del Intent
        userId = getIntent().getIntExtra("USER_ID", -1); // Cambia "USER_ID" por el nombre correcto que usaste

        /*------ MENU HAMBURGUESA DEL DRAWER -------*/
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout1, toolbar1, 0, 0);
        drawerLayout1.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        /*---- FUNCIONES DEL DRAWER ------*/
        navigationView1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_integrantes:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new Fragment_Integrantes()).commit();
                        break;
                    case R.id.item_principal:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.item_cerrarsesion:
                        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent2);
                        finish(); // para cerrar la sesión
                        break;
                    case R.id.item_ubicacion:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new Fragment_Ubicacion()).commit();
                        break;
                    case R.id.item_yape:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new Fragment_Yape()).commit();
                }
                drawerLayout1.closeDrawers();
                return true;
            }
        });

        // Cargar el Primer Fragmento al iniciar la actividad y pasarle el ID del usuario
        if (savedInstanceState == null) {
            Primer_fragment primerFragment = Primer_fragment.newInstance(userId); // Crear una nueva instancia pasando el ID
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame1, primerFragment)
                    .commit();
        }
    }
}