package com.example.finalproyect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproyect.tarifa.Tarifa_fragment_lista;
import com.example.finalproyect.tarifa.Tarifa_fragment_registro;

public class Tercer_fragment extends Fragment {

    Button btn_registrar_tarifa, btn_lista_tarifa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_tercer_fragment, container, false);

        btn_registrar_tarifa = v.findViewById(R.id.btn_registrar_tarifa);
        btn_lista_tarifa =v.findViewById(R.id.btn_lista_tarifa);

        btn_registrar_tarifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tarifa_fragment_registro miFragmento = new Tarifa_fragment_registro();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame1, miFragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        btn_lista_tarifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tarifa_fragment_lista miFragmento = new Tarifa_fragment_lista();
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