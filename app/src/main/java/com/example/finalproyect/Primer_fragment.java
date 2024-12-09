package com.example.finalproyect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalproyect.registro.Registro_fragment_buscar;
import com.example.finalproyect.registro.Registro_fragment_lista;
import com.example.finalproyect.registro.Registro_fragment_registro;
import com.example.finalproyect.registro.Registro_fragment_salida;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Primer_fragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";
    private int userId; // Para almacenar el ID del usuario

    public static Primer_fragment newInstance(int userId) {
        Primer_fragment fragment = new Primer_fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId); // Guardar el ID en los argumentos
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID); // Recuperar el ID del usuario desde los argumentos
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_primer_fragment, container, false);

        Button btn_lista_ingreso = v.findViewById(R.id.btn_lista_ingreso);
        Button btn_registrar_ingreso = v.findViewById(R.id.btn_registrar_ingreso);
        btn_lista_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registro_fragment_lista miFragmento = Registro_fragment_lista.newInstance(userId); // Pasar el ID al nuevo fragmento
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame1, miFragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_registrar_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear instancia de RetrofitClient y WebService
                RetrofitClient retrofitClient = new RetrofitClient();
                Retrofit retrofit = retrofitClient.getRetrofit();
                WebService webService = retrofit.create(WebService.class);

                // Realizar la llamada para obtener el estado del usuario
                Call<Integer> call = webService.obtenerEstadoUsuario(userId);

                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            int estado = response.body();

                            if (estado == 2) {
                                Toast.makeText(getContext(), "Ya tienes un espacio reservado, no puedes entrar a esta opción", Toast.LENGTH_LONG).show();
                            } else {
                                // Permitir acceso a Registro_fragment_registro
                                Registro_fragment_registro miFragmento = Registro_fragment_registro.newInstance(userId);

                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame1, miFragmento);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        } else {
                            Toast.makeText(getContext(), "Error al obtener el estado del usuario", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getContext(), "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



        return v;
    }
}