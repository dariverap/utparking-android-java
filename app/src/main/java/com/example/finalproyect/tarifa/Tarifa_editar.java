package com.example.finalproyect.tarifa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.databinding.FragmentRegistroEditarBinding;
import com.example.finalproyect.databinding.FragmentTarifaEditarBinding;
import com.example.finalproyect.databinding.FragmentTarifaListaBinding;
import com.example.finalproyect.registro.Registro_fragment_lista;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Tarifa_editar extends Fragment {


    FragmentTarifaEditarBinding binding;

    TarifaAdapter adaptador;

    Tarifa tarifa = new Tarifa();

    EditText etIdTarifa, etDescripcion, etCosto;

    Button btnActualizar;
    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Tarifa_editar() {

    }


    public static Tarifa_editar newInstance(String param1, String param2) {
        Tarifa_editar fragment = new Tarifa_editar();
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
            tarifa.setId(getArguments().getInt("idTarifa"));
            tarifa.setDescripcion(getArguments().getString("descripcion"));
            tarifa.setCosto_hora(getArguments().getDouble("costohora"));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTarifaEditarBinding.inflate(inflater, container, false);
        etDescripcion=binding.etDescripcion;
        etCosto=binding.etCosto;
        btnActualizar = binding.btnActualizar;

        etDescripcion.setText(tarifa.getDescripcion());
        etCosto.setText(String.valueOf(tarifa.getCosto_hora()));



        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarTarifa();
            }
        });

        return binding.getRoot();
    }

    public void actualizarTarifa(){

        this.tarifa.setDescripcion(binding.etDescripcion.getText().toString());
        this.tarifa.setCosto_hora(Double.valueOf(binding.etCosto.getText().toString()));

        Call<String> call = webService.actualizarTarifa(this.tarifa.getId(),this.tarifa);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(requireContext(),response.body(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                //Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                irListaTarifas();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al actualizar tarifa !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR ADD", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void irListaTarifas(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1, new Tarifa_fragment_lista());
        fragmentTransaction.commit();
    }

}