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




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*--------LLAMAR A LOS ELEMENTOS ---------*/
        setContentView(R.layout.activity_main);
        bottom_bar = findViewById(R.id.bottom_bar);
        drawerLayout1 = findViewById(R.id.drawer1);
        toolbar1 = findViewById(R.id.toolbar1);
        navigationView1 = findViewById(R.id.nv1);
        toolbar1.setTitle("SIMA PARKING");
        setSupportActionBar(toolbar1);






        /*------ MENU HAMBURGUESA DEL DRAWER -------*/
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout1,toolbar1,0,0);
        drawerLayout1.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        /*---- FUNCIONES DEL DRAWER ------*/
        navigationView1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_integrantes:getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new Fragment_Integrantes()).commit();
                    break;
                    case R.id.item_principal:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                        break;
                    case R.id.item_cerrarsesion:
                        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent2);
                        finish(); // para cerrar la sesión"
                        break;
                    case R.id.item_ubicacion:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new Fragment_Ubicacion()).commit();
                        break;
                    case R.id.item_yape:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new Fragment_Yape()).commit();
                }
                drawerLayout1.closeDrawers();
                return true;
            }
        });

        /*------------*/





        /*----------FUNCIONES DEL BOTTOM BAR ANIMATED -------------------*/
        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch(newTab.getId()){
                    case R.id.btn_bar_1:
                        fragment = new Primer_fragment();
                        break;
                    case R.id.btn_bar_2:
                        fragment = new Segundo_fragment();
                        break;
                    case R.id.btn_bar_3:
                        fragment = new Tercer_fragment();
                        break;
                }
                if (fragment!=null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame1, fragment).commit();
                }else{
                    Log.e(TAG,"Error al crear fragment");
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab newTab) {

            }
        });
    }

}