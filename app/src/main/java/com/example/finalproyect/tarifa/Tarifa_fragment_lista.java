package com.example.finalproyect.tarifa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.databinding.FragmentRegistroListaBinding;
import com.example.finalproyect.databinding.FragmentTarifaListaBinding;
import com.example.finalproyect.registro.Registro;
import com.example.finalproyect.registro.RegistroAdapter;
import com.example.finalproyect.registro.RegistroResponse;
import com.example.finalproyect.registro.Registro_editar;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Tarifa_fragment_lista extends Fragment implements TarifaAdapter.OnItemClicked{

    FragmentTarifaListaBinding binding;

    TarifaAdapter adaptador;

    ArrayList<Tarifa> listaTarifas = new ArrayList<>();

    Tarifa tarifa = new Tarifa();

    Boolean isEditando = false;

    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Tarifa_fragment_lista() {

    }


    public static Tarifa_fragment_lista newInstance(String param1, String param2) {
        Tarifa_fragment_lista fragment = new Tarifa_fragment_lista();
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

    public void obtenerTarifas() {
        Call<TarifaResponse> call = webService.obtenerTarifas();
        call.enqueue(new Callback<TarifaResponse>() {
            @Override
            public void onResponse(Call<TarifaResponse> call, Response<TarifaResponse> response) {
                listaTarifas = response.body().listaTarifas;
                setupRecyclerView();
            }

            @Override
            public void onFailure(Call<TarifaResponse> call, Throwable t) {
                FancyToast.makeText(requireContext(),"ERROR AL CONSULTAR TODAS LAS TARIFAS !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR AL CONSULTAR TODAS LAS TARIFAS", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void setupRecyclerView() {
        adaptador = new TarifaAdapter(requireActivity(), listaTarifas);
        adaptador.setOnClick(this);
        binding.rvTarifas.setAdapter(adaptador);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTarifaListaBinding.inflate(inflater, container, false);
        binding.rvTarifas.setLayoutManager(new LinearLayoutManager(requireContext()));
        setupRecyclerView();
        obtenerTarifas();

        return binding.getRoot();
    }

    @Override
    public void editarTarifa(Tarifa tarifa) {
        Tarifa_editar fragment = new Tarifa_editar();

        // Configurar los argumentos del fragment con los datos del registro
        Bundle args = new Bundle();
        args.putInt("idTarifa", tarifa.getId());
        args.putString("descripcion", tarifa.getDescripcion());
        args.putDouble("costohora", tarifa.getCosto_hora());
        fragment.setArguments(args);

        // Abrir el nuevo fragment
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame1, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void borrarTarifa(int idTarifa) {
        Call<String> call = webService.borrarTarifa(idTarifa);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(requireContext(),response.body(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                //Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                obtenerTarifas();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al borrar la tarifa !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR DELETE", Toast.LENGTH_LONG).show();
            }
        });

    }
}