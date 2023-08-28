package com.example.finalproyect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproyect.registro.Registro_fragment_buscar;
import com.example.finalproyect.registro.Registro_fragment_lista;
import com.example.finalproyect.registro.Registro_fragment_registro;
import com.example.finalproyect.registro.Registro_fragment_salida;

public class Primer_fragment extends Fragment {


    Button btn_registrar_ingreso,btn_lista_ingreso,btn_buscar_ingreso,btn_salida_ingreso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_primer_fragment, container, false);

        btn_registrar_ingreso = v.findViewById(R.id.btn_registrar_ingreso);
        btn_lista_ingreso =v.findViewById(R.id.btn_lista_ingreso);
        btn_salida_ingreso = v.findViewById(R.id.btn_salida_ingreso);

        btn_registrar_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registro_fragment_registro miFragmento = new Registro_fragment_registro();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame1, miFragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        btn_lista_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registro_fragment_lista miFragmento = new Registro_fragment_lista();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame1, miFragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_salida_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registro_fragment_salida miFragmento = new Registro_fragment_salida();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame1, miFragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return v;
    }
}