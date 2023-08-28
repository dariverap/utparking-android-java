package com.example.finalproyect.tarifa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.databinding.FragmentTarifaRegistroBinding;
import com.shashank.sony.fancytoastlib.FancyToast;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Tarifa_fragment_registro extends Fragment {

    FragmentTarifaRegistroBinding binding;

    TarifaAdapter adaptador;

    ArrayList<Tarifa> listaTarifas = new ArrayList<>();

    Tarifa tarifa = new Tarifa();

    Boolean isEditando = false;

    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tarifa_fragment_registro() {

    }

    public static Tarifa_fragment_registro newInstance(String param1, String param2) {
        Tarifa_fragment_registro fragment = new Tarifa_fragment_registro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void agregarTarifa() {
        this.tarifa.setId(-1);
        this.tarifa.setDescripcion(binding.etDescripcion.getText().toString());
        this.tarifa.setCosto_hora(Double.valueOf(binding.etCosto.getText().toString()));
        Log.i("Etiqueta", (this.tarifa.toString()));

        Call<String> call = webService.agregarTarifa(this.tarifa);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(requireContext(),response.body(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                //Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show();

                limpiarCampos();
                limpiarObjeto();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al agregar tarifa !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR ADD", Toast.LENGTH_LONG).show();

            }
        });
    }


    public Boolean validarCampos() {
        return !(binding.etDescripcion.getText().toString().equals("") || binding.etCosto.getText().toString().equals(""));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTarifaRegistroBinding.inflate(inflater, container, false);

        // Obtener referencia al botón
        Button btnAddUpdate = binding.btnAddUpdate;

        // Agregar OnClickListener al botón
        btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()){
                    agregarTarifa();
                }else {
                    FancyToast.makeText(getContext(),"Porfavor llena todos los campos !",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    //Toast.makeText(getContext(), "Porfavor llena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }

    public void limpiarCampos(){
        binding.etDescripcion.setText("");
        binding.etCosto.setText("");
    }

    public void limpiarObjeto(){

        this.tarifa.setId(-1);
        this.tarifa.setDescripcion("");
        this.tarifa.setCosto_hora(-1);
    }

}