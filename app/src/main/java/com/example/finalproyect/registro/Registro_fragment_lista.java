package com.example.finalproyect.registro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.databinding.FragmentRegistroListaBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Registro_fragment_lista extends Fragment implements RegistroAdapter.OnItemClicked{


    FragmentRegistroListaBinding binding;

    RegistroAdapter adaptador;

    ArrayList<Registro> listaRegistros = new ArrayList<>();

    Registro registro = new Registro();

    Boolean isEditando = false;

    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Registro_fragment_lista() {

    }


    public static Registro_fragment_lista newInstance(String param1, String param2) {
        Registro_fragment_lista fragment = new Registro_fragment_lista();
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

    public void obtenerRegistros() {
        Call<RegistroResponse> call = webService.obtenerRegistros();
        call.enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
                listaRegistros = response.body().listaRegistros;

                setupRecyclerView();
            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {
                FancyToast.makeText(requireContext(),"ERROR AL CONSULTAR TODOS LOS REGISTROS !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR AL CONSULTAR TODOS LOS REGISTROS", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setupRecyclerView() {
        adaptador = new RegistroAdapter(requireActivity(), listaRegistros);
        adaptador.setOnClick(this);
        binding.rvRegistro.setAdapter(adaptador);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistroListaBinding.inflate(inflater, container, false);
        binding.rvRegistro.setLayoutManager(new LinearLayoutManager(requireContext()));
        setupRecyclerView();
        obtenerRegistros();

        return binding.getRoot();
    }


    @Override
    public void editarRegistro(Registro registro) {
        // Crear un nuevo fragment de formulario para editar el registro
        Registro_editar fragment = new Registro_editar();

        // Configurar los argumentos del fragment con los datos del registro
        Bundle args = new Bundle();
        args.putInt("idRegistro", registro.getId());
        args.putString("fechaIngreso", registro.getFecha_ingreso());
        args.putString("fechaSalida", registro.getFecha_salida());
        args.putInt("idEspacio", registro.getId_espacio());
        args.putString("patenteVehiculo", registro.getPatente_vehiculo());
        args.putInt("idUsuario", registro.getId_usuario());
        args.putInt("idTarifa", registro.getId_tarifa());
        args.putDouble("costoTotal", registro.getCosto_total());
        fragment.setArguments(args);

        // Abrir el nuevo fragment
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame1, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void borrarRegistro(int idRegistro) {
        Call<String> call = webService.borrarRegistro(idRegistro);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(requireContext(),response.body(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                //Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                obtenerRegistros();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al borrar registro !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR DELETE", Toast.LENGTH_LONG).show();
            }
        });
    }
}