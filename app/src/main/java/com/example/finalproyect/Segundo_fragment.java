package com.example.finalproyect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproyect.registro.Registro_fragment_lista;
import com.example.finalproyect.registro.Registro_fragment_registro;
import com.example.finalproyect.usuario.Usuario_fragment_lista;
import com.example.finalproyect.usuario.Usuario_fragment_registro;

public class Segundo_fragment extends Fragment {

    Button btn_registrar_usuario, btn_lista_usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_segundo_fragment, container, false);

        btn_registrar_usuario = v.findViewById(R.id.btn_registrar_usuario);
        btn_lista_usuario =v.findViewById(R.id.btn_lista_usuario);

        btn_registrar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario_fragment_registro miFragmento = new Usuario_fragment_registro();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame1, miFragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        btn_lista_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario_fragment_lista miFragmento = new Usuario_fragment_lista();
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